package com.example.foodplanner.search.searchPage.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class  IngredientSearchAdapter extends RecyclerView.Adapter<IngredientSearchAdapter.myViewHolder> {

        public static String SKIP ;
        private final Context context;
        private List<IngeriedientPojo> ingeriedientPojos;
        SearchClickListner searchClickListner;


        public IngredientSearchAdapter(Context context, List<IngeriedientPojo> ingeriedientPojos, SearchClickListner searchClickListner) {
            this.context = context;
            this.ingeriedientPojos = ingeriedientPojos;
            this.searchClickListner = searchClickListner;
        }

    public IngredientSearchAdapter(Context context, List<IngeriedientPojo> ingeriedientPojos) {
        this.context = context;
        this.ingeriedientPojos = ingeriedientPojos;

    }

    public void performSearch(String newText) {
            if (ingeriedientPojos != null){
        List<IngeriedientPojo> result = new ArrayList<>();
        Observable.fromIterable(ingeriedientPojos)
                .filter(e -> e.getStrIngredient().contains(newText))
                .subscribe(i -> result.add(i));

        setList(result);
        notifyDataSetChanged();
            }
            else {
        Log.d("InspirationAdapter", "performSearch: view search meals");
            }
    }



        public static class myViewHolder extends RecyclerView.ViewHolder{
            CardView cardView;
            ImageView mealImage;
            TextView mealName;
            View itemview;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                itemview = itemView;
                cardView = itemView.findViewById(R.id.cardCategory);
                mealImage = itemView.findViewById(R.id.pagerimageview);
                mealName = itemView.findViewById(R.id.categoryName);

            }
        }

        public void setList(List<IngeriedientPojo> ingeriedientPojos) {
            this.ingeriedientPojos = ingeriedientPojos;
        }


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.searchitem,parent,false);
           myViewHolder viewHolder = new  myViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientSearchAdapter.myViewHolder holder, int position) {

            if (ingeriedientPojos != null && position >= 0 && position < ingeriedientPojos.size()) {
                IngeriedientPojo item = ingeriedientPojos.get(position);
                // Set the meal name to the TextView
                holder.mealName.setText(ingeriedientPojos.get(position).getStrIngredient());

                // Load the meal image using Glide
                Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + ingeriedientPojos.get(position).getStrIngredient() + ".png")
                        .apply(new RequestOptions().override(holder.mealImage.getWidth(), holder.mealImage.getHeight()))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.mealImage);


                // Set an OnClickListener for the card view
                holder.cardView.setOnClickListener(event ->
                        searchClickListner.ingredientItemOnclick(ingeriedientPojos.get(position).getStrIngredient()));
            } else {
                Log.e("IngredientSearchAdapter", "onBindViewHolder: Invalid position or null list");
            }

        }




        @Override
        public int getItemCount() {
            return ingeriedientPojos != null ? ingeriedientPojos.size() : 0;
        }



    }


