package com.example.wait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton getstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getstarted =findViewById(R.id.getstarted);
        getstarted.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent getstarted = new Intent(getApplicationContext(), Selection1.class);
        startActivity(getstarted);
    }
}