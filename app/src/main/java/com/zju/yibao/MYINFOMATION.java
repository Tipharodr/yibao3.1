package com.zju.yibao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.bean.MyInformation;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.GetData;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hmw on 2016/2/2.
 */
public class MYINFOMATION extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener, GetJson {
    private Toolbar toolbar;
    private TextView title;
    private EditText email;
    private EditText name;
    private EditText phone;
    private EditText age;
    private EditText preference;
    private Button update;
    boolean[] selected = new boolean[]{false, false, false, false, false, false};

    private String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/queryStuInfo.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinformation);

        initView();
        loadData();
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
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_userinfo_age:
                if (hasFocus) {
                    chooseAge();
                }
                break;
            case R.id.edit_userinfo_preference:
                if (hasFocus) {
                    choosePreference();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_userinfo_age:
                chooseAge();
                break;
            case R.id.edit_userinfo_preference:
                choosePreference();
                break;
            case R.id.btn_update_userinfo:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("studentId", GetData.getStudentId(this));
                    jsonObject.put("studentAge", Integer.valueOf(age.getText().toString()));
                    jsonObject.put("phone", phone.getText().toString());
                    jsonObject.put("preference", preference.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, CHANGEIMFORMATION.class);
                intent.putExtra("data", jsonObject.toString());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getJsonString(String s) {
        System.out.println(s);
        MyInformation myInformation = JSON.parseObject(s, MyInformation.class);
        name.setText(myInformation.getStudentName());
        email.setText(myInformation.getEmail());
        phone.setText(myInformation.getPhone());
        age.setText(myInformation.getStudentAge() + "");
        preference.setText(myInformation.getPreference());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("修改个人信息");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.edit_userinfo_user);
        email = (EditText) findViewById(R.id.edit_userinfo_email);

        phone = (EditText) findViewById(R.id.edit_userinfo_phone);

        age = (EditText) findViewById(R.id.edit_userinfo_age);
        age.setOnClickListener(this);
        age.setOnFocusChangeListener(this);

        preference = (EditText) findViewById(R.id.edit_userinfo_preference);
        preference.setOnFocusChangeListener(this);
        preference.setOnClickListener(this);

        update = (Button) findViewById(R.id.btn_update_userinfo);
        update.setOnClickListener(this);
    }

    private void loadData() {
        int studentId = GetData.getStudentId(this);
        new MyJsonGetter(this).loadJson(string_http + "?studentId=" + studentId);
    }

    private void chooseAge() {
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(80);
        numberPicker.setValue(16);

        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle("请选择您的年龄")
                .setView(numberPicker)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        age.setText(numberPicker.getValue() + "");
                    }
                })
                .setNegativeButton("取消", null);
        builder.create()
                .show();

    }

    public void choosePreference() {

        DialogInterface.OnMultiChoiceClickListener mutiListener =
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                };
        DialogInterface.OnClickListener btnListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String selectedStr = "";
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i] == true) {
                        selectedStr = selectedStr + " " +
                                getResources().getStringArray(R.array.preferenceList)[i];
                    }
                }
                preference.setText(selectedStr);
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("选择偏好(可多选)")
                .setMultiChoiceItems(R.array.preferenceList, selected, mutiListener)
                .setPositiveButton("确定", btnListener)
                .create()
                .show();
    }
}
