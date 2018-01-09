package com.example.idarkduck.shopify_backend;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iDarkDuck on 1/8/18.
 */


public class Downloader {

    OkHttpClient client;
    String bodyRespose;

    Downloader(String link){
        client =  new OkHttpClient();
        bodyRespose = new String ();
        try {
            bodyRespose=run(link);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String getBodyRespose(){
        return bodyRespose;
    }

}

