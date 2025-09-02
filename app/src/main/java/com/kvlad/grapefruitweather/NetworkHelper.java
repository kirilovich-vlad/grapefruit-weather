package com.kvlad.grapefruitweather;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkHelper {
    // Check whether OWM API key is valid.
    public static boolean checkOWMAPIKey(String key) throws RuntimeException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(String.format(
                        "https://api.openweathermap.org/geo/1.0/direct?q=London&limit=1&appid=%s",
                        key
                ))
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            Log.d("Net", e.toString());
            throw new RuntimeException("Check your internet connection! " + e);
        }
        return response.code() == 200;
    }
}
