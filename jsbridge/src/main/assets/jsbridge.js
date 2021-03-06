/**
 * Created by Administrator on 2017/9/27.
 */
(function () {
    if (window.androidBridge) {
        return;
    }
    window.androidBridge = {
        callbacks: {},
        isNull: function (data) {
            return data == undefined || data == null || data == "";
        },
        /**
         * JS调用Java
         * @param className             类名
         * @param functionName          方法名
         * @param params                参数
         * @param callback              回调
         */
        doCall: function (className, functionName, params, callback) {
            // 解决连续调用问题
            var _this = this;
            if (this.lastCallTime && (Date.now() - this.lastCallTime) < 100) {
                setTimeout(function () {
                    _this.doCall(className, functionName, params, callback);
                }, 100);
                return;
            }
            this.lastCallTime = Date.now();

            if (this.isNull(className) || this.isNull(functionName)) {
                console.log("---------androidBridge need className and functionName---------");
                return;
            }

            var requestData = {};
            requestData.javaClassName = className;
            requestData.javafunctionName = functionName;
            if (!this.isNull(params)) {
                requestData.javaParams = params;
            }
            if (!this.isNull(callback)) {
                var callbackId = Math.floor(Math.random() * (1 << 30));
                this.callbacks[callbackId] = callback;
                requestData.javaCallbackId = callbackId;
            }

            window.prompt(JSON.stringify(requestData), "");
        },
        /**
         * 回调方法
         * @param callbackId
         * @param jsonObj
         */
        callback: function (callbackId, jsonObj) {
            console.log("---------androidBridge callback---------");
            callback(jsonObj);
            delete this.callbacks[callbackId];
        }
    };
})();