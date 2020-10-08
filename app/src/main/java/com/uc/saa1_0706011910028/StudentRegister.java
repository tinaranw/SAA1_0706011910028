package com.uc.saa1_0706011910028;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.saa1_0706011910028.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentRegister extends AppCompatActivity implements TextWatcher {

//    TextInputLayout studentEmail, studentPassword, studentFullName, studentNIM, studentAge, studentAddress;
//    String email, password, fname, nim, age, address, gender;
//    Button register;
//    RadioGroup studentGender;
//    RadioButton studentFemale;
//    RadioButton studentMale;
//    Toolbar toolbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student_register);
//
//        studentEmail = findViewById(R.id.studentEmailRegisterInput);
//        studentPassword = findViewById(R.id.studentPasswordRegisterInput);
//        studentFullName = findViewById(R.id.studentFullNameRegisterInput);
//        studentNIM = findViewById(R.id.studentNIMRegisterInput);
//        studentAge = findViewById(R.id.studentAgeRegisterInput);
//        studentAddress = findViewById(R.id.studentAddressRegisterInput);
//
//
//        studentEmail.getEditText().addTextChangedListener(this);
//        studentPassword.getEditText().addTextChangedListener(this);
//        studentFullName.getEditText().addTextChangedListener(this);
//        studentNIM.getEditText().addTextChangedListener(this);
//        studentAge.getEditText().addTextChangedListener(this);
//        studentAddress.getEditText().addTextChangedListener(this);
//
//        register = findViewById(R.id.studentRegisterBtn);
//
//        toolbar = findViewById(R.id.studentRegisterToolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        email = studentEmail.getEditText().getText().toString().trim();
//        password = studentPassword.getEditText().getText().toString().trim();
//        fname = studentFullName.getEditText().getText().toString().trim();
//        nim = studentNIM.getEditText().getText().toString().trim();
//        age = studentAge.getEditText().getText().toString().trim();
//        address = studentAddress.getEditText().getText().toString().trim();
//
//        if(!email.isEmpty() && !password.isEmpty() && !fname.isEmpty() && !nim.isEmpty() && !age.isEmpty() && !address.isEmpty()){
//            register.setEnabled(true);
//        } else {
//            register.setEnabled(false);
//        }
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }

    Toolbar bar;
    Dialog dialog;
    TextInputLayout input_email, input_pass, input_name, input_nim, input_age, input_address;
    RadioGroup rg_gender;
    RadioButton radioButton;
    RadioButton input_male, input_female;
    Button btn_register;
    String uid="", email="", pass="", name="", nim="", age="", gender="male",address="", action="";
    Student student;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        bar = findViewById(R.id.studentRegisterToolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog = Glovar.loadingDialog(StudentRegister.this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("student");

        input_email = findViewById(R.id.studentEmailRegisterInput);
        input_pass = findViewById(R.id.studentPasswordRegisterInput);
        input_name = findViewById(R.id.studentFullNameRegisterInput);
        input_nim = findViewById(R.id.studentNIMRegisterInput);
        input_age = findViewById(R.id.studentAgeRegisterInput);
        input_address = findViewById(R.id.studentAddressRegisterInput);

        input_male = findViewById(R.id.studentMaleRadioButton);
        input_female = findViewById(R.id.studentFemaleRadioButton);



        input_email.getEditText().addTextChangedListener(this);
        input_pass.getEditText().addTextChangedListener(this);
        input_name.getEditText().addTextChangedListener(this);
        input_nim.getEditText().addTextChangedListener(this);
        input_age.getEditText().addTextChangedListener(this);
        input_address.getEditText().addTextChangedListener(this);

        btn_register = findViewById(R.id.studentRegisterBtn);
        rg_gender = findViewById(R.id.studentGenderRegisterRadioGroup);


        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if(action.equalsIgnoreCase("add")){
            getSupportActionBar().setTitle("Register as Student");
            btn_register.setText("Register");
            rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    radioButton = findViewById(i);
                    gender = radioButton.getText().toString();
                }
            });
            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFormValue();
                    addStudent();
                }
            });
        } else if (action.equalsIgnoreCase("edit")){
            getSupportActionBar().setTitle(R.string.editStudent);
            student = intent.getParcelableExtra("edit_data_student");
            input_email.getEditText().setFocusable(false);
            input_pass.getEditText().setFocusable(false);
            input_email.getEditText().setText(student.getEmail());
            input_pass.getEditText().setText(student.getPassword());
            input_name.getEditText().setText(student.getName());
            input_nim.getEditText().setText(student.getNim());
            input_age.getEditText().setText(student.getAge());
            input_address.getEditText().setText(student.getAddress());
            if(student.getGender().equalsIgnoreCase("male")){
//                rg_gender.check(R.id.lecturerMaleRadioButton);
                input_male.setChecked(true);
            }else{
//                rg_gender.check(R.id.lecturerFemaleRadioButton);
                input_female.setChecked(true);
            }
            rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    radioButton = findViewById(i);
                    gender = radioButton.getText().toString();
                }
            });
            btn_register.setText(R.string.editStudent);
            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    getFormValue();
                    Map<String,Object> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", pass);
                    params.put("name", name);
                    params.put("nim", nim);
                    params.put("age", age);
                    params.put("gender", gender);
                    params.put("address", address);
                    mDatabase.child(student.getUid()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.cancel();
                            Intent intent;
                            intent = new Intent(StudentRegister.this, StudentData.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    public void addStudent(){
        getFormValue();
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.cancel();
                    uid = mAuth.getCurrentUser().getUid();
                    Student student = new Student(uid, email, pass, name, nim, gender,age, address);
                    mDatabase.child(uid).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mAuth.signOut();
                            Toast.makeText(StudentRegister.this, "Student Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (StudentRegister.this, Starter.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                } else {
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthInvalidCredentialsException malFormed){
                        Toast.makeText(StudentRegister.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                    }catch(FirebaseAuthUserCollisionException malFormed){
                        Toast.makeText(StudentRegister.this, "Email already registered!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(StudentRegister.this, "Register failed!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(StudentRegister.this, Starter.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        } else if(id == R.id.studentList){
            Intent intent;
            intent = new Intent(StudentRegister.this, StudentData.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(StudentRegister.this, Starter.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void getFormValue(){
        email = input_email.getEditText().getText().toString().trim();
        pass = input_pass.getEditText().getText().toString().trim();
        name = input_name.getEditText().getText().toString().trim();
        nim = input_nim.getEditText().getText().toString().trim();
        age = input_age.getEditText().getText().toString().trim();
        address = input_address.getEditText().getText().toString().trim();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        getFormValue();
        if (!email.isEmpty() && !pass.isEmpty() && !name.isEmpty() && !nim.isEmpty() && !age.isEmpty() &&
                !address.isEmpty()){
            btn_register.setEnabled(true);
        }else{
            btn_register.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
}