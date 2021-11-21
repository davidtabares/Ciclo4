package com.inwi.digitalworld.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inwi.digitalworld.database.dao.UserDAO;
import com.inwi.digitalworld.database.model.User;
import com.inwi.digitalworld.util.Constant;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();

    private static UserDatabase userDB;

    public static UserDatabase getInstance(Context context) {

        if (userDB == null) {
            userDB = buildDatabaseBuilder(context);
        }

        return userDB;

    }

    private static UserDatabase buildDatabaseBuilder(Context context) {

        return Room.databaseBuilder(context, UserDatabase.class, Constant.NAME_DATABASE)
                .allowMainThreadQueries()
                .build();

    }

}
