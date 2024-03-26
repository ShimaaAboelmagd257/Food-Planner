package com.example.foodplanner.homeforyou.view;

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
import com.example.foodplanner.R;
import com.example.foodplanner.mealPreparation.view.MealPreparation;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.splashEntrance.view.EntrancePage;
import com.example.foodplanner.utility.Helper.Helper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerRandomAdapter extends RecyclerView.Adapter<RecyclerRandomAdapter.ViewHolder> {

    private final Context context;
    private List<ProductsPOJO> list;
    public static final String TAG = "RECYCLER";
    String [] pagersFlags;
    private int visibleCardPosition = 0;
   private HomeOnClickListner homeOnClickListner ;

    public RecyclerRandomAdapter(Context context, List<ProductsPOJO> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView ;
        public TextView mealname ;
        public ImageView cardFavorate ;
        public CardView cardBottom ;
        public View view;
        //private OnClickListener listener;
        public ViewHolder(View itemView){
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.mealImage);
            mealname = itemView.findViewById(R.id.MealName);
            cardBottom = itemView.findViewById(R.id.cardBottom);
            cardFavorate = itemView.findViewById(R.id.mealFav);

        }

    }
    public void setViewPagerAdepterList(List<ProductsPOJO> mealModels) {
        this.list = mealModels;
    }

    public RecyclerRandomAdapter(Context context, List<ProductsPOJO> list , HomeOnClickListner homeOnClickListner){
        this.context = context;
        this.list = list;
        this.homeOnClickListner = homeOnClickListner;
        pagersFlags =context.getResources().getStringArray(R.array.flags);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mealitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.e("HomeAdapter", "onBindViewHolder : " + list.size());

        Glide.with(context).load(list.get(position).getStrMealThumb())
                        .placeholder(R.drawable.defaultbackground)
                        .error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.mealname.setText(list.get(position).getStrMeal());
// Set the layout parameters for the CardView
        ViewGroup.LayoutParams layoutParams = holder.cardBottom.getLayoutParams();
        holder.cardBottom.setOnClickListener( v -> {

                Intent intent = new Intent(holder.itemView.getContext(), MealPreparation.class);
                intent.putExtra("mealName" , list.get(holder.getAdapterPosition()).getStrMeal());
                Log.i(TAG, "onClick: "+list.get(holder.getAbsoluteAdapterPosition()).getStrMeal());
            holder.itemView.getContext().startActivity(intent);

        });
        holder.cardFavorate.setOnClickListener(event -> {

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
                homeOnClickListner.addToFavoriteOnClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
