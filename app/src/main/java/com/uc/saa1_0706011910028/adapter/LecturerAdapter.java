package com.uc.saa1_0706011910028.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Lecturer;

import java.util.ArrayList;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.CardViewViewHolder>{

    private Context context;
    private ArrayList<Lecturer> listLecturer;
    String gender;
    ImageView lecturerImg;
    private ArrayList<Lecturer> getListLecturer() {
        return listLecturer;
    }
    public void setListLecturer(ArrayList<Lecturer> listLecturer) {
        this.listLecturer = listLecturer;
    }
    public LecturerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LecturerAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lecturer_adapter, parent, false);
        return new LecturerAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final LecturerAdapter.CardViewViewHolder holder, int position) {
        final Lecturer lecturer = getListLecturer().get(position);
        holder.lbl_name.setText(lecturer.getName());
        holder.lbl_gender.setText(lecturer.getGender());
        gender = lecturer.getGender();
        if(gender.equalsIgnoreCase("Male")){
            lecturerImg.setImageResource(R.drawable.teacher);
        } else if(gender.equalsIgnoreCase("Female")){
            lecturerImg.setImageResource(R.drawable.femaleteacher);
        }
        holder.lbl_expertise.setText(lecturer.getExpertise());

    }

    @Override
    public int getItemCount() {
        return getListLecturer().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView lbl_name, lbl_gender, lbl_expertise;

        CardViewViewHolder(View itemView) {
            super(itemView);
            lbl_name = itemView.findViewById(R.id.labelNameLectAdp);
            lbl_gender = itemView.findViewById(R.id.labelGenderLectAdp);
            lbl_expertise = itemView.findViewById(R.id.labelExpertiseLectAdp);
            lecturerImg = itemView.findViewById(R.id.lecturerIconImg);

        }
    }
}