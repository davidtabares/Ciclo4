package com.inwi.digitalworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TermsActivity extends AppCompatActivity {

    private Button btn_terms;
    private TextView textView_activity_terms,textView_activity_terms2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        btn_terms = findViewById(R.id.btn_terms);
        textView_activity_terms = findViewById(R.id.textView_activity_terms);
        textView_activity_terms.setMovementMethod(new ScrollingMovementMethod());

    }

    public void onCheck(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }
}