package com.smq.commerce.adapter.fenlei;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smq.commerce.R;
import com.smq.commerce.bean.ThreeListinfo;

import java.util.List;

/**
 * Time:2019/4/1
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ListThreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ThreeListinfo.ResultBean> threeresult;

    public ListThreeAdapter(Context context, List<ThreeListinfo.ResultBean> threeresult) {
        this.context = context;
        this.threeresult = threeresult;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_twolist, null);
        ThreeHolder threeHolder = new ThreeHolder(view);
        return threeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ThreeHolder threeHolder = (ThreeHolder) viewHolder;
        threeHolder.money.setText("￥"+threeresult.get(i).getPrice());
        Glide.with(context).load(threeresult.get(i).getMasterPic()).into(threeHolder.image);
        threeHolder.text.setText(threeresult.get(i).getCommodityName());
    }

    @Override
    public int getItemCount() {
        return threeresult.size();
    }


    public class ThreeHolder extends RecyclerView.ViewHolder {

        private final TextView text;
        private final ImageView image;
        private final TextView money;

        public ThreeHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.threeHolder_text);
            image = itemView.findViewById(R.id.threeHolder_image);
            money = itemView.findViewById(R.id.threeHolder_money);
        }
    }

    public interface OnOneClick {
        void setIdData(String id);
    }

    public OnOneClick onOneClick;

    public void setOnOneClick(OnOneClick onOneClick) {
        this.onOneClick = onOneClick;
    }

}
