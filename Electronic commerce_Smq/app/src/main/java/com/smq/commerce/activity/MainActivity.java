package com.smq.commerce.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smq.commerce.R;
import com.smq.commerce.frag.Frag_car;
import com.smq.commerce.frag.Frag_circle;
import com.smq.commerce.frag.Frag_home;
import com.smq.commerce.frag.Frag_main;
import com.smq.commerce.frag.Frag_order;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.button1)
    RadioButton button1;
    @BindView(R.id.button2)
    RadioButton button2;
    @BindView(R.id.button3)
    RadioButton button3;
    @BindView(R.id.button4)
    RadioButton button4;
    @BindView(R.id.button5)
    RadioButton button5;
    @BindView(R.id.radio)
    RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new Frag_home());
        arrayList.add(new Frag_circle());
        arrayList.add(new Frag_car());
        arrayList.add(new Frag_order());
        arrayList.add(new Frag_main());
        radio.check(radio.getChildAt(0).getId());
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return arrayList.get(i);
            }

            @Override
            public int getCount() {
                return arrayList.size();
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.button1:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.button2:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.button3:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.button4:
                        pager.setCurrentItem(3);
                        break;
                    case R.id.button5:
                        pager.setCurrentItem(4);
                        break;
                }
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radio.check(radio.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}

