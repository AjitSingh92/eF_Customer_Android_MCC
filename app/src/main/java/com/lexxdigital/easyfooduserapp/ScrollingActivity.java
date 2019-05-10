package com.lexxdigital.easyfooduserapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

public class ScrollingActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private ImageView imageView;
    private LinearLayout lyContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ObservableScrollView observableScrollView = (ObservableScrollView) findViewById(R.id.observable_scrollview);
        observableScrollView.setScrollViewCallbacks(this);
        lyContainer = (LinearLayout) findViewById(R.id.container);

    }
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        lyContainer.setTranslationY(scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
