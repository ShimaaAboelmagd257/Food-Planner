package com.example.foodplanner.homeforyou.view;


import android.annotation.SuppressLint;
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

public class RecycleCountryAdepter extends RecyclerView.Adapter<RecycleCountryAdepter.ViewHolder> {

    private final Context context;
    private List<AreasPojo> list;
    public static final String TAG = "RECYCLER";
    String [] countriesFlags;
    HomeOnClickListner homeOnClickListner;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView name;
        public CardView cardItem;
        public View view;

        public ViewHolder(View v){
            super(v);
            view = v;
            imageView = v.findViewById(R.id.imageCounty);
            name = v.findViewById(R.id.countyName);
            cardItem = v.findViewById(R.id.cardCounty);

        }
    }
    public void setRecycleCountryAdepterList(List<AreasPojo> CountryList) {
        this.list = CountryList;
    }

    public RecycleCountryAdepter(Context context, ArrayList<AreasPojo> list , HomeOnClickListner homeOnClickListner) {
        this.context = context;
        this.list = list;
        this.homeOnClickListner= homeOnClickListner;
       countriesFlags=context.getResources().getStringArray(R.array.flags);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.country_raw ,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int index = position % countriesFlags.length;
        if (position >= 0 && position  < list.size()) {
            Glide.with(context).load(countriesFlags[index])
                    .apply(new RequestOptions().override(60, 60)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)).into(holder.imageView);
            holder.name.setText(list.get(position).getStrArea());
        }
        else {
            Log.d("recycleCountryAdapter","country out of bounds ");
        }
        //holder.cardItem.setOnClickListener(new View.OnClickListener() {
            /*@Override
            public void onClick(View v) {
                ActionHomePageFragmentToCountriesFragment action= HomePageFragmentDirections.actionHomePageFragmentToCountriesFragment(list.get(position).getStrArea());
                Navigation.findNavController(v).navigate(action);
            }
        });*/
    }//

    @Override
    public int getItemCount() {
        return list.size() ;
    }
}


