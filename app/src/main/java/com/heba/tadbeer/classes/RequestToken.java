package com.heba.tadbeer.classes;

/**
 * Created by bello on 3/4/16.
 */
public class RequestToken {
    private String token;
    private long expires;

    public RequestToken(String token, long expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
