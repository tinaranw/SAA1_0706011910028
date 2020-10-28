package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;
import java.util.List;

public class AddCourse extends AppCompatActivity implements TextWatcher{

    Spinner spinnerDay, spinnerTimeStart, spinnerTimeEnd, spinnerLecturer;
    TextInputLayout courseSubject;
    String course, day, timeStart, timeEnd;
    Button addCourse;
    List<String> lecturer_array;
    private DatabaseReference mDatabase;
    String action;
    Course courseModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        spinnerDay = findViewById(R.id.courseDaySpinner);
        ArrayAdapter<CharSequence> adapterday = ArrayAdapter.createFromResource(AddCourse.this,
                R.array.days, android.R.layout.simple_spinner_item);
        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterday);

        spinnerTimeStart = findViewById(R.id.courseTimeStartSpinner);
        ArrayAdapter<CharSequence> adapterTimeStart = ArrayAdapter.createFromResource(AddCourse.this,
                R.array.timeStart, android.R.layout.simple_spinner_item);
        adapterTimeStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeStart.setAdapter(adapterTimeStart);

        spinnerLecturer = findViewById(R.id.courseLecturerSpinner);
        ArrayAdapter<CharSequence> adapterLecturer = ArrayAdapter.createFromResource(AddCourse.this,
                R.array.lecturer, android.R.layout.simple_spinner_item);
        adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLecturer.setAdapter(adapterLecturer);

        spinnerTimeEnd = findViewById(R.id.courseTimeEndSpinner);

        courseSubject = findViewById(R.id.courseSubjectInput);

        courseSubject.getEditText().addTextChangedListener(this);

        addCourse = findViewById(R.id.addCourseBtn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.addCourseToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerTimeStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapterend = null;
                if(position==0){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0730, android.R.layout.simple_spinner_item);
                }else if(position==1){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0800, android.R.layout.simple_spinner_item);
                }else if(position==2){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0830, android.R.layout.simple_spinner_item);
                }else if(position==3){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0900, android.R.layout.simple_spinner_item);
                }else if(position==4){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0930, android.R.layout.simple_spinner_item);
                }else if(position==5){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1000, android.R.layout.simple_spinner_item);
                }else if(position==6){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1030, android.R.layout.simple_spinner_item);
                }else if(position==7){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1100, android.R.layout.simple_spinner_item);
                }else if(position==8){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1130, android.R.layout.simple_spinner_item);
                }else if(position==9){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1200, android.R.layout.simple_spinner_item);
                }else if(position==10){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1230, android.R.layout.simple_spinner_item);
                }else if(position==11){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1300, android.R.layout.simple_spinner_item);
                }else if(position==12){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1330, android.R.layout.simple_spinner_item);
                }else if(position==13){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1400, android.R.layout.simple_spinner_item);
                }else if(position==14){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1430, android.R.layout.simple_spinner_item);
                }else if(position==15){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1500, android.R.layout.simple_spinner_item);
                }else if(position==16){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1530, android.R.layout.simple_spinner_item);
                }else if(position==17){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1600, android.R.layout.simple_spinner_item);
                }else if(position==18){
                    adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1630, android.R.layout.simple_spinner_item);
                }

                adapterend.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTimeEnd.setAdapter(adapterend);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        action = getIntent().getStringExtra("action");
        if(action.equalsIgnoreCase("add")){
            toolbar.setTitle("Add Course");
            addCourse.setText("Add");
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    course = courseSubject.getEditText().getText().toString().trim();
                    day = spinnerDay.getSelectedItem().toString();
                    timeEnd = spinnerTimeEnd.getSelectedItem().toString();
                    timeStart = spinnerTimeStart.getSelectedItem().toString();
                }
            });
        } else if(action.equalsIgnoreCase("edit")) {
            toolbar.setTitle("Edit Course");
            addCourse.setText("Update");
            courseModel = getIntent().getParcelableExtra("edit_data_course");
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        lecturer_array = new ArrayList<>();
        mDatabase.child("lecturer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    String firebaseLecturer = childSnapshot.child("name").getValue(String.class);
                    lecturer_array.add(firebaseLecturer);
                }

                ArrayAdapter<String> adapterLecturer = new ArrayAdapter<>(AddCourse.this, android.R.layout.simple_spinner_item, lecturer_array);
                adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLecturer.setAdapter(adapterLecturer);

                if(action.equalsIgnoreCase("edit")){
                    int index = adapterLecturer.getPosition(courseModel.getLecturer());
                    spinnerLecturer.setSelection(index);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addCourse(String course,String day, String timeStart, String timeEnd, String lecturer){
        String id = mDatabase.child("course").push().getKey();
        Course courseObj = new Course(id, course, day, timeStart, timeEnd, lecturer);
        mDatabase.child("course").child("id").setValue(courseObj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(AddCourse.this, Starter.class);
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
        intent = new Intent(AddCourse.this, Starter.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        course = courseSubject.getEditText().getText().toString().trim();
        day = spinnerDay.getSelectedItem().toString();
        timeEnd = spinnerTimeEnd.getSelectedItem().toString();
        timeStart = spinnerTimeStart.getSelectedItem().toString();

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