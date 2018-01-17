package com.qianmo.jsbridge;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2017/9/26 16:40
 */
public class JSBridge {

    private static final String TAG = "JSBridge";

    private static final String CLASSNAME = "javaClassName";
    private static final String METHODNAME = "javaMethodName";
    private static final String PARAMS = "javaParams";
    private static final String CALLBACKID = "javaCallbackId";

    private static Map<String, HashMap<String, Method>> exposedMethods = new HashMap<>();

    public static void register(String exposedName, Class<? extends IBridge> clazz) {
        if (!exposedMethods.containsKey(exposedName)) {
            try {
                exposedMethods.put(exposedName, getAllMethod(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static HashMap<String, Method> getAllMethod(Class injectedCls) throws Exception {
        HashMap<String, Method> methodsMap = new HashMap<>();
        Method[] methods = injectedCls.getDeclaredMethods();
        for (Method method : methods) {
            String name;
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (name = method.getName()) == null) {
                continue;
            }
            methodsMap.put(name, method);
        }
        return methodsMap;
    }

    public static String callJava(WebView webView, String message) {
        if (TextUtils.isEmpty(message)) return "android js call data null";
        try {
            JSONObject requestData = new JSONObject(message);

            String className = requestData.optString(CLASSNAME);
            String methodName = requestData.optString(METHODNAME);
            JSONObject params = requestData.optJSONObject(PARAMS);
            String callbackId = requestData.optString(CALLBACKID);
            if (TextUtils.isEmpty(className) || TextUtils.isEmpty(methodName)) {
                Log.e(TAG, "---------参数异常----------");
                return message;
            }
            if (exposedMethods.containsKey(className)) {
                HashMap<String, Method> methodHashMap = exposedMethods.get(className);
                if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(methodName)) {
                    Method method = methodHashMap.get(methodName);
                    if (method != null) {
                        try {
                            if (TextUtils.isEmpty(callbackId)) {
                                method.invoke(null, params, null);
                                if (TextUtils.isEmpty(message))
                                    return "android js call success";
                            } else {
                                method.invoke(null, params, new Callback(webView, callbackId));
                                if (TextUtils.isEmpty(message))
                                    return "android js call success";
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "---------callJava fail!!!----------");
                            return message;
                        }
                    }
                }
            }
            return message;
        } catch (JSONException e) {
            Log.e(TAG, "---------callJava fail!!!----------");
            return message;
        }
    }
}