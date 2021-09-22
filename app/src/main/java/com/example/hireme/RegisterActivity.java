package com.example.hireme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hireme.SQL.dbHelper;
import com.example.hireme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private NestedScrollView nestedScrollView;
    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextMobileNumber;
    private LinearLayout tvSignIn;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private dbHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initViews();
        initListeners();
        initObjects();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
        }

    }
    private void initObjects() {

        databaseHelper = new dbHelper(this);
        user = new User();
    }
    private void initViews(){
        textInputEditTextFirstName = (TextInputEditText) findViewById(R.id.etFName);
        textInputEditTextLastName = (TextInputEditText) findViewById(R.id.etFName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.etEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.etPassword);
        textInputEditTextMobileNumber = (TextInputEditText) findViewById(R.id.etMobileNumber);
        tvSignIn = (LinearLayout) findViewById(R.id.tvSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nScrollView);
    }
    private void initListeners() {
        btnSignUp.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
    }
    private void CreateUser(){
        try {

            String mEmail = textInputEditTextEmail.getText().toString().trim();
            String mPassword = textInputEditTextPassword.getText().toString().trim();
//        Toast.makeText(RegisterActivity.this, "Create User Clcik =",
//              Toast.LENGTH_LONG).show();
            String mConfirmPassword = textInputEditTextMobileNumber.getText().toString().trim();
            if (mPassword.length() > 0 && mEmail.length() > 0) {
                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    goToLogin();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Snackbar snackbar = Snackbar.make(nestedScrollView,"Email and/or Password is incorrect",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });
//                user.setFirstName(textInputEditTextFirstName.getText().toString().trim());
//                user.setLasstName(textInputEditTextLastName.getText().toString().trim());
//                user.setEmail(textInputEditTextEmail.getText().toString().trim());
//                user.setPassword(textInputEditTextPassword.getText().toString().trim());
//                user.setMobileNo(textInputEditTextMobileNumber.getText().toString().trim());
//                databaseHelper.addUser(user);
//                goToLogin();
            } else {
                Snackbar snackbar = Snackbar.make(nestedScrollView, "Please Enter valid Email and/or Password", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        catch (Exception ex){
            Snackbar snackbar = Snackbar.make(nestedScrollView, "Something Went Wrong", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    private void goToLogin(){
        Intent intent = new Intent( RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                CreateUser();
                break;
            case R.id.tvSignIn:
                goToLogin();
                 break;
        }
    }
}
