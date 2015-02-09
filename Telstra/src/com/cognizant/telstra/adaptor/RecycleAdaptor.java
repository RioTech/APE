/**
 * 
 */
package com.cognizant.telstra.adaptor;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cognizant.telstra.R;
import com.cognizant.telstra.bean.Facts.FactsList;

/**
 * @author Ravi Bhojani
 * 
 */
public class RecycleAdaptor extends RecyclerView.Adapter<RecycleAdaptor.ViewHolder>
{

	private List<FactsList> listOfFacts;
	
	
	public RecycleAdaptor(List<FactsList> listOfFacts)
	{
		this.listOfFacts = listOfFacts;
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
		holder.title.setText(factsList.getTitle());
		holder.description.setText(factsList.getDescription());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
		return new ViewHolder(v);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public ImageView image;
		public TextView title, description;

		public ViewHolder(View view)
		{
			super(view);
			description = (TextView) view.findViewById(R.id.descriptionTV);
			title = (TextView) view.findViewById(R.id.titleTV);
			image = (ImageView) view.findViewById(R.id.imageView);
		}
	}
}
