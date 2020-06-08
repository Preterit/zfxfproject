package com.zfxf.zfxfproject;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zfxf.base.BaseActivity;
import com.zfxf.zfxfproject.adapter.MyFragmentPageAdapter;
import com.zfxf.zfxfproject.ui.fragment.ChartFragment;
import com.zfxf.zfxfproject.weight.TopLabSelectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements TopLabSelectView.OnTopLabSelectListener {

    private ViewPager viewPager;
    private TopLabSelectView tpoSelect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        tpoSelect = findViewById(R.id.topSelect);

        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            fragments.add(ChartFragment.newInstance());
        }
        viewPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(fragments.size());
        tpoSelect.setOnTopLabSelectListener(this);
    }

    /**
     * 选中top的item的回调
     *
     * @param position
     */
    @Override
    public void onLabSelected(int position) {
        viewPager.setCurrentItem(position);
    }
}
