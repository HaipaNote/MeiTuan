package com.li.meituan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.li.meituan.R;
import com.li.meituan.adapter.AdBannerAdapter;
import com.li.meituan.adapter.ShopAdapter;
import com.li.meituan.bean.ShopBean;
import com.li.meituan.util.Constant;
import com.li.meituan.util.JsonParse;
import com.li.meituan.views.ShopListView;
import com.li.meituan.views.ViewPagerIndicator;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity {
    private TextView tv_back, tv_title;        //返回键与标题控件
    private ShopListView slv_list;             //列表控件
    private ShopAdapter adapter;               //列表的适配器
    private RelativeLayout rl_title_bar;
    private ViewPager adPager;         //广告
    private ViewPagerIndicator vpi;   //小圆点
    private View adBannerLay;          //广告条容器
    private AdBannerAdapter ada;      //适配器
    public static final int MSG_AD_SLID = 1;  //广告自动滑动
    public static final int MSG_SHOP_OK = 2;  //获取数据
    
    private MHander mHander;
    //记录第一次点击时的时间
    protected  long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
        && event.getAction()==KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis()-exitTime) >2000) {
                Toast.makeText(this,"再按一次对出美团",Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }else{
                finish();
                /*System.exit(0);*/
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHander=new MHander();
        initData();
        init();

    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Constant.WEB_SITE + Constant.REQUEST_SHOP_URL).build();
        Log.d("ning", String.valueOf(request));
        Call call = okHttpClient.newCall(request);
        //开启异步线程访问网络
        call.enqueue(new  Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //捕获店铺数据
                String res = response.body().string();
                Message message = new Message();
                message.what=MSG_SHOP_OK;
                message.obj=res;
                mHander.sendMessage(message);
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }


    /**
     * 初始化界面控件
     */
    private void init() {
        tv_back = findViewById(R.id.tv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("店铺");
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(getResources().getColor(
                R.color.blue_color));
        tv_back.setVisibility(View.GONE);
        slv_list = findViewById(R.id.slv_list);
        adapter = new ShopAdapter(this);
        slv_list.setAdapter(adapter);
        adBannerLay = findViewById(R.id.adbanner_layout);
        adPager = findViewById(R.id.slidingAdvertBanner);
        vpi = findViewById(R.id.advert_indicator);
        adPager.setLongClickable(false);
        ada = new AdBannerAdapter(getSupportFragmentManager());
        adPager.setAdapter(ada);

        /**
         * 黑点颜色变换效果
         */
        adPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (ada.getSize() >0) {
                    vpi.setCurrentPostion(position % ada.getSize());//设置当前小圆点
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetSize();
        new AdAutoSlidThread().start();

    }
    class AdAutoSlidThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(mHander!=null){
                    mHander.sendEmptyMessage(MSG_AD_SLID);
                }
            }
        }
    }

    /*计算控件大小*/
    private void resetSize() {
        //获取屏幕宽度
        int sw = getScreenWidth();
        //广告条高度
        int adLheight = sw / 3;
        ViewGroup.LayoutParams adlp = adBannerLay.getLayoutParams();
        adlp.width=sw;
        adlp.height=adLheight;
        adBannerLay.setLayoutParams(adlp);


    }

    /*获取屏幕宽度*/
    private int getScreenWidth() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    /*事件捕获*/
    class MHander extends Handler{
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case MSG_SHOP_OK:
                    if (msg.obj != null) {
                        String vlResult=(String)msg.obj;
                        //使用json解析器解析数据
                        List<ShopBean> shopList = JsonParse.getInstance().getShopList(vlResult);
                        adapter.setData(shopList);
                    }
                    break;
            }
        }
    }
}
