package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class Starter extends AppCompatActivity {

    ImageView addLecturer, addCourse, loginStudent, registerStudent;
    TextView dateText;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        addLecturer = findViewById(R.id.addLecturerStarterBtn);
        addCourse = findViewById(R.id.addCourseStarterBtn);
        loginStudent = findViewById(R.id.loginStudentStarterBtn);
        registerStudent = findViewById(R.id.addStudentStarterBtn);
        dateText = findViewById(R.id.dateTextView);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        dateText.setText(currentDate);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent(Starter.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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