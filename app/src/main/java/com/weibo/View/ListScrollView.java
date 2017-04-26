package com.weibo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.weibo.R;

/**
 * Created by 丶 on 2017/4/24.
 */

public class ListScrollView extends ScrollView {

    private final static String TAG = "TAG";

    private static ListView listView;
    private ViewGroup viewGroup;
    private View topbar;
    private int listview_loc[] = new int[2];
    private int topbar_loc[] = new int[2];

    public ListScrollView(Context context) {
        super(context);
        Init();
    }

    public ListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public ListScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    public void Init() {
        viewGroup = (ViewGroup) findViewById(R.id.searchBar);
    }

    public static void setListView(ListView listView) {
        ListScrollView.listView = listView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;

//        if (listview_loc[1] <= topbar_loc[1]) {
//            requestDisallowInterceptTouchEvent(true);
//            return false;
//        } else {
////            requestDisallowInterceptTouchEvent(true);
//            return super.onInterceptTouchEvent(ev);
//        }
    }
}
