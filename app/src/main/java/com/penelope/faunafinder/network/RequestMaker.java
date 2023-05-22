package com.penelope.faunafinder.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import lombok.Getter;

/**
 * <code>RequestMake</code> handles get requests
 */
public class RequestMaker {
    @Getter
    private final RequestQueue queue;

    /**
     * <code>RequestMaker<code> constructor
     *
     * @param applicationContext The app's context
     */
    public RequestMaker(Context applicationContext) {
        // Create request queue
        this.queue = Volley.newRequestQueue(applicationContext);
    }

    /**
     * Performs a network GET request
     *
     * @param url    The endpoint to query.
     * @param result {@link Result} implementation
     */
    public void query(String url, Result result) {
        // Create request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, result::onSuccess, result::onError);

        // Add request to q
        queue.add(stringRequest);
    }
}
