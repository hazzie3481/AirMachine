package com.example.hireme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ForgotPasswordActivity  extends AppCompatActivity implements View.OnClickListener  {
    private EditText etEmail;
    private Button btnResetPassword;
    private LinearLayout btnLogin;
    private ConstraintLayout llLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initView();
        initListeners();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //if(currentUser != null){
            //reload();
        //}
    }
    public void initView(){


        etEmail = (EditText) findViewById(R.id.etEmailAddress);
        btnResetPassword = (Button) findViewById(R.id.btnResetPasswrod);
        btnLogin = (LinearLayout) findViewById(R.id.llSignIn);
        llLogin = (ConstraintLayout)findViewById(R.id.llLogin);
    }
    private void initListeners() {
        btnLogin.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.llSignIn:
                intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnResetPasswrod:
                if(etEmail.getText().toString().trim().length()>0) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = etEmail.getText().toString().trim();
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Snackbar snackbar = Snackbar.make(llLogin, "An Email has been sent to your email address. Follow the steps to reset password.", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                    else{
                                        Snackbar snackbar = Snackbar.make(llLogin, "Email could not be sent. Please try again and double if the email is valid.", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                            });
                }
                else {
                    Snackbar snackbar = Snackbar.make(llLogin, "Please Enter Email.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
        }
    }
}
