package com.zju.yibao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zju.yibao.Interface.GetJson;
import com.zju.yibao.staticCode.LocalConfig;
import com.zju.yibao.tool.MyJsonGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Atlas on 15/12/16.
 */

public class MyFragment1 extends android.app.Fragment implements AdapterView.OnItemClickListener, GetJson {

    private View root;

    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<>();

    private ListView listView;
    private List<Map<String, Object>> listItems;
    private SimpleAdapter adapter;
    private JSONArray jsonArray;

    String string_http = LocalConfig.SERVICE_IP + "/ArtEducation/course/queryCourseTypeList.action";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_1, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initBanner();
        loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (jsonArray == null) {
            Toast.makeText(getActivity(), "暂时没有数据", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Intent intent = new Intent(getActivity(), COURSES.class);

                JSONObject jsonObject = jsonArray.getJSONObject(position);
                int courseTypeId = jsonObject.getInt("courseTypeId");
                String courseTypeName = jsonObject.getString("courseTypeName");

                intent.putExtra("courseTypeId", courseTypeId);
                intent.putExtra("courseTypeName", courseTypeName);

                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        convenientBanner = (ConvenientBanner) root.findViewById(R.id.convenientBanner);
        listView = (ListView) root.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    private void initBanner() {
        initImageLocal();

        CBViewHolderCreator<LocalImageHolderView> cbViewHolderCreator1 = new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        };

        convenientBanner.setPages(cbViewHolderCreator1, localImages);
        convenientBanner
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        try {
            convenientBanner.getViewPager().setPageTransformer(true, DefaultTransformer.class.newInstance());
        } catch (Exception e) {
        }
    }

    private void initImageLocal() {
        if (localImages.size() != 0) {
            return;
        }
        for (int position = 0; position < 6; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void loadData() {
        listItems = new ArrayList<>();

        int[] icons = new int[]{
                R.drawable.class_shengyue,
                R.drawable.class_gangqin,
                R.drawable.class_shoutiqin,
                R.drawable.class_jiazigu,
                R.drawable.class_wudao};

        String[] names = new String[]{"声 乐", "钢 琴", "手 提琴", "架 子鼓", "舞 蹈"};

        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", icons[i]);
            item.put("name", names[i]);

            listItems.add(item);
        }

        adapter = new SimpleAdapter(
                getActivity(),
                listItems,
                R.layout.list_item_fragment1,
                new String[]{"icon", "name"},
                new int[]{R.id.image_icon, R.id.text_name});

        listView.setAdapter(adapter);
        new MyJsonGetter(MyFragment1.this).loadJson(string_http);
    }


    @Override
    public void getJsonString(String s) {
        if (s == null) {
            return;
        }
        int[] icons = new int[]{
                R.drawable.class_shengyue,
                R.drawable.class_gangqin,
                R.drawable.class_shoutiqin,
                R.drawable.class_jiazigu,
                R.drawable.class_wudao};
        try {
            listItems.clear();
            jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> item = new HashMap<>();
                item.put("icon", icons[i % 5]);
                item.put("name", jsonArray.getJSONObject(i).get("courseTypeName"));

                listItems.add(item);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
