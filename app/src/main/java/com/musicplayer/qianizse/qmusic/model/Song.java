package com.musicplayer.qianizse.qmusic.model;

import java.io.File;
import java.io.Serializable;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class Song implements Serializable{

    private String songName;
    private String singer;
    private String albumoh;
    private String filePath;
    private String lrcPath;
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }



    public Song(String filePath) {
        this(new File(filePath).getName(),"","",filePath,filePath);
    }

    public Song() {

    }

    public Song(String songName, String singer, String albumoh, String filePath, String lrcPath) {
        this.songName = songName;
        this.singer = singer;
        this.albumoh = albumoh;
        this.filePath = filePath;
        this.lrcPath = lrcPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbumoh() {
        return albumoh;
    }

    public void setAlbumoh(String albumoh) {
        this.albumoh = albumoh;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLrcPath() {
        return lrcPath;
    }

    public void setLrcPath(String lrcPath) {
        this.lrcPath = lrcPath;
    }


    public static class Tool{
        public static String convert2min(int milliseconds){
            int m=(milliseconds/1000)/60;
            int s=(milliseconds/1000)%60;
            return m+":"+s;
        }


    }

}
