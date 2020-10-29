package com.uc.saa1_0706011910028.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.adapter.CourseFragmentAdapter;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;

public class Courses extends Fragment {

    DatabaseReference dbCourse;
    ArrayList<Course> listCourse = new ArrayList<>();
    RecyclerView rvEnrollCourse;

    public Courses() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbCourse = FirebaseDatabase.getInstance().getReference("course");
        rvEnrollCourse = view.findViewById(R.id.rvCourseDataFragment);

        dbCourse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCourse.clear();
                rvEnrollCourse.setAdapter(null);
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Course course = childSnapshot.getValue(Course.class);
                    listCourse.add(course);
                }
                fetchCourseData(listCourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fetchCourseData(ArrayList<Course> list){
        rvEnrollCourse.setLayoutManager(new LinearLayoutManager(Courses.this.getActivity()));
        CourseFragmentAdapter courseFragmentAdapter = new CourseFragmentAdapter(Courses.this.getActivity());
        courseFragmentAdapter.setListCourse(list);
        rvEnrollCourse.setAdapter(courseFragmentAdapter);

        final Observer<Course> addedCourseObserver = new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                FirebaseDatabase.getInstance().getReference().child("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("course").child(course.getId()).setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Add Course Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Add Course Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        courseFragmentAdapter.getAddedCourse().observe(this , addedCourseObserver);
    }
}