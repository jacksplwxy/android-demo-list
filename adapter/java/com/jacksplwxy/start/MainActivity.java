package com.jacksplwxy.start;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = findViewById(R.id.base_adapter_lv);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: position=" + position + ";text=" + parent.getAdapter().getItem(position).toString());
            }
        });

        List<AnimeBean> animeBeans = new ArrayList<>();
        Resources resources = this.getResources();
        String[] anime_names = resources.getStringArray(R.array.anime_name);
        String[] anime_authors = resources.getStringArray(R.array.anime_author);
        int[] coverImgs = {R.drawable.hzw, R.drawable.jjdjr, R.drawable.hyrz,
                R.drawable.zchzt, R.drawable.qsmy, R.drawable.xyj, R.drawable.hlw};
        for (int i = 0; i < anime_names.length; i++) {
            animeBeans.add(new AnimeBean(anime_names[i], anime_authors[i], coverImgs[i]));
        }
        gridView.setAdapter(new AnimeAdapter(this, animeBeans));
    }
}