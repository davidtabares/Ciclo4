package com.inwi.digitalworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login, btn_sign_up;

    private EditText edt_user, edt_password;

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
                    Toast.makeText(this, getResources().getString(R.string.txt_logged), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);

                }  else {
                    Toast.makeText(this, getResources().getString(R.string.txt_login_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

}