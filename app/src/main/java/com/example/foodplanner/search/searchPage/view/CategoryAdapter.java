package com.example.foodplanner.search.searchPage.view;

import android.content.Context;
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
import com.example.foodplanner.model.Pojos.CategoriesPojo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.myViewHolder> {

    private final Context context;
    private  List<CategoriesPojo> categoriesPojoList;
    SearchClickListner searchClickListner;


    public CategoryAdapter(Context context, List<CategoriesPojo> categoriesPojoList, SearchClickListner searchClickListner) {
        this.context = context;
        this.categoriesPojoList = categoriesPojoList;
        this.searchClickListner = searchClickListner;
    }

    public CategoryAdapter(Context context, List<CategoriesPojo> categoriesPojoList) {
        this.context = context;
        this.categoriesPojoList = categoriesPojoList;
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

    public void setList(List<CategoriesPojo> categorylist) {
        this.categoriesPojoList = categorylist;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public CategoryAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.searchitem,parent,false);
        CategoryAdapter.myViewHolder viewHolder = new  CategoryAdapter.myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.myViewHolder holder, int position) {


        CategoriesPojo item = categoriesPojoList.get(position);
        // Set the meal name to the TextView
        holder.mealName.setText(categoriesPojoList.get(position).getStrCategory());

        // Load the meal image using Glide
        Glide.with(context).load(categoriesPojoList.get(position).getStrCategoryThumb())
                .apply(new RequestOptions().override(holder.mealImage.getWidth(),holder.mealImage.getHeight()))
                .placeholder(R.drawable.defualtbackground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImage);


        // Set an OnClickListener for the card view
        holder.cardView.setOnClickListener(v -> searchClickListner.categoryItemOnClick(categoriesPojoList.get(position).getStrCategory()));


    }


    public void performSearch(String newText) {
        List<CategoriesPojo> result = new ArrayList<>();
        Observable.fromIterable(categoriesPojoList)
                .filter(e -> e.getStrCategory().contains(newText))
                .subscribe( result::add);
        setList(result);
    }

    @Override
    public int getItemCount() {
        return categoriesPojoList != null ? categoriesPojoList.size() : 0;
    }



}
