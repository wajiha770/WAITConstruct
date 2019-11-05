package com.example.wait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class Selection1 extends AppCompatActivity {

    ImageButton proceed;
    public static int option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection1);
        proceed=findViewById(R.id.next);
        proceed();
    }

    private void proceed()
    {
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option==0) {
                    Intent proceed = new Intent(getApplicationContext(), Arview1.class);
                    startActivity(proceed);
                }
                else if(option==1){
                    Intent proceed = new Intent(getApplicationContext(), Arview.class);
                    startActivity(proceed);
                }
                else if(option==2)
                {
                    Intent proceed = new Intent(getApplicationContext(), MeasureDistance.class);
                    startActivity(proceed);
                }
                else {
                    Toast.makeText(Selection1.this, "You Must Choose One Option", LENGTH_SHORT).show();
                }
            }
        });
    }

    public void radioClick(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.own:
                if(checked)
                {
                    option=0;
                }
                break;

            case R.id.prebuilt:
                if(checked)
                {
                    option=1;
                }
                break;
            case R.id.plotSize:
                if(checked)
                {
                    option=2;
                }
        }
    }
}