package com.uc.saa1_0706011910028.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.adapter.ScheduleFragmentAdapter;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;

public class MySchedule extends Fragment {

    RecyclerView rvSchedule;
    DatabaseReference dbSched;
    ArrayList<Course> listCourse = new ArrayList<>();

    public MySchedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSchedule = view.findViewById(R.id.rvScheduleDataFragment);

        dbSched = FirebaseDatabase.getInstance()
                .getReference("student")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid())
                .child("course");

        dbSched.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCourse.clear();
                rvSchedule.setAdapter(null);
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Course course = childSnapshot.getValue(Course.class);
                    listCourse.add(course);
                }
                fetchScheduleData(listCourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fetchScheduleData(ArrayList<Course>list){
        rvSchedule.setLayoutManager(new LinearLayoutManager(MySchedule.this.getActivity()));
        ScheduleFragmentAdapter scheduleFragmentAdapter = new ScheduleFragmentAdapter(MySchedule.this.getActivity());
        scheduleFragmentAdapter.setListCourse(list);
        rvSchedule.setAdapter(scheduleFragmentAdapter);
    }
}