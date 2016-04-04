package com.zju.yibao.tool;

import android.os.AsyncTask;
import android.util.Log;

import com.zju.yibao.Interface.GetJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Atlas on 16/3/1.
 */
public class MyJsonGetter {

    private GetJson getJson;

    public MyJsonGetter(GetJson getJson) {
        if (!(getJson instanceof GetJson)) {
            throw new IllegalStateException(getJson + "必须实现GetJson接口");
        }
        this.getJson = (GetJson) getJson;
    }

    public void loadJson(String string_http) {
        URL url = null;
        try {
            url = new URL(string_http);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new AsyncTask<URL, Float, String>() {
            @Override
            protected String doInBackground(URL... params) {
                try {
                    URLConnection connection = params[0].openConnection();
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "utf-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String temp;
                    while ((temp = bufferedReader.readLine()) != null) {
                        sb.append(temp);
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    return sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("MyJsonGetter", "" + s);
                getJson.getJsonString(s);
            }
        }.execute(url);
    }
}
