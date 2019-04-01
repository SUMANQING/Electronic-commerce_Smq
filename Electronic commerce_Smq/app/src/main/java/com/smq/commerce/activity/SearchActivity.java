package com.smq.commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smq.commerce.R;
import com.smq.commerce.adapter.search.SearchAdapter;
import com.smq.commerce.adapter.search.onLoadMoreListener;
import com.smq.commerce.bean.SearchBean;
import com.smq.commerce.mvp.search.presenter.SearchPresenter;
import com.smq.commerce.mvp.search.view.SearchView;
import com.smq.commerce.widget.Custom_home;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchView {


    @BindView(R.id.custom_search)
    Custom_home customSearch;
    @BindView(R.id.search_ryc)
    RecyclerView searchRyc;
    @BindView(R.id.search_sw)
    SwipeRefreshLayout searchSw;
    @BindView(R.id.Success)
    LinearLayout Success;
    @BindView(R.id.Error)
    LinearLayout Error;
    private String title = "鞋";
    private int page = 1;
    private int count = 20;
    private SearchPresenter searchPresenter;
    private Handler handler = new Handler();
    private ImageView sou;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        sou = findViewById(R.id.custom_home_s);
        editText = findViewById(R.id.custom_home_edit);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        searchRyc.setLayoutManager(gridLayoutManager);

        searchPresenter = new SearchPresenter(this);
        searchPresenter.attachView(this);

        searchSw.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        //下拉刷新
        searchSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                searchPresenter.onRetlade(title, page, count);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchSw.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //上拉加载
        searchRyc.addOnScrollListener(new onLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                page++;
                //count++;
                searchPresenter.onRetlade(title, page, count);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchSw.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        initData();
    }

    private void initData() {
        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editText.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                } else {
                    searchPresenter.onRetlade(title, page, count);
                }
            }
        });
    }

    @Override
    public void getHttpData(List<SearchBean.ResultBean> result) {
        if (result.size() != 0) {
            SearchAdapter adapter = new SearchAdapter(SearchActivity.this, (ArrayList<SearchBean.ResultBean>) result);

            adapter.setOnSearchRecyclerViewListener(new SearchAdapter.onSearchRecyclerViewListener() {
                @Override
                public void getData(int position) {
                    Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                    intent.putExtra("date", position);
                    startActivity(intent);
                }
            });
            searchRyc.setAdapter(adapter);
            Success.setVisibility(View.VISIBLE);
            Error.setVisibility(View.GONE);
        } else {
            //Toast.makeText(this, "到底啦！", Toast.LENGTH_SHORT).show();
            Success.setVisibility(View.GONE);
            Error.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.deatchView();
    }
}

