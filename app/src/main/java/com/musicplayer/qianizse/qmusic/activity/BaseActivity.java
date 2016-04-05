package com.musicplayer.qianizse.qmusic.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.musicplayer.qianizse.qmusic.R;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_include);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
       toolbar.setTitle("播放器");

    }

    @Override
    public void setContentView(int layoutResID) {

        if(layoutResID!=R.layout.activity_toolbar_include){
            View view=getLayoutInflater().inflate(layoutResID,null);
            FrameLayout frameLayout= (FrameLayout) findViewById(R.id.ly_include);
            frameLayout.addView(view);

        }else{
            super.setContentView(layoutResID);

        }


    }
}
