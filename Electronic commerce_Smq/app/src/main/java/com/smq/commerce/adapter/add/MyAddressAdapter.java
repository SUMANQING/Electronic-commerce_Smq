package com.smq.commerce.adapter.add;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smq.commerce.R;
import com.smq.commerce.bean.AddressList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Time:2019/3/25
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class MyAddressAdapter  extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder>{
    private Context context;
    private List<AddressList.ResultBean> resultBeanList;

    public MyAddressAdapter(Context context, List<AddressList.ResultBean> resultBeanList) {
        this.context = context;
        this.resultBeanList = resultBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_list_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.addrName.setText(resultBeanList.get(i).getRealName());
        viewHolder.addrPhone.setText(resultBeanList.get(i).getPhone());
        viewHolder.addrAddress.setText(resultBeanList.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.addr_name)
        TextView addrName;
        @BindView(R.id.addr_phone)
        TextView addrPhone;
        @BindView(R.id.addr_address)
        TextView addrAddress;
        @BindView(R.id.text_updata)
        Button textUpdata;
        @BindView(R.id.text_delete)
        Button textDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
