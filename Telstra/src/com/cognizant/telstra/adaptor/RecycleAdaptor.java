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
	
	
	public RecycleAdaptor(List<FactsList> listOfFacts, Context context)
	{
		this.context = context;
		this.listOfFacts = listOfFacts;
		imageLoader = new ImageLoader(context);
	}
	
	@Override
	public int getItemCount()
	{
		return listOfFacts.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		FactsList factsList = listOfFacts.get(position);
		holder.titleTV.setText(factsList.getTitle());
		holder.descriptionTV.setText(factsList.getDescription());
		imageLoader.DisplayImage(factsList.imageUrl, (Activity)context, holder.logoIV);
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
