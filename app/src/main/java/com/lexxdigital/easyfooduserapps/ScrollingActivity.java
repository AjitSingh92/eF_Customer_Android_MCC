package com.lexxdigital.easyfooduserapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.newrelic.agent.android.NewRelic;

public class ScrollingActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private ImageView imageView;
    private LinearLayout lyContainer;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
