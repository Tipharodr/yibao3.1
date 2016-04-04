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

public class CHANGEIMFORMATION extends Activity implements GetJson {
    private ProgressBar progressBar;
    private TextView tvMessage;

    private String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/updateStuInfoSubmit.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);

        initView();

        String string_data = getIntent().getStringExtra("data");
        new MyJsonPoster(this).loadJson(string_http, string_data);
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMessage.setText("正在修改...");
    }

    @Override
    public void getJsonString(String s) {
        if (s == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(s);
            Looper.prepare();
            switch (jsonObject.getInt("resultCode")) {
                case 0:
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
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
