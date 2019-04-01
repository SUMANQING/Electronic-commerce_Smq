package com.smq.commerce.adapter.car;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.smq.commerce.R;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.widget.Custom_addview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ShoppBean.ResultBean> list;

    public ShopAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShoppBean.ResultBean> mlist) {
        if (mlist!=null){
            list.clear();
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public ShoppBean.ResultBean getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.shopcar_item,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.getdata(getItem(i),context,i);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CheckBox box_all;
        private ImageView item_image;
        private TextView text_name;
        private TextView text_price;
        private TextView text_delete;
        private Custom_addview addview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            box_all=itemView.findViewById(R.id.item_select_box_all);
            item_image=itemView.findViewById(R.id.item_select_image);
            text_name=itemView.findViewById(R.id.item_select_text_name);
            text_price=itemView.findViewById(R.id.item_select_text_price);
            text_delete=itemView.findViewById(R.id.item_select_text_delete);
            addview=itemView.findViewById(R.id.item_custom_num);
        }
        public void getdata(final ShoppBean.ResultBean item, Context context, final int i) {
            Glide.with(context).load(item.getPic()).into(item_image);
            text_name.setText(item.getCommodityName());
            text_price.setText("￥"+item.getPrice());
            box_all.setChecked(item.isIscheck());
            addview.setnum(item);
            addview.setOnCallBack(new Custom_addview.CallBackListener() {
                @Override
                public void callBack() {
                    if (mshopClick!=null){
                        mshopClick.shopPrice(list);
                    }
                }
            });
            //改变复选框的状态
            box_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setIscheck(isChecked);
                    if (mshopClick!=null){
                        mshopClick.shopPrice(list);
                    }
                }
            });
            text_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(i);
                    notifyDataSetChanged();
                    if (mRemoveCallBack!=null){
                        mRemoveCallBack.removeposition(list,i);
                    }
                }
            });
        }
    }

    //接口回调
    public ShopClick mshopClick;
    public void setOnClick(ShopClick mshopClick){
        this.mshopClick=mshopClick;
    }
    public interface ShopClick{
        void shopPrice(List<ShoppBean.ResultBean> list);
    }
    public RemoveCallBack mRemoveCallBack;
    public void setRemove(RemoveCallBack mRemoveCallBack){
        this.mRemoveCallBack=mRemoveCallBack;
    }
    public interface RemoveCallBack{
        void removeposition(List<ShoppBean.ResultBean> list, int position);
    }
}
