package com.musicplayer.qianizse.qmusic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.musicplayer.qianizse.qmusic.activity.BaseActivity;
import com.musicplayer.qianizse.qmusic.activity.PlayerActivity;
import com.musicplayer.qianizse.qmusic.fragment.BaseListFragment;
import com.musicplayer.qianizse.qmusic.fragment.ListFragment;
import com.musicplayer.qianizse.qmusic.fragment.PlayFragment;
import com.musicplayer.qianizse.qmusic.model.Song;
import com.musicplayer.qianizse.qmusic.service.MusicService;

import java.io.File;
import java.io.Serializable;

public class MainActivity extends BaseActivity implements ServiceConnection, SongLib.Observer, View.OnClickListener, MusicService.Observer {


    private ImageButton play = null;
    private LinearLayout controller=null;
    private ImageButton next = null;
    private TextView title=null;
    private TextView singer=null;

    private FragmentManager manager = null;

    private MusicService.MusicBind bind=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //绑定
        play = (ImageButton) findViewById(R.id.ib_play);
        next = (ImageButton) findViewById(R.id.ib_next);
        title= (TextView) findViewById(R.id.tv_title);
        singer= (TextView) findViewById(R.id.tv_singer);
        controller= (LinearLayout) findViewById(R.id.ll_controller);

        controller.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        toolbar.setTitle("播放器");

        final SongLib songLib = SongLib.getInstance(this);
        songLib.registerObserver(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                songLib.loadMusic();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    @Override
    protected void onDestroy() {
        bind.removeObserver(this);
        SongLib.getInstance(this).removeObserver(this);
        unbindService(this);
        super.onDestroy();

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        bind= (MusicService.MusicBind) service;
        bind.registerObserver(this);//绑定service观察者，歌曲更换后及时通知，并更新主界面中的歌曲信息
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void upDate(int all, int new_num) {

        manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fl_content);

        if (fragment == null) {
            fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseListFragment.LIST, (Serializable) SongLib.getInstance(this).getSongList());
            fragment.setArguments(bundle);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_content, fragment).commit();
        }


    }

    @Override
    public void onClick(View v) {
        //按钮点击事件
        switch (v.getId()) {
            case R.id.ll_controller:{
                Intent intent=new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.ib_play: {
                bind.play();
                break;
            }
            case R.id.ib_next: {

                break;
            }

        }


    }

    @Override
    public void musicChange(int state) {
        switch (state){
            case MusicService.MUSIC_CHANGE:{
                //歌曲更换
                Song song=bind.getSong();
                title.setText(song.getSongName());
                singer.setText(song.getSinger());
                break;
            }

            case MusicService.MUSIC_STATE_CHANGE:{
                //播放参数更换
                if (bind.isPlaying()){
                    play.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                }else {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                }


                break;
            }

        }

    }
}
