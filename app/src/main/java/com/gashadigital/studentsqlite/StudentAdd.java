package com.gashadigital.studentsqlite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StudentAdd extends Fragment {


    public StudentAdd() {
        // Required empty public constructor
    }

    public static StudentAdd newInstance(String param1, String param2) {
        StudentAdd fragment = new StudentAdd();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_add, container, false);
    }
}