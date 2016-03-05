package com.heba.tadbeer.Util;

import org.json.JSONObject;

/**
 * Created by bello on 3/4/16.
 */
public interface ApiCallback {
    void onSuccess(Boolean success, JSONObject data);
    void onError(Boolean success, String error);
}
