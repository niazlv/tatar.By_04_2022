package com.example.sberteacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.*;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {
    ImageView im;
    TextView tv;
    String st;
    String iimg;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //reading json from assets
        String text = "level1.json";
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(text);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String str_data = new String(buffer);
        //result str_data json string

        //clear icons
        for(int i=1;i<=24;i++) {
            int iimid = getResources().getIdentifier("im"+String.valueOf(i),
                    "id",getPackageName());
            int itid = getResources().getIdentifier("t"+String.valueOf(i),
                    "id",getPackageName());


            try {
                JSONObject obj = new JSONObject(str_data);
                iimg = obj.getJSONObject(String.valueOf(i)).getString("img");
            } catch (Exception e){
                e.printStackTrace();
            }

            im = findViewById(iimid);
            tv = findViewById(itid);

            //im.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(iimg, "drawable", this.getPackageName())));

            im.setImageResource(this.getResources().getIdentifier(iimg, "drawable", this.getPackageName()));

            tv.setText("");
            if(i<=3)  continue;
            im.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }

        //main action
        for(int i=1;i<=24;i++) {
            final int iimid = getResources().getIdentifier("im"+String.valueOf(i),
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
                    String iimg = "ic_exit";
                    try {
                        JSONObject obj = new JSONObject(str_data);
                        iimg = obj.getJSONObject(String.valueOf(finalI)).getString("img");
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    Toast.makeText(GameActivity.this,st,Toast.LENGTH_SHORT).show();
                    //im.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(iimg, "drawable", getPackageName())));

                    //view.setVisibility(View.INVISIBLE);
                    //findViewById(getResources().getIdentifier("t"+String.valueOf(finalI),
                    //        "id",getPackageName())).setVisibility(View.INVISIBLE);
                }
            });


            im.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String rus = "null";
                    String tat = "null";
                    try {
                        JSONObject obj = new JSONObject(str_data);
                        rus = obj.getJSONObject(String.valueOf(finalI)).getString("rus");
                        tat = obj.getJSONObject(String.valueOf(finalI)).getString("tatar");
                        Toast.makeText(GameActivity.this,rus,Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    st = "Hold on "+String.valueOf(finalI)+" btn";

                    //create alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    View mView = (ConstraintLayout) getLayoutInflater()
                            .inflate(R.layout.alert_item_info, null);

                    ImageView mDictionary = mView.findViewById(R.id.dictionary);
                    ImageView mCloseAlert = mView.findViewById(R.id.close_alert);
                    TextView mRus = mView.findViewById(R.id.text_rus);
                    TextView mTat = mView.findViewById(R.id.text_tat);
                    ImageView mImage = mView.findViewById(R.id.mImage);

                    mImage.setImageDrawable(im.getDrawable());

                    mRus.setText(rus);
                    mTat.setText(tat);

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