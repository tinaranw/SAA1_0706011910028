package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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
                intent.putExtra("action", "add");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Starter.this);
//                startActivity(intent, options.toBundle());
                startActivity(intent);
                finish();
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, AddCourse.class);
                intent.putExtra("action", "add");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Starter.this);
//                startActivity(intent, options.toBundle());
                startActivity(intent);
                finish();
            }
        });

        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, LoginStudent.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Starter.this);
//                startActivity(intent, options.toBundle());
                startActivity(intent);
                finish();
            }
        });

        registerStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Starter.this, StudentRegister.class);
                intent.putExtra("action", "add");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Starter.this);
//                startActivity(intent, options.toBundle());
                startActivity(intent);
                finish();
            }
        });
    }
}