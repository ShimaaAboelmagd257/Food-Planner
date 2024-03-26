package com.example.foodplanner.favoriteMeals.view;

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
import com.example.foodplanner.search.searchPage.view.SearchClickListner;
import com.example.foodplanner.splashEntrance.view.EntrancePage;

import java.util.ArrayList;
import java.util.List;
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.myViewHolder>{
        private final String SKIP = "skip";
        private final Context context;
        private List<ProductsPOJO> favlist;
        onFavouriteClickListner onFavouriteClickListner;


        public static class myViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            ImageView mealImage;
            TextView mealName;
            ImageButton favoriteImage;
            View v;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                v  = itemView;
                cardView = itemView.findViewById(R.id.cardMeal);
                mealImage = itemView.findViewById(R.id.pagerimagevieww);
                mealName = itemView.findViewById(R.id.mealName);
                favoriteImage = itemView.findViewById(R.id.mealFavv);

            }
        }

        public FavouriteAdapter(Context context, List<ProductsPOJO> list) {
            this.context = context;
            this.favlist = list;
        }
        public FavouriteAdapter(Context context, List <ProductsPOJO> list , onFavouriteClickListner onFavouriteClickListner) {
            this.context = context;
            this.favlist = list;
            this.onFavouriteClickListner = onFavouriteClickListner;

        }
        public void setList(List<ProductsPOJO> dailylist) {
            this.favlist = dailylist;
        }


        @NonNull
        @Override
        public FavouriteAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.pageritem,parent,false);
            FavouriteAdapter.myViewHolder viewHolder = new FavouriteAdapter.myViewHolder(view);
            return viewHolder;
        }



        @Override
        public void onBindViewHolder(@NonNull FavouriteAdapter.myViewHolder holder, int position) {
            if (position < favlist.size()){

            ProductsPOJO item = favlist.get(position);

            if (item != null&& item.getStrMealThumb() != null) {
                holder.mealName.setText(favlist.get(position).getStrMeal());

                Glide.with(context).load(favlist.get(position).getStrMealThumb())
                        .apply(new RequestOptions().override(500, 500)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_foreground)).into(holder.mealImage);

            }}
                // Set an OnClickListener for the card view
                holder.cardView.setOnClickListener(v -> {

                    Intent intent = new Intent(context, MealPreparation.class);
                    intent.putExtra("mealName", favlist.get(holder.getAdapterPosition()).getStrMeal());
                    intent.putExtra("mealID", favlist.get(holder.getAdapterPosition()).getIdMeal());
                    intent.putExtra("mealthumb", favlist.get(holder.getAdapterPosition()).getStrMealThumb());
                    context.startActivity(intent);

                });


                // Set an OnClickListener for the favorite button
                holder.favoriteImage.setOnClickListener(v -> {

                        onFavouriteClickListner.onRemoveFavClick(favlist.get(position));

                });
            }








      /*  private void showSignUpAlertDialog() {
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
        }*/


    public void removeFavorite(ProductsPOJO mealModel){
        this.favlist.remove(mealModel);
    }

    public  List<ProductsPOJO> remove(ProductsPOJO mealModel){
        this.favlist.remove(mealModel);
        return  favlist;

    }

        @Override
        public int getItemCount() {
            return favlist!= null ? favlist.size() : 0;
        }
    }


