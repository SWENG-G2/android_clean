package com.penelope.faunafinder.network;

import com.android.volley.VolleyError;

/**
 * <code>Result</code> is an interface providing call backs for when a network request is performed
 */
public interface Result {
    /**
     * Success callback.
     *
     * @param response The server's response content as String.
     */
    void onSuccess(String response);

    /**
     * Error callback
     *
     * @param volleyError {@link VolleyError}
     */
    void onError(VolleyError volleyError);
}
