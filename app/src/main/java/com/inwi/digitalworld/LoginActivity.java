package com.inwi.digitalworld;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inwi.digitalworld.util.Constant;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login, btn_sign_up;

    private EditText edt_user, edt_password;

    private final int ACTIVITY_SIGN_UP = 1;

    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        edt_user = findViewById(R.id.edt_user);
        edt_password = findViewById(R.id.edt_password);

        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);

        myPreferences = getSharedPreferences(Constant.PREFERENCE,MODE_PRIVATE);

        String user = myPreferences.getString("user", "");
        String password = myPreferences.getString("password", "");

        if (!user.equals("") && !password.equals("")) {
            toLogin(user, password);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String user = edt_user.getText().toString();
                String password = edt_password.getText().toString();

                Log.e("USER", user);
                Log.e("PASSWORD", password);

                if (user.equals("admin@admin.com") && password.equals("admin")) {
                    toLogin(user, password);
                }
                else {
                    Toast.makeText(this, getResources().getString(R.string.txt_login_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void toLogin(String user, String password){

        Toast.makeText(this, getResources().getString(R.string.txt_logged), Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString("user", user);
        editor.putString("password", password);
        editor.commit();

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("password", password);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_SIGN_UP){

            if (resultCode == Activity.RESULT_OK) {

                String user = data.getStringExtra("email");
                String password = data.getStringExtra("password");

                toLogin(user, password);

            }
        }
    }


}