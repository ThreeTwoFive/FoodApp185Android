package edu.ucsb.cs.cs185.frickenhamster.food.history;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ucsb.cs.cs185.frickenhamster.food.FoodOrder;
import edu.ucsb.cs.cs185.frickenhamster.food.R;

/**
 * Created by Dario on 6/62015.
 */
public class HistoryActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<FoodOrder> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        //emptyText = (TextView)findViewById(R.id.empty);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput("history.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataset = new ArrayList<FoodOrder>();
        String line;
        if (reader == null) return;
        try {
            while ((line = reader.readLine()) != null) {
                myDataset.add(new FoodOrder(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(myDataset);
        mAdapter = new HistoryAdapter(this, myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_visualize) {
            visualizeHistory();
        }

        if (id == R.id.action_delete_all) {
            deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    void visualizeHistory() {
        Intent intent = new Intent(this, VisualizeActivity.class);
        startActivity(intent);
    }

    void deleteAll() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedOutputStream(openFileOutput("history.txt", MODE_PRIVATE)));
        } catch (FileNotFoundException e) {
            System.out.println("no history to delete");
            return;
        }

        writer.println("");
        myDataset.clear();
        mAdapter.notifyDataSetChanged();

    }
}
