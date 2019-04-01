package com.smq.commerce.activity;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smq.commerce.R;
import com.smq.commerce.adapter.search.pagerAdapter;
import com.smq.commerce.base.BaseActivity;
import com.smq.commerce.bean.DetailsBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.bean.addMoreBean;
import com.smq.commerce.mvp.details.presenter.DetailsPresenter;
import com.smq.commerce.mvp.details.view.DetailsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity<DetailsPresenter> implements DetailsView {


    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.numbre)
    TextView numbre;
    @BindView(R.id.shoopprice)
    TextView shoopprice;
    @BindView(R.id.shoopnum)
    TextView shoopnum;
    @BindView(R.id.shoopname)
    TextView shoopname;
    @BindView(R.id.web)
    WebView web;


    private String sid;
    private String uid;
    private SharedPreferences sp;
    private DetailsBean.ResultBean resultok;
    private List<ImageView> list = new ArrayList<>();
    private ImageView addshopp;
    private ImageView fin;
    private ImageView addbuy;
    private ImageView add;

    @Override
    protected int layoutResID() {
        return R.layout.activity_details;
    }

    @Override
    protected DetailsPresenter initPresenter() {
        date = new DetailsPresenter(this);
        date.attachview(this);
        return date;
    }

    @Override
    protected void initView() {
        addshopp = findViewById(R.id.addshopp);
        fin = findViewById(R.id.fin);
        addbuy = findViewById(R.id.addbuy);
        add = findViewById(R.id.addshop);
        add.setVisibility(View.GONE);
    }

    @Override
    protected void initDate() {
        sp = getSharedPreferences("config", MODE_PRIVATE);

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this,SearchActivity.class));
                finish();
            }
        });

        addshopp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = sp.getString("userId", "");
                sid = sp.getString("sessionId", "");
                Log.d("l***detai",uid+"****"+sid);
                if (uid.equals("")){
                    Intent intent = new Intent(DetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(DetailsActivity.this, "登录在添加购物车", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    date.chaShopp(uid,sid);
                    parabola();
                    add.setVisibility(View.VISIBLE);
                }

            }
        });



        //得到首页展示的id
        Intent intent = getIntent();
        int id = intent.getExtras().getInt("date");
        //网络请求详情的数据
        date.detailsDate(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void parabola()
    {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(1000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {

            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                /**x方向200px/s ，则y方向0.5 * 200 * t**/
                PointF point = new PointF();
                point.x = 300 * fraction * 3;
                point.y = 0.6f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                add.setX(point.x);
                add.setY(point.y);

            }
        });
    }

    @Override
    public void vieww(List<ShoppBean.ResultBean> result) {
        Toast.makeText(this, result.size()+"", Toast.LENGTH_SHORT).show();

    List<addMoreBean>list1 = new ArrayList<>();
        for (int i = 0; i <result.size(); i++) {
            //查询购物车获得的id和count
            list1.add(new addMoreBean(result.get(i).getCommodityId()+"",1+""));
        }
        //详情的id  和count
        list1.add(new addMoreBean(resultok.getCommodityId()+"",1+""));
        Gson gson = new Gson();
        String  sss = gson.toJson(list1);
        date.addShopp(uid,sid,sss);
    }

    @Override
    public void viewww(String message) {
//        Gson gson = new Gson();
//        AddShoppBean addShoppBean = gson.fromJson(message, AddShoppBean.class);
//        String message1 = addShoppBean.getMessage();
        Toast.makeText(this, "成功添加购物车！", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void view(DetailsBean.ResultBean result) {
        this.resultok=result;
        //得到图片的值
        String picture = result.getPicture();
        //截取字符串
        final String[] split = picture.split(",");
        for (int i = 0; i < split.length; i++) {
            ImageView imageView = new ImageView(DetailsActivity.this);
            Glide.with(DetailsActivity.this).load(split[i]).into(imageView);
            //把ImageView添加给集合
            list.add(imageView);
            //轮播适配器
            pagerAdapter pageAdapter = new pagerAdapter(list);
            pager.setAdapter(pageAdapter);
        }
        //第一次默认展示第一页的数值
        int currentItem = pager.getCurrentItem() + 1;
        numbre.setText(currentItem + "/" + split.length);
        //设置监听的方法  使当前页面跟随动
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                int currentItem = pager.getCurrentItem() + 1;
                numbre.setText(currentItem + "/" + split.length);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //名字  价格  购买数量赋值
        shoopname.setText(result.getCommodityName());
        shoopprice.setText("¥" + result.getPrice());
        shoopnum.setText("已售:" + result.getStock());

        String details = result.getDetails();
        web.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        web.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        web.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadWithOverviewMode(true);
        web.loadData(details, "text/html; charset=UTF-8", null);
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient());
    }

    //解决内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        date.detachview();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        }
}

