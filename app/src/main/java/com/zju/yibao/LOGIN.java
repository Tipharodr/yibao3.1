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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.tool.MyJsonPoster;
import com.zju.yibao.staticCode.LocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class LOGIN extends AppCompatActivity implements View.OnClickListener, GetJson {

    private Toolbar toolbar;
    private TextView tv_title;
    private Button btnLogin;
    private Button btnRegister;
    private EditText editName;
    private EditText editPass;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/login.action";
    String string_data_raw = "{     \"account\": \"%s\",     \"password\": \"%s\" }";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
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
        switch (v.getId()) {
            case R.id.btn_login:
                String account = editName.getText().toString();
                String password = editPass.getText().toString();
                String string_data = String.format(string_data_raw, account, password);
                new MyJsonPoster(this).loadJson(string_http, string_data);
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LOGIN.this, REGISTER1.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登录");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.edit_name);
        editPass = (EditText) findViewById(R.id.edit_pass);
    }

    @Override
    public void getJsonString(String s) {
//        Log.d("LOGIN", s);
        Looper.prepare();
        try {
            JSONObject jsonObject = new JSONObject(s);
            String result_code = jsonObject.getString("resultCode");
            switch (result_code) {
                case "0":
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                    int studentId = jsonObject.getInt("studentId");
                    SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("studentId", studentId);
                    editor.putBoolean("isLogin", true);
                    editor.commit();

                    this.finish();
                    break;
                case "9001":
                    Toast.makeText(this, "用户名或邮箱不存在", Toast.LENGTH_SHORT).show();
                    break;
                case "9002":
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case "9009":
                    Toast.makeText(this, "其他原因，导致登录失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Looper.loop();
    }
}
