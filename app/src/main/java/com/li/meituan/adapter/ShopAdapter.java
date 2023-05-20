package com.li.meituan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.li.meituan.R;
import com.li.meituan.bean.ShopBean;

import java.util.List;

public class ShopAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopBean> sb1;
    public ShopAdapter(Context context){
        this.mContext=context;
    }


    /**
     * 获取数据并更新
     */
    public  void setData(List<ShopBean> sb1 ){
        this.sb1=sb1;
        //更新界面数据
        notifyDataSetChanged();
    }
    /**
     * 返回数据集中元素的数量
     * @return
     */
    public int getSize(){
        return sb1 == null ? 0 : sb1.size();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * 根据position获取对应的条目对象的id
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
        final  ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.shop_item,null);
            vh.tv_shop_name=convertView.findViewById(R.id.tv_shop_name);
            vh.tv_sale_num=convertView.findViewById(R.id.tv_sale_num);
            vh.tv_cost=convertView.findViewById(R.id.tv_cost);
            vh.tv_feature=convertView.findViewById(R.id.tv_feature);
            vh.tv_time=convertView.findViewById(R.id.tv_time);
            vh.iv_shop_pic=convertView.findViewById(R.id.iv_shop_pic);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder) convertView.getTag();
        }
        //获取position对应的条目数据对象
        final ShopBean bean = (ShopBean) getItem(position);
        if (bean != null) {
            vh.tv_shop_name.setText(bean.getShopName());
            vh.tv_sale_num.setText("月售" + bean.getSaleNum());
            vh.tv_cost.setText("起送￥" + bean.getOfferPrice() + " | 配送￥" +
                    bean.getDistributionCost());
            vh.tv_time.setText(bean.getTime());
            vh.tv_feature.setText(bean.getFeature());
            Glide.with(mContext)
                    .load(bean.getShopPic())
                    .error(R.mipmap.ic_launcher)
                    .into(vh.iv_shop_pic);
        }
        //每个条目的点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到店铺详情界面
               /* if (bean == null) return;
                Intent intent = new Intent(mContext,ShopDetailActivity.class);
                //把店铺的详细信息传递到店铺详情界面
                intent.putExtra("shop", bean);
                mContext.startActivity(intent);*/
            }
        });

        return  convertView;

    }

    class  ViewHolder{
        public TextView tv_shop_name,tv_sale_num,tv_cost,tv_feature,tv_time;
        public ImageView iv_shop_pic;
    }






}
