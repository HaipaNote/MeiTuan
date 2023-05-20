package com.li.meituan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.li.meituan.R;

public class ViewPagerIndicator extends LinearLayout {
    //设置圆点个数
    private int mCount;
    //当前原点的位置
    private int mIndex;
    private Context context;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    /**
     * 设置移动到当前小圆点时其他圆点的位置
     * @param currentIndex
     */
    public void setCurrentPostion(int currentIndex){
        //当前小圆点
        mIndex=currentIndex;
        //移除界面上的view
        this.removeAllViews();
        /*
        * getDimension和getDimensionPixelOffset的功能类似，
        都是获取某个dimen的值，但是如果单位是dp或sp，则需要将其乘以density
        如果是px，则不乘。并且getDimension返回float，getDimensionPixelOffset返回int.
        而getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.*/
        int pex = context.getResources().getDimensionPixelSize(R.dimen.view_indicator_padding);
        for (int i = 0; i <this.mCount ; i++) {
            //创建一个ImageView控件来放置小圆点
            ImageView imageView = new ImageView(context);
            //滑动到当前页面
            if (mIndex == i) {
                //设置小圆点的颜色为白色
                imageView.setImageResource(R.drawable.indicator_on);
            }else {
                //设置小圆点的颜色为灰色
                imageView.setImageResource(R.drawable.indicator_off);
            }
            //设置小圆点的padding
            imageView.setPadding(pex,0,pex,0);
            //将小圆点添加至自定义控件中
            this.addView(imageView);
        }
    }

    /**
     * 设置个数
     * @param count
     */
    public void setCount(int count){
        this.mCount=count;
    }
}
