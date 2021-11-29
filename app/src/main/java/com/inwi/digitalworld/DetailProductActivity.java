package com.inwi.digitalworld;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView imv_detail_image;
    private TextView tev_detail_name;
    private TextView tev_detail_category;
    private TextView tev_detail_value;
    private TextView tev_detail_stock;
    private TextView tev_detail_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        imv_detail_image = findViewById(R.id.imv_detail_image);
        tev_detail_name = findViewById(R.id.tev_detail_name);
        tev_detail_category = findViewById(R.id.tev_detail_category);
        tev_detail_value = findViewById(R.id.tev_detail_value);
        tev_detail_stock = findViewById(R.id.tev_detail_stock);
        tev_detail_description = findViewById(R.id.tev_detail_description);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.e("PRODUCT_RECEIVED", bundle.getString("product"));

            try {
                JSONObject product = new JSONObject(bundle.getString("product"));

                String name = product.getString("name");
                String category = product.getString("category");
                String value = product.getString("value");
                boolean instock = product.getBoolean("instock");
                String description = product.getString("description");
                String image = product.getString("image");

                tev_detail_name.setText(name);
                tev_detail_category.setText(category);
                tev_detail_value.setText("$" + value);
                tev_detail_description.setText(description);

                if (instock = true) {
                    tev_detail_stock.setText("Product in stock");
                    tev_detail_stock.setTextColor(Color.GREEN);
                }
                else {
                    tev_detail_stock.setText("Product out of stock");
                    tev_detail_stock.setTextColor(Color.RED);
                }

                Glide.with(this)
                        .load(image)
                        .placeholder(new ColorDrawable(Color.LTGRAY))
                        .into(imv_detail_image);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}