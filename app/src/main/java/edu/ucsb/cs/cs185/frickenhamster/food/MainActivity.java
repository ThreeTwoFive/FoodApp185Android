package edu.ucsb.cs.cs185.frickenhamster.food;

import android.app.Activity;
import android.content.*;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edu.ucsb.cs.cs185.frickenhamster.food.history.HistoryActivity;
import edu.ucsb.cs.cs185.frickenhamster.food.pref.*;
import edu.ucsb.cs.cs185.frickenhamster.food.restaurants.*;


public class MainActivity extends Activity
{
	public final static String FOOD_TYPE = "edu.ucsb.cs.cs185.frickenhamster.food.FOOD_TYPE";

	public final static int GET_NUM = 2;

	private ArrayList<Food> array_image;

	private ArrayAdapter<String> arrayAdapter;
	private CustomImageAdapter arrayPicAdapter;
	@InjectView(R.id.frame)
	SwipeFlingAdapterView flingContainer;
	private Intent intent;

	private Context mainContext = this;

	//for sidebar menu
	private String[] listItems;
	//private String[] historyListItems;
	private ArrayList<String> historyListItems;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ListView mDrawerHistoryList;
	private LinearLayout mDrawer;

	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private FoodManager foodManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.drawer_layout);
		
		System.gc();
		
		ButterKnife.inject(this);


		FoodApplication app = (FoodApplication) getApplicationContext();
		foodManager = app.getFoodManager();

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		ArrayList<Cuisine> cuisines = foodManager.getCuisineList();
		


		array_image = new ArrayList<Food>();
		/*Drawable myDrawable = getResources().getDrawable(R.drawable.caje_coffee);
		Bitmap image1 = ((BitmapDrawable) myDrawable).getBitmap();
		fImage1 = new FoodImage(image1, "coffee");
        array_image.add(fImage1);
        
		Drawable myDrawable2 = getResources().getDrawable(R.drawable.captains_bbq);
		Bitmap image2 = ((BitmapDrawable) myDrawable2).getBitmap();
        fImage2 = new FoodImage(image2, "bbq");
		array_image.add(fImage2);
        
		Drawable myDrawable3 = getResources().getDrawable(R.drawable.habit_burger);
		Bitmap image3 = ((BitmapDrawable) myDrawable3).getBitmap();
        fImage3 = new FoodImage(image3, "burger");
        array_image.add(fImage3);

        Drawable myDrawable4 = getResources().getDrawable(R.drawable.silvergreens_salad);
        Bitmap image4 = ((BitmapDrawable) myDrawable4).getBitmap();
        fImage4 = new FoodImage(image4, "salad");
        array_image.add(fImage4);

        Drawable myDrawable5 = getResources().getDrawable(R.drawable.spudnuts_bagels);
        Bitmap image5 = ((BitmapDrawable) myDrawable5).getBitmap();
        fImage5 = new FoodImage(image5, "bagels");
        array_image.add(fImage5);

        Drawable myDrawable6 = getResources().getDrawable(R.drawable.spudnuts_donuts);
        Bitmap image6 = ((BitmapDrawable) myDrawable6).getBitmap();
        fImage6 = new FoodImage(image6, "donuts");
        array_image.add(fImage6);

        Drawable myDrawable7 = getResources().getDrawable(R.drawable.sushiya_sushi);
        Bitmap image7 = ((BitmapDrawable) myDrawable7).getBitmap();
        fImage7 = new FoodImage(image7, "sushi");
        array_image.add(fImage7);

        Drawable myDrawable8 = getResources().getDrawable(R.drawable.woodstocks_pizza);
        Bitmap image8 = ((BitmapDrawable) myDrawable8).getBitmap();
        fImage8 = new FoodImage(image8, "salad");
        array_image.add(fImage8);*/


		for (int j = 0; j < GET_NUM; j++)
		{
			array_image.add(foodManager.getRandomFood());
		}

		arrayPicAdapter = new CustomImageAdapter(this, array_image);

		//temporarily creating the fake history file
		boolean exists = false;
        for (String file : fileList()) {
            if (file.compareTo("history.txt") == 0) exists = true;
        }

        File myFile = new File ("history.txt");
        if (myFile.exists()) {
            exists = true;
        }

        if (!exists) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new BufferedOutputStream(openFileOutput("history.txt", MODE_PRIVATE)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (writer != null) {
                String[] dates = new String[]{"Thursday, April 2, 2015", "Monday, May 4, 2015", "Friday, May 26, 2015", "Monday, June 1, 2015", "Friday, June 5, 2015"};
                Random random = new Random();
                for (int i = 0; i < 5; i++) {
                    Cuisine type = cuisines.get(random.nextInt(cuisines.size()));
                    Food food = type.getFoods().get(random.nextInt(type.getFoods().size()));
                    Restaurant restaurant = food.getRestaurants().get(random.nextInt(food.getRestaurants().size()));
                    writer.println(food.getName() + "---" + restaurant.getName() + "---" + dates[random.nextInt(dates.length)]);
                }
                writer.close();
            }
        }

		//CUSTOM ADAPTER
		flingContainer.setAdapter(arrayPicAdapter);
		flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener()
		{
			@Override
			public void removeFirstObjectInAdapter()
			{
				// this is the simplest way to delete an object from the Adapter (/AdapterView)
				Log.d("LIST", "removed object!");
				array_image.remove(0);
				arrayPicAdapter.notifyDataSetChanged();
			}

			@Override
			public void onLeftCardExit(Object dataObject)
			{
				//Do something on the left!
				//You also have access to the original object.
				//If you want to use it just cast it (String) dataObject
				array_image.add(foodManager.getRandomFood());
				arrayPicAdapter.notifyDataSetChanged();
				Log.d("uguu", "cards in array" + array_image.size());
				System.gc();
			}

			@Override
			public void onRightCardExit(Object dataObject)
			{
				Food mFoodImage = (Food) dataObject;
				array_image.add(foodManager.getRandomFood());
				arrayPicAdapter.notifyDataSetChanged();

				intent = new Intent(mainContext, RestaurantsActivity.class);
				foodManager.selectFood(mFoodImage);
				startActivity(intent);
			}

			@Override
			public void onAdapterAboutToEmpty(int itemsInAdapter)
			{
				// Ask for more data here
                /*array_image.add(fImage1);
                array_image.add(fImage2);
                array_image.add(fImage3);
				array_image.add(fImage4);
                array_image.add(fImage5);
                array_image.add(fImage6);
                array_image.add(fImage7);
                array_image.add(fImage8);*/
				System.gc();
				/*for (int j = array_image.size(); j < GET_NUM; j++)
				{
					array_image.add(foodManager.getRandomFood());
				}*/
				arrayPicAdapter.notifyDataSetChanged();
				Log.d("LIST", "notified");
			}

			@Override
			public void onScroll(float scrollProgressPercent)
			{
				View view = flingContainer.getSelectedView();
				//view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
				//view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
			}
		});


		// Optionally add an OnItemClickListener
		flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClicked(int itemPosition, Object dataObject)
			{

			}
		});

		// sidebar stuff

		// Initialize Drawer List
		listItems = new String[2];
		listItems[0] = "Preferenes";
		listItems[1] = "History";
		//initialize history list
		historyListItems = new ArrayList<String>();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(openFileInput("history.txt")));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		String line;
		if (reader == null) return;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				FoodOrder item = new FoodOrder(line);
				historyListItems.add(item.type + "\n" + item.restaurant + "\n" + item.date);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		Collections.reverse(historyListItems);

		mTitle = mDrawerTitle = getTitle();
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
//                R.string.drawer_open,  /* "open drawer" description */
//                R.string.drawer_close  /* "close drawer" description */
//                ) {
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                getActionBar().setTitle(mTitle);
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle(mDrawerTitle);
//            }
//        };
//
//        // Set the drawer toggle as the DrawerListener
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
//
//        mDrawer = (LinearLayout) findViewById(R.id.drawer_linearlayout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        mDrawerHistoryList = (ListView) findViewById(R.id.left_drawer_history);
//
//        // Set the adapter for the menu list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item_layout, R.id.list_item_name, listItems));
//        // Set the adapter for the history list view
//        mDrawerHistoryList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_history_list_item, R.id.list_item_name, historyListItems));
//        // Set the list's click listener
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        //mDrawerHistoryList.setOnItemClickListener(new DrawerItemClickListener());
	}

	static void makeToast(Context ctx, String s)
	{
		Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
	}


	//@OnClick(R.id.right)
	public void right()
	{
		/**
		 * Trigger the right event manually.
		 */
		flingContainer.getTopCardListener().selectRight();
	}

	//@OnClick(R.id.left)
	public void left()
	{
		flingContainer.getTopCardListener().selectLeft();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_history)
		{
			launchHistoryActivity();
		}

		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description */
				R.string.drawer_close  /* "close drawer" description */
		)
		{
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view)
			{
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawer = (LinearLayout) findViewById(R.id.drawer_linearlayout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerHistoryList = (ListView) findViewById(R.id.left_drawer_history);

		// Set the adapter for the menu list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item_layout, R.id.list_item_name, listItems));
		// Set the adapter for the history list view
		mDrawerHistoryList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_history_list_item, R.id.list_item_name, historyListItems));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		//mDrawerHistoryList.setOnItemClickListener(new DrawerItemClickListener());


		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	void launchHistoryActivity()
	{
		Intent intent = new Intent(this, HistoryActivity.class);
		startActivity(intent);
	}

	void launchPreferences()
	{
		PrefDialog dialog = new PrefDialog();
		dialog.show(getFragmentManager(), "Pref Pick");
	}

	// handle clicks in the side bar menu
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id)
		{
			switch (position)
			{
				case 0:
					launchPreferences();
					break;
				case 1:
					launchHistoryActivity();
			}
		}
	}
}
