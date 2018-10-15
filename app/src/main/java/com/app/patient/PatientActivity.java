package com.app.patient;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class PatientActivity extends AppCompatActivity  {



    Button button;
    Button button2;
    EditText text;
    EditText text2;
    String st;
    String st2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        button = findViewById(R.id.button);
        text = findViewById(R.id.edittext);

        button2 = findViewById(R.id.button2);
        text = findViewById(R.id.edittext2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientActivity.this, DoctorFeedback.class);
                st = text.getText().toString();
                i.putExtra("Value:", st);
                startActivity(i);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(PatientActivity.this, DoctorFeedback.class);
                st2 = text.getText().toString();
                i2.putExtra("Value:", st2);
                startActivity(i2);
                finish();
            }
        });
    }






};
