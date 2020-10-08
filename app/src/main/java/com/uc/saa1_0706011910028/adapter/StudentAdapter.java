

package com.uc.saa1_0706011910028.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.saa1_0706011910028.Glovar;
import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.StudentData;
import com.uc.saa1_0706011910028.StudentRegister;
import com.uc.saa1_0706011910028.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CardViewViewHolder>{

    private Context context;
    DatabaseReference dbStudent;
    Dialog dialog;
    int pos = 0;
    private ArrayList<Student> listStudent;


    private ArrayList<Student> getListStudent() {
        return listStudent;
    }
    public void setListStudent(ArrayList<Student> listStudent) {
        this.listStudent = listStudent;
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

        holder.btn_edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentRegister.class);
                intent.putExtra("action", "edit");
                intent.putExtra("edit_data_student", student);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        holder.btn_del.setOnClickListener(new View.OnClickListener() {

            FirebaseAuth fAuth;
            FirebaseUser fUser;

            @Override
            public void onClick(View v) {

                fAuth = FirebaseAuth.getInstance();
                fUser  = fAuth.getCurrentUser();
                Log.d("fuser", String.valueOf(fUser));
                new AlertDialog.Builder(context)
                        .setTitle("Konfirmasi")
                        .setMessage("Are you sure to delete "+student.getName()+" data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                        String uid = student.getUid();
                                        fAuth.signInWithEmailAndPassword(student.getEmail(),student.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                fUser = fAuth.getCurrentUser();
                                                fUser.delete();
                                                dbStudent.child(student.getUid()).removeValue(new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        Intent in = new Intent(context, StudentData.class);
                                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        Toast.makeText(context, "Delete success!", Toast.LENGTH_SHORT).show();
                                                        context.startActivity(in);
                                                        ((Activity)context).finish();
                                                        dialogInterface.cancel();
                                                    }
                                                });
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
        return getListStudent().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView labelname, labelnim, labelemail, labelgenderage, labeladdress;
        ImageView btn_edit, btn_del;

        CardViewViewHolder(View itemView) {
            super(itemView);
            labelname = itemView.findViewById(R.id.labelNameStudentAdp);
            labelnim = itemView.findViewById(R.id.labelNimStudentAdp);
            labelemail = itemView.findViewById(R.id.labelEmailStudentAdp);
            labelgenderage = itemView.findViewById(R.id.labelGenderAgeStudentAdp);
            labeladdress = itemView.findViewById(R.id.labelAddressStudentAdp);
            btn_del = itemView.findViewById(R.id.deleteStudentAdpImg);
            btn_edit = itemView.findViewById(R.id.editStudentAdpImg);
            dbStudent = FirebaseDatabase.getInstance().getReference("student");
            dialog = Glovar.loadingDialog(context);
            pos = getAdapterPosition();

        }

    }

}
