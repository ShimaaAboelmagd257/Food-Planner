package com.example.foodplanner.search.searchPage.view;

import static com.example.foodplanner.homeforyou.view.RecyclerRandomAdapter.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IngredientAdapter extends  RecyclerView.Adapter<IngredientAdapter.myViewHolder> {

    List<IngeriedientPojo> ingeriedientPojoList ;
    Context context;
    SearchClickListner searchClickListner;

    public IngredientAdapter(Context context, List<IngeriedientPojo> ingeriedientPojoList) {
        this.context = context;
        this.ingeriedientPojoList = ingeriedientPojoList;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.ingrediant_raw,parent,false);
        return new myViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.mealName.setText(ingeriedientPojoList.get(position).getName());
        Glide.with(context).load(ingeriedientPojoList.get(position).getImg())
                .apply(new RequestOptions().override(holder.mealImage.getWidth(),holder.mealImage.getHeight()))
                .placeholder(R.drawable.defualtbackground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImage);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getting Ingredient Item ");
        return ingeriedientPojoList!= null ? ingeriedientPojoList.size() : 0;
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        CircleImageView mealImage;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.ingrediantName);
            mealImage = itemView.findViewById(R.id.imageIngrediant);

        }
    }
}
