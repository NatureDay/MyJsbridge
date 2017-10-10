package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.qianmo.jsbridge.JSBridge;
import com.qianmo.jsbridge.JSBridgeWebChromeClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.getSettings().setJavaScriptEnabled(true);

        JSBridge.register("bridge", BridgeImpl.class);
        mWebView.setWebChromeClient(new JSBridgeWebChromeClient());

        mWebView.loadUrl("file:///android_asset/index.html");
    }

}
