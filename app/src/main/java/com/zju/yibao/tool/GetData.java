package com.zju.yibao.tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Atlas on 16/4/1.
 */
public class GetData {
    public static int getStudentId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        int studentId = preferences.getInt("studentId", -1);
        return studentId;
    }
}
