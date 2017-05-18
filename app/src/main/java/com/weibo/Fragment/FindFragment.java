package com.weibo.Fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weibo.Interface.impl.HttpCallback;
import com.weibo.R;
import com.weibo.Utils.HttpUtil;
import com.weibo.Utils.RequestUrls;
import com.weibo.Utils.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丶 on 2017/4/20.
 */

public class FindFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = "TAG";

    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    /**
     *
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FragmentAdapter fragmentAdapter;
    private Trending_FindFragment trending_findFragment;
    private Star_FindFragment star_findFragment;

    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private TextView textView;

    private int imageViewWidth;
    private int maxWidth;

    /**
     * 顶部指示栏
     */
    private GradientDrawable gradientDrawable;
    int drawableWidth;

    /**
     * 顶部标签
     */
    private TextView textView_Trending;
    private TextView textView_Star;

    /**
     *
     */
    private TextView textView_Hotwords;

    /**
     * 获取屏幕宽度
     */
    private int winWidth;

    /**
     * 记录viewPager上一次的offset值
     */
    private float lastPositionOffest;

    /**
     * Viewpager指示器布局属性
     */
    private RelativeLayout.LayoutParams layoutParams;

    private ViewGroup container;

    /***/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();
        fragmentList = new ArrayList<Fragment>();
        trending_findFragment = new Trending_FindFragment();
        star_findFragment = new Star_FindFragment();
        fragmentList.add(trending_findFragment);
        fragmentList.add(star_findFragment);
        getHotWords();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        container = (ViewGroup) inflater.inflate(R.layout.fragment_find, null);
        this.container = container;
        return container;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) container.findViewById(R.id.viewPager);
        imageView = (ImageView) container.findViewById(R.id.tab);
        relativeLayout = (RelativeLayout) container.findViewById(R.id.Title);
        textView = (TextView) container.findViewById(R.id.textview_trending);

        textView_Trending = (TextView) container.findViewById(R.id.textview_trending);
        textView_Star = (TextView) container.findViewById(R.id.textview_start);
        textView_Trending.setTextColor(Color.BLACK);
        textView_Star.setTextColor(Color.GRAY);
        textView_Trending.setOnClickListener(this);
        textView_Star.setOnClickListener(this);

        fragmentAdapter = new FragmentAdapter(fragmentManager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(this);

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(8);
        gradientDrawable.setColor(Color.parseColor("#f48341"));

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int padding = textView.getWidth() * 1 / 7;
                winWidth = viewPager.getWidth();
                maxWidth = relativeLayout.getWidth();
                drawableWidth = textView.getWidth();
                imageViewWidth = drawableWidth;
                layoutParams = new RelativeLayout.LayoutParams(imageViewWidth, 10);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.relative);
                layoutParams.setMargins(0, 10, 0, 0);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageDrawable(gradientDrawable);
                imageView.setPadding(padding, 0, padding, 0);

            }
        });
    }

    /**
     * 实现了类似微博的Viewpager指示器
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.i(TAG, "onPageScrolled: " + winWidth);

        if (winWidth == 0) {
            return;
        }
        if (positionOffsetPixels < winWidth / 2) {
            imageViewWidth = drawableWidth + 2 * (maxWidth - drawableWidth) * positionOffsetPixels / winWidth;
            layoutParams = new RelativeLayout.LayoutParams(imageViewWidth, 10);
            if (position == 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
        }
        if (positionOffsetPixels > winWidth / 2) {
            imageViewWidth = 2 * maxWidth - 2 * (maxWidth - drawableWidth) * positionOffsetPixels / winWidth - drawableWidth;
            layoutParams = new RelativeLayout.LayoutParams(imageViewWidth, 10);
            if (position == 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
        }

        layoutParams.addRule(RelativeLayout.BELOW, R.id.relative);
        layoutParams.setMargins(0, 10, 0, 0);
        imageView.setLayoutParams(layoutParams);


    }

    @Override
    public void onPageSelected(int position) {

        if (position == 0) {
            textView_Trending.setTextColor(Color.BLACK);
            textView_Star.setTextColor(Color.GRAY);
        } else {
            textView_Star.setTextColor(Color.BLACK);
            textView_Trending.setTextColor(Color.GRAY);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.textview_trending:
                viewPager.setCurrentItem(0);
                break;
            case R.id.textview_start:
                viewPager.setCurrentItem(1);
                break;
        }

    }

    private void getHotWords() {

        HttpUtil.sendJsonObjectRequest(RequestUrls.getHotWords_URL(), new HttpCallback() {
            @Override
            public void onFinish(JSONObject jsonObject) {
                String hotWords = Utility.handleHotWordsResponse(jsonObject);
                ((CallBack) trending_findFragment).onFinish(hotWords);
                ((CallBack) star_findFragment).onFinish(hotWords);
            }
        });
    }

    public interface CallBack {
        void onFinish(String HotWords);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}
