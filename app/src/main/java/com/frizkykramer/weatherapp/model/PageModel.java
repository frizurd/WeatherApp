package com.frizkykramer.weatherapp.model;

import com.frizkykramer.weatherapp.R;

/**
 * Created by frizurd on 15/01/2018.
 */

public enum PageModel {

    FIRST(R.string.first, R.layout.activity_slide1),
    SECOND(R.string.second, R.layout.activity_slide2),
    THIRD(R.string.third, R.layout.activity_slide3);

    private int mTitleResId;
    private int mLayoutResId;

    PageModel(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
