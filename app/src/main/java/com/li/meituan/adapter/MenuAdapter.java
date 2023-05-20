package com.li.meituan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.li.meituan.R;
import com.li.meituan.activity.FoodActivity;
import com.li.meituan.bean.FoodBean;
import com.li.meituan.bean.ShopBean;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodBean> fb1;
    private OnSelectListener onSelectListener;

    public MenuAdapter(Context context,OnSelectListener onSelectListener){
        this.mContext=context;
        this.onSelectListener=onSelectListener;
    }

    /**
     * 设置数据更新界面
     */
    public void setData(List<FoodBean> fb1){
        this.fb1=fb1;
        notifyDataSetChanged();
    }


    /**
     * 处理添加入购物车按钮的方法
     */
    public interface  OnSelectListener{
        void onSelectAddCar(int position);
    }

    /**
     * 获取条目数
     * @return
     */
    @Override
    public int getCount() {
        return fb1 == null ? 0 : fb1.size();
    }


    /**
     * 根据position获取对应的条目对象
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return fb1 == null ? null : fb1.get(position);
    }

    /**
     * 根据条目获取id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position:当前条目的位置
     * @param convertView：滚动屏幕的条目视图
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuAdapter.ViewHolder vh;
        if (convertView == null) {
            vh = new MenuAdapter.ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.shop_item,null);
            vh.tv_food_name=(TextView) convertView.findViewById(R.id.tv_food_name);
            vh.tv_popularity=(TextView) convertView.findViewById(R.id.tv_popularity);
            vh.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
            vh.btn_add_car=(Button) convertView.findViewById(R.id.btn_add_car);

            vh.iv_food_pic=(ImageView) convertView.findViewById(R.id.iv_food_pic);
            convertView.setTag(vh);
        }else{
            vh=(MenuAdapter.ViewHolder) convertView.getTag();
        }
        //获取position对应的条目数据对象
        final FoodBean bean = (FoodBean) getItem(position);
        if (bean != null) {
            vh.tv_food_name.setText(bean.getFoodName());
            vh.tv_popularity.setText(bean.getPopularity ());
            vh.tv_sale_num.setText("月售" + bean.getSaleNum());
            vh.tv_price.setText("￥"+bean.getPrice());
            Glide.with(mContext)
                    .load(bean.getFoodPic())
                    .error(R.mipmap.ic_launcher)
                    .into(vh.iv_food_pic);
        }
        //每个条目的点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到菜品详情界面
                if (bean == null)return;
                Intent intent = new Intent(mContext, FoodActivity.class);
                //把菜品的详细信息传递到菜品详情界面
                intent.putExtra("food", bean);
                mContext.startActivity(intent);
             /*   onSelectListener.onSelectAddCar(position);*/
            }
        });

        return  convertView;

    }
    class  ViewHolder{
        public TextView tv_food_name,tv_popularity,tv_sale_num,tv_price;
        public Button btn_add_car;
        public ImageView iv_food_pic;
    }
}
