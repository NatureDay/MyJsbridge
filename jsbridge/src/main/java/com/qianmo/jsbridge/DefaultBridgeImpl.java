package com.qianmo.jsbridge;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2017/9/26 16:46
 */
public class DefaultBridgeImpl implements IBridge {

    private static final String CODE = "code";
    private static final String DATA = "data";
    private static final String MESSAGE = "msg";

    public static void showToast(JSONObject param, Callback callback) {
        Log.e("fff", "---------showToast----------");
        if (param != null) {
            String message = param.optString("msg");
            Log.e("fff", "---------showToast-----message==" + message);
        }
        if (null != callback) {
            try {
                JSONObject object = new JSONObject();
                object.put("key", "value");
                object.put("key1", "value1");
                callback.apply(getJSONObject(0, "ok", object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static JSONObject getJSONObject(int code, String msg, JSONObject data) {
        JSONObject object = new JSONObject();
        try {
            object.put(CODE, code);
            object.put(MESSAGE, msg);
            object.putOpt(DATA, data);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
