package com.penelope.faunafinder.network;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.android.volley.VolleyError;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import com.penelope.faunafinder.network.RequestMaker;
import com.penelope.faunafinder.network.Result;

public class RequestMakerTest {
    private final CountDownLatch networkLock = new CountDownLatch(1);

    @Test
    public void createsQueue() {
        Context context = ApplicationProvider.getApplicationContext();
        RequestMaker classUnderTest = new RequestMaker(context);

        assertNotNull(classUnderTest.getQueue());
    }

    @Test
    public void queryIsHandled() throws InterruptedException {
        Context context = ApplicationProvider.getApplicationContext();
        RequestMaker classUnderTest = new RequestMaker(context);

        Result resultMock = mock(Result.class);
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        String baseUrl = mockWebServer.url("").toString();

        // Success
        classUnderTest.query(baseUrl, resultMock);
        // Error, a bad url
        classUnderTest.query("", resultMock);
        networkLock.await(2, TimeUnit.SECONDS);

        verify(resultMock, times(1))
                .onSuccess(any(String.class));
        verify(resultMock, times(1))
                .onError(any(VolleyError.class));
    }
}
