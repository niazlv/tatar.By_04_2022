package com.example.telek;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mBackground, mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBackground = findViewById(R.id.background);
        mStart = findViewById(R.id.start);
        ObjectAnimator animation = ObjectAnimator.ofFloat(mBackground, "translationX", -600f);
        animation.setDuration(2000);
        animation.start();

        new CountDownTimer(2000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mStart.setVisibility(View.VISIBLE);
            }
        }.start();

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelActivity.class));
            }
        });
    }

}