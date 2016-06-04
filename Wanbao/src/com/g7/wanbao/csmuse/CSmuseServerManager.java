package com.g7.wanbao.csmuse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import android.content.Context;

/**
 * Created by XNS on 2016/4/10.
 */
public class CSmuseServerManager {
    private static final String username = "S124847823";
    private static final String password = "g7isgood";


    private static final String PRODUCT_DETAIL = "REQUEST_DETAIL";
    private static final String CREATE_ORDER = "CREATE_ORDER";
    private static final String QUERY_ORDER = "QUERY_ORDER";

    private static final byte TIME = 0;
    private static final byte KEY = 1;

    private static CSmuseServerManager instance;
    private static Context ctx;

    public static RequestQueue queue;

    public static synchronized CSmuseServerManager getInstance(Context _ctx) {
        if(instance == null) {
            instance = new CSmuseServerManager(_ctx);
        }
        return instance;
    }

    private CSmuseServerManager(Context _ctx) {
        ctx = _ctx;
        queue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    public void cancelAllRequests() {
        queue.cancelAll(PRODUCT_DETAIL);
        queue.cancelAll(CREATE_ORDER);
        queue.cancelAll(QUERY_ORDER);
    }

    private ArrayList<String> getTimeAndKey() {
        ArrayList<String> ret = new ArrayList<String>();

        long unixTime = System.currentTimeMillis() / 1000L;
        ret.add(String.valueOf(unixTime));

        byte[] key = {};
        StringBuffer sb = new StringBuffer();

        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            String buff = unixTime + "kikirace";
            digester.update(buff.getBytes());
            key = digester.digest();

            for(int i = 0; i < key.length; i++) {
                sb.append(byte2Hex(key[i]));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        ret.add(sb.toString());
        return ret;
    }

    private static String byte2Hex(byte b) {
        String[] h={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        int i=b;
        if (i < 0) {i += 256;}
        return h[i / 16] + h[i % 16];
    }

    public void sendRequest(int method, String url, JSONObject json, Response.Listener<JSONObject> listener, Response.ErrorListener errlistener,String tag) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(method, url, json, listener, errlistener);
        jsObjRequest.setTag(tag);
        queue.add(jsObjRequest);
        System.out.println(url);
    }

    public Product getProduct(int serialNum, byte language) {
        ArrayList<String> timeAndKey = getTimeAndKey();

        String url = "http://kikistore.csmuse.com/kikistore/api/kikirace_getProductDetail.php?"
                + "ProductSN=" + serialNum
                + "&language=" + language
                + "&Time=" + timeAndKey.get(TIME)
                + "&Key=" + timeAndKey.get(KEY);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        sendRequest(Request.Method.GET, url, null, future, future, PRODUCT_DETAIL);

        try {
            return new Product(future.get(3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Product> getProducts(int[] serialNums, byte[] languages)	 {
        ArrayList<Product> ret = new ArrayList<Product>();

        for(int i = 0; i < serialNums.length; i++) {
            ret.add(getProduct(serialNums[i], languages[i]));
        }

        return ret;
    }

    public JSONObject createOrder(Map<String, String> orderDetails) {
        String url = "http://kikistore.csmuse.com/kikistore/api/kikirace_createOrder.php?"

                + "Username=" + username
                + "&Password=" + password;

        for(Map.Entry<String, String> entry : orderDetails.entrySet()) {
            url += ("&" + entry.getKey() + "=" + entry.getValue());
        }

        ArrayList<String> timeAndKey = getTimeAndKey();
        url += ("&Time=" + timeAndKey.get(TIME) + "&Key=" + timeAndKey.get(KEY));


        System.out.println(url);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        sendRequest(Request.Method.GET, url, null, future, future, CREATE_ORDER);

        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrder(String externalOrderNo) {
        ArrayList<String> timeAndKey = getTimeAndKey();

        String url = "http://kikistore.csmuse.com/kikistore/api/kikirace_queryOrder.php?"
                + "Username=" + username
                + "&Password=" + password
                + "&Time=" + timeAndKey.get(TIME)
                + "&Key=" + timeAndKey.get(KEY);

        System.out.println(url);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        sendRequest(Request.Method.GET, url, null, future, future, QUERY_ORDER);

        try {
            JSONArray orders = future.get().getJSONArray("Order");
            for(int i = 0; i < orders.length(); i++) {
                JSONObject orderJson = orders.getJSONObject(i);
                if(orderJson.getString("ExternalOrderNo").equals(externalOrderNo)) {
                    return new Order(orderJson);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}