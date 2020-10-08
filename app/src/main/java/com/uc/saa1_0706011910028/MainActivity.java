package com.uc.saa1_0706011910028;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uc.saa1_0706011910028.fragment.Courses;
import com.uc.saa1_0706011910028.fragment.MyAccount;
import com.uc.saa1_0706011910028.fragment.MySchedule;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle("Home");

        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle(R.string.menu_courses);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.studentBottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.menu_schedule:
                        toolbar.setTitle(R.string.menu_schedule);
                        setSupportActionBar(toolbar);
                        fragment = new MySchedule();
                        loadFragment(fragment);
                        return true;

                    case R.id.menu_courses:
                        toolbar.setTitle(R.string.menu_courses);
                        setSupportActionBar(toolbar);
                        fragment = new Courses();
                        loadFragment(fragment);
                        return true;
                    case R.id.menu_account:
                        toolbar.setTitle(R.string.menu_account);
                        setSupportActionBar(toolbar);
                        fragment = new MyAccount();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.studentFrameMain, fragment);
        transaction.commit();
    }
}