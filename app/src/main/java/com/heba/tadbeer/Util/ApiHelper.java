package com.heba.tadbeer.Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heba.tadbeer.R;
import com.heba.tadbeer.classes.RequestToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class ApiHelper {
    public final static String TAG = "TBR";
    private final String server = "http://tadbeer.gummybearlabs.com";
    private RequestToken requestToken;
    private RequestQueue mRequestQueue;
    private Cache cache;
    private com.android.volley.Network network;
    private static Context context;

    public ApiHelper(Context context, RequestToken requestToken) {
        this.requestToken = requestToken;
        this.context = context;

        //Setup Request Queue
        cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        this.network = new BasicNetwork(new HurlStack());
        this.mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        if(this.requestToken == null){
            this.get_request_token(new ApiCallback() {
                @Override
                public void onSuccess(Boolean success, JSONObject data) {

                }

                @Override
                public void onError(Boolean success, String error) {

                }
            });
        }
    }
    public void login(String email, final String password, final ApiCallback callback){
        String url = server + "?scope=auth&action=login";
        JSONObject requestData = new JSONObject();

        //create postdata
        try {
            requestData.put("scope","auth");
            requestData.put("action","login");
            requestData.put("email", email);
            requestData.put("password", password);
            sendRequest(requestData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void register(String email, String password, ApiCallback callback){
        String url = server;
        JSONObject postData = new JSONObject();

        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","register");
            postData.put("email", email);
            postData.put("password", password);
            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void get_request_token(final ApiCallback callback){
        String url = server + "?scope=auth&action=get_request_token";
        //create postdata
        if(this.requestToken !=null) {
                JSONObject postData = new JSONObject();
                sendRequest(postData, callback);
        }
    }
    public void sendRequest(JSONObject requestData, final ApiCallback callback){
        try {
            if(requestToken!=null){
                requestData.put("token", requestToken.getToken());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = this.server+"?";
        Iterator<?> keys = requestData.keys();

        while( keys.hasNext() ) {
            String key = (String)keys.next();
            try {
                url += key + "=" + (String)requestData.get(key) +"&";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //upsdate Request Token
                            //create Request Token
                            if(!response.isNull("token")){
                                requestToken = new RequestToken(response.getString("token"), response.getInt("expires"));
                            }


                            if(response.getBoolean("success")){
                                callback.onSuccess(true, response);
                            }
                            else{
                                callback.onError(false, response.getString("error"));
                            }
                        } catch (JSONException e) {
                            callback.onError(false, "error accessing server");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, ": " + e.getMessage());
                        callback.onError(false, context.getString(R.string.error_accessing_server));
                    }
                }
        ){

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsObjRequest);
    }
}