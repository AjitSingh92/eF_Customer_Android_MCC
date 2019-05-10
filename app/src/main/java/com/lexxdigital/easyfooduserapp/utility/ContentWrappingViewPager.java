package com.lexxdigital.easyfooduserapp.utility;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class ContentWrappingViewPager extends ViewPager
{
    private int width = 0;
    public ContentWrappingViewPager(Context context) {
        super(context);
    }

    public ContentWrappingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = 0;
        width = widthMeasureSpec;
        if (getChildCount() > getCurrentItem()) {
            View child = getChildAt(getCurrentItem());
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void onRefresh()
    {
        try {
            int height = 0;
            if (getChildCount() > getCurrentItem()) {
                View child = getChildAt(getCurrentItem());
                child.measure(width, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if(h > height) height = h;
            }

            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.height = heightMeasureSpec;
            this.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}