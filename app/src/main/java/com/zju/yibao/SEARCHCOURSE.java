package com.zju.yibao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SEARCHCOURSE extends Activity implements GetJson {
    private ProgressBar progressBar;
    private TextView tvMessage;

    private String courseName;

    private String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/lookforCourse.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);

        initView();

        courseName = getIntent().getStringExtra("courseName");
        if (courseName != null) {
            new MyJsonGetter(this).loadJson(string_http + "?courseName=" + courseName);
        }
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMessage.setText("正在搜索...");
    }

    @Override
    public void getJsonString(String s) {
        if (s == null) {
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(s);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}