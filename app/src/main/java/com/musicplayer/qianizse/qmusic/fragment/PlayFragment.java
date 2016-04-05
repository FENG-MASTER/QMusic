package com.musicplayer.qianizse.qmusic.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicplayer.qianizse.qmusic.R;
import com.musicplayer.qianizse.qmusic.model.Song;

/**
 * Created by 55428 on 2016/4/3 0003.
 *
 * 废弃
 */
public class PlayFragment extends Fragment{
    public static final String SONG="PlayFragment.SONG";
    private Song song=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        song= (Song) getArguments().getSerializable(SONG);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
      View view=  inflater.inflate(R.layout.fragment_player,null);
        return view;
    }
}
