package com.example.wait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Selection1 extends AppCompatActivity {
    Spinner dropdown;
    String [] plotsizes = {"5 Marla","7 Marla","10 Marla","12 Marla","1 Kanal"};
    Button proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection1);

        dropdown = findViewById(R.id.plotsize);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,R.layout.support_simple_spinner_dropdown_item,plotsizes);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                Toast.makeText
                        (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                        .show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        proceed=findViewById(R.id.next);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proceed = new Intent(getApplicationContext(), Arview.class);
                startActivity(proceed);
            }
        });
    }
    public void radioClick(View view)
    {//shakalaka

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.modern:
                if(checked)
                {

                }
                break;

            case R.id.urban:
                if(checked)
                {

                }
                break;
        }
    }
}
