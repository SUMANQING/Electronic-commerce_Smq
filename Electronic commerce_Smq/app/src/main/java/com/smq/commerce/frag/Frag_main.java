package com.smq.commerce.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.lib.settingview.LSettingItem;
import com.smq.commerce.R;
import com.smq.commerce.activity.AddActivity;
import com.smq.commerce.activity.AddressActivity;
import com.smq.commerce.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class Frag_main extends Fragment {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.item_ziliao)
    LSettingItem itemZiliao;
    @BindView(R.id.my_circle)
    LSettingItem myCircle;
    @BindView(R.id.my_foot)
    LSettingItem myFoot;
    @BindView(R.id.my_wallet)
    LSettingItem myWallet;
    @BindView(R.id.my_address)
    LSettingItem myAddress;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main, null, false);
        unbinder = ButterKnife.bind(this, view);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });

        myAddress.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getActivity(),AddActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
