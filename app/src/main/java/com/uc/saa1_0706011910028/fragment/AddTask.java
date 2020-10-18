package com.uc.saa1_0706011910028.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.saa1_0706011910028.Glovar;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Task;

public class AddTask extends Fragment implements TextWatcher {

    TextInputLayout titleinput;
    EditText duedateinput, descinput;
    RadioGroup typeInput;
    RadioButton tasktype;
    String title, duedate, type, desc;
    Dialog dialog;
    Button newtask;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    String uid;

    public AddTask() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleinput = view.findViewById(R.id.taskTitleInput);
        duedateinput = view.findViewById(R.id.taskDueDateInput);
        descinput = view.findViewById(R.id.taskDescInput);

        dialog = Glovar.loadingDialog(getActivity());

        typeInput = view.findViewById(R.id.taskTypeRadioGroup);
        typeInput.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                tasktype = view.findViewById(i);
                type = tasktype.getText().toString();
            }
        });

        titleinput.getEditText().addTextChangedListener(this);
        duedateinput.addTextChangedListener(this);
        descinput.addTextChangedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid = firebaseAuth.getCurrentUser().getUid();

        newtask = view.findViewById(R.id.newTaskBtn);
        newtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleinput.getEditText().getText().toString().trim();
                duedate = duedateinput.getText().toString().trim();
                desc = descinput.getText().toString().trim();

                addTask(title, duedate, type, desc);
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        title = titleinput.getEditText().getText().toString().trim();
        duedate = duedateinput.getText().toString().trim();
        desc = descinput.getText().toString().trim();

        if(!title.isEmpty() && !duedate.isEmpty() && !desc.isEmpty()){
            newtask.setEnabled(true);
        } else {
            newtask.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void addTask(String ttitle,String tduedate, String ttype, String tdesc){
        String mid = mDatabase.child("student").child(uid).child("task").push().getKey();
        Task task = new Task(mid, ttitle, tduedate,  ttype, tdesc);
        mDatabase.child("student").child(uid).child("task").child(mid).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Fragment fragment = new Tasks();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.studentFrameMain, fragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

    }
}