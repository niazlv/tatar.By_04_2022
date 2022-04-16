package com.example.sberteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    String[] findInJSON(String str_data,String find,Boolean isTat)
    {
        String lrus = null,limg=null,ltat=null,ldef=null,example = null,translation = null;

        for(int x = 1;x<24;x++) {
            try {
                JSONObject obj = new JSONObject(str_data);
                lrus = obj.getJSONObject(String.valueOf(x)).getString("rus");
                ltat = obj.getJSONObject(String.valueOf(x)).getString("tatar");
                if((!lrus.equals(find) && !isTat)||(!ltat.equals(find) && isTat))
                    continue;
                limg = obj.getJSONObject(String.valueOf(x)).getString("img");
                ltat = obj.getJSONObject(String.valueOf(x)).getString("tatar");
                ldef = obj.getJSONObject(String.valueOf(x)).getString("def");
                example = obj.getJSONObject(String.valueOf(x)).getString("example");
                translation = obj.getJSONObject(String.valueOf(x)).getString("translation");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String[]{lrus, ltat, limg, ldef,example,translation};
    }
    String[] findInJSON(String str_data,String find){
        return findInJSON(str_data,find,false);
    }

    public String Assets2Str(String filename)
    {
        //reading json from assets
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(filename);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(buffer);
    }

    ImageView mAns1, mAns2, mAns3, mAns4;
    Integer flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[] images = {"ic_pencil", "ic_paper", "ic_dye", "ic_teacher", "ic_student", "ic_5", "ic_school", "ic_book", "ic_workbook", "ic_library", "ic_human", "ic_backpack"};
        String src = Assets2Str("test1.json");
        ArrayList<Integer> test = new ArrayList<Integer>();
        JSONObject obj = null;
        try {
            obj = new JSONObject(src);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAns1 = findViewById(R.id.ans1);
        mAns2 = findViewById(R.id.ans2);
        mAns3 = findViewById(R.id.ans3);
        mAns4 = findViewById(R.id.ans4);
        ImageView im;
        Random r = new Random();
        int ran = r.nextInt(4)+1;

        {
            String str=null;
            try {
                str = obj.getJSONObject(String.valueOf(1)).getString("answer_img");
            } catch (Exception e){}
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

            im = findViewById(getResources().getIdentifier("ans"+String.valueOf(ran), "id",getPackageName()));
            //im.setImageResource(R.drawable.ic_pencil);
            im.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(str, "drawable", this.getPackageName())));
            for(int j = 1; j<=4;j++)
            {
                int rando = r.nextInt(images.length);
                if(j==ran)
                    continue;
                while(images[rando].equals(str))
                    rando = r.nextInt(images.length);
                for(int g = 0;g<test.size();g++)
                {
                    while (rando == test.get(g))
                        rando = r.nextInt(images.length);
                }
                test.add(rando);
                im = findViewById(getResources().getIdentifier("ans"+String.valueOf(j), "id",getPackageName()));
                im.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(images[rando], "drawable", this.getPackageName())));
            }

            flag = 0;

            ImageView m13, m14, m15, m16;
            m13 = findViewById(R.id.imageView13);
            m14 = findViewById(R.id.imageView14);
            m15 = findViewById(R.id.imageView15);
            m16 = findViewById(R.id.imageView16);

            mAns1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m13.setImageResource(R.drawable.ic_test_holder);
                    m14.setImageResource(R.drawable.ic_test_holder_tr);
                    m15.setImageResource(R.drawable.ic_test_holder_tr);
                    m16.setImageResource(R.drawable.ic_test_holder_tr);
                    flag = 1;
                }
            });

            mAns2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m13.setImageResource(R.drawable.ic_test_holder_tr);
                    m14.setImageResource(R.drawable.ic_test_holder);
                    m15.setImageResource(R.drawable.ic_test_holder_tr);
                    m16.setImageResource(R.drawable.ic_test_holder_tr);
                    flag = 2;
                }
            });

            mAns3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m13.setImageResource(R.drawable.ic_test_holder_tr);
                    m14.setImageResource(R.drawable.ic_test_holder_tr);
                    m15.setImageResource(R.drawable.ic_test_holder);
                    m16.setImageResource(R.drawable.ic_test_holder_tr);
                    flag = 3;
                }
            });

            mAns4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m13.setImageResource(R.drawable.ic_test_holder_tr);
                    m14.setImageResource(R.drawable.ic_test_holder_tr);
                    m15.setImageResource(R.drawable.ic_test_holder_tr);
                    m16.setImageResource(R.drawable.ic_test_holder);
                    flag = 4;
                }
            });

            ImageView mCheck;
            mCheck = findViewById(R.id.check_button);
            mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag==ran){
                        mCheck.setImageResource(R.drawable.ic_correct_button);
                    } else{
                        mCheck.setImageResource(R.drawable.ic_error_button);
                    }
                }
            });

        }
    }
}