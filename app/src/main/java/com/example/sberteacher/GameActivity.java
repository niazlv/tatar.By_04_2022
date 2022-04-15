package com.example.sberteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    ImageView im;
    TextView tv;
    String st;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for(int i=1;i<=24;i++) {
            int iimid = getResources().getIdentifier("im"+String.valueOf(i),
                    "id",getPackageName());
            int itid = getResources().getIdentifier("t"+String.valueOf(i),
                    "id",getPackageName());
            im = findViewById(iimid);
            tv = findViewById(itid);
            if(i%2==1)  continue;
            im.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }

        for(int i=1;i<=24;i++) {
            int iimid = getResources().getIdentifier("im"+String.valueOf(i),
                    "id",getPackageName());
            int itid = getResources().getIdentifier("t"+String.valueOf(i),
                    "id",getPackageName());
            im = findViewById(iimid);
            tv = findViewById(itid);


            final int finalI = i;
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    st = "Test"+String.valueOf(finalI);
                    Toast.makeText(GameActivity.this,st,Toast.LENGTH_SHORT).show();
                    view.setVisibility(View.INVISIBLE);
                    findViewById(getResources().getIdentifier("t"+String.valueOf(finalI),
                            "id",getPackageName())).setVisibility(View.INVISIBLE);
                }
            });
            
            im.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    st = "Hold on "+String.valueOf(finalI)+" btn";
                    Toast.makeText(GameActivity.this,st,Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}