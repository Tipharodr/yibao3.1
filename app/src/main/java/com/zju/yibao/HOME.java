package com.zju.yibao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HOME extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tv_title;
    private MyFragment1 myFragment1;
    private MyFragment2 myFragment2;
    private LinearLayout bottom_bar_1;
    private LinearLayout bottom_bar_2;
    private TextView tv_course;
    private TextView tv_my;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private boolean isLogin;

    private long currentBackPressedTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smoothSwitchScreen();
        setContentView(R.layout.activity_home);

        initView();
        showFragment1();

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = preferences.edit();
        isLogin = preferences.getBoolean("isLogin", false);

    }

    @Override
    protected void onDestroy() {
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isLogin) {
            menu.getItem(0).setTitle("注销");
        } else {
            menu.getItem(0).setTitle("登录");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_login:
                isLogin = !isLogin;
                break;
        }
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_bar_1:
                showFragment1();
                break;
            case R.id.bottom_bar_2:
                isLogin = preferences.getBoolean("isLogin", false);
//                Log.d("HOME", "isLogin:" + isLogin);
                if (isLogin) {
                    showFragment2();
                } else {
                    Intent intent = new Intent(HOME.this, LOGIN.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("艺术宝");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        bottom_bar_1 = (LinearLayout) findViewById(R.id.bottom_bar_1);
        bottom_bar_1.setOnClickListener(this);
        bottom_bar_2 = (LinearLayout) findViewById(R.id.bottom_bar_2);
        bottom_bar_2.setOnClickListener(this);

        tv_course = (TextView) findViewById(R.id.bottom_bar_tv_1);
        tv_my = (TextView) findViewById(R.id.bottom_bar_tv_2);

    }

    private void showFragment1() {
        if (myFragment1 == null) {
            myFragment1 = new MyFragment1();
        }
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_show, myFragment1)
                .commit();
        tv_course.setTextColor(getResources().getColor(R.color.colorText));
        tv_my.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void showFragment2() {
        if (myFragment2 == null) {
            myFragment2 = new MyFragment2();
        }
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_show, myFragment2)
                .commit();
        tv_my.setTextColor(getResources().getColor(R.color.colorText));
        tv_course.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void smoothSwitchScreen() {
        // 5.0以上修复了此bug
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup rootView = ((ViewGroup) this.findViewById(android.R.id.content));
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            rootView.setPadding(0, statusBarHeight, 0, 0);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
