package edu.ucsb.cs.cs185.frickenhamster.food;

import android.graphics.*;
import edu.ucsb.cs.cs185.frickenhamster.food.restaurants.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/2/2015
 * Time: 5:00 PM
 * <p/>
 * specific food type
 */
public class Food
{

	private String name;
	private Bitmap image;
	//private ArrayList<Cuisine> cuisines;
	private Cuisine cuisine;
	
	private ArrayList<Restaurant> restaurants;	
	


	public Food(String name, Bitmap image, Cuisine cuisine)
	{
		this.name = name;
		this.image = image;
		this.cuisine = cuisine;
		
		restaurants = new ArrayList<Restaurant>(5);
		restaurants.add(new Restaurant("Look up on Yelp", "", "http://www.cdkglobaldigitalmarketing.com/wp-content/uploads/2013/04/Yelp_Estimate_tool.jpg"));
	}
	
	public void addRestaurant(String name, String link, String image)
	{
		Restaurant restaurant = new Restaurant(name, link, image);
		restaurants.add(restaurant);
	}

	public String getName()
	{
		return name;
	}

	public Bitmap getImage()
	{
		return image;
	}

	public ArrayList<Restaurant> getRestaurants()
	{
		return restaurants;
	}
}
