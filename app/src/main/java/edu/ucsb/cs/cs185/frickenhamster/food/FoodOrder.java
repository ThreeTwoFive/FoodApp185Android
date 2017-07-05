package edu.ucsb.cs.cs185.frickenhamster.food;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by Jon on 6/7/2015.
 */
public class FoodOrder {
    public String type;
    public String restaurant;
    public String date;

    public FoodOrder(String type, String restaurant, String date) {

    }

    public FoodOrder(String historyLine) {
        String[] order = historyLine.split("---");
        type = order[0];
        restaurant = order[1];
        date = order[2];
    }

}
