package com.zju.yibao.tool;

import android.util.Log;

import com.zju.yibao.Interface.GetJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Atlas on 16/3/1.
 */
public class MyJsonPoster {

    private GetJson getJson;

    public MyJsonPoster(GetJson getJson) {
        if (!(getJson instanceof GetJson)) {
            throw new IllegalStateException(getJson + "必须实现GetJson接口");
        }
        this.getJson = (GetJson) getJson;
    }

    public void loadJson(final String string_http, final String string_data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    //连接
                    URL url = new URL(string_http);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    //post请求必须设置下面两项
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    //发送数据
                    OutputStream outputStream = conn.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
                    outputStreamWriter.write("data=" + string_data + "\r");
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                    outputStream.close();
                    //读取数据
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp = "";
                    while ((temp = bufferedReader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    Log.d("MyJsonPoster", "" + stringBuilder);

                    if (conn.getResponseCode() == 200) {
                        getJson.getJsonString(stringBuilder.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }
            }
        }).start();
    }
}
