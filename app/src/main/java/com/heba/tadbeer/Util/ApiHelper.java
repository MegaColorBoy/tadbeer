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

    /**
     * Loging In user
     * @param email user email address
     * @param password  user password
     * @param  callback ApiCallback.
     */
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

    /**
     * Register new user account
     * @param email user email address
     * @param password  user password
     * @param  callback ApiCallback.
     */
    public void register(String email, String password, ApiCallback callback){
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

    /**
     * Add a new Loyalty card to user account
     * @param cardNumber card number provided by retailer
     * @param email  the email address linked to the card - on the retailer side
     * @param retailerId the retailer ID per Tadbeer DB
     * @param  callback ApiCallback.
     */
    public void addCard(String cardNumber, String email, int retailerId, ApiCallback callback){
        JSONObject postData = new JSONObject();

        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","post_Card");
            postData.put("email", email);
            postData.put("cardNumber", cardNumber);
            postData.put("retailerId", retailerId);
            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Get Loyalty card details
     * @param id card ID provided by Tadbeer
     * @param cardNumber card number provided by retailer (optional - only used to search for card when not
     *                   found in Tadbeer database.
     * @param  callback ApiCallback.
     */
    public void getCard(int id, String cardNumber, ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","get_card");
            postData.put("id", id);
            postData.put("cardNumber", cardNumber);

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Receipt
     * @param receiptId receipt ID provided by Tadbeer
     * @param cardId card ID required for verification
     * @param receiptNumber receipt number provided by retailer - optional
     *                      only used to check against retailer database
     * @param  callback ApiCallback.
     */
    public void getReceipt(int receiptId, int cardId, String receiptNumber, ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","get_receipt");
            postData.put("id", cardId);
            postData.put("receiptId", receiptId);
            postData.put("receiptNumber", receiptNumber);

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * General Search
     * @param q search term
     * @param  callback ApiCallback.
     */
    public void search(String q, ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","search");
            postData.put("q", q);

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Item details
     * @param itemId
     * @param  callback ApiCallback.
     */
    public void getItem(int itemId, ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","get_item");
            postData.put("itemId", itemId);

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Receipt Item details
     * refers to particular appearance of an item on a specified receipt
     * @param receiptItemId
     * @param receiptId
     * @param  callback ApiCallback.
     */
    public void getReceiptItem(int receiptItemId, int receiptId, ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","get_receipt_item");
            postData.put("receiptId", receiptId);
            postData.put("itemId", receiptItemId);

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Warranty details
     * @param warrantyId
     * @param  callback ApiCallback.
     */
    public void getWarranty(int warrantyId, ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","get_warranty");
            postData.put("warrantyId", warrantyId);

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get User Warranty - These are any warranties for items a user has purchased
     * @param  callback ApiCallback.
     */
    public void getUserWarranties(ApiCallback callback){
        JSONObject postData = new JSONObject();
        //create postdata
        try {
            postData.put("scope","user");
            postData.put("action","get_user_warranties");

            sendRequest(postData, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete Loyalty Card - From tadbeer records
     * @param cardId - as supplied by Tadbeer
     * @param  callback ApiCallback.
     */
    public void deleteCard(int cardId, ApiCallback callback){
        JSONObject postData = new JSONObject();
        try{
            postData.put("scope", "user");
            postData.put("action", "delete_card");
            postData.put("id", cardId);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    /**
     * Get Request Token
     * @param  callback ApiCallback.
     */
    private void get_request_token(final ApiCallback callback){
        String url = server + "?scope=auth&action=get_request_token";
        //create postdata
        if(this.requestToken !=null) {
                JSONObject postData = new JSONObject();
                sendRequest(postData, callback);
        }
    }


    /**
     * Send Request - bundles request URL and prameters and adds request to volley queue
     * @param requestData a JSONObject of request parameters
     * @param  callback ApiCallback.
     */
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