package com.example.hireme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivatedActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvMsg;
    Button btnBackToHome;
    AppCompatImageView ivLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated);
        tvMsg = findViewById(R.id.tvMsg);
        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(this);
        ivLogo = findViewById(R.id.ivLogo);
        ivLogo.setOnClickListener(this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");
        tvMsg.setText(userName+ "'s Account Activated Successfully." );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLogo:
                Intent intent = new Intent(ActivatedActivity.this, UserManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.btnBackToHome:
                finish();
                break;
        }
    }
}
