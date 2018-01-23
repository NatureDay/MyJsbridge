package com.qianmo.jsbridge;

import android.util.Log;

import org.json.JSONObject;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/1/17 15:05
 */
public class BridgeUtil {

    public static final String TAG = "AndroidBridge";

    private static final String CODE = "code";
    private static final String DATA = "data";
    private static final String MESSAGE = "message";

    public static JSONObject getSuccessJSONObject(JSONObject data) {
        return getJSONObject(20000, "Success !!!", data);
    }

    public static JSONObject getFailureJSONObject(JSONObject data) {
        return getJSONObject(40000, "Failure !!!", data);
    }

    public static JSONObject getJSONObject(int code, String msg, JSONObject data) {
        JSONObject object = new JSONObject();
        try {
            object.put(CODE, code);
            object.put(MESSAGE, msg);
            object.putOpt(DATA, data);
            return object;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
