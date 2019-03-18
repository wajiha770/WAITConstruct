package com.example.wait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Selection1 extends AppCompatActivity {

    ImageButton proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection1);

        proceed=findViewById(R.id.next);



        //proceed=findViewById(R.id.next);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent proceed = new Intent(getApplicationContext(), Arview.class);
                //startActivity(proceed);
            }
        });
    }
    public void radioClick(View view)
    {//shakalaka

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.prebuilt:
                if(checked)
                {
                    Intent proceed = new Intent(getApplicationContext(), Arview.class);
                    startActivity(proceed);
                }
                break;

            case R.id.own:
                if(checked)
                {
                    Intent proceed = new Intent(getApplicationContext(), Arview1.class);
                    startActivity(proceed);
                }
                break;
        }
    }
}
