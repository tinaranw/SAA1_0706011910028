package com.uc.saa1_0706011910028.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.adapter.TaskAdapter;
import com.uc.saa1_0706011910028.model.Task;

import java.util.ArrayList;

public class Tasks extends Fragment {

    FloatingActionButton addnewtask;
    DatabaseReference dbTask;
    ArrayList<Task> listTask = new ArrayList<>();
    RecyclerView rvTaskData;
    FirebaseAuth firebaseAuth;
    String uid;

    public Tasks() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        dbTask = FirebaseDatabase.getInstance().getReference("student").child(uid).child("task");
        rvTaskData = view.findViewById(R.id.taskRecyclerView);
        Log.d("lolo", "fetch: " + uid);
        fetchTaskData();


        addnewtask = view.findViewById(R.id.addTaskBtn);
        addnewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddTask();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.studentFrameMain, fragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

    }

    public void fetchTaskData(){
        Log.d("waaaa", "fetch2: " + uid);
        dbTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("genshin impact is laifu", "fetch3: " + uid);
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Task task = childSnapshot.getValue(Task.class);
                    Log.d("hmm", "fetch: " + task.getTasktype());
                    listTask.add(task);
                }
                showTaskData(listTask);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showTaskData(final ArrayList<Task> list){
        rvTaskData.setLayoutManager(new LinearLayoutManager(getActivity()));
        TaskAdapter taskAdapter = new TaskAdapter(getActivity());
        taskAdapter.setListTask(list);
        rvTaskData.setAdapter(taskAdapter);
    }
}