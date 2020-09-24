package com.uc.saa1_0706011910028;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class StudentRegister extends AppCompatActivity implements TextWatcher {

    TextInputLayout studentEmail, studentPassword, studentFullName, studentNIM, studentAge, studentAddress;
    String email, password, fname, nim, age, address, gender;
    Button register;
    RadioGroup studentGender;
    RadioButton studentFemale;
    RadioButton studentMale;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        studentEmail = findViewById(R.id.studentEmailRegisterInput);
        studentPassword = findViewById(R.id.studentPasswordRegisterInput);
        studentFullName = findViewById(R.id.studentFullNameRegisterInput);
        studentNIM = findViewById(R.id.studentNIMRegisterInput);
        studentAge = findViewById(R.id.studentAgeRegisterInput);
        studentAddress = findViewById(R.id.studentAddressRegisterInput);


        studentEmail.getEditText().addTextChangedListener(this);
        studentPassword.getEditText().addTextChangedListener(this);
        studentFullName.getEditText().addTextChangedListener(this);
        studentNIM.getEditText().addTextChangedListener(this);
        studentAge.getEditText().addTextChangedListener(this);
        studentAddress.getEditText().addTextChangedListener(this);

        register = findViewById(R.id.studentRegisterBtn);

        toolbar = findViewById(R.id.studentRegisterToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        if (studentGender.getCheckedRadioButtonId() == -1)
//        {
//            gender ="";
//        }
//        else
//        {
//            if(studentFemale.isChecked())
//            {
//                gender = "female";
//            }
//            else if(studentMale.isChecked())
//            {
//                gender = "male";
//            } else {
//                gender ="";
//            }
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        email = studentEmail.getEditText().getText().toString().trim();
        password = studentPassword.getEditText().getText().toString().trim();
        fname = studentFullName.getEditText().getText().toString().trim();
        nim = studentNIM.getEditText().getText().toString().trim();
        age = studentAge.getEditText().getText().toString().trim();
        address = studentAddress.getEditText().getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty() && !fname.isEmpty() && !nim.isEmpty() && !age.isEmpty() && !address.isEmpty()){
            register.setEnabled(true);
        } else {
            register.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}