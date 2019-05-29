package com.nikola3in1.audiobooks.service;

import com.android.volley.Response;

public interface VolleyResponseListener<T> extends Response.Listener<T> {
    void onResponse(Object response);
}
