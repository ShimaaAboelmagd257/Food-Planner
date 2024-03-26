package com.example.foodplanner.model.LocalDataSource;


import android.content.Context;
import android.util.Log;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
//import androidx.room.RoomOpenHelper;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import io.reactivex.rxjava3.annotations.NonNull;


@Database( entities = {ProductsPOJO.class} , version = 2)

public abstract class ProductDataBase extends RoomDatabase{

    public static ProductDataBase productDataBase = null;
    public abstract DataAcessObjectDAO dataAcessObjectDAO() ; // to access the data base operation methods

    public static synchronized ProductDataBase getInstance(Context context) {
        if (productDataBase == null) {
            productDataBase = Room.databaseBuilder(context.getApplicationContext(),
                            ProductDataBase.class, "Meals")
                    .fallbackToDestructiveMigration()
                    
                    .build();

            Log.d("ProductDataBase", "singleton apply succeeded");
        }
        productDataBase.getOpenHelper().getWritableDatabase(); //<<<<< FORCE OPEN

        return productDataBase;
    }
}
