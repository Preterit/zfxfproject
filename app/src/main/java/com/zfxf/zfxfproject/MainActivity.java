package com.zfxf.zfxfproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zfxf.base.BaseActivity;
import com.zfxf.zfxfproject.ui.fragment.ChartFragment;
import com.zfxf.zfxfproject.weight.TopLabSelectView;


public class MainActivity extends BaseActivity implements TopLabSelectView.OnTopLabSelectListener {

    private TopLabSelectView tpoSelect;

    private ChartFragment jgcFragment,nrkFragment,nsdFragment,zmybFragment,shopFragment;
    private Fragment currentFragment;
    private Fragment newFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tpoSelect = findViewById(R.id.topSelect);

        initFirstFragment();
        switchFragment(0);
        tpoSelect.setOnTopLabSelectListener(this);
    }

    /**
     * 选中top的item的回调
     *
     * @param position
     */
    @Override
    public void onLabSelected(int position) {
        switchFragment(position);
    }

    /**
     * 初始化第一个Fragment
     */
    private void initFirstFragment() {
        jgcFragment = ChartFragment.newInstance("m1001");
        currentFragment = jgcFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home_container, currentFragment).commitAllowingStateLoss();
    }

    /**
     * 切换Fragment
     *
     * @param position
     */
    private void switchFragment(int position) {
        switch (position) {
            case 0:  // m1001 金股池
                if (jgcFragment == null) {
                    jgcFragment = ChartFragment.newInstance("m1001");
                }
                newFragment = jgcFragment;
                break;
            case 1:   // m1017 牛人课
                if (nrkFragment == null) {
                    nrkFragment = ChartFragment.newInstance("m1017");
                }
                newFragment = nrkFragment;
                break;
            case 2:  // m1007 牛视点
                if (nsdFragment == null) {
                    nsdFragment = ChartFragment.newInstance("m1007");
                }
                newFragment = nsdFragment;
                break;
            case 3:   // m1004 知码研报
                if (zmybFragment == null) {
                    zmybFragment = ChartFragment.newInstance("m1004");
                }
                newFragment = zmybFragment;
                break;
            case 4:   //m1002 商城
                if (shopFragment == null) {
                    shopFragment = ChartFragment.newInstance("m1002");
                }
                newFragment = shopFragment;
                break;
        }
        setCurrentFragment();
    }

    //设置底部按钮被选中
    private void setCurrentFragment() {
        if (newFragment != currentFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (newFragment.isAdded()) {
                transaction.show(newFragment);
                newFragment.onResume();
            } else {
                transaction.add(R.id.fl_home_container, newFragment);
            }
            transaction.hide(currentFragment).commit();
            currentFragment = newFragment;
        }
    }
}
