package com.zju.yibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zju.yibao.R;
import com.zju.yibao.bean.MyOrders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Atlas on 16/2/5.
 */
public class MyBaseAdapter extends BaseAdapter {

    private Context context;
    private List<MyOrders.OrdersEntity> list;
    private List<Boolean> isSelected;
    private HashMap<Integer, View> viewHashMap;

    private static class ViewHolder {
        CheckBox checkBox;
        TextView tv_courseName;
        TextView tv_teacherName;
        TextView tv_organizationName;
    }

    public MyBaseAdapter(Context context, List<MyOrders.OrdersEntity> list) {
        this.context = context;
        this.list = list;

        isSelected = new ArrayList<>();
        viewHashMap = new HashMap<>();

        for (int i = 0; i < this.list.size(); i++) {
            isSelected.add(false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MyOrders.OrdersEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View root;
        ViewHolder viewHolder = new ViewHolder();

        if (viewHashMap.get(position) == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root = inflater.inflate(R.layout.list_item_orders, null);

            viewHolder.checkBox = (CheckBox) root.findViewById(R.id.checkbox);
            viewHolder.tv_courseName = (TextView) root.findViewById(R.id.tv_course_name);
            viewHolder.tv_teacherName = (TextView) root.findViewById(R.id.tv_course_teacher);
            viewHolder.tv_organizationName = (TextView) root.findViewById(R.id.tv_course_organization);

            viewHashMap.put(position, root);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelected.set(position, ((CheckBox) v).isChecked());
                }
            });

            root.setTag(viewHolder);
        } else {
            root = viewHashMap.get(position);
            viewHolder = (ViewHolder) root.getTag();
        }

        viewHolder.checkBox.setChecked(isSelected.get(position));
        viewHolder.tv_courseName.setText(list.get(position).getCourseName());
        viewHolder.tv_teacherName.setText(list.get(position).getTeacherName());
        viewHolder.tv_organizationName.setText(list.get(position).getOrganizationName());

        return root;
    }

    public List<Boolean> getIsSelected() {
        return isSelected;
    }
}
