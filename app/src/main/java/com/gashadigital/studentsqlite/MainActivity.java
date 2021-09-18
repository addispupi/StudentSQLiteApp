package com.gashadigital.studentsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {
    BottomNavigationView btnNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNav = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_layout, new StudentList()).commit();

        btnNav.setOnNavigationItemSelectedListener(navListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.menu_list:
                selectedFragment = new StudentList();
                break;
            case R.id.menu_add:
                selectedFragment = new StudentAdd();
                break;
            case R.id.menu_fav:
                selectedFragment = new StudentDetail();
                break;
        }

        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_layout, selectedFragment).commit();
        return true;
    };
}