package edu.ucsb.cs.cs185.frickenhamster.food;

import android.app.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/8/2015
 * Time: 5:11 PM
 */
public class FoodApplication extends Application
{
	private FoodManager foodManager;

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		foodManager = new FoodManager(getApplicationContext());
	}

	public FoodManager getFoodManager()
	{
		return foodManager;
	}
}
