package com.uc.saa1_0706011910028;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class LoginStudent extends AppCompatActivity implements TextWatcher {

    TextInputLayout studentEmail, studentPassword;
    String email, password;
    Button login;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        studentEmail = findViewById(R.id.studentEmailLoginInput);
        studentPassword = findViewById(R.id.studentPasswordLoginInput);

        studentEmail.getEditText().addTextChangedListener(this);
        studentPassword.getEditText().addTextChangedListener(this);

        login = findViewById(R.id.studentLoginBtn);

        toolbar = findViewById(R.id.studentLoginToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        email = studentEmail.getEditText().getText().toString().trim();
        password = studentPassword.getEditText().getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()){
            login.setEnabled(true);
        } else {
            login.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}