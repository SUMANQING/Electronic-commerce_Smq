package com.smq.commerce.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.smq.commerce.R;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class Custom_home extends LinearLayout implements View.OnClickListener {

    private EditText editText;

    public Custom_home(Context context) {
        super(context);
    }

    public Custom_home(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getData();
    }

    public Custom_home(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void getData() {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.custom_home,null,false);
        addView(view);
        editText = findViewById(R.id.custom_home_edit);
        editText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_home_edit:
                if (onIntent != null) {
                    onIntent.onintent();
                }
                break;
        }
    }

    public interface OnIntent {
        void onintent();
    }

    public OnIntent onIntent;

    public void setOnIntent(OnIntent onIntent) {
        this.onIntent = onIntent;
    }
}
