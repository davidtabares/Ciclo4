package com.inwi.digitalworld.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inwi.digitalworld.database.model.Product;
import com.inwi.digitalworld.util.Constant;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    long insertProduct(Product product);

    @Update
    int updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * from " + Constant.NAME_TABLE_PRODUCT)
    List<Product> getProduct();

    @Query("SELECT * from " + Constant.NAME_TABLE_PRODUCT + " WHERE name = :name and category = :category")
    Product getProductRegister(String name, String category);



}