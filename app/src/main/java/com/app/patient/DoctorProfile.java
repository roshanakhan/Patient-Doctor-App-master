package com.app.patient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DoctorProfile extends AppCompatActivity {

    public static final String NAME_KEY = "name";
    public static final String DOCTORTYPE_KEY = "doctorType";
    public static final String GENDER_KEY = "gender";
    public static final String PHONE_KEY = "phone";
    public static final String EMAIL_KEY = "email";
    public static final String ADDRESS_KEY = "address";
    public static final String PATIENTS_KEY = "patients";
    private static final String TAG = "MainActivity";

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("profile/doctor");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile);

    }

    public void saveDocProfile(View view) {
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editType = (EditText) findViewById(R.id.editDoctorType);
        EditText editGender = (EditText) findViewById(R.id.editGender);
        EditText editPhone = (EditText) findViewById(R.id.editPhone);
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        EditText editPatients = (EditText) findViewById(R.id.editPatients);

        String nameText = editName.getText().toString();
        String doctorType = editType.getText().toString();
        String genderText = editGender.getText().toString();
        String phoneText = editPhone.getText().toString();
        String emailText = editEmail.getText().toString();
        String addressText = editAddress.getText().toString();
        String patientsText = editPatients.getText().toString();

        if(nameText.isEmpty()
                || doctorType.isEmpty()
                || genderText.isEmpty()
                || phoneText.isEmpty()
                || emailText.isEmpty()
                || addressText.isEmpty()
                || patientsText.isEmpty())
        { return; }

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(NAME_KEY, nameText);
        dataToSave.put(DOCTORTYPE_KEY, doctorType);
        dataToSave.put(GENDER_KEY, genderText);
        dataToSave.put(PHONE_KEY, phoneText);
        dataToSave.put(EMAIL_KEY, emailText);
        dataToSave.put(ADDRESS_KEY, addressText);
        dataToSave.put(PATIENTS_KEY, patientsText);

        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Document was not saved!", e);
            }
        });
    }
}
