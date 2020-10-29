package com.uc.saa1_0706011910028;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.saa1_0706011910028.model.Lecturer;

import java.util.HashMap;
import java.util.Map;

public class AddLecturer extends AppCompatActivity implements TextWatcher {

    TextInputLayout lecturerName, lecturerExpertise;
    String name="", expertise="", gender="male", action="";
    Button addLecturer;
    Toolbar toolbar;
    RadioGroup lecturerGender;
    RadioButton radioButtonGender;
    Dialog dialog;
    Lecturer lecturer;
    ImageView lecturerImg;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        lecturerName = findViewById(R.id.lecturerNameInput);
        lecturerExpertise = findViewById(R.id.lecturerExpertiseInput);

        lecturerName.getEditText().addTextChangedListener(this);
        lecturerExpertise.getEditText().addTextChangedListener(this);
        dialog = Glovar.loadingDialog(AddLecturer.this);
        toolbar = findViewById(R.id.addLecturerToolbar);
        setSupportActionBar(toolbar);
        addLecturer = findViewById(R.id.addLecturerBtn);
        lecturerImg = findViewById(R.id.lecturerImg);

        lecturerGender = findViewById(R.id.lecturerGenderRadioGroup);
        lecturerGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButtonGender = findViewById(i);
                gender = radioButtonGender.getText().toString();
                if(gender.equalsIgnoreCase("Male")){
                    lecturerImg.setImageResource(R.drawable.teacher);
                } else if(gender.equalsIgnoreCase("Female")){
                    lecturerImg.setImageResource(R.drawable.femaleteacher);
                }
            }
        });

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if(action.equals("add")){
            getSupportActionBar().setTitle(R.string.addlecturer);
            addLecturer.setText(R.string.addlecturer);
            addLecturer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = lecturerName.getEditText().getText().toString().trim();
                    expertise = lecturerExpertise.getEditText().getText().toString().trim();
                    addLecturer(name, gender, expertise);
                }
            });
        }else{
            getSupportActionBar().setTitle(R.string.editlecturer);
            getSupportActionBar().setTitle(R.string.editlecturer);
            lecturer = intent.getParcelableExtra("edit_data_lect");
            lecturerName.getEditText().setText(lecturer.getName());
            lecturerExpertise.getEditText().setText(lecturer.getExpertise());
            if(lecturer.getGender().equalsIgnoreCase("male")){
                lecturerGender.check(R.id.lecturerMaleRadioButton);
                lecturerImg.setImageResource(R.drawable.teacher);

            }else{
                lecturerGender.check(R.id.lecturerFemaleRadioButton);
                lecturerImg.setImageResource(R.drawable.femaleteacher);
            }
            addLecturer.setText(R.string.editlecturer);
            addLecturer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    name = lecturerName.getEditText().getText().toString().trim();
                    expertise = lecturerExpertise.getEditText().getText().toString().trim();
                    Map<String,Object> params = new HashMap<>();
                    params.put("name", name);
                    params.put("expertise", expertise);
                    params.put("gender", gender);
                    mDatabase.child("lecturer").child(lecturer.getId()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.cancel();
                            Toast.makeText(AddLecturer.this, "Lecturer data updated!", Toast.LENGTH_SHORT).show();
                            Intent intent;
                            intent = new Intent(AddLecturer.this, LecturerData.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddLecturer.this);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }

    }

    public void addLecturer(String mnama, String mgender, String mexpertise){
        String mid = mDatabase.child("lecturer").push().getKey();
        Lecturer lecturer = new Lecturer(mid, mnama, mgender, mexpertise);
        mDatabase.child("lecturer").child(mid).setValue(lecturer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddLecturer.this, "Lecturer added!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddLecturer.this,LecturerData.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(AddLecturer.this, Starter.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
//            finish();
        } else if(id == R.id.lecturerList){
            Intent intent;
            intent = new Intent(AddLecturer.this, LecturerData.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        name = lecturerName.getEditText().getText().toString().trim();
        expertise = lecturerExpertise.getEditText().getText().toString().trim();

        if(!name.isEmpty() && !expertise.isEmpty()){
            addLecturer.setEnabled(true);
        } else {
            addLecturer.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lecturer_menu, menu);
        return true;
    }
}