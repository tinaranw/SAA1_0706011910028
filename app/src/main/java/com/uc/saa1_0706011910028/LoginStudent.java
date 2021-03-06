package com.uc.saa1_0706011910028;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginStudent extends AppCompatActivity implements TextWatcher {

    TextInputLayout studentEmail, studentPassword;
    String email, password;
    Button login;
    Toolbar toolbar;
    FirebaseAuth fAuth;
    Dialog dialog;
    DatabaseReference dbUsers;
    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        studentEmail = findViewById(R.id.studentEmailLoginInput);
        studentPassword = findViewById(R.id.studentPasswordLoginInput);
        fAuth = FirebaseAuth.getInstance();

        dialog = Glovar.loadingDialog(LoginStudent.this);
        dbUsers = FirebaseDatabase.getInstance().getReference("student");
        userPref = getSharedPreferences("user", MODE_PRIVATE);
        userEditor = userPref.edit();

        studentEmail.getEditText().addTextChangedListener(this);
        studentPassword.getEditText().addTextChangedListener(this);

        toolbar = findViewById(R.id.studentLoginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        login = findViewById(R.id.studentLoginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task2) {
                                String utoken= task2.getResult();
                                dbUsers.child(fAuth.getCurrentUser().getUid()).child("token").setValue(utoken);
                                userEditor.putString("utoken", utoken);
                                userEditor.commit();
                                Toast.makeText(LoginStudent.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginStudent.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    } else {
                        Toast.makeText(LoginStudent.this, "Failed to log in!", Toast.LENGTH_SHORT).show();
                }
                }
            }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.cancel();
                        Toast.makeText(LoginStudent.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(LoginStudent.this, Starter.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        startActivity(intent);
        finish();
    }
}