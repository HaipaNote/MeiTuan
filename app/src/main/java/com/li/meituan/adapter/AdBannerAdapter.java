package com.li.meituan.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.li.meituan.bean.ShopBean;
import com.li.meituan.fragment.AdBannerFragment;

import java.util.ArrayList;
import java.util.List;

public class AdBannerAdapter extends FragmentStatePagerAdapter {
    private List<ShopBean> sb1;
    public  AdBannerAdapter(FragmentManager fm){
        super(fm);
        sb1=new ArrayList<>();
    }

    /**
     * 获取数据并更新
     */
    public  void setData(List<ShopBean> sb1 ){
        this.sb1=sb1;
        //更新界面数据
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        if(sb1.size()>0)
            args.putSerializable("ad",sb1.get(position % sb1.size()));
        return AdBannerFragment.newInstance(args);
    }

    /**
     * 返回数据集中元素的数量
     * @return
     */
    public int getSize(){
        return sb1 == null ? 0 : sb1.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //防止刷新，列表出现缓存数据，重载该函数，并返回POSITION_NONE
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
