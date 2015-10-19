package com.bakery.taskmgt;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by wangj on 10/14/15.
 */
public class LandingActivity extends FragmentActivity
        implements IFragementInteractionListener.OnFragmentInteractionListener {
    private ImageButton _btnTask;
    private ImageButton _btnUser;
    private ImageButton _btnCreateTask;
    private ImageButton _btnClient;
    private Fragment _mContent;
    private String Tag = "LandingActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.SetLeftMenu(savedInstanceState);
        this.SetLeftMenuEvent();
       // Utils.getMetaValue(MainActivity.this, "api_key")
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "Gm7CgjP22WEOMiB6S5xSGYY3");
    }

    private void SetLeftMenu(Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            _mContent = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }
        setContentView(R.layout.landing);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TaskFragment()).commit();
        SlidingMenu menu = new SlidingMenu(this);
       // menu.setMenu(R.layout.leftmenu);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidth(50);
        menu.setFadeDegree(0.35f);
        //menu.setBehindOffset(R.dimen.slidingmenu_offset);
        menu.setBehindWidth(500);
        menu.setMenu(R.layout.leftmenu);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }

    private void SetLeftMenuEvent()
    {
        this._btnTask = (ImageButton)findViewById(R.id.BtnTask);
        this._btnUser = (ImageButton)findViewById(R.id.BtnUser);
        this._btnCreateTask = (ImageButton)findViewById(R.id.BtnAddTask);
        this._btnCreateTask = (ImageButton)findViewById(R.id.BtnClient);
        this._btnTask.setOnClickListener(BtnClick_Listner);
        this._btnUser.setOnClickListener(BtnClick_Listner);
        this._btnCreateTask.setOnClickListener(BtnClick_Listner);
        Log.v(Tag,"BtnTask is clicked");
    }

    View.OnClickListener BtnClick_Listner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(Tag,"Button event has been set.");
            switch (v.getId()) {
                case R.id.BtnTask:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TaskFragment()).commit();
                    break;
                case R.id.BtnUser:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new EmployeeFragment()).commit();
                    break;
                case R.id.BtnAddTask:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TaskFragment()).commit();
                    break;
                case R.id.BtnClient:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ClientFragment()).commit();
                    break;
            }
        }
    };


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
