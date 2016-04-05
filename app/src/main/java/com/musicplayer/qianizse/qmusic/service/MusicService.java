package com.musicplayer.qianizse.qmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.musicplayer.qianizse.qmusic.model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class MusicService extends Service {
    public static final int MUSIC_CHANGE = 1;
    public static final int MUSIC_STATE_CHANGE = 2;

    private MediaPlayer player = null;
    private Song song = null;
    private List<Observer> observerList = new ArrayList<>();




    @Override
    public void onCreate() {
          player = new MediaPlayer();
        player.reset();
        super.onCreate();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
            player = null;
        }
        observerList.clear();
        observerList=null;
        super.onDestroy();
    }

    public class MusicBind extends Binder {

        public void  seekTo(int postion){
            player.seekTo(postion);
        }


        public boolean isPlaying() {
            return player.isPlaying();
        }

        public int getCurrentPosition(){

            return player.getCurrentPosition();
        }


        public void registerObserver(Observer observer) {
            observerList.add(observer);
        }

        public void removeObserver(Observer observer) {
            observerList.remove(observer);

        }

        public Song getSong() {

            return song;
        }

        public void play() {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }

            dispatch(MUSIC_STATE_CHANGE);

        }

        public void play(Song _song) {

            song = _song;
            if (player == null) {
                return;
            }

            try {
                player.stop();
                player.reset();
                player.setDataSource(song.getFilePath());
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.start();

            dispatch(MUSIC_CHANGE);


        }


        public void pause() {
            if (player == null) return;
            player.pause();
            dispatch(MUSIC_STATE_CHANGE);
        }

        public void stop() {
            if (player == null) return;
            player.stop();
            dispatch(MUSIC_STATE_CHANGE);
        }


    }


    public void dispatch(int state) {
        Iterator<Observer> iterator = observerList.iterator();
        while (iterator.hasNext()) {
            iterator.next().musicChange(state);
        }

    }


    public interface Observer {
        //观察者，用于更换歌曲后通知各个观察者
        void musicChange(int state);

    }

}
