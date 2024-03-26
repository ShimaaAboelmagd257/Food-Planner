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
import com.example.foodplanner.model.Pojos.AreasPojo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.myViewHolder> {

    public static String SKIP ;
    private final Context context;
    private  List<AreasPojo> areasPojoList;
    String [] Flags;
    SearchClickListner  searchClickListner;

    public static class myViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView mealImage;
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
    public CountryAdapter(Context context, List <AreasPojo> areasPojoList , SearchClickListner searchClickListner) {
        this.context = context;
        this.areasPojoList = areasPojoList;
        Flags = context.getResources().getStringArray(R.array.flags);
        this.searchClickListner = searchClickListner;
    }

    public CountryAdapter(Context context, List <AreasPojo> areasPojoList ) {
        this.context = context;
        this.areasPojoList = areasPojoList;}
    public void setList(List<AreasPojo> areasPojoList) {
        this.areasPojoList = areasPojoList;
    }


    @NonNull
    @Override
    public CountryAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.searchitem,parent,false);
        CountryAdapter.myViewHolder  viewHolder = new CountryAdapter.myViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.myViewHolder holder, int position) {


        AreasPojo item = areasPojoList.get(position);
        // Set the meal name to the TextView
        holder.mealName.setText(areasPojoList.get(position).getStrArea());

        // Load the meal image using Glide
        try {
            // Check if the position is within the bounds of Flags array
            if (position < Flags.length) {
                Glide.with(context).load(Flags[position])
                        .apply(new RequestOptions().override(60, 60)
                                .placeholder(R.drawable.defualtbackground)
                                .error(R.drawable.ic_launcher_foreground))
                        .into(holder.mealImage);

                holder.cardView.setOnClickListener(v -> searchClickListner.countryItemOnClick(areasPojoList.get(position).getStrArea()));
            } else {
                Log.e("CountryAdapter", "Invalid position: " + position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void performSearch(String newText) {
        if (areasPojoList != null){

        List<AreasPojo> result = new ArrayList<>();
        Observable.fromIterable(areasPojoList)
                .filter(e -> e.getStrArea().contains(newText))
                .subscribe(i -> result.add(i));

        setList(result);
        notifyDataSetChanged();
    }else {
            Log.d("CountryAdapter", "performSearch: view search meals");

        }

    }


    @Override
    public int getItemCount() {

        return areasPojoList!= null ? areasPojoList.size() : 0;    }

}

