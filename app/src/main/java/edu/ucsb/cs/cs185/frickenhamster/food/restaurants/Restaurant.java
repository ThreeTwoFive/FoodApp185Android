package edu.ucsb.cs.cs185.frickenhamster.food.restaurants;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/9/2015
 * Time: 1:26 AM
 */
public class Restaurant
{

	public enum ResType {YELP, ResType, REST}
	private String name;
	private String link;
	private String image;

	ResType type;

	public Restaurant(String name, String link, String image)
	{
		this.name = name;
		this.link = link;
		this.image = image;
		if (link.equals(""))
		{
			type = ResType.YELP;
		}
		else
		{
			type = ResType.REST;
		}
	}
	
	

	public String getName()
	{
		return name;
	}

	public String getLink()
	{
		return link;
	}

	public String getImage()
	{
		return image;
	}
}
