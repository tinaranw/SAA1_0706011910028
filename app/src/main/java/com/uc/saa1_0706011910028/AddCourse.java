package com.uc.saa1_0706011910028;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

public class AddCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {

    Spinner spinnerDay, spinnerTimeStart, spinnerTimeEnd, spinnerLecturer;
    TextInputLayout courseSubject, courseDay, courseTime, courseLecturer;
    String course;
    Button addCourse;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        spinnerDay = findViewById(R.id.courseDaySpinner);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setOnItemSelectedListener(this);


        spinnerTimeStart = findViewById(R.id.courseTimeStartSpinner);
        ArrayAdapter<CharSequence> adapterTimeStart = ArrayAdapter.createFromResource(this, R.array.timeStart,android.R.layout.simple_spinner_item);
        adapterTimeStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeStart.setAdapter(adapterTimeStart);
        spinnerTimeStart.setOnItemSelectedListener(this);

        spinnerTimeEnd = findViewById(R.id.courseTimeEndSpinner);
        ArrayAdapter<CharSequence> adapterTimeEnd = ArrayAdapter.createFromResource(this, R.array.timeEnd,android.R.layout.simple_spinner_item);
        adapterTimeEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeEnd.setAdapter(adapterTimeEnd);
        spinnerTimeEnd.setOnItemSelectedListener(this);

        spinnerLecturer = findViewById(R.id.courseLecturerSpinner);
        ArrayAdapter<CharSequence> adapterLecturer = ArrayAdapter.createFromResource(this, R.array.lecturer, android.R.layout.simple_spinner_item);
        adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLecturer.setAdapter(adapterLecturer);
        spinnerLecturer.setOnItemSelectedListener(this);

        courseSubject = findViewById(R.id.courseSubjectInput);

        courseSubject.getEditText().addTextChangedListener(this);

        addCourse = findViewById(R.id.addCourseBtn);

        toolbar = findViewById(R.id.addCourseToolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        course  = courseSubject.getEditText().getText().toString().trim();


        if(!course.isEmpty() && spinnerDay != null && spinnerDay.getSelectedItem() !=null && spinnerLecturer != null && spinnerLecturer.getSelectedItem() !=null && spinnerTimeStart != null && spinnerTimeStart.getSelectedItem() !=null && spinnerTimeEnd != null && spinnerTimeEnd.getSelectedItem() !=null){
            addCourse.setEnabled(true);
        } else {
            addCourse.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}