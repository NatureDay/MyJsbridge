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

    public static void showToast(JSONObject param, final Callback callback) {
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

    private static JSONObject getJSONObject(int code, String msg, JSONObject result) {
        JSONObject object = new JSONObject();
        try {
            object.put("code", code);
            object.put("msg", msg);
            object.putOpt("result", result);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
