package com.bakery.taskmgt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

public class MainActivity extends Activity {

    EditText _txtUser;
    EditText _txtPwd;
    Button _btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._txtUser = (EditText)findViewById(R.id.txtUser);
        this._txtPwd = (EditText)findViewById(R.id.txtPwd);
        this._btnLogin = (Button)findViewById(R.id.btnLogin);
        this._btnLogin.setOnClickListener(BtnClick_Listner);

    }

    OnClickListener BtnClick_Listner = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String user = _txtUser.getText().toString();
            String pwd =  _txtPwd.getText().toString();

            //TODO Login Function needs to be completed
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
			/*
			new AlertDialog.Builder(v.getContext())
						   .setTitle("登录错误")
						   .setMessage(user + pwd)
						   .setPositiveButton("确定", null)
						   .show();
						   */
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
