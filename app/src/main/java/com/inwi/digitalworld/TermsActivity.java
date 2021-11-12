package com.inwi.digitalworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class TermsActivity extends AppCompatActivity {

    private Button btn_terms;
    private TextView textView_activity_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        btn_terms = findViewById(R.id.btn_terms);
        textView_activity_terms = findViewById(R.id.textView_activity_terms);
        textView_activity_terms.setMovementMethod(new ScrollingMovementMethod());

    }
}