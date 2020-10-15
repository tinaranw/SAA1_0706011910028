package com.uc.saa1_0706011910028.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.uc.saa1_0706011910028.Starter;
import com.uc.saa1_0706011910028.model.Student;

public class MyAccount extends Fragment {

    Button logoutBtn;
    TextView labelname, labelnim, labelemail, labelgenderage, labeladdress;
    DatabaseReference dbStudent;
    FirebaseAuth firebaseAuth;
    Student student;

    public MyAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        labelname = view.findViewById(R.id.nameStudentAcc);
        labelnim = view.findViewById(R.id.nimStudentAcc);
        labelemail = view.findViewById(R.id.emailStudentAcc);
        labelgenderage = view.findViewById(R.id.genderageStudentAcc);
        labeladdress = view.findViewById(R.id.addressStudentAcc);


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

        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Starter.class);
                startActivity(intent);
            }
        });
    }

    public void setData(){
        labelname.setText(student.getName());
        labelemail.setText(student.getEmail());
        labelnim.setText(student.getNim());
        labelgenderage.setText("Gender: "+ student.getGender() + " | Age: "+ student.getAge() + " years old");
        labeladdress.setText(student.getAddress());
    }

}