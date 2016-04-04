package com.zju.yibao;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hmw on 2016/2/2.
 */
public class REGISTER1 extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText conformPwd;
    private EditText passwd;
    private EditText email;
    private EditText user;
    private TextView title;
    private Button nextRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter1);
        initView();
        //initActionbar();
    }



    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        //title = (TextView) findViewById(R.id.title);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("注册");

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextRegister = (Button) findViewById(R.id.btn_nextRegister);
        nextRegister.setOnClickListener(listener);

        conformPwd = (EditText) findViewById(R.id.conformPasswdText);
        Drawable drawable1 = getResources().getDrawable(R.drawable.class_password);
        drawable1.setBounds(0, 0, 70, 70);
        conformPwd.setCompoundDrawables(drawable1, null, null, null);

        passwd = (EditText) findViewById(R.id.passwdText);
        Drawable drawable2 = getResources().getDrawable(R.drawable.class_password);
        drawable2.setBounds(0, 0, 70, 70);
        passwd.setCompoundDrawables(drawable2, null, null, null);

        user = (EditText) findViewById(R.id.user_text);
        Drawable drawable3 = getResources().getDrawable(R.drawable.class_username);
        drawable3.setBounds(0, 0, 70, 70);
        user.setCompoundDrawables(drawable3, null, null, null);

        email = (EditText) findViewById(R.id.emailText);
        Drawable drawable4 = getResources().getDrawable(R.drawable.class_email);
        drawable4.setBounds(0, 0, 70, 70);
        email.setCompoundDrawables(drawable4, null, null, null);
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
    Button.OnClickListener listener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            String string_user = user.getText().toString();
            String string_email = email.getText().toString();
            String string_passwd = passwd.getText().toString();
            String string_conformPwd = conformPwd.getText().toString();
            if (string_user.equals("")) {
                Toast.makeText(REGISTER1.this, "账户不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (string_email.equals("")) {
                Toast.makeText(REGISTER1.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (string_passwd.equals("")) {
                Toast.makeText(REGISTER1.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!string_passwd.equals(string_conformPwd)) {
                Toast.makeText(REGISTER1.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            switch (view.getId()) {
                case R.id.btn_nextRegister:
                    Bundle bundle = new Bundle();
                    bundle.putString("studentName",string_user);
                    bundle.putString("email",string_email);
                    bundle.putString("password",string_passwd);
                    Intent intent1 = new Intent(REGISTER1.this, REGISTER2.class);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    };
}
