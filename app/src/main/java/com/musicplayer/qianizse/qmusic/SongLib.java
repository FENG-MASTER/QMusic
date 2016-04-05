package com.musicplayer.qianizse.qmusic;

import android.content.Context;
import android.database.Cursor;
import android.os.Looper;
import android.provider.MediaStore;

import com.musicplayer.qianizse.qmusic.model.Song;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class SongLib {
    private Context context = null;
    private static SongLib ourInstance = null;
    private List<Song> songList = new ArrayList<>();
    private List<Observer> observerList = new ArrayList<>();


    public static SongLib getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SongLib(context);
        }
        return ourInstance;
    }

    private SongLib(Context context) {
        this.context = context;
    }


    public void loadMusic() {
        Looper.prepare();//启动loop
        int num=0;
        //用系统的内容提供者数据，存到songList中
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null, null);
        while (cursor.moveToNext()) {
            num++;
            Song song = new Song();
            song.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            song.setSongName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            song.setSinger(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            song.setAlbumoh(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            song.setTime(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            songList.add(song);
        }
        dispatch(songList.size(),num);
        Looper.loop();
    }


    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    public void removeObserver(Observer observer) {
        observerList.remove(observer);

    }

    public void dispatch(int all,int new_num){
        Iterator<Observer> iterator=observerList.iterator();
        while (iterator.hasNext()){
            iterator.next().upDate(all,new_num);
        }

    }

    public List<Song> getSongList() {
        return songList;
    }

    public interface Observer {
        //观察者，用于更新歌曲列表后通知各个观察者
        void upDate(int all, int new_num);

    }
}
