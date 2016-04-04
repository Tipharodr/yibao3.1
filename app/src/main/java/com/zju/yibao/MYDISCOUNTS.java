package com.zju.yibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.bean.MyDiscounts;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.GetData;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MYDISCOUNTS extends AppCompatActivity implements AdapterView.OnItemClickListener, GetJson {

    private Toolbar toolbar;
    private TextView tv_title;
    private ListView listView;
    private List<Map<String, Object>> listItems;
    private SimpleAdapter adapter;

    private MyDiscounts myDiscounts;
    private String[] string_from;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/queryDiscountList.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiscounts);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String string = "{\n" +
                "    \"courseName\": \"声乐1\",\n" +
                "    \"discountCode\": \"123456\",\n" +
                "    \"discountUseInfo\": \"" +
                "1、购买商品时（除特例商品外），购物券可抵购物券券面显示的现金价值；\n\n" +
                "2、原则上每张订单只能使用一张购物券，且不得与其他优惠方式同时使用；\n\n" +
                "3、本券不得兑换现金不设找零\n\n4、退货时，本券价值不予退还现金；\n\n" +
                "最后一般都会有最终解释权归XX所有，还应该有使用期限\"\n" +
                "}";

        Intent intent = new Intent(MYDISCOUNTS.this, MYDISCOUNTDETAIL.class);
        //传送discountId,请求过程放在下一个界面
        intent.putExtra("discountId", myDiscounts.getMyDiscounts().get(position).getDiscountId());
        intent.putExtra("data", string);

        startActivity(intent);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的优惠劵");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    private void loadData() {
        listItems = new ArrayList<>();

        String string = "{\n" +
                "    \"myDiscounts\": ";
        string += getIntent().getStringExtra("data");
        string += "\n" +
                "}";

        myDiscounts = JSON.parseObject(string, MyDiscounts.class);

        string_from = new String[]{"course_name", "course_teacher", "course_organization", "course_image", "discount_code"};
        int[] int_to = {R.id.tv_course_name, R.id.tv_course_teacher, R.id.tv_course_organization, R.id.img_course, R.id.tv_disocunt_code};

        for (int i = 0; i < myDiscounts.getMyDiscounts().size(); i++) {
            Map<String, Object> item = new HashMap<>();

            item.put(string_from[0], myDiscounts.getMyDiscounts().get(i).getCourseName());
            item.put(string_from[1], myDiscounts.getMyDiscounts().get(i).getTeacherName());
            item.put(string_from[2], myDiscounts.getMyDiscounts().get(i).getOrganizationName());
            item.put(string_from[3], R.drawable.user);
            item.put(string_from[4], myDiscounts.getMyDiscounts().get(i).getDiscountCode());

            listItems.add(item);
        }

        adapter = new SimpleAdapter(
                MYDISCOUNTS.this,
                listItems,
                R.layout.list_item_discount,
                string_from,
                int_to);

        listView.setAdapter(adapter);

        int studentId = GetData.getStudentId(this);
        new MyJsonGetter(this).loadJson(string_http + "?studentId=" + studentId);
    }

    @Override
    public void getJsonString(String s) {
        if (s == null) {
            return;
        }
        try {
            listItems.clear();
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> item = new HashMap<>();

                item.put(string_from[0], jsonArray.getJSONObject(i).get("courseName"));
                item.put(string_from[1], jsonArray.getJSONObject(i).get("teacherName"));
                item.put(string_from[2], jsonArray.getJSONObject(i).get("organizationName"));
                item.put(string_from[3], R.drawable.user);
                item.put(string_from[4], jsonArray.getJSONObject(i).get("discountCode"));

                listItems.add(item);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String string = "{\n" +
                "    \"myDiscounts\": ";
        string += s;
        string += "\n" +
                "}";
        myDiscounts = JSON.parseObject(string, MyDiscounts.class);
    }
}
