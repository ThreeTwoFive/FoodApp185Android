package edu.ucsb.cs.cs185.frickenhamster.food.history;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.FillFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsb.cs.cs185.frickenhamster.food.FoodOrder;
import edu.ucsb.cs.cs185.frickenhamster.food.R;

/**
 * Created by Dario on 6/62015.
 */
public class VisualizeActivity extends Activity {
    int days;
    ArrayList<FoodOrder> dataSet;
    boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize);
//        GraphView graph = (GraphView) findViewById(R.id.graph);
        days = -1;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput("history.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dataSet = new ArrayList<FoodOrder>();
        String line;

        if (reader == null) return;
        try {
            while ((line = reader.readLine()) != null) {
                dataSet.add(new FoodOrder(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        firstTime = true;

        drawGraph();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_visualize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void drawGraph() {

        int[] data = new int[9];
        HashMap<String, Integer> dataMap = new HashMap<String, Integer>();
        if (days == -1) {
            for (FoodOrder order : dataSet) {
                if (dataMap.containsKey(order.type)) {
                    dataMap.put(order.type, dataMap.get(order.type) + 1);
                } else {
                    dataMap.put(order.type, 1);
                }
            }
        } else {
            //need to compare date string to date
            for (FoodOrder order : dataSet) {
                int daysOld = daysOld(order);

                if (daysOld < days) {
                    if (dataMap.containsKey(order.type)) {
                        dataMap.put(order.type, dataMap.get(order.type) + 1);
                    } else {
                        dataMap.put(order.type, 1);
                    }
                }
            }
        }

        BarChart chart = (BarChart) findViewById(R.id.chart);
        //String[] labels = new String[]{"coffee", "bbq", "burger", "salad", "bagels", "donuts", "sushi", "pizza", "other"};
        String[] labels = new String[dataMap.size()];
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        int maxVal = 0;
        int i = 0;
        for (Map.Entry keyValue : dataMap.entrySet()) {
            BarEntry entry = new BarEntry(((Integer) keyValue.getValue()), i);
            System.out.println("dataSet: " + i + ", " + entry.getVal());
            labels[i] = (String) keyValue.getKey();
            entries.add(entry);
            if ((Integer) keyValue.getValue() > maxVal) maxVal = (Integer) keyValue.getValue();
            i++;
        }
//        for (int i = 0; i < 9; i++) {
//            BarEntry entry = new BarEntry(data[i], i);
//            entries.add(entry);
//
//            if (data[i] > maxVal)
//                maxVal = data[i];
//        }
        BarDataSet barDataSet = new BarDataSet(entries, "Food Types");
        int[] colors = new int[]{getResources().getColor(R.color.hamburger), getResources().getColor(R.color.pizza), getResources().getColor(R.color.steak), getResources().getColor(R.color.pancakes)};
        barDataSet.setColors(colors);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(labels, barDataSet);
        barData.setValueTextSize(16f);
        if (firstTime) {
            chart.getAxisLeft().setAxisMaxValue(maxVal + 1);
            chart.getAxisRight().setAxisMaxValue(maxVal + 1);
            chart.setDrawValueAboveBar(true);
            chart.setDrawValuesForWholeStack(true);
            chart.getAxisRight().setDrawAxisLine(false);
            chart.getAxisRight().setDrawGridLines(false);
            chart.getAxisRight().setDrawLabels(false);
            chart.getAxisLeft().setDrawGridLines(false);
            chart.getAxisLeft().setShowOnlyMinMax(true);
            chart.getXAxis().setDrawGridLines(false);
            chart.getXAxis().setDrawAxisLine(true);
            chart.getXAxis().setTextSize(12f);
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getAxisLeft().setTextSize(16f);
            chart.setDescription("");
            chart.setDescriptionTextSize(16f);
            chart.getLegend().setTextSize(16f);
            firstTime = false;
        }

        chart.setData(barData);
        chart.invalidate();

        chart.setVisibleXRange(4);
        chart.setHorizontalScrollBarEnabled(true);

        chart.setData(barData);
        chart.invalidate();
    }


    public void allTime(MenuItem item) {
        days = -1;
        drawGraph();
    }

    public void set1month(MenuItem item) {
        days = 30;
        drawGraph();
    }

    public void set2weeks(MenuItem item) {
        days = 14;
        drawGraph();
    }

    public void set7days(MenuItem item) {
        days = 7;
        drawGraph();
    }

    private int daysOld(FoodOrder order) {
        String[] dateString = order.date.split(",");
        if (dateString.length < 2) {
            System.out.println("date parsing failed, date=" + order.date);
            return 0;
        }
        String[] monthDay = dateString[1].split(" ");
        String month, day, year;
        if (dateString.length >= 3 && monthDay.length >= 3) {
            month = monthDay[1];
            day = monthDay[2];
            year = dateString[2].split(" ")[1];
        }
        else {
            System.out.println("date parsing failed, date=" + order.date);
            return 0;
        }
        System.out.println(order.date);
        System.out.println("I think the date of this order is {" + month + "/" + day + "/" + year + "}");

        DateTime currentDate = new DateTime();
        int monthint;
        if (month.compareTo("January") == 0) monthint = 1;
        else if (month.compareTo("February") == 0) monthint = 2;
        else if (month.compareTo("March") == 0) monthint = 3;
        else if (month.compareTo("April") == 0) monthint = 4;
        else if (month.compareTo("May") == 0) monthint = 5;
        else if (month.compareTo("June") == 0) monthint = 6;
        else if (month.compareTo("July") == 0) monthint = 7;
        else if (month.compareTo("August") == 0) monthint = 8;
        else if (month.compareTo("September") == 0) monthint = 9;
        else if (month.compareTo("October") == 0) monthint = 10;
        else if (month.compareTo("November") == 0) monthint = 11;
        else if (month.compareTo("December") == 0) monthint = 12;
        else monthint = 6;
        DateTime orderDate = new DateTime(Integer.parseInt(year), monthint, Integer.parseInt(day), 0, 0);

        Days days = Days.daysBetween(orderDate, currentDate);
        System.out.println("this is " + days.getDays() + "days ago");
        return days.getDays();
    }
}
