package com.uc.saa1_0706011910028;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCourse extends AppCompatActivity implements TextWatcher{


    //    List<String> lecturer_array;
//    private DatabaseReference mDatabase;
//    String action;
//    Course courseModel;
//    Toolbar toolbar;
//    Dialog dialog;
//    ArrayAdapter<CharSequence> adapterend;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_course);
//
//        dialog = Glovar.loadingDialog(AddCourse.this);
//
//        spinnerDay = findViewById(R.id.courseDaySpinner);
//        ArrayAdapter<CharSequence> adapterday = ArrayAdapter.createFromResource(AddCourse.this,
//                R.array.days, android.R.layout.simple_spinner_item);
//        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDay.setAdapter(adapterday);
//
//        spinnerTimeStart = findViewById(R.id.courseTimeStartSpinner);
//        ArrayAdapter<CharSequence> adapterTimeStart = ArrayAdapter.createFromResource(AddCourse.this,
//                R.array.timeStart, android.R.layout.simple_spinner_item);
//        adapterTimeStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerTimeStart.setAdapter(adapterTimeStart);
//
//        spinnerLecturer = findViewById(R.id.courseLecturerSpinner);
//        ArrayAdapter<CharSequence> adapterLecturer = ArrayAdapter.createFromResource(AddCourse.this,
//                R.array.lecturer, android.R.layout.simple_spinner_item);
//        adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLecturer.setAdapter(adapterLecturer);
//
//        spinnerTimeEnd = findViewById(R.id.courseTimeEndSpinner);
//
//        courseSubject = findViewById(R.id.courseSubjectInput);
//
//        courseSubject.getEditText().addTextChangedListener(this);
//
//        addCourse = findViewById(R.id.addCourseBtn);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        toolbar = findViewById(R.id.addCourseToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        spinnerTimeStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                adapterend = null;
//                setSpinnerEnd(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        action = getIntent().getStringExtra("action");
//        if(action.equalsIgnoreCase("add")){
//            toolbar.setTitle("Add Course");
//            addCourse.setText("Add");
//            addCourse.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    course = courseSubject.getEditText().getText().toString().trim();
//                    day = spinnerDay.getSelectedItem().toString();
//                    timeEnd = spinnerTimeEnd.getSelectedItem().toString();
//                    timeStart = spinnerTimeStart.getSelectedItem().toString();
//                    lecturer = spinnerLecturer.getSelectedItem().toString();
//                    addCourse(course, day, timeStart, timeEnd,lecturer);
//                }
//            });
//        } else  {
//            getSupportActionBar().setTitle("Edit Course");
//            courseModel = getIntent().getParcelableExtra("edit_data_course");
//            Log.d("hah", courseModel.getSubject());
//            String subject = courseModel.getSubject();
//            courseSubject = findViewById(R.id.courseSubjectInput);
//            courseSubject.getEditText().setText(subject);
//
//            int dayInd =  adapterday.getPosition(courseModel.getDay());
//            spinnerDay.setSelection(dayInd);
//
//            int timeStartInd =  adapterTimeStart.getPosition(courseModel.getStart());
//            spinnerTimeStart.setSelection(timeStartInd);
//
//            setSpinnerEnd(timeStartInd);
//            int timeEndInd =  adapterend.getPosition(courseModel.getEnd());
//            spinnerTimeEnd.setSelection(timeEndInd);
//
//            int lecturerInd =  adapterend.getPosition(courseModel.getLecturer());
//            spinnerLecturer.setSelection(lecturerInd);
//            addCourse.setText("Edit Course");
//            addCourse.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.show();
//                    getFormValue();
//                    Map<String,Object> params = new HashMap<>();
//                    params.put("subject", course);
//                    params.put("day", day);
//                    params.put("start", timeStart);
//                    params.put("end", timeEnd);
//                    params.put("lecturer", lecturer);
//
//                    mDatabase.child(courseModel.getId()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            dialog.cancel();
//                            Intent intent;
//                            intent = new Intent(AddCourse.this, CourseData.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                }
//            });
//        }
//
//        lecturer_array = new ArrayList<>();
//        mDatabase.child("lecturer").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
//                    String firebaseLecturer = childSnapshot.child("name").getValue(String.class);
//                    lecturer_array.add(firebaseLecturer);
//                }
//
//                ArrayAdapter<String> adapterLecturer = new ArrayAdapter<>(AddCourse.this, android.R.layout.simple_spinner_item, lecturer_array);
//                adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerLecturer.setAdapter(adapterLecturer);
//
//                if(action.equalsIgnoreCase("edit")){
//                    int index = adapterLecturer.getPosition(courseModel.getLecturer());
//                    spinnerLecturer.setSelection(index);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    public void addCourse(String course,String day, String timeStart, String timeEnd, String lecturer){
//        String id = mDatabase.child("course").push().getKey();
//        Course courseObj = new Course(id, course, day, timeStart, timeEnd, lecturer);
//        mDatabase.child("course").child(id).setValue(courseObj).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(AddCourse.this, "Course added!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AddCourse.this,CourseData.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == android.R.id.home){
//            Intent intent;
//            intent = new Intent(AddCourse.this, Starter.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//            return true;
//        }else if(id == R.id.courseList){
//            Intent intent;
//            intent = new Intent(AddCourse.this, CourseData.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent;
//        intent = new Intent(AddCourse.this, Starter.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        getFormValue();
//        if(!course.isEmpty() && spinnerDay != null && spinnerDay.getSelectedItem() !=null && spinnerLecturer != null && spinnerLecturer.getSelectedItem() !=null && spinnerTimeStart != null && spinnerTimeStart.getSelectedItem() !=null && spinnerTimeEnd != null && spinnerTimeEnd.getSelectedItem() !=null && spinnerLecturer.getSelectedItem() !=null){
//            addCourse.setEnabled(true);
//        } else {
//            addCourse.setEnabled(false);
//        }
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.course_menu, menu);
//        return true;
//    }
//
//    public void getFormValue(){
//
//        course = courseSubject.getEditText().getText().toString().trim();
//        day = spinnerDay.getSelectedItem().toString();
//        timeEnd = spinnerTimeEnd.getSelectedItem().toString();
//        timeStart = spinnerTimeStart.getSelectedItem().toString();
//        lecturer = spinnerLecturer.getSelectedItem().toString();
//    }
//
//    public void setSpinnerEnd(int position){
//        if(position==0){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0730, android.R.layout.simple_spinner_item);
//        }else if(position==1){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0800, android.R.layout.simple_spinner_item);
//        }else if(position==2){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0830, android.R.layout.simple_spinner_item);
//        }else if(position==3){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0900, android.R.layout.simple_spinner_item);
//        }else if(position==4){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd0930, android.R.layout.simple_spinner_item);
//        }else if(position==5){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1000, android.R.layout.simple_spinner_item);
//        }else if(position==6){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1030, android.R.layout.simple_spinner_item);
//        }else if(position==7){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1100, android.R.layout.simple_spinner_item);
//        }else if(position==8){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1130, android.R.layout.simple_spinner_item);
//        }else if(position==9){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1200, android.R.layout.simple_spinner_item);
//        }else if(position==10){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1230, android.R.layout.simple_spinner_item);
//        }else if(position==11){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1300, android.R.layout.simple_spinner_item);
//        }else if(position==12){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1330, android.R.layout.simple_spinner_item);
//        }else if(position==13){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1400, android.R.layout.simple_spinner_item);
//        }else if(position==14){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1430, android.R.layout.simple_spinner_item);
//        }else if(position==15){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1500, android.R.layout.simple_spinner_item);
//        }else if(position==16){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1530, android.R.layout.simple_spinner_item);
//        }else if(position==17){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1600, android.R.layout.simple_spinner_item);
//        }else if(position==18){
//            adapterend = ArrayAdapter.createFromResource(AddCourse.this, R.array.timeEnd1630, android.R.layout.simple_spinner_item);
//        }
//
//        adapterend.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerTimeEnd.setAdapter(adapterend);
//    }
    Spinner spinnerDay, spinnerTimeStart, spinnerTimeEnd, spinnerLecturer;
    TextInputLayout courseSubject;
    TextInputEditText courseEditText;
    Button addCourse;
    Toolbar toolbar;
    String subject, day, timeStart, timeEnd, lecturer, action;
    Course course;
    Dialog dialog;
    private DatabaseReference mDatabase;
    List<String> lecturer_array;
    ArrayAdapter<CharSequence> adapterend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        dialog = Glovar.loadingDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.addCourseToolbar);

        courseSubject = findViewById(R.id.courseSubjectInput);
        courseEditText = findViewById(R.id.courseSubjectEditText);
        spinnerDay = findViewById(R.id.courseDaySpinner);
        spinnerTimeStart = findViewById(R.id.courseTimeStartSpinner);
        spinnerTimeEnd = findViewById(R.id.courseTimeEndSpinner);
        spinnerLecturer = findViewById(R.id.courseLecturerSpinner);
        addCourse = findViewById(R.id.addCourseBtn);

        courseSubject.getEditText().addTextChangedListener(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourse.this, Starter.class);
                startActivity(intent);
                finish();
            }
        });

        spinnerDay = findViewById(R.id.courseDaySpinner);
        ArrayAdapter<CharSequence> adapterDays = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDays);

        spinnerLecturer = findViewById(R.id.courseLecturerSpinner);
        final ArrayAdapter<CharSequence> adapterLecturers = ArrayAdapter.createFromResource(this, R.array.lecturer, android.R.layout.simple_spinner_item);
        adapterLecturers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLecturer.setAdapter(adapterLecturers);

        spinnerTimeStart = findViewById(R.id.courseTimeStartSpinner);
        ArrayAdapter<CharSequence> adapterTimeStart = ArrayAdapter.createFromResource(this, R.array.timeStart, android.R.layout.simple_spinner_item);
        adapterTimeStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeStart.setAdapter(adapterTimeStart);

        spinnerTimeEnd = findViewById(R.id.courseTimeEndSpinner);


        spinnerTimeStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                adapterend = null;
                setSpinnerTimeEnd(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lecturer_array = new ArrayList<>();
        mDatabase.child("lecturer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot:snapshot.getChildren()){
                    String firebase_lecturer = childSnapshot.child("name").getValue(String.class);
                    lecturer_array.add(firebase_lecturer);
                }
                ArrayAdapter<String> adapterLecturers = new ArrayAdapter<>(AddCourse.this, android.R.layout.simple_spinner_item,lecturer_array);
                adapterLecturers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLecturer.setAdapter(adapterLecturers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if(action.equalsIgnoreCase("add")){
            getSupportActionBar().setTitle(R.string.addCourseTitle);
            addCourse.setText("Add");
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subject = courseSubject.getEditText().getText().toString().trim();
                    day = spinnerDay.getSelectedItem().toString();
                    timeStart = spinnerTimeStart.getSelectedItem().toString();
                    timeEnd = spinnerTimeEnd.getSelectedItem().toString();
                    lecturer = spinnerLecturer.getSelectedItem().toString();
                    addCourse(subject,day,timeStart,timeEnd,lecturer);
                    Intent intent = new Intent(AddCourse.this, CourseData.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            getSupportActionBar().setTitle(R.string.editCourse);
//            toolbar.setTitle("Edit Course");
            addCourse.setText("Edit");
            course = intent.getParcelableExtra("edit_data_course");
            String subj = course.getSubject();
            Log.d("editDataCourse", subj);
//            courseSubject.getEditText().setText(subj);//Yang error

            int dayIndex = adapterDays.getPosition(course.getDay());
            spinnerDay.setSelection(dayIndex);

            int startIndex = adapterTimeStart.getPosition(course.getStart());
            spinnerTimeStart.setSelection(startIndex);

            setSpinnerTimeEnd(startIndex);
            int endIndex = adapterend.getPosition(course.getEnd());
            spinnerTimeEnd.setSelection(endIndex);

            int index = adapterLecturers.getPosition(course.getLecturer());
            spinnerLecturer.setSelection(index);


            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    subject = courseSubject.getEditText().getText().toString().trim();
                    day = spinnerDay.getSelectedItem().toString();
                    timeStart = spinnerTimeStart.getSelectedItem().toString();
                    timeEnd = spinnerTimeEnd.getSelectedItem().toString();
                    lecturer = spinnerLecturer.getSelectedItem().toString();

                    Map<String, Object> params = new HashMap<>();
                    params.put("subject", subject);
                    params.put("day", day);
                    params.put("start", timeStart);
                    params.put("end", timeEnd);
                    params.put("lecturer", lecturer);
                    mDatabase.child(course.getId()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                        //                    mDatabase.child("student").child(student.getUid()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.cancel();
                            Intent intent;
                            Toast.makeText(AddCourse.this, "Course Data Updated Successful", Toast.LENGTH_SHORT).show();
                            intent = new Intent(AddCourse.this, CourseData.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    public void addCourse(String subject, String day, String timeStart, String timeEnd, String lecturer){
        String id = mDatabase.child("course").push().getKey();
        Course course = new Course(id, subject, day, timeStart, timeEnd, lecturer);
        mDatabase.child("course").child(id).setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.cancel();
                Toast.makeText(AddCourse.this, "Add Course Successfully", Toast.LENGTH_SHORT).show();
                courseSubject.getEditText().setText("");
                spinnerDay.setSelection(0);
                spinnerTimeStart.setSelection(0);
                spinnerTimeEnd.setSelection(0);
                spinnerLecturer.setSelection(0);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("hem", "ehyheyehyehyeh");
                Toast.makeText(AddCourse.this, "Failed to add Course", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        setSpinnerTimeEnd(position);
        subject = courseSubject.getEditText().getText().toString().trim();
        day = spinnerDay.getSelectedItem().toString();
        timeStart = spinnerTimeStart.getSelectedItem().toString();
        timeEnd = spinnerTimeEnd.getSelectedItem().toString(); //Yang error
        lecturer = spinnerLecturer.getSelectedItem().toString();

        if (!subject.isEmpty()) {
            addCourse.setEnabled(true);
        } else {
            addCourse.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent;
            intent = new Intent(this, Starter.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.courseList) {
            Intent intent;
            intent = new Intent(AddCourse.this, CourseData.class);
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

    public void setSpinnerTimeEnd(int position){
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
}