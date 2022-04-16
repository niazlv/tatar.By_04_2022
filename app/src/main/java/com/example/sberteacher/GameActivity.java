package com.example.sberteacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.annotation.SuppressLint;
import android.media.Image;
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
import java.util.ArrayList;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {
    ImageView im;
    TextView tv;
    Integer count = 0;
    String tat, rus, img;

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

        for (int i=1; i<=24; i++){
            try {
                JSONObject obj = new JSONObject(str_data);
                tat = obj.getJSONObject(String.valueOf(i)).getString("tatar");
                rus = obj.getJSONObject(String.valueOf(i)).getString("rus");
                img = obj.getJSONObject(String.valueOf(i)).getString("img");
            } catch (Exception e){
                e.printStackTrace();
            }



            im = findViewById(getResources().getIdentifier("im"+String.valueOf(i), "id",getPackageName()));
            tv = findViewById(getResources().getIdentifier("t"+String.valueOf(i), "id",getPackageName()));

            if (i <= 3){
                im.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(img, "drawable", this.getPackageName())));
                tv.setText(tat);
            } else{
                im.setVisibility(View.INVISIBLE);
                tv.setText("");
            }

            final String new_tat = tat;
            final String new_rus = rus;
            final ImageView new_img = im;

            final ImageView mHolder1 = findViewById(R.id.holder1);
            final ImageView mHolder2 = findViewById(R.id.holder2);
            final ImageView mHolder3 = findViewById(R.id.holder3);

            final ArrayList<String> merge = new ArrayList<String>();

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count == 0){
                        mHolder1.setImageDrawable(new_img.getDrawable());
                        count += 1;
                        merge.add(new_rus);
                    } else if (count == 1){
                        mHolder2.setImageDrawable(new_img.getDrawable());
                        count += 1;
                        merge.add(new_rus);
                    } else if (count == 2){
                        mHolder3.setImageDrawable(new_img.getDrawable());
                        count += 1;
                        merge.add(new_rus);
                    } else{
                        Toast.makeText(GameActivity.this, "Вы не можете смешивать более 3-х предметов!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            im.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //create alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    View mView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_item_info, null);
                    ImageView mImage = mView.findViewById(R.id.mImage);
                    ImageView mDictionary = mView.findViewById(R.id.dictionary);
                    ImageView mCloseAlert = mView.findViewById(R.id.close_alert);
                    TextView mRus = mView.findViewById(R.id.text_rus);
                    TextView mTat = mView.findViewById(R.id.text_tat);

                    mTat.setText(new_tat);
                    mRus.setText(new_rus);
                    mImage.setImageDrawable(new_img.getDrawable());

                    builder.setView(mView);
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
                    dialog.show();


                    return true;
                }
            });
        }

    }
}