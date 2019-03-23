package com.smq.commerce.adapter.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.smq.commerce.R;
import com.smq.commerce.bean.SearchBean;

import java.util.ArrayList;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<SearchBean.ResultBean> list;

    public SearchAdapter(Context context, ArrayList<SearchBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.search_item,null);
        final MyViewHolder myViewHolder=new MyViewHolder(view);
        //添加条目点击事件
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = myViewHolder.getLayoutPosition();
//                if (onSearchRecyclerViewListener!=null){
//                    onSearchRecyclerViewListener.getData(position);
//                }
//            }
//        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.simpleDraweeView.setImageURI(list.get(i).getMasterPic());
        myViewHolder.textView.setText(list.get(i).getCommodityName());
        myViewHolder.price.setText("￥"+list.get(i).getPrice());
        final int id = list.get(i).getCommodityId();
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchRecyclerViewListener!=null){
                    onSearchRecyclerViewListener.getData(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simpleDraweeView;
        private final TextView textView;
        private final TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.search_sim);
            textView = itemView.findViewById(R.id.search_name);
            price = itemView.findViewById(R.id.search_price);
        }
    }

    public interface onSearchRecyclerViewListener{
        void getData(int position);
    }
    public onSearchRecyclerViewListener onSearchRecyclerViewListener;

    public void setOnSearchRecyclerViewListener(SearchAdapter.onSearchRecyclerViewListener onSearchRecyclerViewListener) {
        this.onSearchRecyclerViewListener = onSearchRecyclerViewListener;
    }
}
