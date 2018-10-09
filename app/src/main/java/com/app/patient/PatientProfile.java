package com.app.patient;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PatientProfile extends AppCompatActivity{

    public static final String AGE_KEY = "age";
    public static final String GENDER_KEY = "gender";
    public static final String PHONE_KEY = "phone";
    public static final String EMAIL_KEY = "email";
    public static final String HEIGHT_KEY = "height";
    public static final String WEIGHT_KEY = "weight";
    public static final String DOCTOR_KEY = "doctor";
    public static final String MEDCONDITION_KEY = "medCondition";

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("profile/patient");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_profile);
        }

        public void saveProfile(View view){
            EditText editAge = (EditText) findViewById(R.id.editAge);
            EditText editGender = (EditText) findViewById(R.id.editGender);
            EditText editPhone = (EditText) findViewById(R.id.editPhone);
            EditText editEmail = (EditText) findViewById(R.id.editEmail);
            EditText editHeight = (EditText) findViewById(R.id.editHeight);
            EditText editWeight = (EditText) findViewById(R.id.editWeight);
            EditText editDoctor = (EditText) findViewById(R.id.editDoctor);
            EditText editMedCondition = (EditText) findViewById(R.id.editMedConditions);

            String ageText = editAge.getText().toString();
            String genderText = editGender.getText().toString();
            String phoneText = editPhone.getText().toString();
            String emailText = editEmail.getText().toString();
            String heightText = editHeight.getText().toString();
            String weightText = editWeight.getText().toString();
            String doctorText = editDoctor.getText().toString();
            String medConditionText = editMedCondition.getText().toString();

            if(ageText.isEmpty()
                    || medConditionText.isEmpty()
                    || genderText.isEmpty()
                    || phoneText.isEmpty()
                    || emailText.isEmpty()
                    || heightText.isEmpty()
                    || weightText.isEmpty()
                    || doctorText.isEmpty())
            { return; }

            Map<String, Object> dataToSave = new HashMap<String, Object>();
            dataToSave.put(AGE_KEY, ageText);
            dataToSave.put(GENDER_KEY, ageText);
            dataToSave.put(PHONE_KEY, phoneText);
            dataToSave.put(EMAIL_KEY, emailText);
            dataToSave.put(HEIGHT_KEY, heightText);
            dataToSave.put(WEIGHT_KEY, weightText);
            dataToSave.put(DOCTOR_KEY, doctorText);
            dataToSave.put(MEDCONDITION_KEY, medConditionText);

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

