package com.uc.saa1_0706011910028.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Student;

import java.text.DateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    TextView date, greeting;
    ImageView profilepic;
    DatabaseReference dbStudent;
    FirebaseAuth firebaseAuth;
    Student student;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date = view.findViewById(R.id.dateHomeFragment);
        greeting = view.findViewById(R.id.greetingsHomeFragment);
        profilepic = view.findViewById(R.id.studentPicHomeFragment);

        firebaseAuth = FirebaseAuth.getInstance();

        dbStudent = FirebaseDatabase.getInstance().getReference("student").child(firebaseAuth.getCurrentUser().getUid());

        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    student = snapshot.getValue(Student.class);
                    setData();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setData(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);
        greeting.setText("Hi, "+ student.getName());
        if(student.getGender().equalsIgnoreCase("male")){
            profilepic.setImageResource(R.drawable.malestudent);
        } else if (student.getGender().equalsIgnoreCase("female")){
            profilepic.setImageResource(R.drawable.student);
        }
    }
}