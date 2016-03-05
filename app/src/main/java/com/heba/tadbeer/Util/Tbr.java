package com.heba.tadbeer.Util;

import android.app.Application;

import com.heba.tadbeer.classes.User;

public class Tbr extends Application {
    private static  ApiHelper apiHelper;
    private static User me;
    public ApiHelper getApiHelper() {
        apiHelper = (apiHelper == null) ? new ApiHelper(this, null) : this.apiHelper;
        return apiHelper;
    }

    public static User getMe() {
        return me;
    }

    public static void setMe(User me) {
        Tbr.me = me;
    }
}
