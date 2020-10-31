package com.uc.saa1_0706011910028.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.Glovar;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.Starter;
import com.uc.saa1_0706011910028.StudentRegister;
import com.uc.saa1_0706011910028.model.Student;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccount extends Fragment {

    Button logoutBtn, editBtn;
    TextView labelname, labelnim, labelemail, labelgenderage, labeladdress, labelnim2;
    DatabaseReference dbStudent;
    FirebaseAuth firebaseAuth;
    Student student;
    ImageView changeProfileImg;
    CircleImageView profileimg;
    Dialog dialog;


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
        labelnim2 = view.findViewById(R.id.nimStudentAcc2);
        labelemail = view.findViewById(R.id.emailStudentAcc);
        labelgenderage = view.findViewById(R.id.genderageStudentAcc);
        labeladdress = view.findViewById(R.id.addressStudentAcc);
        profileimg = view.findViewById(R.id.profileStudentAcc);
        changeProfileImg = view.findViewById(R.id.changeProfilePicButton);
        dialog = Glovar.loadingDialog(getActivity());
        logoutBtn = view.findViewById(R.id.logoutBtn);
        editBtn = view.findViewById(R.id.editAccBtn);

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

        changeProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentRegister.class);
                intent.putExtra("action", "edit_account");
                intent.putExtra("edit_data_student", student);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure to logout of "+student.getName()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(getActivity(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), Starter.class);
                                        startActivity(intent);
                                        dialogInterface.cancel();
                                        getActivity().finish();
                                    }
                                }, 2000);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileimg.setImageURI(imageUri);

            }
        }
    }

    public void setData(){
        labelname.setText(student.getName());
        labelemail.setText(student.getEmail());
        labelnim.setText(student.getNim());
        labelnim2.setText(student.getNim());
        labelgenderage.setText("Gender: "+ student.getGender() + " | Age: "+ student.getAge() + " years old");
        labeladdress.setText(student.getAddress());
        if(student.getGender().equalsIgnoreCase("male")){
            profileimg.setImageResource(R.drawable.malestudent);
        } else if (student.getGender().equalsIgnoreCase("female")){
            profileimg.setImageResource(R.drawable.student);
        }
    }

}