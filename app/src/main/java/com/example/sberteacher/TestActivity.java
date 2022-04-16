package com.example.sberteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    ImageView mAns1, mAns2, mAns3, mAns4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[] images = {"ic_pencil", "ic_paper", "ic_dye", "ic_teacher", "ic_student", "ic_5", "ic_school", "ic_book", "ic_workbook", "ic_library", "ic_human", "ic_backpack"};

        mAns1 = findViewById(R.id.ans1);
        mAns2 = findViewById(R.id.ans2);
        mAns3 = findViewById(R.id.ans3);
        mAns4 = findViewById(R.id.ans4);


    }
}