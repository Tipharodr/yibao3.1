package com.zju.yibao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class COURSES extends AppCompatActivity implements AdapterView.OnItemClickListener, GetJson {

    private Toolbar toolbar;
    private TextView tv_title;
    private SearchView search_course;

    private Spinner spinner_star;
    private Spinner spinner_ways;
    private Spinner spinner_sex;
    private Spinner spinner_order;

    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> listItems;
    private String[] string_from;
    private JSONArray jsonArray;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/course/queryCoursesList.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        initView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSoftInput();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            Intent intent = new Intent(COURSES.this, COURSEDETAIL.class);
            intent.putExtra("data", string);
            intent.putExtra("courseId", jsonArray.getJSONObject(position).getInt("courseId"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        /**
         * 设置actionbar
         * */
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = getIntent().getStringExtra("courseTypeName");
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(name);

        search_course = (SearchView) findViewById(R.id.search_course);
        //设置searchview字体颜色
        int id = search_course.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) search_course.findViewById(id);
        textView.setTextColor(Color.WHITE);
        search_course.setOnQueryTextListener(queryTextListener);

        /**
         * 设置筛选条
         * */
        spinner_star = (Spinner) findViewById(R.id.spinner_star);
        spinner_ways = (Spinner) findViewById(R.id.spinner_ways);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        spinner_order = (Spinner) findViewById(R.id.spinner_order);

        spinner_star.setOnItemSelectedListener(itemSelectedListener);
        spinner_ways.setOnItemSelectedListener(itemSelectedListener);
        spinner_sex.setOnItemSelectedListener(itemSelectedListener);
        spinner_order.setOnItemSelectedListener(itemSelectedListener);

        /**
         * 设置listview
         * */
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                hideSoftInput();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    private void loadData() {
        listItems = new ArrayList<>();

        string_from = new String[]{"course_name", "course_teacher", "course_organization", "course_image"};
        int[] int_to = {R.id.tv_course_name, R.id.tv_course_teacher, R.id.tv_course_organization, R.id.img_course};

//        for (int i = 0; i < 20; i++) {
//            Map<String, Object> item = new HashMap<>();
//            item.put(string_from[0], "课程" + i);
//            item.put(string_from[1], "教师" + i);
//            item.put(string_from[2], "机构" + i);
//            item.put(string_from[3], R.drawable.user);
//
//            listItems.add(item);
//        }
        adapter = new SimpleAdapter(
                COURSES.this,
                listItems,
                R.layout.list_item_course,
                string_from,
                int_to);

        listView.setAdapter(adapter);
        int courseTypeId = getIntent().getIntExtra("courseTypeId", -1);
        if (courseTypeId != -1) {
            new MyJsonGetter(this).loadJson(string_http + "?courseTypeId=" + courseTypeId);
        }
    }

    private void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(search_course.getWindowToken(), 0);
        }
        search_course.clearFocus();
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(COURSES.this, query, Toast.LENGTH_SHORT).show();
            hideSoftInput();
            Intent intent = new Intent(COURSES.this, SEARCHCOURSE.class);
            intent.putExtra("courseName", query);
            startActivity(intent);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) view).setTextColor(Color.WHITE);
            switch (parent.getId()) {
                case R.id.spinner_star:
                    System.out.println("star");
                    break;
                case R.id.spinner_ways:
                    System.out.println("ways");
                    break;
                case R.id.spinner_sex:
                    System.out.println("sex");
                    break;
                case R.id.spinner_order:
                    System.out.println("order");
                    break;
            }
            System.out.println(position);
            System.out.println(((TextView) view).getText());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void getJsonString(String s) {
        if (s == null) {
            return;
        }
        if (s.equals("[]")) {
            Toast.makeText(this, "该分类下面没有课程", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            listItems.clear();
            jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> item = new HashMap<>();
                item.put(string_from[0], jsonArray.getJSONObject(i).getString("courseName"));
                item.put(string_from[1], jsonArray.getJSONObject(i).getString("teacherName"));
                item.put(string_from[2], jsonArray.getJSONObject(i).getString("organizationName"));
                item.put(string_from[3], R.drawable.user);

                listItems.add(item);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
