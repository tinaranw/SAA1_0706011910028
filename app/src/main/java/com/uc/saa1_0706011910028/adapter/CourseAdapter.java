

package com.uc.saa1_0706011910028.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.AddCourse;
import com.uc.saa1_0706011910028.CourseData;
import com.uc.saa1_0706011910028.Glovar;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CardViewViewHolder>{

    private Context context;
    private DatabaseReference dbStudent;
    private DatabaseReference dbStudentCourse;
    private DatabaseReference dbCourse;
    Dialog dialog;
    int pos = 0;
    private ArrayList<Course> listCourse;
    ImageView courseImg;

    private ArrayList<Course> getListCourse() {
        return listCourse;
    }
    public void setListCourse(ArrayList<Course> listCourse) {
        this.listCourse = listCourse;
    }
    public CourseAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public CourseAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_adapter, parent, false);
        return new CourseAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CourseAdapter.CardViewViewHolder holder, int position) {
        final Course course = getListCourse().get(position);

        holder.labelname.setText(course.getSubject());
        holder.labellecturer.setText("Lecturer: " + course.getLecturer());
        holder.labelday.setText("Day: " + course.getDay());
        holder.labeltime.setText("Time: " + course.getStart() + " - " + course.getEnd());
        holder.btn_edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddCourse.class);
                intent.putExtra("action", "edit");
                intent.putExtra("edit_data_course", course);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure to delete "+course.getSubject()+" data?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            dialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                dialog.cancel();

                                deleteCourseStudent(course.getId());

                                dbCourse.child(course.getId()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Intent in = new Intent(context, CourseData.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        Toast.makeText(context, "Delete success!", Toast.LENGTH_SHORT).show();
                                        context.startActivity(in);
                                        ((Activity)context).finish();
                                        dialogInterface.cancel();
                                    }
                                });

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
    public int getItemCount() {
        return getListCourse().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView labelname, labellecturer, labelday, labeltime;
        ImageView btn_edit, btn_del;

        CardViewViewHolder(View itemView) {
            super(itemView);
            labelname = itemView.findViewById(R.id.labelCourseName);
            labellecturer = itemView.findViewById(R.id.labelCourseLecturer);
            labelday = itemView.findViewById(R.id.labelCourseDay);
            labeltime = itemView.findViewById(R.id.labelCourseTime);
            btn_del = itemView.findViewById(R.id.deleteCourseImg);
            btn_edit = itemView.findViewById(R.id.editCourseImg);
            dbCourse = FirebaseDatabase.getInstance().getReference("course");
            dialog = Glovar.loadingDialog(context);
            pos = getAdapterPosition();
            courseImg = itemView.findViewById(R.id.courseIconImg);
        }

    }

    public void deleteCourseStudent(String course_id){
        //check if student has the course
        dbStudent = FirebaseDatabase.getInstance().getReference("student");
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    //student yang berbeda-beda

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
