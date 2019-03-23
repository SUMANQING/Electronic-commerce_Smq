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
public class MyPazhAdapter  extends RecyclerView.Adapter<MyPazhAdapter.ViewHolder>{
    Context context;
    List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> pzshCommodityList;

    public MyPazhAdapter(Context context, List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> pzshCommodityList) {
        this.context = context;
        this.pzshCommodityList = pzshCommodityList;
    }

    @NonNull
    @Override
    public MyPazhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pzsh_show_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPazhAdapter.ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(pzshCommodityList.get(i).getMasterPic());
        viewHolder.pzshSimpleView.setImageURI(uri);
        viewHolder.pzshTitle.setText(pzshCommodityList.get(i).getCommodityName());
        viewHolder.pzshPrice.setText("￥:"+pzshCommodityList.get(i).getPrice());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(pzshCommodityList.get(i).getCommodityId()+"");
                // context.startActivity(new Intent(context,DetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pzshCommodityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pzsh_simple_view)
        SimpleDraweeView pzshSimpleView;
        @BindView(R.id.pzsh_title)
        TextView pzshTitle;
        @BindView(R.id.pzsh_price)
        TextView pzshPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
