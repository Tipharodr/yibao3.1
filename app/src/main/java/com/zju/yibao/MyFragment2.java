package com.zju.yibao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Atlas on 15/12/16.
 */
public class MyFragment2 extends android.app.Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View root;
    private CircleImageView user_image;
    private ListView listView;
    private List<Map<String, Object>> listItems;
    private SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_2, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_image:
                shake(user_image);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                showMyCourses();
                break;
            case 1:
                showMyOrders();
                break;
            case 2:
                showMyDiscounts();
                break;
            case 3:
                showMyInformation();
                break;
        }
    }

    private void initView() {
        user_image = (CircleImageView) root.findViewById(R.id.user_image);
        user_image.setOnClickListener(this);

        listView = (ListView) root.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    private void loadData() {
        listItems = new ArrayList<>();

        int[] icons = new int[]{
                R.drawable.icon_course,
                R.drawable.icon_shopping,
                R.drawable.icon_ticket,
                R.drawable.icon_personal};

        String[] names = new String[]{"我的课程", "购物车", "优惠券", "个人信息"};

        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", icons[i]);
            item.put("name", names[i]);

            listItems.add(item);
        }

        adapter = new SimpleAdapter(
                getActivity(),
                listItems,
                R.layout.list_item_fragment2,
                new String[]{"icon", "name"},
                new int[]{R.id.image_icon, R.id.text_name});

        listView.setAdapter(adapter);
    }

    private void shake(View view) {
        final TranslateAnimation anim = new TranslateAnimation(0, 10, 0, 0);
        anim.setInterpolator(new CycleInterpolator(2f));
        anim.setDuration(300);
        view.startAnimation(anim);
    }

    private void showMyCourses() {

        String string = "[\n" +
                "    {\n" +
                "        \"couStuId\": 1,\n" +
                "        \"courseId\": 1,\n" +
                "        \"courseName\": \"声乐1\",\n" +
                "        \"mainImage\": \"meiyou\",\n" +
                "        \"teacherName\": \"陈红\",\n" +
                "        \"teachAddress\": \"宁波\",\n" +
                "        \"organization\": \"新东方\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"couStuId\": 3,\n" +
                "        \"courseId\": 2,\n" +
                "        \"courseName\": \"声乐2\",\n" +
                "        \"mainImage\": \"meiyou\",\n" +
                "        \"teacherName\": \"陈红\",\n" +
                "        \"teachAddress\": \"宁波\",\n" +
                "        \"organization\": \"新东方\"\n" +
                "    }\n" +
                "]";

        Intent intent = new Intent(getActivity(), MYCOURSE.class);
        intent.putExtra("data", string);
        startActivity(intent);
    }

    private void showMyOrders() {

        String string = "{\n" +
                "    \"orders\":[\n" +
                "        {\n" +
                "            \"ordersCarId\":23123123 ,\n" +
                "            \"studentId\":\"34534534 \",\n" +
                "            \"courseName\":\"声乐课程 \",\n" +
                "            \"teacherName\":\"王老师 \",\n" +
                "            \"teacherAge\":\"23 \",\n" +
                "            \"organizationName\":\"科瑞教育 \",\n" +
                "            \"organizationAddr\":\"浙江省宁波市\",\n" +
                "            \"education\":\"硕士\",\n" +
                "            \"time\":\"2016-02-05 15:22:20\",\n" +
                "            \"count\":2,\n" +
                "            \"totalPrice\":300.01\n" +
                "        },\n" +
                "        {\n" +
                "            \"ordersCarId\":23123123 ,\n" +
                "            \"studentId\":\"34534534 \",\n" +
                "            \"courseName\":\"声乐课程2 \",\n" +
                "            \"teacherName\":\"王老师 \",\n" +
                "            \"teacherAge\":\"23 \",\n" +
                "            \"organizationName\":\"科瑞教育 \",\n" +
                "            \"organizationAddr\":\"浙江省宁波市\",\n" +
                "            \"education\":\"硕士\",\n" +
                "            \"time\":\"2016-02-05 15:22:20\",\n" +
                "            \"count\":2,\n" +
                "            \"totalPrice\":300.01\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Intent intent = new Intent(getActivity(), MYORDERS.class);
        intent.putExtra("data", string);
        startActivity(intent);
    }

    private void showMyDiscounts() {

        String string = "[\n" +
                "    {\n" +
                "        \"discountId\": 1,\n" +
                "        \"discountCode\": \"000000\",\n" +
                "        \"courseId\": 1,\n" +
                "        \"courseName\": \"声乐1\",\n" +
                "        \"mainImage\": \"meiyou\",\n" +
                "        \"teacherName\": \"陈红\",\n" +
                "        \"organizationName\": \"新东方\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"discountId\": 2,\n" +
                "        \"discountCode\": \"111111\",\n" +
                "        \"courseId\": 2,\n" +
                "        \"courseName\": \"声乐2\",\n" +
                "        \"mainImage\": \"meiyou\",\n" +
                "        \"teacherName\": \"陈红\",\n" +
                "        \"organizationName\": \"新东方\"\n" +
                "    }\n" +
                "]";

        Intent intent = new Intent(getActivity(), MYDISCOUNTS.class);
        intent.putExtra("data", string);
        startActivity(intent);
    }

    private void showMyInformation() {
        Intent intent = new Intent(getActivity(), MYINFOMATION.class);
        startActivity(intent);
    }
}
