package com.smq.commerce.adapter.create;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.commerce.R;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.widget.Custom_addview;

import java.util.ArrayList;
import java.util.List;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class CreationRecyAdapter extends RecyclerView.Adapter<CreationRecyAdapter.ViewHolder>{
    private List<ShoppBean.ResultBean> list;
    private Context context;

    public CreationRecyAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShoppBean.ResultBean> mlist) {
        if (mlist!=null){
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }
    public ShoppBean.ResultBean getItem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recy_creation_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(getItem(i),context,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_image;
        private TextView text_name;
        private TextView text_price;
        private Custom_addview text_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.item_creation_recy_image);
            text_name=itemView.findViewById(R.id.item_creation_recy_text_name);
            text_price=itemView.findViewById(R.id.item_creation_recy_text_price);
            text_num=itemView.findViewById(R.id.item_creation_recy_num);
        }

        public void getdata(final ShoppBean.ResultBean item, Context context, int i) {
            Glide.with(context).load(item.getPic()).into(item_image);
            text_name.setText(item.getCommodityName());
            text_price.setText("￥"+item.getPrice());
            text_num.setnum(item);

            text_num.setOnCallBack(new Custom_addview.CallBackListener() {
                @Override
                public void callBack() {
                    if (mshopClick!=null){
                        mshopClick.shopPrice(list);
                    }
                }
            });
        }
    }

    public ShopClick mshopClick;
    public void setOnClick(ShopClick mshopClick){
        this.mshopClick=mshopClick;
    }
    public interface ShopClick{
        void shopPrice(List<ShoppBean.ResultBean> list);
    }
}
