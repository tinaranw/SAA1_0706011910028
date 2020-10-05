package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(LoginStudent.this, Starter.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginStudent.this);
//            startActivity(intent, options.toBundle());
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(LoginStudent.this, Starter.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginStudent.this);
//        startActivity(intent, options.toBundle());
        startActivity(intent);
        finish();
    }
}