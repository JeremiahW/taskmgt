package com.bakery.taskmgt;

import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.bakery.custom.CustomViewFragment;
import com.bakery.helper.Utils;

/**
 * Created by wangj on 10/14/15.
 */
public class LandingActivity extends FragmentActivity
        implements IFragementInteractionListener.OnFragmentInteractionListener {
    private ImageButton _btnTask;
    private ImageButton _btnUser;
    private ImageButton _btnCreateTask;
    private ImageButton _btnClient;
    private ImageButton _btnAddClient;
    private Fragment _mContent;
    private DrawerLayout _leftMenu;
    private ActionBarDrawerToggle _drawerToggle;
     private String Tag = "LandingActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.SetLeftMenu(savedInstanceState);
        this.SetLeftMenuEvent();
        try {
            this._leftMenu = (DrawerLayout)findViewById(R.id.mainDrawer);
              /*set the shadow for drawer at start(left) or end(right)*/
          //  this._leftMenu.setDrawerShadow(R.drawable.logo,
            //        GravityCompat.START);
             this._drawerToggle = new ActionBarDrawerToggle(this, _leftMenu, R.string.leftmenu_open, R.string.leftmenu_close)
            {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    //getActionBar().setTitle("");
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };

            this._leftMenu.setDrawerListener(_drawerToggle);

            String apiKey =  Utils.getMetaValue(LandingActivity.this, "api_key");
            Log.d(Tag, apiKey);
            //百度推送服务
            //PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,apiKey);
        }
        catch (Exception e){
           e.printStackTrace();
        }

    }

    private void SetLeftMenu(Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            _mContent = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }
        setContentView(R.layout.landing);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CustomViewFragment()).commit();

    }

    private void SetLeftMenuEvent()
    {
        this._btnTask = (ImageButton)findViewById(R.id.BtnTask);
        this._btnUser = (ImageButton)findViewById(R.id.BtnUser);
        this._btnCreateTask = (ImageButton)findViewById(R.id.BtnAddTask);
        this._btnClient = (ImageButton)findViewById(R.id.BtnClient);
        this._btnAddClient = (ImageButton)findViewById(R.id.BtnAddClient);

        this._btnTask.setOnClickListener(BtnClick_Listner);
        this._btnUser.setOnClickListener(BtnClick_Listner);
        this._btnCreateTask.setOnClickListener(BtnClick_Listner);
        this._btnClient.setOnClickListener(BtnClick_Listner);
        this._btnAddClient.setOnClickListener(BtnClick_Listner);
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AddTaskFragment()).commit();
                    break;
                case R.id.BtnClient:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ClientFragment()).commit();
                    break;
                case R.id.BtnAddClient:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AddClientFragment()).commit();
                    break;
            }
        }
    };


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
