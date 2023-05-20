package com.li.meituan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.li.meituan.R;
import com.li.meituan.bean.ShopBean;

public class AdBannerFragment extends Fragment {
    //广告
    private ShopBean sb;
    //图片
    private ImageView iv;

    public static Fragment newInstance(Bundle args) {
        AdBannerFragment af=new AdBannerFragment();
        af.setArguments(args);
        return af;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        //获取一个店铺对象
        sb=(ShopBean)arg.getSerializable("ad");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sb != null) {
            //调用Glide框架加载图像
            Glide.with(getActivity())
                    .load(sb.getBanner())
                    .error(R.mipmap.ic_launcher)
                    .into(iv);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建一个Imageview对象
        iv=new ImageView(getActivity());
        ViewGroup.LayoutParams lp=new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //设置宽高
        iv.setLayoutParams(lp);
        //图片填满整个控件
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到店铺详情页面
            }
        });
        return iv;
    }
}
