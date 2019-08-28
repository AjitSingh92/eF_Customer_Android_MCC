package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by akumar7 on 5/19/2016.
 */
public class RecyclerLayoutManager extends StaggeredGridLayoutManager {
    private boolean isScrollEnabled = true;
    private Context mContext;
    public RecyclerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    public RecyclerLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}