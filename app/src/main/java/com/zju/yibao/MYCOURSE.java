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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.bean.MyCourses;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.GetData;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MYCOURSE extends AppCompatActivity implements GetJson, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private TextView tv_title;
    private ListView listView;
    private List<Map<String, Object>> listItems;
    private SimpleAdapter adapter;
    private String[] string_from;
    private JSONArray jsonArray;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/student/queryStuCourseList.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourses);

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
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的课程");

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
                "    \"myCourses\": ";
        string += getIntent().getStringExtra("data");
        string += "\n" +
                "}";

        MyCourses myCourses = JSON.parseObject(string, MyCourses.class);

        string_from = new String[]{"course_name", "course_teacher", "course_organization", "course_image"};
        int[] int_to = {R.id.tv_course_name, R.id.tv_course_teacher, R.id.tv_course_organization, R.id.img_course};

        for (int i = 0; i < myCourses.getMyCourses().size(); i++) {
            Map<String, Object> item = new HashMap<>();

            item.put(string_from[0], myCourses.getMyCourses().get(i).getCourseName());
            item.put(string_from[1], myCourses.getMyCourses().get(i).getTeacherName());
            item.put(string_from[2], myCourses.getMyCourses().get(i).getOrganization());
            item.put(string_from[3], R.drawable.user);

            listItems.add(item);
        }

        adapter = new SimpleAdapter(
                MYCOURSE.this,
                listItems,
                R.layout.list_item_course,
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
            jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> item = new HashMap<>();

                item.put(string_from[0], jsonArray.getJSONObject(i).get("courseName"));
                item.put(string_from[1], jsonArray.getJSONObject(i).get("teacherName"));
                item.put(string_from[2], jsonArray.getJSONObject(i).get("organizationName"));
                item.put(string_from[3], R.drawable.user);

                listItems.add(item);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
        String string = "{\n" +
                "    \"courseId\": 1,\n" +
                "    \"courseName\": \"声乐1\",\n" +
                "    \"teacherName\": \"陈红\",\n" +
                "    \"mainImage\": \"meiyou\",\n" +
                "    \"courseDesc\": \"声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1声乐1\",\n" +
                "    \"courseStatus\": 1002,\n" +
                "    \"education\": \"本科\",\n" +
                "    \"seniority\": 6,\n" +
                "    \"description\": \"很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮很漂亮\",\n" +
                "    \"teacherHeadPortraits\": null,\n" +
                "    \"organizationName\": \"新东方\",\n" +
                "    \"organizationDescription\": \"新东方教育\",\n" +
                "    \"organizationImageId\": null,\n" +
                "    \"courseCommentViews\": [\n" +
                "        {\n" +
                "            \"studentName\": \"hardor\",\n" +
                "            \"starLevel\": \"2\",\n" +
                "            \"comment\": \"真好\",\n" +
                "            \"studentHeadPortraits\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"studentName\": \"xiaoli\",\n" +
                "            \"starLevel\": \"4\",\n" +
                "            \"comment\": \"很棒\",\n" +
                "            \"studentHeadPortraits\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        try {
            Intent intent = new Intent(this, COURSEDETAIL.class);
            intent.putExtra("data", string);
            intent.putExtra("courseId", jsonArray.getJSONObject(position).getInt("courseId"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
