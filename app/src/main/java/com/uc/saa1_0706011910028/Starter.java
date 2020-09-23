package com.uc.saa1_0706011910028;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Starter extends AppCompatActivity {

    ImageButton addLecturer, addCourse, loginStudent, registerStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        addLecturer = findViewById(R.id.addLecturerStarterBtn);
        addCourse = findViewById(R.id.addCourseStarterBtn);
        loginStudent = findViewById(R.id.loginStudentStarterBtn);
        registerStudent = findViewById(R.id.addStudentStarterBtn);

        addLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, AddLecturer.class);
                startActivity(intent);
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, AddCourse.class);
                startActivity(intent);
            }
        });

        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, LoginStudent.class);
                startActivity(intent);
            }
        });

        registerStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, StudentRegister.class);
                startActivity(intent);
            }
        });
    }
}