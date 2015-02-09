package com.cognizant.telstra;

import java.util.List;
import java.util.Map;

import com.cognizant.http.HttpConstant;
import com.cognizant.http.Request;
import com.cognizant.http.ServiceBean;
import com.cognizant.http.ServiceCallAsyncTask;
import com.cognizant.http.listeners.ResponseEventListener;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
		swipeRefreshLayout.setOnRefreshListener(this);
		
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		//Prepared request for get 
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
						if(dialog != null && dialog.isShowing())
						{
							dialog.dismiss();
						}
						
						Facts facts = (Facts) data;
						if(facts != null)
						{
							if(facts.factsTitle != null)
							{
								getSupportActionBar().setTitle(facts.factsTitle);
							}
							recyclerView.setAdapter(new RecycleAdaptor(facts.getFactsList(),ListActivity.this));
						}
					}
				});
				
			}
			
			@Override
			public void onPreExecute()
			{
				dialog = ProgressDialog.show(ListActivity.this, "Loading", "Loading");
			}
			
			@Override
			public void onException(Throwable e)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						if(dialog != null && dialog.isShowing())
						{
							dialog.dismiss();
						}
					}
				});
				Log.e(LOG_TAG, "Exception "+e.getMessage(),e);
			}
			
			@Override
			public void onError(Object data, int httpStatus, Map<String, List<String>> responseHeader)
			{
				
			}
		}, new Facts(), null).build();
		
		
		ServiceCallAsyncTask asyncTask = new ServiceCallAsyncTask(this,serviceBean);
		asyncTask.execute();
	}

	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

}
