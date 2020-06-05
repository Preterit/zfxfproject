package com.zfxf.zfxfproject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zfxf.base.BaseActivity;
import com.zfxf.zfxfproject.adapter.TopItemAdapter;

import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private TopItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new TopItemAdapter();
        recyclerview.setAdapter(adapter);

        adapter.setList(Arrays.asList("金股池", "牛人课", "商城", "牛观点", "知码研报"));
    }
}
