package com.smq.commerce.adapter.home;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.smq.commerce.R;
import com.smq.commerce.bean.HomeBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class MyMlssAdapter extends RecyclerView.Adapter<MyMlssAdapter.ViewHolder> {
    Context context;
    List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> mlssCommodityList;

    public MyMlssAdapter(Context context, List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> mlssCommodityList) {
        this.context = context;
        this.mlssCommodityList = mlssCommodityList;
    }

    @NonNull
    @Override
    public MyMlssAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mlsh_show_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMlssAdapter.ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(mlssCommodityList.get(i).getMasterPic());
        viewHolder.mlshSimpleView.setImageURI(uri);
        viewHolder.mlshTitle.setText(mlssCommodityList.get(i).getCommodityName());
        viewHolder.mlshPrice.setText("￥:"+mlssCommodityList.get(i).getPrice());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(mlssCommodityList.get(i).getCommodityId()+"");
                // context.startActivity(new Intent(context,DetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlssCommodityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mlsh_simple_View)
        SimpleDraweeView mlshSimpleView;
        @BindView(R.id.mlsh_title)
        TextView mlshTitle;
        @BindView(R.id.mlsh_price)
        TextView mlshPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
