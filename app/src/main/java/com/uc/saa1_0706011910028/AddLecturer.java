package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.saa1_0706011910028.model.Lecturer;

public class AddLecturer extends AppCompatActivity implements TextWatcher {

    TextInputLayout lecturerName, lecturerExpertise;
    String name, expertise;
    Button addLecturer;
    Toolbar toolbar;
    RadioGroup lecturerGender;
    RadioButton radioButtonGender;
    String gender;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        lecturerName = findViewById(R.id.lecturerNameInput);
        lecturerExpertise = findViewById(R.id.lecturerExpertiseInput);

        lecturerName.getEditText().addTextChangedListener(this);
        lecturerExpertise.getEditText().addTextChangedListener(this);

        toolbar = findViewById(R.id.addLecturerToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lecturerGender = findViewById(R.id.lecturerGenderRadioGroup);
        lecturerGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButtonGender = findViewById(i);
                gender = radioButtonGender.getText().toString();
            }
        });

        addLecturer = findViewById(R.id.addLecturerBtn);
        addLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = lecturerName.getEditText().getText().toString().trim();
                expertise = lecturerExpertise.getEditText().getText().toString().trim();
                addLecturer(name, gender, expertise);
            }
        });

    }

    public void addLecturer(String mnama, String mgender, String mexpertise){
        String mid = mDatabase.child("lecturer").push().getKey();
        Lecturer lecturer = new Lecturer(mid, mnama, mgender, mexpertise);
        mDatabase.child("lecturer").setValue(lecturer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(AddLecturer.this,Starter.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        name = lecturerName.getEditText().getText().toString().trim();
        expertise = lecturerExpertise.getEditText().getText().toString().trim();

        if(!name.isEmpty() && !expertise.isEmpty()){
            addLecturer.setEnabled(true);
        } else {
            addLecturer.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}