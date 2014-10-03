package com.balazscsernai.museum;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.balazscsernai.kioskmodehelper.KioskModeHelper;

import static com.balazscsernai.museum.Constants.FILE_PROTOCOL;
import static com.balazscsernai.museum.Constants.OFFLINE_DIRECTORY;
import static com.balazscsernai.museum.Constants.START_PAGE;


public class MuseumActivity extends Activity {

    private WebView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_museum);
        setupViews();
    }

    private void setupViews() {
        content = (WebView) findViewById(R.id.museumActivity_content);
        WebSettings contentSettings = content.getSettings();
        contentSettings.setJavaScriptEnabled(true);
        contentSettings.setAllowFileAccess(true);
        contentSettings.setLoadsImagesAutomatically(true);
        content.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void onStart() {
        super.onStart();
        KioskModeHelper.enableKioskMode(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        KioskModeHelper.disableKioskMode(this);
    }

    private void loadContent() {
        content.loadUrl(getLocalURL());
    }

    private String getLocalURL() {
        return new StringBuilder().append(FILE_PROTOCOL).append(Environment.getExternalStorageDirectory()).append(OFFLINE_DIRECTORY).append(START_PAGE).toString();
    }
}
