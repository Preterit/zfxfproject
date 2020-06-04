package com.zfxf.zfxfproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zfxf.zfxfproject.adapter.TopItemAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private TopItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new TopItemAdapter();
        recyclerview.setAdapter(adapter);

        adapter.setList(Arrays.asList("金股池", "牛人课", "商城", "牛观点", "知码研报"));
    }
}
