package edu.ucsb.cs.cs185.frickenhamster.food.history;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ucsb.cs.cs185.frickenhamster.food.FoodOrder;
import edu.ucsb.cs.cs185.frickenhamster.food.R;

/**
 * Created by Dario on 6/62015.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<FoodOrder> myDataset; // replace with List<Food> later
    private HistoryActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myFoodTypeView;
        public TextView myRestaurantNameView;
        public TextView myDateView;
        public ImageView myPhotoView;
        public Context context;
        final HistoryActivity historyActivity;

        public ViewHolder(HistoryActivity activity, View v, Context context) {
            super(v);
            myFoodTypeView = (TextView) v.findViewById(R.id.food_type);
            myRestaurantNameView = (TextView) v.findViewById(R.id.restaurant_name);
            myDateView = (TextView) v.findViewById(R.id.date);
            myPhotoView = (ImageView) v.findViewById(R.id.photo);

            this.context = context;
            this.historyActivity = activity;

            /*
            myTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //openFullScreenPhoto(cardactivity, currentPhoto);
                }
            });
            */
        }
    }

    public HistoryAdapter(HistoryActivity activity, List<FoodOrder> myDataset) {
        this.myDataset = myDataset;
        this.activity = activity;
    }

    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card_layout, parent, false);

        ViewHolder vh = new ViewHolder(activity, v, parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        // later, get all of this information from an
        // ArrayList<FoodOrder> called myDataset
        holder.myFoodTypeView.setText(myDataset.get(position).type);
        holder.myRestaurantNameView.setText(myDataset.get(position).restaurant);
        holder.myDateView.setText(myDataset.get(position).date);

        if (myDataset.get(position).type.equals("Coffee")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.caje_coffee);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Barbecue")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.captains_bbq);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Hamburger")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.habit_burger);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Salad")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.silvergreens_salad);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Bagel")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.spudnuts_bagels);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Donut")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.spudnuts_donuts);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Burrito")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.burrito);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Tacos")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.tacos);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Sushi")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.sushiya_sushi);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        else if (myDataset.get(position).type.equals("Pizza")) {
            Drawable myDrawable = holder.context.getResources().getDrawable(R.drawable.woodstocks_pizza);
            Bitmap image = ((BitmapDrawable) myDrawable).getBitmap();
            holder.myPhotoView.setImageBitmap(image);
        }
        //Picasso.with(holder.context).load(myDataset.get(position)).resize(800, 400).centerCrop().into(holder.myImageView);

        //holder.currentPhoto = myDataset.get(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myDataset.size();
    }

}
