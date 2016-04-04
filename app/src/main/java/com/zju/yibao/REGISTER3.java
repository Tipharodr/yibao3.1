package com.zju.yibao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.tool.MyJsonPoster;
import com.zju.yibao.staticCode.LocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


/**
 * Created by hmw on 2016/2/2.
 */
public class REGISTER3 extends AppCompatActivity implements View.OnClickListener, GetJson {
    private Toolbar toolbar;
    private TextView title;
    private Button btnRegister;
    private JSONObject json;

    private String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/registerStudent.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        initView();

        Bundle bundle = getIntent().getExtras();
        json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                json.put(key, JSONObject.wrap(bundle.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(json.toString());

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("注册");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister = (Button) findViewById(R.id.btn_registerEnd);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        new MyJsonPoster((GetJson) this).loadJson(string_http, json.toString());
    }

    @Override
    public void getJsonString(String s) {
        try {
//            Log.d("REGISTER3", s);
            JSONObject jsonObject = new JSONObject(s);
            String result_code = jsonObject.getString("resultCode");
            Looper.prepare();
            switch (result_code) {
                case "0":
                    Toast.makeText(REGISTER3.this, "注册成功", Toast.LENGTH_SHORT).show();
                    
                    int studentId = jsonObject.getInt("studentId");
                    SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("studentId", studentId);
                    editor.commit();

                    Intent intent = new Intent(this, LOGIN.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    break;
                case "9003":
                    Toast.makeText(REGISTER3.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                    break;
                case "9005":
                    Toast.makeText(REGISTER3.this, "邮箱已经存在", Toast.LENGTH_SHORT).show();
                    break;
                case "9999":
                    Toast.makeText(REGISTER3.this, "其他错误", Toast.LENGTH_SHORT).show();
                    break;
            }
            Looper.loop();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
