package edu.ucsb.cs.cs185.frickenhamster.food;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.preference.*;
import android.util.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Hamster
 * Date: 6/2/2015
 * Time: 5:03 PM
 */
public class FoodManager
{

	private HashMap<String, Cuisine> cuisines;
	
	private ArrayList<Food> allowedFoods;

	private Context context;
	
	private SharedPreferences sharedPref;
	
	private Food selected;
    private int lastRandom;

	public FoodManager(Context context)
	{
		this.context = context;
		
		allowedFoods = new ArrayList<Food>();
		cuisines = new HashMap<String, Cuisine>();

		Food food;
		food = addFood("Pizza", "Italian", R.drawable.woodstocks_pizza);
		food.addRestaurant("Woodstock's Pizza", "http://www.yelp.com/biz/woodstocks-pizza-isla-vista-2", "http://s3-media1.fl.yelpcdn.com/bphoto/MqngpKj1_2KjhdIoJqYAAw/l.jpg");
		food.addRestaurant("Giovanni's Pizza", "http://www.yelp.com/biz/giovannis-pizza-santa-barbara-2", "http://s3-media1.fl.yelpcdn.com/bphoto/VFi1QEu58gFN5tCQr7RPFQ/l.jpg");
		
        food.addRestaurant("Pizza My Heart", "http://www.yelp.com/biz/pizza-my-heart-isla-vista", "https://s-media-cache-ak0.pinimg.com/originals/51/2e/5c/512e5c37a0a1a8b7d66e988722f0bfa1.jpg");
        food.addRestaurant("Giovanni's", "http://www.yelp.com/biz/giovannis-isla-vista-isla-vista", "http://s3-media3.fl.yelpcdn.com/bphoto/sb26f20lEKLzffZh1XOzlg/l.jpg");
        food.addRestaurant("Blaze Pizza", "www.yelp.com/biz/blaze-fast-fired-pizza-isla-vista", "http://s3-media3.fl.yelpcdn.com/bphoto/GgZXLVZ98z5HSGtmsIrzLw/l.jpg");
        food.addRestaurant("Dominoes", "http://www.yelp.com/biz/dominos-pizza-isla-vista", "http://s3-media1.fl.yelpcdn.com/bphoto/LTed8C33G2myDIRfZKKqjw/l.jpg");

        food = addFood("Hamburger", "American", R.drawable.habit_burger);
        food.addRestaurant("The Habit", "http://www.yelp.com/biz/the-habit-isla-vista", "http://www.habitburger.com/wp-content/themes/habitburger/images/logo-habit.jpg");
        food.addRestaurant("The Krusty Krab", "http://www.yelp.com/biz/the-krusty-krab-portland", "http://d.ibtimes.co.uk/en/full/1385860/actual-krusty-krab.jpg");
        food.addRestaurant("Kyle's Kitchen", "http://www.yelp.com/biz/kyles-kitchen-goleta", "http://s3-media4.fl.yelpcdn.com/bphoto/BP99QQssaupmhssoFPp31Q/l.jpg");
        food.addRestaurant("Kahuna Grill", "http://www.yelp.com/biz/kahuna-grill-goleta", "http://s3-media1.fl.yelpcdn.com/bphoto/CuJ2dolr0vHa1YOyUKlDYQ/l.jpg");
        food.addRestaurant("In-N-Out", "http://www.yelp.com/biz/in-n-out-burger-goleta", "http://s3-media4.fl.yelpcdn.com/bphoto/EOZw_DfUKewYXf71tcjhFw/l.jpg");

        food = addFood("Coffee", "American", R.drawable.caje_coffee);
        food.addRestaurant("Caje", "http://www.yelp.com/biz/caje-isla-vista", "http://1.bp.blogspot.com/-I57oXWaiBW4/U3ppByNtIUI/AAAAAAAAA2o/HXAcIVGSAQs/s1600/caje-coffee.jpg");
        food.addRestaurant("Coffee Collaborative", "http://www.yelp.com/biz/coffee-collaborative-isla-vista", "https://s-media-cache-ak0.pinimg.com/736x/9e/35/37/9e353772511c0d89c8c2001a2887ada4.jpg");
        food.addRestaurant("Cafe Equilibrium", "http://www.yelp.com/biz/cafe-equilibrium-goleta-5", "http://s3-media1.fl.yelpcdn.com/bphoto/awrWa4uGLYEcF6Cv5uXbLQ/o.jpg");
        food.addRestaurant("IV Drip", "http://www.yelp.com/biz/iv-drip-goleta", "http://s3-media2.fl.yelpcdn.com/bphoto/M8gpQ8iXfidf1tkKO__R_Q/l.jpg");
        food.addRestaurant("Starbucks", "http://www.yelp.com/biz/starbucks-goleta-2", "http://s3-media2.fl.yelpcdn.com/bphoto/byTlMl0lQBT1hNW_Ap1dnQ/l.jpg");

        food = addFood("Barbecue", "American", R.drawable.captains_bbq);
        food.addRestaurant("Kaptain’s Firehouse BBQ", "http://www.yelp.com/biz/kaptains-firehouse-bbq-isla-vista", "https://m1.behance.net/rendition/pm/3517159/disp/29a18efe443464e46387676904b37d58.png");
        food.addRestaurant("Woody's BBQ", "http://www.yelp.com/biz/woodys-bbq-goleta", "http://ifranchisenews.com/wp-content/uploads/2013/01/woodys-bar-b-q-logo-300.jpg");
        food.addRestaurant("Kogilicious", "http://www.yelp.com/biz/kogilicious-isla-vista", "http://s3-media4.fl.yelpcdn.com/bphoto/kLkQEf68qFs8GT1oiZQIxQ/l.jpg");
        food.addRestaurant("Nugget Bar and Grill", "http://www.yelp.com/biz/the-nugget-bar-and-grill-goleta", "http://s3-media3.fl.yelpcdn.com/bphoto/nNtP6Cb4Oaak8Yv1QeMV_w/l.jpg");
        food.addRestaurant("The Palace Grill", "http://www.yelp.com/biz/the-palace-grill-santa-barbara", "http://s3-media1.fl.yelpcdn.com/bphoto/KubTcFbLnstIEqsU64OURg/l.jpg");

        food = addFood("Salad", "American", R.drawable.silvergreens_salad);
        food.addRestaurant("Silvegreens", "http://www.yelp.com/biz/silvergreens-isla-vista", "http://www.silvergreens.com/images/New-Silvergreens-Logo.jpg");
        food.addRestaurant("South Coast Deli", "http://www.yelp.com/biz/south-coast-deli-isla-vista", "http://www.santabarbara.com/dining/news/wp-content/uploads/2011/10/111017-south-coast-deli.jpg");
        food.addRestaurant("Hana Kitchen", "http://www.yelp.com/biz/hana-kitchen-isla-vista", "http://s3-media4.fl.yelpcdn.com/bphoto/m_hn_etbsOooy6fdUSWLOA/o.jpg");
        food.addRestaurant("The Natural Cafe", "http://www.yelp.com/biz/the-natural-cafe-santa-barbara", "http://s3-media2.fl.yelpcdn.com/bphoto/o5USf__dvLzX3300gbxjag/l.jpg");
        food.addRestaurant("Finch and Fork", "http://www.yelp.com/biz/finch-and-fork-santa-barbara", "http://s3-media4.fl.yelpcdn.com/bphoto/BP3bMj_DtcJ35_WH_R5rFA/l.jpg");

		food = addFood("Bagel", "American", R.drawable.spudnuts_bagels);
        food.addRestaurant("Spudnuts Donuts", "http://www.yelp.com/biz/spudnuts-donuts-isla-vista", "http://www.dazzleitupevents.com/wp-content/uploads/2014/04/spudnuts.jpg");
        food.addRestaurant("Bagel Cafe", "http://www.yelp.com/biz/bagel-cafe-goleta", "http://s3-media3.fl.yelpcdn.com/bphoto/JWWoPNymDl1li1udhHWb1Q/l.jpg");
        food.addRestaurant("Jacks Bistro and Famous Bagels", "http://www.yelp.com/biz/jacks-bistro-and-famous-bagels-santa-barbara-2","http://s3-media2.fl.yelpcdn.com/bphoto/MVoXaVI4dV7Vf4Lwp9ivNQ/l.jpg");
        food.addRestaurant("Bagel Market Cafe", "http://www.yelp.com/biz/bagel-market-cafe-santa-barbara","http://s3-media3.fl.yelpcdn.com/bphoto/JEWhdWwwjc41jXkbJBfb5Q/l.jpg");
        food.addRestaurant("Finch and Fork", "http://www.yelp.com/biz/finch-and-fork-santa-barbara", "http://s3-media4.fl.yelpcdn.com/bphoto/BP3bMj_DtcJ35_WH_R5rFA/l.jpg");

        food = addFood("Donut", "American", R.drawable.spudnuts_donuts);
        food.addRestaurant("Spudnuts Donuts", "http://www.yelp.com/biz/spudnuts-donuts-isla-vista", "http://www.dazzleitupevents.com/wp-content/uploads/2014/04/spudnuts.jpg");
        food.addRestaurant("Eller's Donut House", "http://www.yelp.com/biz/ellers-donut-house-santa-barbara", "http://s3-media1.fl.yelpcdn.com/bphoto/wXEJpDRqVyG3Pbt4zIbW_A/l.jpg");
        food.addRestaurant("Winchell's Doughnut House", "http://www.yelp.com/biz/winchells-doughnut-house-santa-barbara", "http://s3-media4.fl.yelpcdn.com/bphoto/ytxcxgtQaTxGJWXLvbm3Hg/o.jpg");
        food.addRestaurant("Bagel Market Cafe", "http://www.yelp.com/biz/bagel-market-cafe-santa-barbara","http://s3-media3.fl.yelpcdn.com/bphoto/JEWhdWwwjc41jXkbJBfb5Q/l.jpg");
        food.addRestaurant("Mama's Bakery", "http://www.yelp.com/biz/mamas-bakery-santa-barbara", "http://s3-media2.fl.yelpcdn.com/bphoto/bHpquJ5DYi6aThh9AuE9OQ/l.jpg");

		food = addFood("Burrito", "Mexican", R.drawable.burrito);
        food.addRestaurant("La Super Rica", "http://www.yelp.com/biz/la-super-rica-taqueria-santa-barbara", "http://s3-media1.fl.yelpcdn.com/bphoto/MoaMQsfUyNicrF8IkG5StA/l.jpg");
        food.addRestaurant("Freebirds World Burrito", "http://www.yelp.com/biz/freebirds-world-burrito-isla-vista", "http://loyalogy.com/wp-content/uploads/2013/06/freebirds.jpg");
        food.addRestaurant("Rosarito", "http://www.yelp.com/biz/rosarito-goleta", "http://s3-media3.fl.yelpcdn.com/bphoto/xFRMZv1QUu-g1NONvZ4f5g/o.jpg");
        food.addRestaurant("El Sitio", "http://www.yelp.com/biz/el-sitio-isla-vista?osq=Tacos", "https://www.santabarbara.com/dining/photos/el-sitio-iv-2.jpg");
        food.addRestaurant("Los Agaves", "http://www.yelp.com/biz/los-agaves-santa-barbara-2", "http://www.losagavesfl.com/i/los_agaves.png");

        food = addFood("Tacos", "Mexican", R.drawable.tacos);
        food.addRestaurant("Lilly's Tacos", "http://www.yelp.com/biz/lillys-tacos-santa-barbara-2", "http://s3-media2.fl.yelpcdn.com/bphoto/J8KPYwVmzWyKAtmvJj0R_g/l.jpg");
        food.addRestaurant("El Sitio", "http://www.yelp.com/biz/el-sitio-isla-vista?osq=Tacos", "https://www.santabarbara.com/dining/photos/el-sitio-iv-2.jpg");
        food.addRestaurant("Los Agaves", "http://www.yelp.com/biz/los-agaves-santa-barbara-2", "http://www.losagavesfl.com/i/los_agaves.png");
        food.addRestaurant("Freebirds World Burrito", "http://www.yelp.com/biz/freebirds-world-burrito-isla-vista", "http://loyalogy.com/wp-content/uploads/2013/06/freebirds.jpg");
        food.addRestaurant("Rosarito", "http://www.yelp.com/biz/rosarito-goleta", "http://s3-media3.fl.yelpcdn.com/bphoto/xFRMZv1QUu-g1NONvZ4f5g/o.jpg");

        food = addFood("Sushi", "Japanese", R.drawable.sushiya_sushi);
        food.addRestaurant("Sushiya Express", "http://www.yelp.com/biz/sushiya-express-goleta-2", "https://www.santabarbara.com/dining/photos/sushiya-1.jpg");
		food.addRestaurant("Goleta Sushi House", "http://www.yelp.com/biz/goleta-sushi-house-goleta", "https://res.cloudinary.com/roadtrippers/image/upload/c_fill,h_316,w_520/v1377120383/goleta-sushi-house-52152f504203c3863b000e3c.jpg");

		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		for (Cuisine cuisine : cuisines.values())
		{
			boolean allowed = sharedPref.getBoolean("pref" + cuisine.getName(), true);
			Log.d("uguu", "pref" + cuisine.getName() + allowed);
			cuisine.setAllowed(allowed);
		}
        food.addRestaurant("Arigato Sushi", "http://www.yelp.com/biz/arigato-sushi-santa-barbara", "http://s3-media4.fl.yelpcdn.com/bphoto/5A6f5ZIO1L1vA3Ge4AkNQw/l.jpg");
        food.addRestaurant("Miso Hungry", "http://www.yelp.com/biz/miso-hungry-santa-barbara", "http://s3-media1.fl.yelpcdn.com/bphoto/Rdz4W4uxLMCdEEKw-pt8Xg/l.jpg");
        food.addRestaurant("Kyoto", "http://www.yelp.com/biz/kyoto-santa-barbara", "http://s3-media4.fl.yelpcdn.com/bphoto/Lx0qwB25MARCeJ05s6UIEg/l.jpg");

		/*
		addFood("Boiled Brocolli", "Kawaii");
		addFood("Katsu", "Japanese");
		addFood("Ramen", "Japanese");
		addFood("Curry Rice", "Japanese");
		*/
	}


