package edu.ucsb.cs.cs185.frickenhamster.food;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/2/2015
 * Time: 5:00 PM
 */
public class Cuisine
{
	private String name;
	private ArrayList<Food> foods;
	private boolean allowed;


	public Cuisine(String name)
	{
		this.name = name;
		foods = new ArrayList<Food>();
		allowed = true;
	}


	public void addFood(Food food)
	{
		foods.add(food);
	}

	public boolean isAllowed()
	{
		return allowed;
	}

	public void setAllowed(boolean allowed)
	{
		this.allowed = allowed;
	}

	public ArrayList<Food> getFoods()
	{
		return foods;
	}

	public String getName()
	{
		return name;
	}
}
