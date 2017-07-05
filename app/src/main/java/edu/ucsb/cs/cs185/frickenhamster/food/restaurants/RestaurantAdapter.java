package edu.ucsb.cs.cs185.frickenhamster.food.restaurants;

import android.content.*;
import android.net.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.squareup.picasso.*;

import edu.ucsb.cs.cs185.frickenhamster.food.*;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/5/2015
 * Time: 4:22 PM
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>
{

	private Food food;

	public class RestaurantViewHolder extends RecyclerView.ViewHolder
	{
		CardView cardView;
		ImageView imageView;
		TextView textView;

		public RestaurantViewHolder(View itemView)
		{
			super(itemView);

			cardView = (CardView) itemView.findViewById(R.id.restaurant_card_view);
			imageView = (ImageView) itemView.findViewById(R.id.restaurant_image_view);
			textView = (TextView) itemView.findViewById(R.id.restaurant_text_view);

		}
	}


	private ArrayList<Restaurant> restaurantViewItems;
	private Context context;

	public RestaurantAdapter(Context context)
	{
		this.context = context;

        /*restaurantViewItems = new ArrayList<RestaurantViewItem>();
		restaurantViewItems.add(new RestaurantViewItem("Look up on Yelp", "http://www.cdkglobaldigitalmarketing.com/wp-content/uploads/2013/04/Yelp_Estimate_tool.jpg", "", ResType.YELP));
        restaurantViewItems.add(new RestaurantViewItem("The Krusty Krab", "http://img1.wikia.nocookie.net/__cb20121221034123/spongebob/images/7/76/Krusty_krab_high_quality.jpg", "http://www.yelp.com/biz/the-krusty-krab-portland", ResType.REST));*/
        FoodApplication app = (FoodApplication) context.getApplicationContext();
		FoodManager foodManager = app.getFoodManager();
		food = foodManager.getSelectedFood();
		restaurantViewItems = food.getRestaurants();
	}

	@Override
	public int getItemCount()
	{
		return restaurantViewItems.size();
	}

	@Override
	public void onBindViewHolder(RestaurantViewHolder holder, int position)
	{
		final Restaurant item = restaurantViewItems.get(position);
		final String clickLink = item.getLink();

		holder.textView.setText(item.getName());
		Picasso.with(context).load(item.getImage()).placeholder(android.R.drawable.spinner_background).error(android.R.drawable.ic_menu_delete).into(holder.imageView);
		if (item.type == Restaurant.ResType.YELP)
		{
			holder.cardView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.yelp.com/search?find_desc=" + food.getName()));

					Calendar orderDate = new GregorianCalendar();
					String orderDateString = formatDate(orderDate);
					saveOrder(food.getName(), "Yelp", orderDateString);
					context.startActivity(intent);
				}
			});
		} else
		{
			holder.cardView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(clickLink));

					Calendar orderDate = new GregorianCalendar();
					String orderDateString = formatDate(orderDate);
					
					saveOrder(food.getName(), item.getName(), orderDateString);
					context.startActivity(intent);
				}
			});
		}
	}

	@Override
	public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
		RestaurantViewHolder vh = new RestaurantViewHolder(v);
		return vh;
	}

	void saveOrder(String type, String restaurantName, String date)
	{
		boolean exists = false;
		for (String file : context.fileList())
		{
			if (file == "history.txt")
				exists = true;
		}
		if (exists)
		{
			PrintWriter writer = null;
			try
			{
				writer = new PrintWriter(new BufferedOutputStream(context.openFileOutput("history.txt", context.MODE_APPEND)));
				//writer = new PrintWriter(new FileWriter("history.txt", true));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			if (writer != null)
			{
				String orderString = type + "---" + restaurantName + "---" + date;
				writer.println(orderString);
				Toast.makeText(context, "Order " + type + " at " + restaurantName + " saved", Toast.LENGTH_SHORT).show();
				writer.close();
			} else
				Toast.makeText(context, "writer was null", Toast.LENGTH_SHORT).show();
		} else
		{
			PrintWriter writer = null;
			new File("history.txt");
			try
			{
				writer = new PrintWriter(new BufferedOutputStream(context.openFileOutput("history.txt", context.MODE_APPEND)));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			if (writer != null)
			{
				String orderString = type + "---" + restaurantName + "---" + date;
				writer.println(orderString);
				Toast.makeText(context, "Order " + type + " at " + restaurantName + " saved", Toast.LENGTH_SHORT).show();
				writer.close();
			} else
				Toast.makeText(context, "writer was null", Toast.LENGTH_SHORT).show();
		}
	}

	String formatDate(Calendar date)
	{
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH);
		int year = date.get(Calendar.YEAR);
		String w = "Monday";
		String d = Integer.toString(day);
		String m = "January";
		String y = Integer.toString(year);
		switch (dayOfWeek)
		{
			case 1:
				w = "Sunday";
				break;
			case 2:
				w = "Monday";
				break;
			case 3:
				w = "Tuesday";
				break;
			case 4:
				w = "Wednesday";
				break;
			case 5:
				w = "Thursday";
				break;
			case 6:
				w = "Friday";
				break;
			case 7:
				w = "Saturday";
				break;
		}
		switch (month)
		{
			case 0:
				m = "January";
				break;
			case 1:
				m = "February";
				break;
			case 2:
				m = "March";
				break;
			case 3:
				m = "April";
				break;
			case 4:
				m = "May";
				break;
			case 5:
				m = "June";
				break;
			case 6:
				m = "July";
				break;
			case 7:
				m = "August";
				break;
			case 8:
				m = "September";
				break;
			case 9:
				m = "October";
				break;
			case 10:
				m = "November";
				break;
			case 11:
				m = "December";
				break;
		}

		return w + ", " + m + " " + d + ", " + y;
	}
}
