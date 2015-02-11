/**
 * 
 */
package com.cognizant.telstra.adaptor;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cognizant.telstra.R;
import com.cognizant.telstra.bean.Facts.FactsList;
import com.cognizant.telstra.util.ImageLoader;

/**
 * @author Ravi Bhojani
 * 
 */
public class RecycleAdaptor extends RecyclerView.Adapter<RecycleAdaptor.ViewHolder>
{

	private List<FactsList> listOfFacts;
	private ImageLoader imageLoader;
	private Context context;
	
	
	public RecycleAdaptor(Context context)
	{
		this.context = context;
		imageLoader = new ImageLoader(context);
	}
	
	public void addAll(List<FactsList> listOfFacts)
	{
		this.listOfFacts = listOfFacts;
	}
	
	@Override
	public int getItemCount()
	{
		//If list is null then return zero row else return size
		return listOfFacts != null ? listOfFacts.size() : 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		FactsList factsList = listOfFacts.get(position);
		if(factsList.title != null)
		{
			holder.titleTV.setText(factsList.title);
		}
		if(factsList.description != null)
		{
			holder.descriptionTV.setText(factsList.description);
		}
		if(factsList.imageUrl != null)
		{
			imageLoader.DisplayImage(factsList.imageUrl, (Activity)context, holder.logoIV);
		}
		else
		{
			holder.logoIV.setImageBitmap(null);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
		return new ViewHolder(v);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public ImageView logoIV;
		public TextView titleTV, descriptionTV;

		public ViewHolder(View view)
		{
			super(view);
			descriptionTV = (TextView) view.findViewById(R.id.descriptionTV);
			titleTV = (TextView) view.findViewById(R.id.titleTV);
			logoIV = (ImageView) view.findViewById(R.id.imageView);
		}
	}
}
