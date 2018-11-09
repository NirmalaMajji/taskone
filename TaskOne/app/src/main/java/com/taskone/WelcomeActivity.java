package com.taskone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button welcome_rest_btn, welcome_fav_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        intiUI();
    }


    private void intiUI() {
        welcome_rest_btn = findViewById(R.id.welcome_rest_btn);
        welcome_fav_btn = findViewById(R.id.welcome_fav_btn);

        welcome_rest_btn.setOnClickListener(this);
        welcome_fav_btn.setOnClickListener(this);
    }

    private void redirectToFavsActivity() {
        Intent intent = new Intent(this, FavsActivity.class);
        startActivity(intent);
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcome_rest_btn:
                redirectToMainActivity();
                break;
            case R.id.welcome_fav_btn:
                redirectToFavsActivity();
                break;
        }
    }
}
