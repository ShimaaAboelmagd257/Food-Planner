package com.example.foodplanner.homeforyou.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Pojos.CategoriesPojo;

import java.util.ArrayList;
import java.util.List;

    public class RecycleCategoryAdepter extends RecyclerView.Adapter<RecycleCategoryAdepter.ViewHolder> {

        private final Context context;
        private List<CategoriesPojo> list;
        public static final String TAG = "RECYCLER";
        String [] catedoryesFlags;
        HomeOnClickListner homeOnClickListner;
        public static class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView imageView;
            public TextView name;
            public CardView cardItem;
            public View view;

            public ViewHolder(View v){
                super(v);
                view = v;
                imageView = v.findViewById(R.id.imageCategory);
                name = v.findViewById(R.id.countyName);
                cardItem = v.findViewById(R.id.cardCategory);

            }

        }
        public void setCategoryModelList(List<CategoriesPojo> CategoryList) {
            this.list = CategoryList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.category_raw,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }


        public RecycleCategoryAdepter(Context context, ArrayList<CategoriesPojo> list, HomeOnClickListner homeOnClickListner) {
            this.context = context;
            this.list = list;
            this.homeOnClickListner = homeOnClickListner;
            catedoryesFlags =context.getResources().getStringArray(R.array.flags);
        }




        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(context).load(list.get(position).getStrCategoryThumb())
                    .apply(new RequestOptions().override(holder.imageView.getWidth(),holder.imageView.getHeight()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView);
            holder.cardItem.setOnClickListener(event ->
                    homeOnClickListner.categoryItemOnClick(list.get(position).getStrCategory()));
            /*holder.cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionHomePageFragmentToCategoryFragment action= HomePageFragmentDirections
                           .actionHomePageFragmentToCategoryFragment(list.get(position).getStrCategory());
                    Navigation.findNavController(v).navigate(action);
                }
            });*/

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }


