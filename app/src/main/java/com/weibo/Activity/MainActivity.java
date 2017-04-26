package com.weibo.Activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weibo.Fragment.FindFragment;
import com.weibo.Fragment.MainFragment;
import com.weibo.Fragment.MessageFragment;
import com.weibo.Fragment.UserInfoFragment;
import com.weibo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG="TAG";

    /**
     * 底部栏布局
     */
    private LinearLayout linearLayout_1;
    private LinearLayout linearLayout_2;
    private LinearLayout linearLayout_3;
    private LinearLayout linearLayout_4;
    /**
     * topBar布局
     */
    private RelativeLayout relativeLayout;

    /**
     *
     */
    private View fragmentView;

    /**
     * Fragment管理对象
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    /**
     * Fragment
     */
    private MainFragment mainFragment;
    private MessageFragment messageFragment;
    private FindFragment findFragment;
    private UserInfoFragment userInfoFragment;
    private Fragment currentFragment;

    /**
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().setNavigationBarColor(Color.WHITE);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        Init();
    }


    private void Init() {

        linearLayout_1 = (LinearLayout) findViewById(R.id.linear_icon_1);
        linearLayout_1.setOnClickListener(this);
        linearLayout_2 = (LinearLayout) findViewById(R.id.linear_icon_2);
        linearLayout_2.setOnClickListener(this);
        linearLayout_3 = (LinearLayout) findViewById(R.id.linear_icon_3);
        linearLayout_3.setOnClickListener(this);
        linearLayout_4 = (LinearLayout) findViewById(R.id.linear_icon_4);
        linearLayout_4.setOnClickListener(this);

        relativeLayout = (RelativeLayout) findViewById(R.id.topBar);

        fragmentView = findViewById(R.id.fragmentView);



        /**
         * 初始化Fragment
         */
        mainFragment = new MainFragment();
        messageFragment = new MessageFragment();
        findFragment=new FindFragment();
        userInfoFragment=new UserInfoFragment();

        currentFragment=mainFragment;


        /**
         * 初始化
         */
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentView,mainFragment);
        fragmentTransaction.commitNow();
        linearLayout_1.callOnClick();

    }

    private void switchFragment(Fragment from, Fragment to) {

        currentFragment=to;

        fragmentTransaction=fragmentManager.beginTransaction();

            if (!to.isAdded()) {
                fragmentTransaction.hide(from).add(R.id.fragmentView, to).commit();
            } else {
                fragmentTransaction.hide(from).show(to).commit();
            }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linear_icon_1:
                ((ImageView) (linearLayout_1.getChildAt(0))).setImageResource(R.drawable.icon_1_d);
                ((ImageView) (linearLayout_2.getChildAt(0))).setImageResource(R.drawable.icon_2_n);
                ((ImageView) (linearLayout_3.getChildAt(0))).setImageResource(R.drawable.icon_3_n);
                ((ImageView) (linearLayout_4.getChildAt(0))).setImageResource(R.drawable.icon_4_n);
                switchFragment(currentFragment,mainFragment);
                break;
            case R.id.linear_icon_2:
                ((ImageView) (linearLayout_2.getChildAt(0))).setImageResource(R.drawable.icon_2_d);
                ((ImageView) (linearLayout_1.getChildAt(0))).setImageResource(R.drawable.icon_1_n);
                ((ImageView) (linearLayout_3.getChildAt(0))).setImageResource(R.drawable.icon_3_n);
                ((ImageView) (linearLayout_4.getChildAt(0))).setImageResource(R.drawable.icon_4_n);
                switchFragment(currentFragment,messageFragment);
                break;
            case R.id.linear_icon_3:
                ((ImageView) (linearLayout_3.getChildAt(0))).setImageResource(R.drawable.icon_3_d);
                ((ImageView) (linearLayout_1.getChildAt(0))).setImageResource(R.drawable.icon_1_n);
                ((ImageView) (linearLayout_2.getChildAt(0))).setImageResource(R.drawable.icon_2_n);
                ((ImageView) (linearLayout_4.getChildAt(0))).setImageResource(R.drawable.icon_4_n);
                switchFragment(currentFragment,findFragment);
                break;
            case R.id.linear_icon_4:
                ((ImageView) (linearLayout_4.getChildAt(0))).setImageResource(R.drawable.icon_4_d);
                ((ImageView) (linearLayout_1.getChildAt(0))).setImageResource(R.drawable.icon_1_n);
                ((ImageView) (linearLayout_2.getChildAt(0))).setImageResource(R.drawable.icon_2_n);
                ((ImageView) (linearLayout_3.getChildAt(0))).setImageResource(R.drawable.icon_3_n);
                switchFragment(currentFragment,userInfoFragment);
                break;
        }
    }
}
