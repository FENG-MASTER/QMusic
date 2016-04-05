package com.musicplayer.qianizse.qmusic.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.musicplayer.qianizse.qmusic.R;
import com.musicplayer.qianizse.qmusic.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 55428 on 2016/4/3 0003.
 */
public class BaseListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String LIST = "BaseListFragment.LIST";

    protected List<Song> songList = new ArrayList<>();
    protected ListView listView=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songList = (List<Song>) getArguments().getSerializable(LIST);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_baselist, null);
        listView= (ListView) view.findViewById(R.id.lv_base);
        listView.setAdapter(new ListAdapter());
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



    }


    private class Holder{
        TextView name=null;
        TextView singer=null;
    }

    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return songList.size();
        }

        @Override
        public Object getItem(int position) {
            return songList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder=null;
            if (convertView==null){
                convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_song,null);
                holder=new Holder();
                holder.name= (TextView) convertView.findViewById(R.id.tv_title);
                holder.singer= (TextView) convertView.findViewById(R.id.tv_singer);
                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }
            holder.name.setText(songList.get(position).getSongName());
            holder.singer.setText(songList.get(position).getSinger());
            return convertView;
        }
    }
}
