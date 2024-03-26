package com.example.foodplanner.mealPlans.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.authantication.signup.view.SignUpActivity;
import com.example.foodplanner.mealPreparation.view.MealPreparation;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.search.searchPage.view.SearchClickListner;
import com.example.foodplanner.splashEntrance.view.EntrancePage;
import com.example.foodplanner.utility.Helper.Helper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MealsPlanAdapter extends RecyclerView.Adapter<MealsPlanAdapter.myViewHolder> {
    private final Context context;
    private List<ProductsPOJO> list;
    PlanClickListner planClickListner;
    //private List<List<ProductsPOJO>> listOfList;


    public static class myViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public  ImageView mealImage;
        public TextView mealName;
        public ImageButton favoriteImage;
        public View view;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            cardView = itemView.findViewById(R.id.cardMeal);
            mealImage = itemView.findViewById(R.id.pagerimagevieww);
            mealName = itemView.findViewById(R.id.mealName);
            favoriteImage = itemView.findViewById(R.id.mealFavv);

        }
    }

   /* public MealsPlanAdapter(Context context, List<ProductsPOJO> list) {
        this.context = context;
        this.list = list;
    }*/

    public MealsPlanAdapter(Context context, ArrayList<ProductsPOJO> plans, PlanClickListner planClickListner) {
        this.context = context;
        this.list = plans;
        this.planClickListner = planClickListner;

    }



 /*   public void setList(List<ProductsPOJO> dailylist) {
        this.list = dailylist;
        Log.e("MealsPlanAdapter", " setList of : " + list.size() );

    }*/


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pageritem, parent, false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("MealsPlanAdapter", "onBindViewHolder : Position " + position + " out of " + list.size());


        if (position < list.size()) {
            Glide.with(context).load(list.get(position).getStrMealThumb())
                    .apply(new RequestOptions().override(500, 500)
                            .placeholder(R.drawable.defaultbackground2)
                            .error(R.drawable.ic_launcher_foreground)).into(holder.mealImage);

            holder.mealName.setText(list.get(position).getStrMeal());
            // ProductsPOJO item = list.get(position);
            Log.e("MealsPlanAdapter", "onBindViewHolder : " + list.isEmpty());


            holder.cardView.setOnClickListener(v -> {

                Intent intent = new Intent(context, MealPreparation.class);
                intent.putExtra("mealName", list.get(holder.getAdapterPosition()).getStrMeal());
                context.startActivity(intent);

            });


            // Set an OnClickListener for the favorite button
            holder.favoriteImage.setOnClickListener(event -> {

                if (Helper.SKIP == "skip") {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to signup in application?");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);
                    builder.setPositiveButton("yes, Signup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, EntrancePage.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    });


                    builder.setNegativeButton("No, thanks", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    list.get(position).setFavorite(true);
                    list.get(position).setNameDay("Not");
                    planClickListner.addToFavoriteOnClick(list.get(position));
                }
            });
        }
        else

        {
            Log.e("MealsPlanAdapter", "onBindViewHolder: Position " + position + " out of bounds");
        }

    }

    @Override
    public int getItemCount() {
        Log.e("MealsPlanAdapter", "getItemCount : " + list.size() );
        return list.size();
    }
    public void setList(List<ProductsPOJO> plans) {
        this.list = plans;
        if (list.isEmpty()) {
            Log.e("MealsPlanAdapter", "setList: Data list is empty");
        }
        Log.e("MealsPlanAdapter", " setList of : " + list.size());
        notifyDataSetChanged();

    }
}

