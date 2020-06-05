package com.zfxf.zfxfproject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.zfxf.base.BaseActivity;
import com.zfxf.zfxfproject.adapter.MyFragmentPageAdapter;
import com.zfxf.zfxfproject.adapter.TopItemAdapter;
import com.zfxf.zfxfproject.ui.fragment.ChartFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private TopItemAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        viewPager = findViewById(R.id.viewPager);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 5));
        adapter = new TopItemAdapter();
        recyclerview.setAdapter(adapter);

        List<String> list = Arrays.asList("金股池", "牛人课", "商城", "牛观点", "知码研报");
        List<Fragment> fragments = new ArrayList<>();
        adapter.setList(list);


        for (String s : list) {
            fragments.add(ChartFragment.newInstance());
        }
        viewPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(), fragments));

    }
}
