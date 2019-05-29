package com.nikola3in1.audiobooks.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIClient {
    private static String origin = "http://192.168.0.69:3131/api/";
    public static String accessToken;
    private final static String PROTOCOL_CHARSET = "utf-8";

    public static void login(String idToken, Context context, VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject body = new JSONObject();
        try {
            //Add params
            body.put("idToken", idToken);

            //Make request
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, origin + "login", body, listener, error -> {
                System.out.println("Error: " + error);
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    parseHeaders(response);
                    try {
                        String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                        JSONObject result = new JSONObject("{'empty':'true'}");

                        if (jsonString.length() > 0) {
                            result = new JSONObject(jsonString);
                        }
                        return Response.success(result,
                                HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {

                        return Response.error(new ParseError(e));
                    } catch (JSONException je) {
                        return Response.error(new ParseError(je));
                    }
                }
            };
            //Add to queue
            queue.add(jsonObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void test(Context context, VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        //Make request

        StringRequest jsonObj = new StringRequest(Request.Method.GET, origin + "test", listener, error -> {
            System.out.println("Error: " + error);
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };

        //Add to queue
        queue.add(jsonObj);
    }

    //Util
    private static void parseHeaders(NetworkResponse response) {
        try {
            JSONObject jsonHeaders = new JSONObject(response.headers);
            APIClient.accessToken = jsonHeaders.getString("Authorization");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> setHeaders() {
        return new HashMap<String, String>() {{
            this.put("Authorization", accessToken);
        }};
    }
}

