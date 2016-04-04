package com.zju.yibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.adapter.MySimpleAdapter;
import com.zju.yibao.bean.Course;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.GetData;
import com.zju.yibao.tool.MyJsonGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class COURSEDETAIL extends AppCompatActivity implements GetJson {

    private Toolbar toolbar;
    private TextView tv_title;
    private Button btnAddToOrdersCar;

    private int height = -1;
    private List<Map<String, Object>> listItems;
    private MySimpleAdapter adapter;
    private int courseId;

    private String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/course/queryCourseDetail.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        initView();

        String json = getIntent().getStringExtra("data");
        Course course = JSON.parseObject(json, Course.class);

        fillData(course);
        fillComment(course);

        courseId = getIntent().getIntExtra("courseId", -1);
        Log.d("COURSEDETAIL", "courseId:" + courseId);

        if (courseId != -1) {
            new MyJsonGetter(this).loadJson(string_http + "?courseId=" + courseId);
        }
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
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("课程详情");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAddToOrdersCar = (Button) findViewById(R.id.btn_addToOrdersCar);
        btnAddToOrdersCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(COURSEDETAIL.this, ADDTOORDERSCARS.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("studentId", GetData.getStudentId(COURSEDETAIL.this));
                startActivity(intent);
            }
        });

    }

    private void fillData(Course course) {
        fillString(course.getCourseName(), R.id.tv_course_name);
        fillString(course.getTeacherName(), R.id.tv_course_teacher);
        fillString(course.getOrganizationName(), R.id.tv_course_organization);
        fillString(course.getCourseDesc(), R.id.tv_course_desc);

        fillString(course.getTeacherName(), R.id.tv_teacher_name);
//        fillString(course.getTeacherName(), R.id.tv_teacher_name);
        fillString(course.getEducation(), R.id.tv_teacher_academic);
        fillString(String.valueOf(course.getSeniority()), R.id.tv_teacher_seniority);
        fillString(course.getDescription(), R.id.tv_teacher_description);

        fillString(course.getOrganizationName(), R.id.tv_organization_name);
        fillString(course.getOrganizationDescription(), R.id.tv_organization_desc);

    }

    private void fillString(String string, int id) {
        ((TextView) findViewById(id)).setText(string);
    }

    private void fillComment(Course course) {
        ListView listView = (ListView) findViewById(R.id.list_comment);

        listItems = new ArrayList<>();

        String[] strings = new String[]{"studentName", "comment", "studentHeadPortraits", "starLevel"};
        int[] to = {R.id.tv_studentName, R.id.tv_comment, R.id.img_studentHeadPortraits, R.id.rating_starLevel};

        for (int i = 0; i < course.getCourseCommentViews().size(); i++) {
            Map<String, Object> item = new HashMap<>();
            item.put(strings[0], course.getCourseCommentViews().get(i).getStudentName());
            item.put(strings[1], course.getCourseCommentViews().get(i).getComment());
            item.put(strings[2], R.drawable.user);
            item.put(strings[3], course.getCourseCommentViews().get(i).getStarLevel());

            listItems.add(item);
        }

        adapter = new MySimpleAdapter(
                COURSEDETAIL.this,
                listItems,
                R.layout.list_item_comment,
                strings,
                to
        );

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (height == -1) {
            height = params.height;
        }
        params.height = height * course.getCourseCommentViews().size();
        listView.setLayoutParams(params);

        listView.setAdapter(adapter);
    }

    @Override
    public void getJsonString(String s) {
        if (s == null || s.equals("")) {
            return;
        }
        Course course = JSON.parseObject(s, Course.class);
        fillData(course);
        listItems.clear();
        fillComment(course);
    }
}
