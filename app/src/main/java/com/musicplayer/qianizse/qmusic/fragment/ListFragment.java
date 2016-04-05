package com.musicplayer.qianizse.qmusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.musicplayer.qianizse.qmusic.activity.PlayerActivity;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class ListFragment extends BaseListFragment{




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        intent.putExtra(PlayerActivity.SONG,songList.get(position));
        startActivity(intent);
    }



}
