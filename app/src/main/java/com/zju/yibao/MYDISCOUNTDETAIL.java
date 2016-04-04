package com.zju.yibao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.bean.MyDiscountDetail;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.MyJsonGetter;

public class MYDISCOUNTDETAIL extends AppCompatActivity implements GetJson {

    private Toolbar toolbar;
    private TextView tv_title;
    private TextView tvCourseName;
    private TextView tvDiscountCode;
    private TextView tvDiscountUseInfo;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/queryDiscountDetail.action?discountId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiscountdetail);

        initView();
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("优惠劵说明");

        tvCourseName = (TextView) findViewById(R.id.tv_courseName);
        tvDiscountCode = (TextView) findViewById(R.id.tv_discountCode);
        tvDiscountUseInfo = (TextView) findViewById(R.id.tv_discountUseInfo);
    }

    private void loadData() {
        int discountId = getIntent().getIntExtra("discountId", -1);
        if (discountId != -1) {
            new MyJsonGetter(this).loadJson(string_http + discountId);
        } else {
            String string = getIntent().getStringExtra("data");
            fillData(string);
        }
    }

    private void fillData(String string) {
        MyDiscountDetail myDiscountDetail = JSON.parseObject(string, MyDiscountDetail.class);

        tvCourseName.setText(myDiscountDetail.getCourseName() + "的优惠码");
        tvDiscountCode.setText(myDiscountDetail.getDiscountCode());
        tvDiscountUseInfo.setText(myDiscountDetail.getDiscountUseInfo());
    }

    @Override
    public void getJsonString(String s) {
        fillData(s);
    }
}
