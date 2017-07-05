package edu.ucsb.cs.cs185.frickenhamster.food.pref;

import android.content.*;
import android.support.v7.widget.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import edu.ucsb.cs.cs185.frickenhamster.food.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/7/2015
 * Time: 11:20 PM
 */
public class PrefAdapter extends RecyclerView.Adapter<PrefAdapter.PrefViewHolder>
{
	
	
	public class PrefViewHolder extends RecyclerView.ViewHolder
	{
		RelativeLayout view;
		TextView textView;
		CheckBox checkBox;
		public PrefViewHolder(View itemView)
		{
			super(itemView);
			view = (RelativeLayout) itemView.findViewById(R.id.pref_view);
			textView = (TextView) itemView.findViewById(R.id.pref_name);
			checkBox = (CheckBox) itemView.findViewById(R.id.pref_checkbox);
			
		}
	}
	
	private ArrayList<Cuisine> cuisines;
	
	private FoodManager foodManager;

	public PrefAdapter(ArrayList<Cuisine> cuisines)
	{
		this.cuisines = cuisines;
	}

	public PrefAdapter(ArrayList<Cuisine> cuisines, FoodManager foodManager)
	{
		this.cuisines = cuisines;
		this.foodManager = foodManager;
	}

	@Override
	public PrefViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_item, parent, false);
		PrefViewHolder vh = new PrefViewHolder(v);
		
		
		return vh;
	}

	@Override
	public void onBindViewHolder(final PrefViewHolder holder, int position)
	{
		final Cuisine cuisine = cuisines.get(position);
		holder.textView.setText(cuisine.getName());
		if (cuisine.isAllowed())
		{
			holder.checkBox.setChecked(true);
		}
		
		
		holder.view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				 
				
				if (cuisine.isAllowed())
				{
					
					//cuisine.setAllowed(false);
					foodManager.disallowCuisine(cuisine.getName());
					holder.checkBox.setChecked(false);
					
				}
				else
				{
//					cuisine.setAllowed(true);
					foodManager.allowCuisine(cuisine.getName());
					holder.checkBox.setChecked(true);
				}
				notifyDataSetChanged();
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return cuisines.size();
	}
}
