package com.inwi.digitalworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inwi.digitalworld.database.UserDatabase;
import com.inwi.digitalworld.database.model.User;
import com.inwi.digitalworld.util.Constant;
import com.inwi.digitalworld.util.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login, btn_sign_up;

    private EditText edt_user, edt_password;

    private final int ACTIVITY_REGISTER = 1;

    private SharedPreferences myPreferences;

    private UserDatabase database;

    private List<User> listUsers;

    private FirebaseAuth myAuth;

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

        myPreferences = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);

        database = UserDatabase.getInstance(this);

        String user = myPreferences.getString("user", "");
        String password = myPreferences.getString("password", "");

        if (!user.equals("") && !password.equals("")) {
            toLogin(user, password);
        }

        new GetUserTask(this).execute();

        myAuth = FirebaseAuth.getInstance();

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

                    loginFirestore(user, Utilities.md5(password));
                    //loginFirebase(user, password);
                    //new GetUserLoginTask(this, user, Utilities.md5(password));
                    //Toast.makeText(this, getResources().getString(R.string.txt_login_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, ACTIVITY_REGISTER);
                break;
        }
    }

    public void loginFirestore(String email, String password) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("TAG", "DocumentSnapshot data: " + document.getData());

                        String userPassword = document.getData().get("password").toString();

                        if (password.equals(userPassword)) {
                            toLogin(email, password);
                        } else {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.txt_password_error), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Log.e("TAG", "No such document");
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.txt_login_wrong), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("TAG", "get failed with ", task.getException());
                }
            }
        });

    }

    public void loginFirebase(String user, String password) {
        myAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("TAG", "signInWithEmail:success");
                            FirebaseUser userFB = myAuth.getCurrentUser();
                            toLogin(user, password);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.e("TAG", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
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
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_REGISTER){

            if (resultCode == Activity.RESULT_OK) {

                String user = data.getStringExtra("email");
                String password = data.getStringExtra("password");

                toLogin(user, password);

            }
        }
    }

    private static class GetUserTask extends AsyncTask<Void, Void, List<User>> {

        private WeakReference<LoginActivity> loginActivityWeakReference;

        GetUserTask(LoginActivity context) {
            this.loginActivityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            if (loginActivityWeakReference.get() != null) {
                List<User> users = loginActivityWeakReference.get().database.getUserDAO().getUser();
                return users;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            if (users != null && users.size() > 0) {
                loginActivityWeakReference.get().listUsers = users;
            }
            super.onPostExecute(users);
        }

    }

    private static class GetUserLoginTask extends AsyncTask<Void, Void, User> {

        private String email;
        private String password;
        private WeakReference<LoginActivity> loginActivityWeakReference;

        GetUserLoginTask(LoginActivity context, String email, String password) {
            this.loginActivityWeakReference = new WeakReference<>(context);
            this.email = email;
            this.password = password;
            doInBackground();
        }

        @Override
        protected User doInBackground(Void... voids) {
            if (loginActivityWeakReference.get() != null) {
                User user = loginActivityWeakReference.get().database.getUserDAO().getUserLogin(email, password);
                onPostExecute(user);
                return user;
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {

                loginActivityWeakReference.get().toLogin(email, password);
            }
            else {
                Log.e("LOGINTASK", "LOGIN ERROR");
            }
            super.onPostExecute(user);
        }
    }


}