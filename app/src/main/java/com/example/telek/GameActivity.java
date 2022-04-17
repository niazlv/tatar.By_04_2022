package com.example.telek;

import static com.example.telek.DictionaryActivity.mArrayDict;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

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

    ImageView im;
    TextView tv;
    Integer count = 0;
    Boolean isPlace = false;
    Integer place = 3;
    ArrayList<String> merge = new ArrayList<String>();
    String tat, rus, img,lrus,limg, ltat, def, ldef,lexample,ltranslation;
    ArrayList<String> todo=new ArrayList<String>(Arrays.asList("тетрадь", "книга", "рюкзак", "школьник","учитель","школа","библиотека","-"));
    TextView todoTV;
    String todoBase="";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences sp = getSharedPreferences("pref", Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {

            //выводим алерт
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            View mView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_info, null);
            builder.setView(mView);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
            dialog.show();


            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit();
        }

        final String str_data = Assets2Str("level1.json");
        todoTV = findViewById(R.id.text_hint);
        todoBase = todoTV.getText().toString();
        todoTV.setText(todoBase+todo.get(0));
        final String[][][] mergeList ={ { {"бумага", "бумага"}, {"книга"}, {"читать"}} , {{"бумага", "карандаш"}, {"тетрадь"}, {"писать"}}, {{"книга", "тетрадь"}, {"рюкзак"}, {"большой"}}, {{"человек", "рюкзак"}, {"школьник"}, {"умный"}}, {{"школьник", "школьник"}, {"учитель"}, {"учить"}}, {{"книга", "книга", "книга"}, {"библиотека"}, {"маленькая"}}, {{"книга", "ученик", "тетрадь"}, {"пятерка"}, {"получать"}}, {{"учитель", "школьник", "школьник"}, {"школа"}, {"большая"}}  };

        for (int i=1; i<=24; i++){
            try {
                JSONObject obj = new JSONObject(str_data);
                tat = obj.getJSONObject(String.valueOf(i)).getString("tatar");
                rus = obj.getJSONObject(String.valueOf(i)).getString("rus");
                img = obj.getJSONObject(String.valueOf(i)).getString("img");
                def = obj.getJSONObject(String.valueOf(i)).getString("def");
            } catch (Exception e){
                e.printStackTrace();
            }



            im = findViewById(getResources().getIdentifier("im"+String.valueOf(i), "id",getPackageName()));
            tv = findViewById(getResources().getIdentifier("t"+String.valueOf(i), "id",getPackageName()));
            //init first resources
            if (i <= 3){
                im.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(img, "drawable", this.getPackageName())));
                tv.setText(tat);

                ArrayList<String> mList = new ArrayList<String>();
                mList.add(rus);
                mList.add(def);
                mArrayDict.add(mList);

            } else{
                im.setVisibility(View.INVISIBLE);
                tv.setText("");
            }

            final String new_tat = tat;
            final String new_rus = rus;
            final ImageView new_img = im;
            final String new_def = def;

            final ImageView mHolder1 = findViewById(R.id.holder1);
            final ImageView mHolder2 = findViewById(R.id.holder2);
            final ImageView mHolder3 = findViewById(R.id.holder3);

            final  TextView finalTV = tv;

            //header with 3 slots to marge
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] res = findInJSON(str_data,finalTV.getText().toString(),true);
                    if (count == 0){
                        mHolder1.setImageDrawable(new_img.getDrawable());
                        count += 1;
                        merge.add(res[0]);
                    } else if (count == 1){
                        mHolder2.setImageDrawable(new_img.getDrawable());
                        count += 1;
                        merge.add(res[0]);
                    } else if (count == 2){
                        mHolder3.setImageDrawable(new_img.getDrawable());
                        count += 1;
                        merge.add(res[0]);
                    } else{
                        Toast.makeText(GameActivity.this, "Вы не можете смешивать более 3-х предметов!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //delete button
            ImageView mDelete = findViewById(R.id.delete);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = 0;
                    mHolder1.setImageResource(0);
                    mHolder2.setImageResource(0);
                    mHolder3.setImageResource(0);
                    merge = new ArrayList<String>();
                }
            });

            mHolder1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(count > 0)
                    {
                        mHolder1.setImageResource(0);
                        if(count>1)
                        {
                            mHolder1.setImageDrawable(mHolder2.getDrawable());
                            mHolder2.setImageResource(0);
                            if(count>2) {
                                merge.set(count-3,merge.get(count-2));
                                mHolder2.setImageDrawable(mHolder3.getDrawable());
                                mHolder3.setImageResource(0);
                                merge.set(count-2,merge.get(count-1));
                                merge.remove(count-1);
                            }
                            else {
                                merge.set(count-2,merge.get(count-1));
                                merge.remove(count - 1);
                            }
                        }
                        else
                            merge.remove(count-1);
                        count--;
                    }
                }
            });

            //merge button
            ImageView mMerge = findViewById(R.id.merge);
            mMerge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sort current merge combination
                    Collections.sort(merge);

                    ArrayList<String> mergeTemp;
                    for(int j = 0; j<mergeList.length;j++) {
                        mergeTemp = new ArrayList<String>(Arrays.asList(mergeList[j][0]));
                        Collections.sort(mergeTemp);

                        //if merge found
                        if (mergeTemp.equals(merge)) {
                            //block repeat open new resource
                            mergeList[j][0][0] = "0";
                            mergeList[j][0][1] = "0";

                            if (String.valueOf(mergeTemp.get(0))=="учитель"){


                                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                                View mView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_alert_get_new_word_for_teacher, null);
                                ImageView mTest = mView.findViewById(R.id.to_test);
                                mTest.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(GameActivity.this, TestActivity.class));
                                    }
                                });

                                builder.setView(mView);
                                AlertDialog dialog = builder.create();
                                dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
                                dialog.show();



                            } else{
                                //create alert

                                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                                View mView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_alert_get_new_word, null);
                                TextView mRus = mView.findViewById(R.id.rus);
                                TextView mTat = mView.findViewById(R.id.tat);
                                ImageView mImage = mView.findViewById(R.id.item_image);
                                TextView mExample = mView.findViewById(R.id.example);
                                TextView mTranslation = mView.findViewById(R.id.translation_example);
                                TextView mAdditional = mView.findViewById(R.id.text_additional);


                                //get name in russian, tatar and get resource image
                                String[] res;
                                res = findInJSON(str_data,mergeList[j][1][0]);
                                lrus = res[0];
                                ltat = res[1];
                                limg = res[2];
                                ldef = res[3];
                                lexample=res[4];
                                ltranslation=res[5];
                                mRus.setText(lrus);
                                mTat.setText(ltat);
                                mExample.setText(lexample);
                                mTranslation.setText(ltranslation);
                                if(ldef.equals(""))
                                    mAdditional.setText("Продолжай в том же духе! У тебя хорошо получается!");
                                else
                                    mAdditional.setText(ldef);
                                for(int k =0;k<todo.size();k++)
                                {
                                    if(todo.get(k).equals(lrus))
                                    {
                                        todo.remove(k);
                                    }
                                }
                                todoTV.setText(todoBase+todo.get(0));
                                //add to dict
                                ArrayList<String> mList = new ArrayList<String>();
                                mList.add(ltat);
                                mList.add(ldef);
                                mArrayDict.add(mList);

                                //double word
                                try {
                                    res = findInJSON(str_data, mergeList[j][2][0]);
                                    mList = new ArrayList<String>();
                                    mList.add(res[1]);
                                    mList.add(res[3]);
                                    mArrayDict.add(mList);
                                } catch (Exception e){}

                                mImage.setImageDrawable(ContextCompat.getDrawable(GameActivity.this,getResources().getIdentifier(limg, "drawable", getPackageName())));

                                //alert show
                                builder.setView(mView);
                                AlertDialog dialog = builder.create();
                                dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
                                dialog.show();

                                //simple check count overflow
                                if(place<=24)
                                    place++;

                                //!!!!----MAGIC----!!!!
                                ImageView tempim = findViewById(getResources().getIdentifier("im"+String.valueOf(place), "id",getPackageName()));
                                TextView tempt = findViewById(getResources().getIdentifier("t"+String.valueOf(place), "id",getPackageName()));
                                tempim.setVisibility(View.VISIBLE);
                                tempim.setImageDrawable(ContextCompat.getDrawable(GameActivity.this,getResources().getIdentifier(limg, "drawable", getPackageName())));
                                tempt.setText(ltat);

                                //add human if have 5 items
                                if(place == 5) {
                                    res = findInJSON(str_data, "человек");
                                    place++;
                                    lrus = res[0];
                                    ltat = res[1];
                                    limg = res[2];
                                    tempim = findViewById(getResources().getIdentifier("im" + String.valueOf(place), "id", getPackageName()));
                                    tempt = findViewById(getResources().getIdentifier("t" + String.valueOf(place), "id", getPackageName()));
                                    tempim.setVisibility(View.VISIBLE);
                                    tempim.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, getResources().getIdentifier(limg, "drawable", getPackageName())));
                                    tempt.setText(ltat);
                                }

                                //and reset mergin header
                                count = 0;
                                mHolder1.setImageResource(0);
                                mHolder2.setImageResource(0);
                                mHolder3.setImageResource(0);
                                merge = new ArrayList<String>();
                                //exit from loop
                                break;
                            }

                        } else{
                            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vib.vibrate(400);
                            mHolder1.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.shake));
                            mHolder2.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.shake));
                            mHolder3.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.shake));
                        }
                    }
                }
            });
            //holding resource(opening menu)
            final TextView temptv=tv;
            im.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //create alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    View mView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_item_info, null);
                    ImageView mImage = mView.findViewById(R.id.mImage);
                    ImageView mDictionary = mView.findViewById(R.id.dictionary);
                    mDictionary.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(GameActivity.this, DictionaryActivity.class));
                        }
                    });
                    ImageView mCloseAlert = mView.findViewById(R.id.close_alert);
                    TextView mRus = mView.findViewById(R.id.text_rus);
                    TextView mTat = mView.findViewById(R.id.text_tat);

                    //find tat name in json and get any names and resources to correct give request
                    String[] res = findInJSON(str_data,temptv.getText().toString(),true);
                    mTat.setText(res[1]);
                    mRus.setText(res[0]);
                    mImage.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, getResources().getIdentifier(res[2], "drawable", getPackageName())));

                    //show dialog
                    builder.setView(mView);
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
                    dialog.show();

                    mCloseAlert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    //need to exit and enable tacktic responce
                    return true;
                }
            });
        }

    }
}