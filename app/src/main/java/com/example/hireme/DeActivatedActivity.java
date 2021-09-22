package com.example.hireme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class DeActivatedActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnWelcome;
    AppCompatImageView ivLogo;
    TextView tvMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_activated);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");

        initView();
        initListeners();
        tvMsg.setText(userName+ "'s Account De-Activated Successfully." );
    }
    public void initView(){
        btnWelcome = (Button) findViewById(R.id.btnWelcome);
        tvMsg = findViewById(R.id.tvMsg);
        ivLogo = findViewById(R.id.ivLogo);
    }
    private void initListeners() {
        btnWelcome.setOnClickListener(this);
        ivLogo.setOnClickListener(this);
    }
@Override
public void onClick(View v) {
    switch (v.getId()) {
        case R.id.ivLogo:
            Intent intent = new Intent(DeActivatedActivity.this, UserManagementActivity.class);
            startActivity(intent);
            break;
        case R.id.btnWelcome:
            finish();
            break;
    }
}
}

