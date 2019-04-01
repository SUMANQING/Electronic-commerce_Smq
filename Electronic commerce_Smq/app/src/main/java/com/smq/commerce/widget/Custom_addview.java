package com.smq.commerce.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smq.commerce.R;
import com.smq.commerce.bean.ShoppBean;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class Custom_addview extends LinearLayout implements View.OnClickListener {
    private EditText num_price;
    private int num;
    private ShoppBean.ResultBean getitem;
    public Custom_addview(Context context) {
        super(context);
        init(context);
    }

    public Custom_addview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Custom_addview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private Context context;

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.add_item, null);
        ImageView num_add = (ImageView) view.findViewById(R.id.num_image_add);
        ImageView num_minus = (ImageView) view.findViewById(R.id.num_image_minus);
        num_price = (EditText) view.findViewById(R.id.num_edit_price);
        num_add.setOnClickListener(this);
        num_minus.setOnClickListener(this);
        addView(view);

        num_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:改变数量
                String s1 = s.toString();
                try {
                    num = Integer.valueOf(s1);
                }catch (Exception e){
                    num = 1;
                }finally {
                    getitem.setCount(num);
                    if (listener!=null){
                        listener.callBack();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        //改变数量，设置数量，改变对象内容，回调，局部刷新
        int id = view.getId();
        switch (id) {
            case R.id.num_image_minus:
                if (num > 1) {
                    num--;
                    num_price.setText(num + "");
                    getitem.setCount(num);
                    if (listener != null) {
                        listener.callBack();
                    }
                } else {
                    Toast.makeText(context, "不能再减啦！", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.num_image_add:
                num++;
                num_price.setText(num + "");
                getitem.setCount(num);
                if (listener != null) {
                    listener.callBack();
                }
                break;
            default:
                break;
        }

    }

    //第二种传递数据
    public void setnum(ShoppBean.ResultBean getitem){
        this.getitem=getitem;
        num = getitem.getCount();
        num_price.setText(num+"");
    };

    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBack();
    }
}
