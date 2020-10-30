

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
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.saa1_0706011910028.Glovar;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Course;

import java.util.ArrayList;

public class CourseFragmentAdapter extends RecyclerView.Adapter<CourseFragmentAdapter.CardViewViewHolder>{

    private Context context;
    Dialog dialog;
    private ArrayList<Course> listCourse;

    private DatabaseReference mDatabase;
    FirebaseDatabase dbEnroll;

    private ArrayList<Course> getListCourse() {
        return listCourse;
    }
    public void setListCourse(ArrayList<Course> listCourse) {
        this.listCourse = listCourse;
    }
    public CourseFragmentAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public CourseFragmentAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_fragment_adapter, parent, false);
        return new CourseFragmentAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CourseFragmentAdapter.CardViewViewHolder holder, int position) {
        final Course course = getListCourse().get(position);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        holder.labelname.setText(course.getSubject());
        holder.labellecturer.setText("Lecturer: " + course.getLecturer());
        holder.labelday.setText("Day: " + course.getDay());
        holder.labeltime.setText("Time: " + course.getStart() + " - " + course.getEnd());

        holder.btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOverlap(course);
            }
        });
    }


    @Override
    public int getItemCount() {
        return getListCourse().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView labelname, labellecturer, labelday, labeltime;
        Button btn_enroll;

        CardViewViewHolder(View itemView) {
            super(itemView);
            labelname = itemView.findViewById(R.id.labelCourseFragmentName);
            labellecturer = itemView.findViewById(R.id.labelCourseFragmentLecturer);
            labelday = itemView.findViewById(R.id.labelCourseFragmentDay);
            labeltime = itemView.findViewById(R.id.labelCourseFragmentTime);
            btn_enroll = itemView.findViewById(R.id.enrollCourseFragmentBtn);
            dialog = Glovar.loadingDialog(context);

        }

    }

    MutableLiveData<Course> addedCourse = new MutableLiveData<>();

    public MutableLiveData<Course> getAddedCourse(){
        return addedCourse;
    }

    boolean conflict = false;

    public void checkOverlap(final Course pickedCourse){
        final int selectedCourseStartInt = Integer.parseInt(pickedCourse.getStart().replace(":",""));
        final int selectedCourseEndInt = Integer.parseInt(pickedCourse.getEnd().replace(":",""));

        FirebaseDatabase.getInstance().getReference("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("course").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conflict = false;
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Course course = childSnapshot.getValue(Course.class);
                    int courseStartInt = Integer.parseInt(course.getStart().replace(":",""));
                    int courseEndInt = Integer.parseInt(course.getEnd().replace(":",""));

                    if (pickedCourse.getDay().equalsIgnoreCase(course.getDay())) {

                        if (selectedCourseStartInt >= courseStartInt && selectedCourseStartInt <= courseEndInt) {
                            conflict = true;
                        }
                        if (selectedCourseEndInt >= courseStartInt && selectedCourseEndInt <= courseEndInt) {
                            conflict = true;
                        }
                    }
                }

                if (conflict){
                    Toast.makeText(context, "Course Conflict!", Toast.LENGTH_SHORT).show();
                } else {
                    addedCourse.setValue(pickedCourse);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
