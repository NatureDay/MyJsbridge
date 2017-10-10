package com.qianmo.jsbridge;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2017/9/26 16:42
 */
public class JSBridgeWebChromeClient extends WebChromeClient {

    private static final String JS_PATH = "androidJS.js";

    private boolean mIsInjectedJS = false;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Log.e("fff", "---------onProgressChanged==========url===111==");
        if (mIsInjectedJS) return;
        Log.e("fff", "---------onProgressChanged==========url===222==");
        if (newProgress > 25) {
            Log.e("fff", "---------onProgressChanged==========url===333==");
            mIsInjectedJS = true;
            String jsContent = readAssertResource(view.getContext(), JS_PATH);
            Log.e("fff", "---------onProgressChanged=========jsContent==" + jsContent);
            view.loadUrl("javascript:" + jsContent);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.e("fff", "---------onJsAlert==========url=====" + url);
        Log.e("fff", "---------onJsAlert==========message=====" + message);
        Log.e("fff", "---------onJsAlert==========result=====" + result.toString());
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.e("fff", "---------onJsPrompt==========url=====" + url);
        Log.e("fff", "---------onJsPrompt==========message=====" + message);
        Log.e("fff", "---------onJsPrompt==========result=====" + result.toString());
        result.confirm(JSBridge.callJava(view, message));
        return true;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.e("fff","------------consoleMessage===" + consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }

    public String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
