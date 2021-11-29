package com.inwi.digitalworld.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inwi.digitalworld.database.dao.ProductDAO;
import com.inwi.digitalworld.database.model.Product;
import com.inwi.digitalworld.util.Constant;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDAO getProductDAO();

    private static ProductDatabase productDB;

    public static ProductDatabase getInstance(Context context) {

        if (productDB == null) {
            productDB = buildDatabaseBuilder(context);
        }

        return productDB;

    }

    private static ProductDatabase buildDatabaseBuilder(Context context) {

        return Room.databaseBuilder(context, ProductDatabase.class, Constant.NAME_DATABASE)
                .allowMainThreadQueries()
                .build();

    }

}