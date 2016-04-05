package com.musicplayer.qianizse.qmusic.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.musicplayer.qianizse.qmusic.R;
import com.musicplayer.qianizse.qmusic.model.Lrc;
import com.musicplayer.qianizse.qmusic.model.Song;
import com.musicplayer.qianizse.qmusic.service.MusicService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class PlayerActivity extends BaseActivity implements ServiceConnection, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String SONG = "PlayFragment.SONG";
    private Song song = null;
    private MusicService.MusicBind bind = null;
    private ImageButton play;
    private ImageButton pre;
    private ImageButton next;
    private SeekBar seekBar;
    private TextView currentTime;
    private TextView allTime;
    private Timer timer = new Timer();
    private boolean isRemind = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);
        Bundle bundle = getIntent().getExtras();
        if (null == bundle) {
            isRemind = true;
        } else {
            song = (Song) bundle.getSerializable(SONG);
        }


        //绑定service
        Intent intent = new Intent(PlayerActivity.this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        toolbar.setNavigationIcon(android.R.drawable.arrow_down_float);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerActivity.this.finish();
            }
        });

        play = (ImageButton) findViewById(R.id.ib_play);
        next = (ImageButton) findViewById(R.id.ib_next);
        pre = (ImageButton) findViewById(R.id.ib_pre);
        seekBar = (SeekBar) findViewById(R.id.sb_player);
        currentTime = (TextView) findViewById(R.id.tv_current);
        allTime = (TextView) findViewById(R.id.tv_allTime);
        seekBar.setOnSeekBarChangeListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        pre.setOnClickListener(this);


        Lrc.Loader.loadLrc(Environment.getExternalStorageDirectory()+"/1.lrc");
    }

    @Override
    protected void onDestroy() {
        unbindService(this);
        bind = null;
        //释放，不然ANR
        timer.cancel();
        timer = null;
        super.onDestroy();
    }

    public void init() {
        seekBar.setMax(bind.getSong().getTime());
        toolbar.setTitle(bind.getSong().getSongName());
        toolbar.setSubtitle(bind.getSong().getSinger());
        allTime.setText(Song.Tool.convert2min(bind.getSong().getTime()));
    }

    public void setBarProgress(int time) {
        seekBar.setProgress(time);

    }

    public void bindProgress() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setBarProgress(bind.getCurrentPosition());
            }
        };


        timer.schedule(timerTask, 0, 1000);

    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        bind = (MusicService.MusicBind) service;
        if (isRemind) {
            song = bind.getSong();
            isRemind = false;
        } else {
            bind.play(song);
        }


        init();
        bindProgress();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        bind = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ib_play: {
                bind.play();
                break;
            }
            case R.id.ib_pre: {

                break;
            }
            case R.id.ib_next: {

                break;
            }

        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currentTime.setText(Song.Tool.convert2min(progress));
        if (fromUser) {
            bind.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
