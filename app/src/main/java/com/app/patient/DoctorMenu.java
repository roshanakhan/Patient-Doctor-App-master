package com.app.patient;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class DoctorMenu extends AppCompatActivity {

    private static final String TAG = "DoctorMenu";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    Button btnMap;
    Button btnDoctorProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_menu);
        btnMap = (Button)findViewById(R.id.map);
        btnDoctorProfile = (Button)findViewById(R.id.profile);
    }

    public void onDoctorProfileCLick(View v){
        btnDoctorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorMenu.this, DoctorProfile.class);
                startActivity(intent);
            }
        });
    }
}
