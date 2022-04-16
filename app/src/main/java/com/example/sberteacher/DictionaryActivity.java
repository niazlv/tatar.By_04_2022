package com.example.sberteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity {

    static ArrayList<ArrayList<String>> mArrayDict = new ArrayList<ArrayList<String>>();
    ArrayList<State> states = new ArrayList<State>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);


        RecyclerView recyclerView = findViewById(R.id.list);
        StateAdapter adapter = new StateAdapter(this, states);
        recyclerView.setAdapter(adapter);
        ImageView exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DictionaryActivity.super.finish();
            }
        });
        for (int i=0; i<mArrayDict.size(); i++){
            states.add(new State (mArrayDict.get(i).get(0), mArrayDict.get(i).get(1)));
        }


    }
}