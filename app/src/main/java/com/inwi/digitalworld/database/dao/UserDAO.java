package com.inwi.digitalworld.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inwi.digitalworld.database.model.User;
import com.inwi.digitalworld.util.Constant;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    long insertUser(User user);

    @Update
    int updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * from " + Constant.NAME_TABLE_USER)
    List<User> getUser();

    @Query("SELECT * from " + Constant.NAME_TABLE_USER + " WHERE email = :email and password = :password")
    User getUserLogin(String email, String password);

}
