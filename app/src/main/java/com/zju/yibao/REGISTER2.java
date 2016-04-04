package com.zju.yibao;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by hmw on 2016/2/2.
 */
public class REGISTER2 extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {
    private Toolbar toolbar;
    private TextView title;
    private EditText name;
    private EditText sex;
    private EditText age;
    private EditText preference;
    private EditText phone;
    private Button nextRegister;
    private int choice = 0;
    boolean[] selected = new boolean[]{false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();
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
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_register_sex:
                if (hasFocus) {
                    chooseSex();
                }
                break;
            case R.id.edit_register_age:
                if (hasFocus) {
//                    chooseDate();
                    chooseAge();
                }
                break;
            case R.id.edit_register_preference:
                if (hasFocus) {
                    choosePreference();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_register_sex:
                chooseSex();
                break;
            case R.id.edit_register_age:
//                chooseDate();
                chooseAge();
                break;
            case R.id.edit_register_preference:
                choosePreference();
                break;
            case R.id.btn_nextRegister2:
                String string_name = name.getText().toString();
                String string_sex = sex.getText().toString();
                int int_age = 0;
                try {
                    int_age = Integer.valueOf(age.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                String string_preference = preference.getText().toString();
                String string_phone = phone.getText().toString();

                Bundle bundle = getIntent().getExtras();
                bundle.putString("realName", string_name);
                bundle.putString("gender", string_sex);
                bundle.putInt("studentAge", int_age);
                bundle.putString("preference", string_preference);
                bundle.putString("phone", string_phone);

                Intent intent2 = new Intent(REGISTER2.this, REGISTER3.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("注册");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.edit_register_name);

        sex = (EditText) findViewById(R.id.edit_register_sex);
        sex.setOnFocusChangeListener(this);
        sex.setOnClickListener(this);

        age = (EditText) findViewById(R.id.edit_register_age);
        age.setOnClickListener(this);
        age.setOnFocusChangeListener(this);

        preference = (EditText) findViewById(R.id.edit_register_preference);
        preference.setOnFocusChangeListener(this);
        preference.setOnClickListener(this);

        phone = (EditText) findViewById(R.id.edit_register_phone);

        nextRegister = (Button) findViewById(R.id.btn_nextRegister2);
        nextRegister.setOnClickListener(this);
    }

    public void chooseSex() {
        final String[] sexList = {"男", "女"};
        new AlertDialog.Builder(this)
                .setTitle("选择性别")
                .setSingleChoiceItems(
                        sexList, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                choice = which;
                            }
                        })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (choice != -1) {
                            sex.setText(sexList[choice]);
                        }
                    }
                })
                .show();
    }

    private void chooseAge() {
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(80);
        numberPicker.setValue(16);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle("请选择您的年龄")
                .setView(numberPicker)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        age.setText(numberPicker.getValue() + "");
                    }
                })
                .setNegativeButton("取消", null);
        builder.create()
                .show();

    }

    public void choosePreference() {

        DialogInterface.OnMultiChoiceClickListener mutiListener =
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                };
        DialogInterface.OnClickListener btnListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String selectedStr = "";
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i] == true) {
                        selectedStr = selectedStr + " " +
                                getResources().getStringArray(R.array.preferenceList)[i];
                    }
                }
                preference.setText(selectedStr);
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("选择偏好(可多选)")
                .setMultiChoiceItems(R.array.preferenceList, selected, mutiListener)
                .setPositiveButton("确定", btnListener)
                .create()
                .show();
    }
}
