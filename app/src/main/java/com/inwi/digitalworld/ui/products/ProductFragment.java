package com.inwi.digitalworld.ui.products;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inwi.digitalworld.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductFragment extends Fragment {

    private TextView tev_product;
    private Spinner spn_categories;

    private RecyclerView rev_products;
    private RecyclerView.Adapter myAdapter;

    private String products = "[{\"nombre\":\"Guantes\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal A\",\"direccion\":\"Diección A\",\"encargado\":{\"nombre\":\"Encargado A\"}},{\"nombre\":\"Sucursal B\",\"direccion\":\"Diección B\",\"encargado\":{\"nombre\":\"Encargado B\"}}]},{\"nombre\":\"Guayos\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal C\",\"direccion\":\"Diección C\",\"encargado\":{\"nombre\":\"Encargado C\"}},{\"nombre\":\"Sucursal D\",\"direccion\":\"Diección D\",\"encargado\":{\"nombre\":\"Encargado D\"}}]},{\"nombre\":\"Balón\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Casco\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Medias\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Canilleras\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Pelota\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Tobilleras\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Guantes\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal A\",\"direccion\":\"Diección A\",\"encargado\":{\"nombre\":\"Encargado A\"}},{\"nombre\":\"Sucursal B\",\"direccion\":\"Diección B\",\"encargado\":{\"nombre\":\"Encargado B\"}}]},{\"nombre\":\"Guayos\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal C\",\"direccion\":\"Diección C\",\"encargado\":{\"nombre\":\"Encargado C\"}},{\"nombre\":\"Sucursal D\",\"direccion\":\"Diección D\",\"encargado\":{\"nombre\":\"Encargado D\"}}]},{\"nombre\":\"Balón\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Casco\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Medias\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Canilleras\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Pelota\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]},{\"nombre\":\"Tobilleras\",\"categoria\":\"Futbol\",\"precio\":40000,\"enstock\":true,\"sucursal\":[{\"nombre\":\"Sucursal E\",\"direccion\":\"Diección E\",\"encargado\":{\"nombre\":\"Encargado E\"}},{\"nombre\":\"Sucursal F\",\"direccion\":\"Diección F\",\"encargado\":{\"nombre\":\"Encargado F\"}}]}]";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product,container,false);
        tev_product = root.findViewById(R.id.tev_product);
        spn_categories = root.findViewById(R.id.spn_categories);

        String[] categories = new String[]{"Beisbol", "Atletismo", "Karate", "Salto triple"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                categories);

        spn_categories.setAdapter(adapter);

        spn_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String category = spn_categories.getSelectedItem().toString();
                tev_product.setText(category);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rev_products = root.findViewById(R.id.rev_products);
        rev_products.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            JSONArray jsonProducts = new JSONArray(products);

            myAdapter = new ProductsAdapter(jsonProducts, getActivity());

            rev_products.setAdapter(myAdapter);

            JSONObject product0 = jsonProducts.getJSONObject(0);

            String name = product0.getString("nombre");

            JSONArray sucursal = product0.getJSONArray("sucursal");

            JSONObject sucursal1 = sucursal.getJSONObject(1);

            String nombreSucursal = sucursal1.getString("nombre");

            Toast.makeText(getActivity(), "NOMBRE: "+ nombreSucursal, Toast.LENGTH_SHORT).show();

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return root;

    }

/*    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/

}

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray products;
    private Activity myActividad;

    public ProductsAdapter(JSONArray products, Activity myActividad) {
        this.products = products;
        this.myActividad = myActividad;
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

            holder.tev_item_name.setText(name);
            holder.tev_item_category.setText(category);
            holder.tev_item_value.setText("$" + value);
        } catch (JSONException e) {
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
        public ViewHolder(View v) {
            super(v);
            tev_item_name = v.findViewById(R.id.tev_item_name);
            tev_item_category = v.findViewById(R.id.tev_item_category);
            tev_item_value = v.findViewById(R.id.tev_item_value);

        }
    }
}