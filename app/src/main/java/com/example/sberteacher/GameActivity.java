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
    String st, tat, rus, img;
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