	public Food addFood(String foodName, String cuisineName, int drawable)
	{
		Cuisine cc = cuisines.get(cuisineName);

		if (cc == null)
		{
			cc = new Cuisine(cuisineName);
			cuisines.put(cuisineName, cc);
		}

		Drawable myDrawable = context.getResources().getDrawable(drawable);
		Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
		Food food = new Food(foodName, image, cc);
		cc.addFood(food);
		if (cc.isAllowed())
		{
			allowedFoods.add(food);
		}
		
		return food;
	}
	
	public Food getRandomFood()
	{
		int nn = (int)(Math.random() * allowedFoods.size());
        while(lastRandom == nn) {
            nn = (int)(Math.random() * allowedFoods.size());
        }
        lastRandom = nn;
		return allowedFoods.get(nn);
	}
	
	public void allowCuisine(String cuisineName)
	{
		Cuisine cuisine = cuisines.get(cuisineName);
		if (cuisine == null)
			return;
		cuisine.setAllowed(true);
		SharedPreferences.Editor edit = sharedPref.edit();
		edit.commit();
		
	}
	
	public void disallowCuisine(String cuisineName)
	{
		Cuisine cuisine = cuisines.get(cuisineName);
		if (cuisine == null)
			return;
		cuisine.setAllowed(false);
		SharedPreferences.Editor edit = sharedPref.edit();
		edit.commit();
	}
	
	public void rebuildAllowedFoods()
	{
		allowedFoods.clear();
		for (Cuisine cuisine : cuisines.values())
		{
			if (cuisine.isAllowed())
			{
				for (Food food:cuisine.getFoods())
				{
					allowedFoods.add(food);
				}
			}
		}
	}
	
	public void selectFood(Food food)
	{
		selected = food;
	}

	public Food getSelectedFood()
	{
		return selected;
	}

	public ArrayList<Cuisine> getCuisineList()
	{
		return new ArrayList<Cuisine>(cuisines.values());
	}

}
