package com.inwi.digitalworld.ui.products;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inwi.digitalworld.util.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.inwi.digitalworld.R;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edt_product_name;
    private EditText edt_product_category;
    private EditText edt_product_value;
    private EditText edt_product_description;
    private CheckBox chb_product_instock;
    private Button btn_product_image_select;
    private Button btn_product_image_upload;
    private Button btn_product_image_take;
    private ImageView imv_product_image;
    private Button btn_add_product;

    final int OPEN_GALLERY = 1;

    Uri data1;
    String urlImage;

    private SharedPreferences myPreferences;


    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);

        edt_product_name = root.findViewById(R.id.edt_product_name);
        edt_product_category = root.findViewById(R.id.edt_product_category);
        edt_product_value = root.findViewById(R.id.edt_product_value);
        edt_product_description = root.findViewById(R.id.edt_product_description);
        chb_product_instock = root.findViewById(R.id.chb_product_instock);
        btn_product_image_select = root.findViewById(R.id.btn_product_image_select);
        btn_product_image_upload = root.findViewById(R.id.btn_product_image_upload);
        btn_product_image_take = root.findViewById(R.id.btn_product_image_take);
        imv_product_image = root.findViewById(R.id.imv_product_image);
        btn_add_product = root.findViewById(R.id.btn_add_product);

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String name = edt_product_name.getText().toString();
                String category =  edt_product_category.getText().toString();
                int value = Integer.parseInt(edt_product_value.getText().toString());
                String description =  edt_product_description.getText().toString();
                boolean instock = chb_product_instock.isChecked();

                Map<String, Object> product = new HashMap<>();
                product.put("name", name);
                product.put("category", category);
                product.put("value", value);
                product.put("description", description);
                product.put("instock", instock);
                product.put("image", urlImage);

                // Add a new document with a generated ID
                db.collection("products")
                        .add(product)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.e("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(getActivity(), getResources().getString(R.string.txt_product_added), Toast.LENGTH_SHORT).show();

                                edt_product_name.setText("");
                                edt_product_category.setText("");
                                edt_product_value.setText("");
                                chb_product_instock.setChecked(false);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TAG", "Error adding document", e);
                                Toast.makeText(getActivity(), getResources().getString(R.string.txt_product_added_error), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btn_product_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.txt_product_select)), OPEN_GALLERY);
            }
        });

        btn_product_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {

                data1 = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data1);
                    imv_product_image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.txt_product_image_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void uploadImage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        //if there is a file to upload
        if (data1 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String strDate = sdf.format(c.getTime());
            String nameImage = strDate + ".jpg";

            myPreferences = getActivity().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);

            String user = myPreferences.getString("user", "NO USER");


            StorageReference riversRef = storageReference.child(user + "/" + nameImage);
            riversRef.putFile(data1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getActivity(), getResources().getString(R.string.txt_product_image_upload), Toast.LENGTH_LONG).show();

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    urlImage = uri.toString();
                                    Log.e("URL_IMAGE", urlImage);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }

    }

}