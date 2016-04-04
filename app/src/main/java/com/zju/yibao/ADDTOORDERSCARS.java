package com.zju.yibao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.MyJsonPoster;

import org.json.JSONException;
import org.json.JSONObject;

public class ADDTOORDERSCARS extends Activity implements GetJson {
    private ProgressBar progressBar;
    private TextView tvMessage;

    private int courseId;
    private int studentId;
    private JSONObject jsonObject;

    private String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/addToOrdersCar.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);

        initView();

        courseId = getIntent().getIntExtra("courseId", -1);
        studentId = getIntent().getIntExtra("studentId", -1);

        jsonObject = new JSONObject();
        try {
            jsonObject.put("courseId", courseId);
            jsonObject.put("studentId", studentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject.length() != 0) {
            new MyJsonPoster(this).loadJson(string_http, jsonObject.toString());
        }
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMessage.setText("正在添加...");
    }

    @Override
    public void getJsonString(String s) {
        if (s == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(s);
            int resultCode = jsonObject.getInt("resultCode");
            Looper.prepare();
            switch (resultCode) {
                case 0:
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                    break;
                case 9005:
                    Toast.makeText(this, "购物车已存在该课程", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Looper.loop();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
