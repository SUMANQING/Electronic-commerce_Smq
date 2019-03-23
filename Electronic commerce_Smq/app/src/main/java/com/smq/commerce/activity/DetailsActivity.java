package com.smq.commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smq.commerce.R;
import com.smq.commerce.adapter.search.SearchAdapter;
import com.smq.commerce.adapter.search.onLoadMoreListener;
import com.smq.commerce.adapter.search.pagerAdapter;
import com.smq.commerce.bean.DetailsBean;
import com.smq.commerce.bean.SearchBean;
import com.smq.commerce.mvp.search.presenter.SearchPresenter;
import com.smq.commerce.mvp.search.view.SearchView;
import com.smq.commerce.widget.Custom_home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity{
    private MyHandler MyHandler =new MyHandler();
    private ViewPager kpage;
    private TextView knumber;
    private List<ImageView> list=new ArrayList<>();
    private WebView kwe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        kwe = findViewById(R.id.wbe);
        kpage = findViewById(R.id.pager);
        knumber = findViewById(R.id.numbre);
        Intent intent = getIntent();
        int id = intent.getExtras().getInt("vvv");
        String url="http://172.17.8.100/small/commodity/v1/findCommodityDetailsById?commodityId="+id;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request builder = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(builder);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message = new Message();
                message.obj=string;
                MyHandler.sendMessage(message);
            }
        });
    }
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String obj = (String) msg.obj;
            Gson gson = new Gson();
            DetailsBean detailsBean = gson.fromJson(obj, DetailsBean.class);
            DetailsBean.ResultBean result = detailsBean.getResult();
            String picture = result.getPicture();
            final String[] split = picture.split(",");
            for (int i=0;i<split.length;i++){
                ImageView imageView = new ImageView(DetailsActivity.this);
                Glide.with(DetailsActivity.this).load(split[i]).into(imageView);
                list.add(imageView);
                pagerAdapter pageadapter = new pagerAdapter(list);
                kpage.setAdapter(pageadapter);
            }

            //第一次默认展示第一页的数值
            int currentItem = kpage.getCurrentItem()+1;
            knumber.setText(currentItem+"/"+split.length);
            //设置监听的方法  使当前页面跟随动
            kpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    int currentItem = kpage.getCurrentItem()+1;
                    knumber.setText(currentItem+"/"+split.length);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

            String details = result.getDetails();
            String mimeType = "text/html";
            String enCoding = "utf-8";
            kwe.loadDataWithBaseURL(null, details, mimeType, enCoding, null);
        }

    }
}

