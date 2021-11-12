package com.inwi.digitalworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private CheckBox chb_terms;
    private Button btn_user_registration;
    private TextView textView_terms;
    private EditText edt_password_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        chb_terms = findViewById(R.id.chb_terms);
        btn_user_registration = findViewById(R.id.btn_user_registration);
        edt_password_sign_up = findViewById(R.id.edt_password);

        btn_user_registration.setEnabled(false);


        chb_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btn_user_registration.setEnabled(isChecked);
            }
        });

        btn_user_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edt_password_sign_up.getText().toString();
                if(password.length() < 8 && !isValidPassword(password)){
                    Toast.makeText(SignUpActivity.this, "@string/txt_invalid_password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "@string/txt_valid_password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public void onTerms(View view) {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);

    }
}