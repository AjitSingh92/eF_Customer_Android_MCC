package com.easyfoodcustomer.search_post_code;

import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityWebViewBinding binding;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        init();
        setListeners();
    }

    private void init() {
        url = getIntent().getStringExtra("WEB_URLL");
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.webPrivacyPolicy.setWebViewClient(new MyWebViewClient());
        binding.webPrivacyPolicy.getSettings().setJavaScriptEnabled(true);
        binding.webPrivacyPolicy.getSettings().setDomStorageEnabled(true);
        binding.webPrivacyPolicy.getSettings().setAppCacheEnabled(false);
        binding.webPrivacyPolicy.clearCache(true);
        binding.webPrivacyPolicy.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        binding.webPrivacyPolicy.loadUrl(url);
    }

    private void setListeners() {
        binding.tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:

                finish();
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.progressBar.setVisibility(View.GONE);
            try {
                if (binding.progressBar != null)
                    binding.progressBar.setVisibility(View.GONE);
                if (binding.progressBar.getVisibility() == View.VISIBLE) {
                    binding.progressBar.setVisibility(View.GONE);
                }
                view.clearCache(true);
                view.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                e.getLocalizedMessage();
            }
        }
    }
}
