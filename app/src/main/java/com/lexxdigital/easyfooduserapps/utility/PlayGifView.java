package com.lexxdigital.easyfooduserapps.utility;

/**
 * Created by Mayank on 9/5/2016.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class PlayGifView extends View {
    private Movie mMovie;
    private long mMovieStart = 0;

    @SuppressLint("NewApi")
    public PlayGifView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * Starting from HONEYCOMB have to turn off HardWare acceleration to draw
         * Movie on Canvas.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mMovie != null){
            setMeasuredDimension(mMovie.width(), mMovie.height());
        }else{
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = (int) now;
        }

        if (mMovie != null) {
            int relTime = (int) (now - mMovieStart);

            if (relTime > mMovie.duration()) {
                relTime = mMovie.duration();
            }

            mMovie.setTime(relTime);
            mMovie.draw(canvas,
                    getWidth() / 2 - mMovie.width() / 2,
                    getHeight() / 2 - mMovie.height() / 2);

            if (relTime < mMovie.duration()) {
                invalidate();
            }
        }

    }



}