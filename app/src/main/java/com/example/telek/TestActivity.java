package com.example.telek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class TestActivity extends AppCompatActivity {


    ImageView mA1, mA2, mA3, mA4;
    Integer current_question=1, flag = 0, correct=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //array with questions
        String[] images = {"ic_paper", "ic_student", "ic_school", "ic_book", "ic_workbook", "ic_human", "ic_backpack"};
        String[] q = {"Мин _____ белэн дэфтэрдэ язам", "ул ____ белэн рэсем ясый", "мэктэптэ зур _________", "укучы _____ ала", "_______ язырга ойрэтэ"};
        String[] a = {"ic_pencil", "ic_dye", "ic_library", "ic_5", "ic_teacher"};

        //elements
        TextView mQuestion = findViewById(R.id.question);
        ImageView check = findViewById(R.id.check_button);
        mA1 = findViewById(R.id.ans1);
        mA2 = findViewById(R.id.ans2);
        mA3 = findViewById(R.id.ans3);
        mA4 = findViewById(R.id.ans4);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        TextView mExit = findViewById(R.id.goout);
        TextView mForward = findViewById(R.id.forward);


        //set answers images
        Random random = new Random();
        correct = random.nextInt(4) + 1;
        set_images(images);

        //set correct image
        if (correct==1){
            mA1.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(a[current_question-1], "drawable", this.getPackageName())));
        } else if (correct==2){
            mA2.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(a[current_question-1], "drawable", this.getPackageName())));
        } else if (correct==3){
            mA3.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(a[current_question-1], "drawable", this.getPackageName())));
        } else{
            mA4.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(a[current_question-1], "drawable", this.getPackageName())));
        }

        //set question
        mQuestion.setText(q[current_question-1]);

        //set clicks
        ImageView mA1_b = findViewById(R.id.imageView13);
        ImageView mA2_b = findViewById(R.id.imageView14);
        ImageView mA3_b = findViewById(R.id.imageView15);
        ImageView mA4_b = findViewById(R.id.imageView16);
        mA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA1_b.setImageResource(R.drawable.ic_test_holder);
                mA2_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA3_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA4_b.setImageResource(R.drawable.ic_test_holder_tr);
                flag = 1;
            }
        });
        mA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA2_b.setImageResource(R.drawable.ic_test_holder);
                mA1_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA3_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA4_b.setImageResource(R.drawable.ic_test_holder_tr);
                flag = 2;
            }
        });
        mA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA3_b.setImageResource(R.drawable.ic_test_holder);
                mA2_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA1_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA4_b.setImageResource(R.drawable.ic_test_holder_tr);
                flag = 3;
            }
        });
        mA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA4_b.setImageResource(R.drawable.ic_test_holder);
                mA2_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA3_b.setImageResource(R.drawable.ic_test_holder_tr);
                mA1_b.setImageResource(R.drawable.ic_test_holder_tr);
                flag = 4;
            }
        });

        //set check button
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_question>5){
                    startActivity(new Intent(TestActivity.this, LevelActivity.class));
                }
                if (correct==flag){
                    check.setImageResource(R.drawable.ic_correct_button);
                    mForward.setVisibility(View.VISIBLE);
                    mExit.setVisibility(View.VISIBLE);
                } else{
                    check.setImageResource(R.drawable.ic_error_button);
                }
            }
        });

        //set Exit and Next buttons
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, LevelActivity.class));
            }
        });

        String[] finalImages = images;
        mForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 0;
                current_question += 1;
                if (current_question>5){
                    startActivity(new Intent(TestActivity.this, LevelActivity.class));
                } else{
                    progressBar.setProgress(progressBar.getProgress()+1);
                    check.setImageResource(R.drawable.ic_check_button);
                    mA1_b.setImageResource(R.drawable.ic_test_holder_tr);
                    mA2_b.setImageResource(R.drawable.ic_test_holder_tr);
                    mA3_b.setImageResource(R.drawable.ic_test_holder_tr);
                    mA4_b.setImageResource(R.drawable.ic_test_holder_tr);
                    mQuestion.setText(q[current_question-1]);

                    if (current_question==2){
                        mA1.setImageResource(R.drawable.ic_dye);
                        correct = 1;
                    } else if (current_question==3){
                        mA4.setImageResource(R.drawable.ic_library);
                        correct = 4;
                    } else if (current_question==4){
                        mA2.setImageResource(R.drawable.ic_5);
                        correct = 2;
                    } else if (current_question==5){
                        mA3.setImageResource(R.drawable.ic_teacher);
                        correct = 3;
                    } else{
                        startActivity(new Intent(TestActivity.this, LevelActivity.class));
                    }
                }


            }
        });



    }

    public void set_images(String[] images){
        Random random = new Random();
        Integer r1 = 0, r2 = 0, r3 = 0, r4 = 0;
        while (r1==r2 || r1==r3 || r1==r4 || r2==r3 || r2==r4 || r3==r4 ){
            r1 = random.nextInt(images.length);
            r2 = random.nextInt(images.length);
            r3 = random.nextInt(images.length);
            r4 = random.nextInt(images.length);
        }

        mA1.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(images[r1], "drawable", this.getPackageName())));
        mA2.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(images[r2], "drawable", this.getPackageName())));
        mA3.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(images[r3], "drawable", this.getPackageName())));
        mA4.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(images[r4], "drawable", this.getPackageName())));



    }
}