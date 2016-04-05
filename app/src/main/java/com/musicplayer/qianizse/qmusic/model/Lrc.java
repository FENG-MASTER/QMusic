package com.musicplayer.qianizse.qmusic.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 55428 on 2016/4/4 0004.
 */
public class Lrc {
    private static final String HEAD="\\[(\\w+):(\\w+)\\]";
    private static final String CONTENT="\\[(\\d+):(\\b).(\\b)\\](\\w*)";
    public String content=null;
    public String head=null;
    public Map<String,String> info=new HashMap<>();
    private int cur=0;
    private Map<Integer,String> lrcMap=new HashMap<>();

    private void initInfo(){
        Pattern pattern=Pattern.compile(HEAD);
        Matcher matcher=pattern.matcher(head);
        info.clear();
        matcher.reset();
        while (matcher.find()){
            switch (matcher.group(1)){
                case "ti":
                {
                    info.put("歌曲名",matcher.group(2));
                    break;
                }
                case "ar":
                {
                    info.put("演唱者",matcher.group(2));
                    break;
                }
                case "al":
                {
                    info.put("专辑",matcher.group(2));
                    break;
                }
                case "by":
                {
                    info.put("歌词制作",matcher.group(2));
                    break;
                }
                case "offset":
                {
                    info.put("offset",matcher.group(2));
                    break;
                }
            }

        }
    }

    private void initContent(){
        Pattern pattern=Pattern.compile(CONTENT);
        Matcher matcher=pattern.matcher(content);
        lrcMap.clear();
        matcher.reset();


    }






    public static class Loader{
        public static Lrc loadLrc(String filePath){
            Lrc lrc=new Lrc();
            StringBuilder builder=new StringBuilder("");


            BufferedReader bufferedReader= null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String line=null;
            StringBuilder headBuilder=new StringBuilder("");
            int lineNum=1;
            try {
                while (null!=(line=bufferedReader.readLine())){
                    if (lineNum>0&&lineNum<6)
                    {
                        headBuilder.append(line);
                    }else {
                        builder.append(line);
                    }

                    lineNum++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            lrc.head=headBuilder.toString();
            lrc.content=builder.toString();
            lrc.initInfo();
            return lrc;
        }

    }

}
