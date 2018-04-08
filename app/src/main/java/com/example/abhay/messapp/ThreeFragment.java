package com.example.abhay.messapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ThreeFragment extends Fragment{

    private EditText editText;
    private Button button;

    public ThreeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_three, container, false);

        editText = v.findViewById(R.id.studName);
        button = v.findViewById(R.id.viewStud);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), StudentViewActivity.class);
                        i.putExtra("studentName", editText.getText().toString());
                        startActivity(i);
                    }
                }
        );

        return v;
    }
}