package com.uc.saa1_0706011910028.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CardViewViewHolder>{

    private Context context;
    private ArrayList<Student> ListStudent;
    private ArrayList<Student> getListStudent() {
        return ListStudent;
    }
    public void setListStudent(ArrayList<Student> ListStudent) {
        this.ListStudent = ListStudent;
    }
    public StudentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_student_adapter, parent, false);
        return new StudentAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.CardViewViewHolder holder, int position) {
        final Student student = getListStudent().get(position);
        holder.labelname.setText(student.getName());
        holder.labelnim.setText("NIM: " + student.getNim());
        holder.labelemail.setText("Email: " + student.getEmail());
        holder.labelgenderage.setText("Gender/Age: " + student.getGender() + " (" +student.getAge()+" y.o)");
        holder.labeladdress.setText("Address: " + student.getAddress());
    }

    @Override
    public int getItemCount() {
        return getListStudent().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView labelname, labelnim, labelemail, labelgenderage, labeladdress;

        CardViewViewHolder(View itemView) {
            super(itemView);
            labelname = itemView.findViewById(R.id.labelNameStudentAdp);
            labelnim = itemView.findViewById(R.id.labelNimStudentAdp);
            labelemail = itemView.findViewById(R.id.labelEmailStudentAdp);
            labelgenderage = itemView.findViewById(R.id.labelGenderAgeStudentAdp);
            labeladdress = itemView.findViewById(R.id.labelAddressStudentAdp);

        }
    }
}