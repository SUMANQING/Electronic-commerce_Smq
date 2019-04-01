package com.smq.commerce.frag.Order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smq.commerce.R;

/**
 * Time:2019/3/26
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class AllFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_layout,container,false);
        return view;
    }
}
