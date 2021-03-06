package com.qianmo.jsbridge;

import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2017/9/26 16:45
 */
class Callback {

    private static final String CALLBACK_JS_FORMAT = "javascript:androidBridge.callback('%s', %s);";

    private String mCallbackId;
    private WeakReference<WebView> mWebViewRef;

    Callback(WebView view, String callbackId) {
        mWebViewRef = new WeakReference<>(view);
        mCallbackId = callbackId;
    }

    void apply(JSONObject jsonObject) {
        final String execJs = String.format(CALLBACK_JS_FORMAT, mCallbackId, jsonObject.toString());
        if (mWebViewRef != null && mWebViewRef.get() != null) {
            mWebViewRef.get().loadUrl(execJs);
        }
    }
}