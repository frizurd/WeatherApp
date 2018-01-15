package com.frizkykramer.weatherapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.frizkykramer.weatherapp.R;
import com.frizkykramer.weatherapp.adapter.CustomPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewpagerActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)           ViewPager viewPager;
    @BindView(R.id.vwPager_btnPrev)     TextView prevPage;
    @BindView(R.id.vwPager_btnNext)     TextView nextPage;
    @BindView(R.id.vwPager_btnStart)    TextView startPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);

        viewPager.setAdapter(new CustomPageAdapter(this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        prevPage.setVisibility(View.GONE);
                        nextPage.setVisibility(View.VISIBLE);
                        startPage.setVisibility(View.GONE);
                        break;
                    case 1:
                        prevPage.setVisibility(View.VISIBLE);
                        nextPage.setVisibility(View.VISIBLE);
                        startPage.setVisibility(View.GONE);
                        break;
                    case 2:
                        prevPage.setVisibility(View.VISIBLE);
                        nextPage.setVisibility(View.GONE);
                        startPage.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.vwPager_btnPrev)
    public void previousPage () {
        viewPager.setCurrentItem(getItem(-1), true);
    }

    @OnClick(R.id.vwPager_btnNext)
    public void nextPage () {
        viewPager.setCurrentItem(getItem(+1), true);
    }

    @OnClick(R.id.vwPager_btnStart)
    public void startApplication () {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



    @OnClick(R.id.vwPager_btnSlide1)
    public void changeSlide1 () {
        viewPager.setCurrentItem(0);
    }
    @OnClick(R.id.vwPager_btnSlide2)
    public void setSlide2 () {
        viewPager.setCurrentItem(1);
    }
    @OnClick(R.id.vwPager_btnSlide3)
    public void setSlide3 () {
        viewPager.setCurrentItem(2);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}
