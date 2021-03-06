package com.uc.saa1_0706011910028;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.uc.saa1_0706011910028.adapter.LecturerAdapter;
import com.uc.saa1_0706011910028.model.Lecturer;

import java.util.ArrayList;


public class LecturerData extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseReference dbLecturer;
    ArrayList<Lecturer> listLecturer = new ArrayList<>();
    RecyclerView rvLectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_data);

        toolbar = findViewById(R.id.lecturerDataToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbLecturer = FirebaseDatabase.getInstance().getReference("lecturer");
        rvLectData = findViewById(R.id.lectureDataRecyclerView);
        fetchLecturerData();
    }

    public void fetchLecturerData(){
        dbLecturer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Lecturer lecturer = childSnapshot.getValue(Lecturer.class);
                    listLecturer.add(lecturer);
                    rvLectData.setAdapter(null);
                }
                showLecturerData(listLecturer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showLecturerData(final ArrayList<Lecturer> list){
        rvLectData.setLayoutManager(new LinearLayoutManager(LecturerData.this));
        LecturerAdapter lecturerAdapter = new LecturerAdapter(LecturerData.this);
        lecturerAdapter.setListLecturer(list);
        rvLectData.setAdapter(lecturerAdapter);

        ItemClickSupport.addTo(rvLectData).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(LecturerData.this, LecturerDetail.class);
                Lecturer lecturer = new Lecturer(list.get(position).getId(), list.get(position).getName(), list.get(position).getGender(), list.get(position).getExpertise());
                intent.putExtra("data_lecturer", lecturer);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(LecturerData.this, AddLecturer.class);
            intent.putExtra("action", "add");
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(LecturerData.this, AddLecturer.class);
        intent.putExtra("action","add");
        startActivity(intent);
        finish();
    }

}