package com.example.foodplanner.search.searchPage.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class InspirationAdapter extends RecyclerView.Adapter<InspirationAdapter.myViewHolder>{
    private final String SKIP = "skip";
    private final Context context;
    private List<ProductsPOJO> list;
    SearchClickListner searchClickListner;


    public static class myViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView mealImage;
        TextView mealName;
        ImageButton favoriteImage;
         View view;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            cardView = itemView.findViewById(R.id.cardMeal);
            mealImage = itemView.findViewById(R.id.pagerimageview);
            mealName = itemView.findViewById(R.id.mealName);
            favoriteImage = itemView.findViewById(R.id.mealFav);

        }
    }

    public InspirationAdapter(Context context, List<ProductsPOJO> list) {
        this.context = context;
        this.list = list;
    }

    public InspirationAdapter(Context context, List <ProductsPOJO> list , SearchClickListner searchClickListner) {
        this.context = context;
        this.list = list;
        this.searchClickListner = searchClickListner;

    }
    public void setList(List<ProductsPOJO> dailylist) {
        this.list = dailylist;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pageritem,parent,false);
       myViewHolder viewHolder = new  myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InspirationAdapter.myViewHolder holder, int position) {
        ProductsPOJO item = list.get(position);

        if (item != null) {
            // Set the meal name to the TextView
            holder.mealName.setText(list.get(position).getStrMeal());
            if (holder.mealImage != null) {
                Glide.with(context).load(list.get(position).getStrMealThumb())
                        .apply(new RequestOptions().override(holder.mealImage.getWidth(), holder.mealImage.getHeight()))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.mealImage);
            }
            // Set an OnClickListener for the card view
            holder.cardView.setOnClickListener(v -> {

                Intent intent = new Intent(context, MealPreparation.class);
                intent.putExtra("mealName", list.get(holder.getAdapterPosition()).getStrMeal());
                intent.putExtra("mealID", list.get(holder.getAdapterPosition()).getIdMeal());
                intent.putExtra("mealthumb", list.get(holder.getAdapterPosition()).getStrMealThumb());
                context.startActivity(intent);

            });


            // Set an OnClickListener for the favorite button
            if (holder.favoriteImage != null) {
                holder.favoriteImage.setOnClickListener(v -> onFavoriteClick(list.get(position)));
            }
        }
    }



    private void onFavoriteClick(ProductsPOJO item) {
        if (SKIP.equals("skip")) {
            showSignUpAlertDialog();
        } else {
            item.setFavorite(true);
            item.setNameDay("Not");
            searchClickListner.addToFavoriteOnClick(item);
        }
    }




    private void showSignUpAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to sign up in the application?");
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes, Signup", (dialog, which) -> {
            Intent intent = new Intent(context, SignUpActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        });

        builder.setNegativeButton("No, thanks", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void performSearch(String newText) {
        if (list != null) {
            List<ProductsPOJO> result = new ArrayList<>();
            Observable.fromIterable(list)
                    .filter(e -> e.getStrMeal().contains(newText))
                    .subscribe(i -> result.add(i));

            setList(result);
            notifyDataSetChanged();
        } else {
            Log.d("InspirationAdapter", "performSearch: view search meals");

        }
    }
    @Override
    public int getItemCount() {
        return list!= null ? list.size() : 0;
    }
}
