package com.app.patient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DoctorFeedback extends AppCompatActivity {

    TextView tv;
    TextView tv2;
    String st;
    String st2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_feed_back);

        tv = findViewById(R.id.textView);

        st = getIntent().getExtras().getString("Value");
        tv.setText(st);
///////////////////////////////////////////

        tv2 = findViewById(R.id.textView2);

        st2 = getIntent().getExtras().getString("Value");
        tv2.setText(st2);

    }
}
