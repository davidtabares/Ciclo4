package com.inwi.digitalworld;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inwi.digitalworld.database.UserDatabase;
import com.inwi.digitalworld.database.model.User;
import com.inwi.digitalworld.util.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private CheckBox chb_terms;
    private Button btn_user_registration;
    private TextView textView_terms;
    private EditText edt_first_name, edt_last_name, edt_email_sign_up, edt_password_sign_up;

    private Activity myActivity;

    private final int ACTIVITY_TERMS = 2;

    private UserDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        chb_terms = findViewById(R.id.chb_terms);
        btn_user_registration = findViewById(R.id.btn_user_registration);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email_sign_up = findViewById(R.id.edt_email_sign_up);
        edt_password_sign_up = findViewById(R.id.edt_password);
        textView_terms = findViewById(R.id.textView_terms);

        myActivity = this;

        btn_user_registration.setEnabled(false);
        chb_terms.setEnabled(false);


        chb_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btn_user_registration.setEnabled(isChecked);
            }
        });

        btn_user_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edt_first_name.getText().toString();
                String lastName = edt_last_name.getText().toString();
                String email = edt_email_sign_up.getText().toString();
                String password = edt_password_sign_up.getText().toString();

                if(password.length() < 8 && !isValidPassword(password)){
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.txt_invalid_password), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.txt_valid_password), Toast.LENGTH_SHORT).show();

                    User user = new User(firstName, lastName, email, Utilities.md5(password));

                    long l = database.getUserDAO().insertUser(user);

                    finish();
                }
            }
        });

        textView_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SingUpActivity.this, TerminosActivity.class);
                Intent intent = new Intent(myActivity, TermsActivity.class);
                startActivityForResult(intent, ACTIVITY_TERMS);
            }
        });

        database = UserDatabase.getInstance(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_TERMS){

            if (resultCode == Activity.RESULT_OK) {

                String state = data.getStringExtra("STATE");
                Toast.makeText(myActivity, getResources().getString(R.string.txt_terms_accept), Toast.LENGTH_SHORT).show();
                chb_terms.setChecked(true);

            }
            else {
                Toast.makeText(myActivity, getResources().getString(R.string.txt_terms_no_accept), Toast.LENGTH_SHORT).show();
                chb_terms.setChecked(false);
            }

        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

}