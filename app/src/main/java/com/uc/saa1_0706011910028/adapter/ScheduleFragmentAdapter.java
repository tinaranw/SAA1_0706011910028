package com.uc.saa1_0706011910028.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.saa1_0706011910028.Glovar;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;

public class ScheduleFragmentAdapter extends RecyclerView.Adapter<ScheduleFragmentAdapter.CardViewViewHolder>{

    private DatabaseReference mDatabase;
    Dialog dialog;
    private Context context;
    private ArrayList<Course> listCourse;
    private ArrayList<Course> getListCourse() {
        return listCourse;
    }
    public void setListCourse(ArrayList<Course> listCourse) {
        this.listCourse = listCourse;
    }
    public ScheduleFragmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduleFragmentAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_fragment_adapter, parent, false);
        return new ScheduleFragmentAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ScheduleFragmentAdapter.CardViewViewHolder holder, int position) {
        final Course course = getListCourse().get(position);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        holder.labelname.setText(course.getSubject());
        holder.labellecturer.setText("Lecturer: " + course.getLecturer());
        holder.labelday.setText("Day: " + course.getDay());
        holder.labeltime.setText("Time: " + course.getStart() + " - " + course.getEnd());

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("course").child(course.getId()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(context, "Course removed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return getListCourse().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{

        TextView labelname, labellecturer, labelday, labeltime;
        Button btn_remove;

        CardViewViewHolder(View itemView) {
            super(itemView);
            labelname = itemView.findViewById(R.id.labelScheduleFragmentName);
            labellecturer = itemView.findViewById(R.id.labelScheduleFragmentLecturer);
            labelday = itemView.findViewById(R.id.labelScheduleFragmentDay);
            labeltime = itemView.findViewById(R.id.labelScheduleFragmentTime);
            btn_remove = itemView.findViewById(R.id.removeScheduleFragmentBtn);
            dialog = Glovar.loadingDialog(context);

        }
    }
}