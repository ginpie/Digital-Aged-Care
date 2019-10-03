/*
 * Copyright (C) 2019 Aged Care Project
 * Authors: Jinpei Chen & Yuzhao Li
 * Date: Aug 30, 2019
 */
package com.example.falldeteciton;

import android.content.Intent;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.falldeteciton.R;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.b);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        button2 = findViewById(R.id.b2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity(){
        Intent intent = new Intent(this, FallDetectionActivity.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent = new Intent(this, FallDetectionActivity2.class);
        startActivity(intent);
    }
}
