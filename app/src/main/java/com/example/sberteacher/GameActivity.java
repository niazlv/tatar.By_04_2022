package com.example.sberteacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

                    //create alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    view = (ConstraintLayout) getLayoutInflater()
                            .inflate(R.layout.alert_item_info, null);

                    ImageView mDictionary = view.findViewById(R.id.dictionary);
                    ImageView mCloseAlert = view.findViewById(R.id.close_alert);
                    TextView mRus = view.findViewById(R.id.text_rus);
                    TextView mTat = view.findViewById(R.id.text_tat);
                    ImageView mImage = view.findViewById(R.id.mImage);

                    mImage.setImageDrawable(im.getDrawable());

                    builder.setView(view);
                    AlertDialog dialog
                            = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
                    dialog.show();


                    Toast.makeText(GameActivity.this,st,Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}