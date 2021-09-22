package com.example.hireme;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hireme.SQL.dbHelper;
import com.example.hireme.model.User;
import com.example.hireme.model.UserListData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoCompleteTextView tvEmail;
    private EditText etPassword;
    private Button btnLogin;
    private LinearLayout btnRegister;
    private TextView btnForgotPassword;
    private LinearLayout llLogin;
    private dbHelper databaseHelper;
    private UserListData user;
    private UserLoginTask mAuthTask = null;
    private FirebaseAuth mAuth;
    private ArrayList<UserListData> userlists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initListeners();
        initObjects();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    public void initView(){

        tvEmail = (AutoCompleteTextView) findViewById(R.id.tvEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (LinearLayout) findViewById(R.id.btnRegister);
        btnForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);
        llLogin = (LinearLayout)findViewById(R.id.llLogin);

    }
    private void initObjects() {
        databaseHelper = new dbHelper(this);
    }
    private void initListeners() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }
    private void Login(){
        try {
            String email = tvEmail.getText().toString();
            String password = etPassword.getText().toString();
            if (email.length() > 0 && password.length() > 0) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                     //getAllUsers();
                                    Intent intent = new Intent(LoginActivity.this, UserManagementActivity.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("ARRAYLIST",(Serializable)userlists);
                                    intent.putExtra("BUNDLE",args);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Snackbar snackbar = Snackbar.make(llLogin,"Email and/or Password is incorrect",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });
//                mAuthTask = new UserLoginTask(email, password);
//                mAuthTask.execute((Void) null);
            } else {
                Snackbar snackbar = Snackbar.make(llLogin,"Please Enter Valid Email and/or Password",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }catch (Exception ex){
            Toast.makeText(LoginActivity.this, "Got an Error",
                    Toast.LENGTH_LONG).show();
        }
    }
    public ArrayList<UserListData> getAllUsers(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference usersdRef = rootRef.child("users");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = ds.child("name").getValue(String.class);
//                    String email = ds.child("email").getValue(String.class);

                    Log.d("TAG", name);
                    UserListData userdata = new UserListData();
                    userdata.setTitle(name);
//                    userdata.setEmail(email);
                    userdata.setExpanded(false);
                    userlists.add(userdata);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
        return userlists;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnLogin:
                Login();
                break;
            case R.id.btnRegister:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btnForgotPassword:
                intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
        }

    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                List<User> users =  databaseHelper.getAllUser();
                // Simulate network access.
                if (databaseHelper.checkUser(mEmail.toString().trim(), mPassword.toString().trim())) {

                    Intent confirmIntent = new Intent(LoginActivity.this, UserManagementActivity.class);
                    confirmIntent.putExtra("EMAIL", tvEmail.getText().toString().trim());
                    //emptyInputEditText();
                    startActivity(confirmIntent);
                    Snackbar.make(llLogin, "Login Succesfull", Snackbar.LENGTH_LONG).show();
                    Thread.sleep(2000);

                } else {
                    // Snack Bar to show success message that record is wrong
                    Snackbar.make(llLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                    Thread.sleep(2000);
                    return false;

                }
            } catch (Exception e) {
                return false;
            }

/*            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                Snackbar.make(llLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                //Thread.sleep(2000);
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            Snackbar.make(llLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            //showProgress(false);
        }
    }
}