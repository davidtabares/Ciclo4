package com.inwi.digitalworld.ui.products;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inwi.digitalworld.DetailProductActivity;
import com.inwi.digitalworld.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductFragment extends Fragment {

    //private TextView tev_product;
    //private Spinner spn_categories;

    private RecyclerView rev_products;
    private RecyclerView.Adapter myAdapter;

    private String products = "[{\"name\":\"Desktop\",\"category\":\"Home Basic\",\"value\":1500000,\"instock\":false,\"description\":\"Desktop computer for basic home use.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_910064-MCO44655574490_012021-V.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"Desktop\",\"category\":\"Gaming\",\"value\":4500000,\"instock\":false,\"description\":\"Gaming desktop computer so you can play without any problem.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_715593-MCO45728605208_042021-V.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"AIO\",\"category\":\"Home Basic\",\"value\":1800000,\"instock\":true,\"description\":\"AIO desktop computer for basic home use and forget about annoying cables.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_633207-MCO44266638866_122020-O.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"CPU Gamer\",\"category\":\"Gamer\",\"value\":2500000,\"instock\":true,\"description\":\"Gamer CPU so you can focus on winning and not on your computer's performance.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_944242-MCO47817355191_102021-V.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"AIO Lenovo\",\"category\":\"Advanced\",\"value\":2500000,\"instock\":true,\"description\":\"All-in-one desktop computer, for advanced use and better efficiency in your work.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_672650-MCO43684434312_102020-V.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}}]},{\"name\":\"Asus TUF Gaming\",\"category\":\"Laptop Gamer\",\"value\":3800000,\"instock\":true,\"description\":\"ASUS TUF laptop, so you can play wherever you want and without any inconvenience.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_800226-MCO44047700886_112020-O.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"Combo Gamer\",\"category\":\"Gamer\",\"value\":180000,\"instock\":true,\"description\":\"Gamer combo for you to have the best comfort while you play what you like the most.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_966884-MCO40842599303_022020-O.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"Headphones Gamer\",\"category\":\"Gamer\",\"value\":250000,\"instock\":false,\"description\":\"Gamer headphones so you can concentrate and live the atmosphere of every moment of the game.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_753719-MLA45667958896_042021-O.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"Computer monitor\",\"category\":\"Gamer\",\"value\":600000,\"instock\":true,\"description\":\"Samsung monitor so you have a better view of what you are doing.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_821834-MLA45731686514_042021-O.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]},{\"name\":\"TV 4K\",\"category\":\"Home\",\"value\":5600000,\"instock\":true,\"description\":\"4K TV for you to live the experience of the new video format from the comfort of your home.\",\"image\":\"https://http2.mlstatic.com/D_NQ_NP_915662-MCO31697281687_082019-V.jpg\",\"branchOffice\":[{\"name\":\"Digital World Bogotá\",\"address\":\"Av 17 #21-30\",\"manager\":{\"name\":\"David Tabares\"}},{\"name\":\"Digital World Medellín\",\"address\":\"Carrera 20 #70-45\",\"manager\":{\"name\":\"Caterine Gallego\"}}]}]";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product,container,false);
        //tev_product = root.findViewById(R.id.tev_product);
        //spn_categories = root.findViewById(R.id.spn_categories);

        String[] categories = new String[]{"Home", "Home Basic", "Gamer"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                categories);

        /*spn_categories.setAdapter(adapter);

        spn_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String category = spn_categories.getSelectedItem().toString();
                tev_product.setText(category);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        rev_products = root.findViewById(R.id.rev_products);
        rev_products.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            JSONArray jsonProducts = new JSONArray(products);

            myAdapter = new ProductsAdapter(jsonProducts, getActivity());

            rev_products.setAdapter(myAdapter);

            JSONObject product0 = jsonProducts.getJSONObject(0);

            String name = product0.getString("name");

            JSONArray branchOffice = product0.getJSONArray("branchOffice");

            JSONObject branchOffice1 = branchOffice.getJSONObject(1);

            String nameBranchOffice = branchOffice1.getString("name");

            //Toast.makeText(getActivity(), "NAME: "+ nameBranchOffice, Toast.LENGTH_SHORT).show();

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return root;

    }

}

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray products;
    private Activity myActivity;

    public ProductsAdapter(JSONArray products, Activity myActivity) {
        this.products = products;
        this.myActivity = myActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            Log.e("POSITION", "POS: " + position);
            String name = this.products.getJSONObject(position).getString("name");
            String category = this.products.getJSONObject(position).getString("category");
            int value = this.products.getJSONObject(position).getInt("value");
            String description = this.products.getJSONObject(position).getString("description");
            String image = this.products.getJSONObject(position).getString("image");

            holder.tev_item_name.setText(name);
            holder.tev_item_category.setText(category);
            holder.tev_item_value.setText("$" + value);

            if (image.equals("digital_world")) {

                holder.imv_item_product.setImageResource(myActivity.getResources().getIdentifier(image, "drawable", myActivity.getPackageName()));

                holder.imv_item_product.setImageDrawable(myActivity.getDrawable(R.drawable.digital_world));
            }
            else {
                Glide.with(myActivity)
                        .load(image)
                        .placeholder(new ColorDrawable(Color.BLACK))
                        .into(holder.imv_item_product);
            }

            holder.imv_item_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Log.e("PRODUCTS", products.getJSONObject(position).toString());
                        Intent intent = new Intent(myActivity, DetailProductActivity.class);

                        intent.putExtra("product", products.getJSONObject(position).toString());

                        myActivity.startActivity(intent);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        catch (JSONException e) {
            holder.tev_item_name.setText("Error");
        }

    }

    @Override
    public int getItemCount() {
        Log.e("NUMBER_PRODUCTS", "" + this.products.length());
        return this.products.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tev_item_name;
        private TextView tev_item_category;
        private TextView tev_item_value;
        private TextView tev_item_description;
        private ImageView imv_item_product;
        public ViewHolder(View v) {
            super(v);
            tev_item_name = v.findViewById(R.id.tev_item_name);
            tev_item_category = v.findViewById(R.id.tev_item_category);
            tev_item_value = v.findViewById(R.id.tev_item_value);
            tev_item_description = v.findViewById(R.id.tev_detail_description);
            imv_item_product = v.findViewById(R.id.imv_item_product);
        }
    }
}