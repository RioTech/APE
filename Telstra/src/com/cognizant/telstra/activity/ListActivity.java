package com.cognizant.telstra.activity;

import java.util.List;
import java.util.Map;

import com.cognizant.http.HttpConstant;
import com.cognizant.http.Request;
import com.cognizant.http.ServiceBean;
import com.cognizant.http.ServiceCallAsyncTask;
import com.cognizant.http.listeners.ResponseEventListener;
import com.cognizant.telstra.R;
import com.cognizant.telstra.adaptor.RecycleAdaptor;
import com.cognizant.telstra.bean.Facts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * 
 * @author Ravi Bhojani
 *
 */
public class ListActivity extends ActionBarActivity implements OnRefreshListener{

	private RecyclerView recyclerView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private final String DOWNLOAD_URL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
	private ProgressDialog dialog;
	private final String LOG_TAG = ListActivity.class.getSimpleName();
	private RecycleAdaptor recycleAdaptor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
		swipeRefreshLayout.setOnRefreshListener(this);
		
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recycleAdaptor = new RecycleAdaptor(ListActivity.this);
		recyclerView.setAdapter(recycleAdaptor);
		
		//Load the data from the server.
		loadDataFromNetwork();
	}

	
	@Override
	public void onRefresh() {
		
		//Refresh the data.
		loadDataFromNetwork();
	}
	
	/**
	 * This method will prepare request, send to server and update the listview
	 */
	public void loadDataFromNetwork()
	{
		//Prepared request for get call
		Request request = new Request.RequestBuilder(DOWNLOAD_URL)
									.httpMethod(HttpConstant.GET_METHOD)
									.build();
		
		ServiceBean serviceBean = new ServiceBean.ServiceBeanBuilder(request, new ResponseEventListener()
		{
			@Override
			public void onSuccess(final Object data, Map<String, List<String>> responseHeader)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						//If user has swipe for refresh then stop showing refresh view else dismiss loading bar
						if(swipeRefreshLayout.isRefreshing())
						{
							swipeRefreshLayout.setRefreshing(false);
						}
						else
						{
							if(dialog != null && dialog.isShowing())
							{
								dialog.dismiss();
							}
						}
						
						Facts facts = (Facts) data;
						if(facts != null)
						{
							if(facts.factsTitle != null)
							{
								//Set title to action bar
								getSupportActionBar().setTitle(facts.factsTitle);
							}
							if(facts.factsList != null)
							{
								recycleAdaptor.addAll(facts.factsList);
								recycleAdaptor.notifyDataSetChanged();
							}
						}
					}
				});
				
			}
			
			@Override
			public void onPreExecute()
			{
				if(!swipeRefreshLayout.isRefreshing())
				{
					dialog = ProgressDialog.show(ListActivity.this, "APE", "Loading");
				}
				
			}
			
			@Override
			public void onException(Throwable e)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						//If user has swipe for refresh then stop showing refresh view else dismiss the loading bar
						if(swipeRefreshLayout.isRefreshing())
						{
							swipeRefreshLayout.setRefreshing(false);
						}
						else
						{
							if(dialog != null && dialog.isShowing())
							{
								dialog.dismiss();
							}
						}
					}
				});
				Log.e(LOG_TAG, "Exception "+e.getMessage(),e);
			}
			
			@Override
			public void onError(Object data, int httpStatus, Map<String, List<String>> responseHeader)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						//If user has swipe for refresh then stop showing refresh view else dismiss the loading bar
						if(swipeRefreshLayout.isRefreshing())
						{
							swipeRefreshLayout.setRefreshing(false);
						}
						else
						{
							if(dialog != null && dialog.isShowing())
							{
								dialog.dismiss();
							}
						}
					}
				});
				Log.e(LOG_TAG, "Error from server"+httpStatus);
			}
		}, new Facts(), null).build();
		
		//Service call to fetch the data
		ServiceCallAsyncTask asyncTask = new ServiceCallAsyncTask(this,serviceBean);
		asyncTask.execute();
	}

}
