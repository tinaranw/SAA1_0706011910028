package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.adapter.StudentAdapter;
import com.uc.saa1_0706011910028.model.Student;

import java.util.ArrayList;

public class StudentData extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseReference dbStudent;
    ArrayList<Student> listStudent = new ArrayList<>();
    RecyclerView rvStudData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_data);

        toolbar = findViewById(R.id.studentDataToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbStudent = FirebaseDatabase.getInstance().getReference("student");
        rvStudData = findViewById(R.id.studentDataRecyclerView);
        fetchStudentData();
    }

    public void fetchStudentData(){
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Student student = childSnapshot.getValue(Student.class);
                    listStudent.add(student);
                    rvStudData.setAdapter(null);
                }
                showStudentData(listStudent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showStudentData(final ArrayList<Student> list){
        rvStudData.setLayoutManager(new LinearLayoutManager(StudentData.this));
        StudentAdapter studentAdapter = new StudentAdapter(StudentData.this);
        studentAdapter.setListStudent(list);
        rvStudData.setAdapter(studentAdapter);

//        ItemClickSupport.addTo(rvStudData).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                Intent intent = new Intent(StudentData.this, LecturerDetail.class);
//                Student student = new Student(list.get(position).getUid(), list.get(position).getEmail(), list.get(position).getPassword(), list.get(position).getName(),list.get(position).getNim(),list.get(position).getGender(),list.get(position).getAge(),list.get(position).getAddress());
//                intent.putExtra("data_student", student);
//                intent.putExtra("position", position);
////                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentData.this);
////                startActivity(intent, options.toBundle());
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(StudentData.this, StudentRegister.class);
            intent.putExtra("action", "add");
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentData.this);
//            startActivity(intent, options.toBundle());
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(StudentData.this, StudentRegister.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentData.this);
//        startActivity(intent, options.toBundle());
        startActivity(intent);
        finish();
    }

}