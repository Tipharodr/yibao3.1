package com.zju.yibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.adapter.MyBaseAdapter;
import com.zju.yibao.bean.MyOrders;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.GetData;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MYORDERS extends AppCompatActivity implements View.OnClickListener, GetJson {

    private Toolbar toolbar;
    private TextView tv_title;
    private ListView listView;
    private MyBaseAdapter adapter;

    private Button btnDelete;
    private Button btnGetDiscount;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/queryOrdersCar.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);

        initView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int studentId = GetData.getStudentId(this);
        new MyJsonGetter(this).loadJson(string_http + "?studentId=" + studentId);
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

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        listView = (ListView) findViewById(R.id.list);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的购物车");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        btnGetDiscount = (Button) findViewById(R.id.btn_getDiscount);
        btnGetDiscount.setOnClickListener(this);
    }

    private void loadData() {
        String string = getIntent().getStringExtra("data");
        MyOrders myOrders = JSON.parseObject(string, MyOrders.class);

        for (int i = 0; i < 13; i++) {
            MyOrders.OrdersEntity temp = new MyOrders.OrdersEntity();
            temp.setCourseName("钢琴" + i);
            temp.setTeacherName("李老师");
            temp.setOrganizationName("新东方");
            myOrders.getOrders().add(temp);
        }

        adapter = new MyBaseAdapter(MYORDERS.this, myOrders.getOrders());
        listView.setAdapter(adapter);

        int studentId = GetData.getStudentId(this);
        new MyJsonGetter(this).loadJson(string_http + "?studentId=" + studentId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                try {
                    JSONObject jsonObject_main = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    String string_data = null;
                    for (int i = 0; i < adapter.getIsSelected().size(); i++) {
                        if (adapter.getIsSelected().get(i)) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", adapter.getItem(i).getOrdersCarId());

                            jsonArray.put(jsonObject);

                            jsonObject_main.put("ids", jsonArray);
                            string_data = jsonObject_main.toString();
                        }
                    }
                    if (jsonArray.length() != 0) {
                        Intent intent = new Intent(this, DELETEORDERS.class);
                        intent.putExtra("data", string_data);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "没有选中任何课程", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_getDiscount:
                try {
                    JSONObject jsonObject_main = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    String string_data = null;
                    for (int i = 0; i < adapter.getIsSelected().size(); i++) {
                        if (adapter.getIsSelected().get(i)) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", adapter.getItem(i).getOrdersCarId());
                            jsonObject.put("courseId", adapter.getItem(i).getCourseId());

                            jsonArray.put(jsonObject);

                            jsonObject_main.put("ids", jsonArray);

                            int studentId = GetData.getStudentId(this);
                            jsonObject_main.put("studentId", studentId);
                            string_data = jsonObject_main.toString();
                        }
                    }
                    if (jsonArray.length() != 0) {
                        Intent intent = new Intent(this, GETDISCOUNTS.class);
                        intent.putExtra("data", string_data);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "没有选中任何课程", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
//        Toast.makeText(this, "adapter.getIsSelected():" + adapter.getIsSelected(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getJsonString(String s) {
        String text = "{\"orders\":" + s + "}";
        MyOrders myOrders = JSON.parseObject(text, MyOrders.class);
        adapter = null;
        adapter = new MyBaseAdapter(this, myOrders.getOrders());
        listView.setAdapter(adapter);
    }
}
