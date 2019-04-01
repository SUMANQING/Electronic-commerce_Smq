package com.smq.commerce.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smq.commerce.R;
import com.smq.commerce.bean.HomeBean;

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
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private HomeBean.ResultBean result;
    private int TYPE_ONE=0;
    private int TYPE_TWO=1;
    private int TYPE_THREE=2;
    public MyAdapter(Context context, HomeBean.ResultBean result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==TYPE_ONE){
            return new RxxpViewHolder(LayoutInflater.from(context).inflate(R.layout.shouye_item,null));
        }else if(i==TYPE_TWO){
            return new MlssViewHolder(LayoutInflater.from(context).inflate(R.layout.shouye_item,null));
        }
        return new PzshViewHolder(LayoutInflater.from(context).inflate(R.layout.shouye_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if(viewHolder instanceof RxxpViewHolder){
            ((RxxpViewHolder) viewHolder).text_first.setText(result.getRxxp().getName());
            ((RxxpViewHolder) viewHolder).text_first.setBackgroundResource(R.drawable.shou1);
            ((RxxpViewHolder) viewHolder).text_first.setTextColor(Color.parseColor("#fd7b23"));
            List<HomeBean.ResultBean.RxxpBean.CommodityListBean> commodityList = result.getRxxp().getCommodityList();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            ((RxxpViewHolder) viewHolder).text_rlv.setLayoutManager(gridLayoutManager);
            MyRxxpAdapter myRxxpAdapter = new MyRxxpAdapter(context, commodityList);
            ((RxxpViewHolder) viewHolder).text_rlv.setAdapter(myRxxpAdapter);
        }
        if(viewHolder instanceof MlssViewHolder){
            ((MlssViewHolder) viewHolder).text_first.setText(result.getMlss().getName());
            ((MlssViewHolder) viewHolder).text_first.setBackgroundResource(R.drawable.shou2);
            ((MlssViewHolder) viewHolder).text_first.setTextColor(Color.parseColor("#fd7b23"));
            List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> mlssCommodityList = result.getMlss().getCommodityList();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            ((MlssViewHolder) viewHolder).text_rlv.setLayoutManager(linearLayoutManager);
            MyMlssAdapter myMlssAdapter = new MyMlssAdapter(context,mlssCommodityList);
            ((MlssViewHolder) viewHolder).text_rlv.setAdapter(myMlssAdapter);
        }
        if(viewHolder instanceof PzshViewHolder){
            ((PzshViewHolder) viewHolder).text_first.setText(result.getPzsh().getName());
            ((PzshViewHolder) viewHolder).text_first.setBackgroundResource(R.drawable.shou2);
            ((PzshViewHolder) viewHolder).text_first.setTextColor(Color.parseColor("#fd7b23"));
            List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> pzshCommodityList = result.getPzsh().getCommodityList();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            ((PzshViewHolder) viewHolder).text_rlv.setLayoutManager(gridLayoutManager);

            MyPazhAdapter myPazhAdapter = new MyPazhAdapter(context,pzshCommodityList);
            ((PzshViewHolder) viewHolder).text_rlv.setAdapter(myPazhAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }
    @Override
    public int getItemViewType(int position) {
        return position % 4;
    }

    private class MlssViewHolder extends RecyclerView.ViewHolder {

        private final TextView text_first;
        private final RecyclerView text_rlv;
        public MlssViewHolder(View itemview) {
            super(itemview);
            text_first = itemview.findViewById(R.id.text_first);
            text_rlv = itemview.findViewById(R.id.rxxp_rlv);
        }
    }

    private class PzshViewHolder extends RecyclerView.ViewHolder {
        private final TextView text_first;
        private final RecyclerView text_rlv;

        public PzshViewHolder(View inflate) {
            super(inflate);
            text_first = inflate.findViewById(R.id.text_first);
            text_rlv = inflate.findViewById(R.id.rxxp_rlv);
        }
    }

    private class RxxpViewHolder extends RecyclerView.ViewHolder {

        private final TextView text_first;
        private final RecyclerView text_rlv;

        public RxxpViewHolder(View inflate) {
            super(inflate);
            text_first = inflate.findViewById(R.id.text_first);
            text_rlv = inflate.findViewById(R.id.rxxp_rlv);
        }
    }
}
