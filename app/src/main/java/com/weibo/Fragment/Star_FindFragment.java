package com.weibo.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.weibo.Activity.PictureActivity;
import com.weibo.Adapter.BlogListAdapter;
import com.weibo.Bean.BlogData;
import com.weibo.Interface.impl.HttpCallback;
import com.weibo.Interface.onClickListenerCallBack;
import com.weibo.R;
import com.weibo.Utils.BitmapCache;
import com.weibo.Utils.HttpUtil;
import com.weibo.Utils.RequestUrls;
import com.weibo.Utils.Utility;
import com.weibo.Utils.VolleyControl;
import com.weibo.View.DeliverGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 丶 on 2017/4/21.
 */

public class Star_FindFragment extends Fragment implements FindFragment.CallBack, onClickListenerCallBack {

    private static final String TAG = "TAG";

    private TextView textView_Hotwords;

    private LinearLayout linearLayout;
    private List<BlogData> blogDataList;
    private ListView listView;
    private BlogListAdapter adapter;
    private View headerView;
    /**
     * 记录listview点击坐标
     */
    private float oldX, oldY;
    private float currentX, currnetY;
    private int padding;
    private int height;

    private ViewGroup viewGroup;

    private VolleyControl volleyControl;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    /**
     * 热门微博页号
     */
    private int page;
    /**
     * 头部刷新控件
     */
    private LinearLayout linear_Refresh;
    private TextView textView_Refreash;
    private ImageView imageView_Refresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleyControl = VolleyControl.getInstance();
        requestQueue = volleyControl.getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, BitmapCache.getInstace());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.fragment_find_star, null);
        viewGroup = container;

        textView_Hotwords = (TextView) container.findViewById(R.id.hotwords);
        listView = (ListView) container.findViewById(R.id.listview);

        headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_listview_star, null);
        listView.addHeaderView(headerView);
        blogDataList = new ArrayList<BlogData>();
        adapter = new BlogListAdapter(getContext(), blogDataList, Star_FindFragment.this);
        listView.setAdapter(adapter);

        textView_Hotwords = (TextView) container.findViewById(R.id.hotwords);
        linearLayout = (LinearLayout) container.findViewById(R.id.linearLayout);

        linear_Refresh = (LinearLayout) container.findViewById(R.id.linear_refresh);
        textView_Refreash = new TextView(getContext());
        imageView_Refresh = new ImageView(getContext());

        /**
         * 隐藏下拉刷新控件
         */
        height = 150;
        padding = -height;
        viewGroup.setPadding(0, padding, 0, 0);

        page = 1;

        setListView();
        getDatas();


        return container;
    }

    public void setListView() {

        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int headerView_Top = 0;
                if (headerView != null) {
                    headerView_Top = listView.getChildAt(0).getTop();
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = event.getRawX();
                        oldY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取当前坐标
                        if (oldY == 0) {
                            oldY = event.getRawY();
                        }
                        currentX = event.getRawX();
                        currnetY = event.getRawY();
                        int sub = (int) (currnetY - oldY);
                        oldX = currentX;
                        oldY = currnetY;
                        if (headerView_Top == 0 || padding > -1 * height) {
//                            Log.i(TAG, "onTouch: " + linear_Refresh.getChildAt(0).hashCode() + " " + textView_Refreash.hashCode());
                            if (linear_Refresh.getChildAt(0) != textView_Refreash) {
                                imageView_Refresh.clearAnimation();
                                linear_Refresh.removeAllViews();
                                linear_Refresh.addView(textView_Refreash);
                            }
                            if (padding < 0) {
                                textView_Refreash.setText("下拉刷新");
                            } else {
                                textView_Refreash.setText("释放刷新");
                            }
                            viewGroup.setPadding(0, sub / 3 + padding, 0, 0);
                            padding = padding + sub / 3;
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        listView.setFastScrollEnabled(true);
                        linear_Refresh.removeAllViews();
                        if (padding >= 0) {
                            viewGroup.setPadding(0, 0, 0, 0);
                            imageView_Refresh.setImageResource(R.drawable.loading_01);
                            linear_Refresh.addView(imageView_Refresh);
                            //设置动画
                            Animation rotateAnimator = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                            imageView_Refresh.startAnimation(rotateAnimator);
                            getDatas();
                        } else {
                            padding = -height;
                            viewGroup.setPadding(0, padding, 0, 0);
                        }
                        oldY = 0;
                        break;
                }
                //事件未处理完
                return false;
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        BlogListAdapter.setIsListViewIDLE(true);
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "onScrollStateChanged: " + view.getFirstVisiblePosition() + " " + view.getLastVisiblePosition());
                        if (view.getLastVisiblePosition() >= view.getCount() - 2) {
                            View childView = view.getChildAt(view.getLastVisiblePosition() - view.getFirstVisiblePosition());
                            if (childView.getBottom() > view.getHeight()) {
                                break;
                            }
                        } else {
                            break;
                        }
                        getDatas();
                        break;
                    case SCROLL_STATE_FLING:
                        BlogListAdapter.setIsListViewIDLE(false);
                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    private void getDatas() {


        HttpUtil.sendJsonObjectRequest(RequestUrls.getStar_URL(), new HttpCallback() {
            @Override
            public void onFinish(JSONObject jsonObject) {
                try {
                    JSONArray cards = jsonObject.getJSONArray("cards");
                    JSONArray group = cards.getJSONObject(0).getJSONArray("pics");
//                    mapList = new ArrayList<Map<String, String>>();
                    linearLayout.removeAllViews();
                    for (int i = 0; i < group.length(); i++) {
                        String str = group.getJSONObject(i).getString("desc1");
                        String url = group.getJSONObject(i).getString("pic");
                        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(
                                getContext()).inflate(R.layout.item_header_gridview_listview_star, null);
                        ((NetworkImageView) (viewGroup.findViewById(R.id.img))).setImageUrl(url, imageLoader);
                        ((TextView) (viewGroup.findViewById(R.id.textview))).setText(str);
                        linearLayout.addView(viewGroup, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    }
                    //解析微博数据
                    blogDataList.addAll(0, Utility.handlemBlogDataResponse(cards));
                    adapter.notifyDataSetChanged();
                    if (padding > -height) {
                        imageView_Refresh.clearAnimation();
                        padding = -height;
                        viewGroup.setPadding(0, padding, 0, 0);
                    }
//                    page++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * HotWords数据获得接口回调
     */
    @Override
    public void onFinish(String HotWords) {
        textView_Hotwords.setText(textView_Hotwords.getText() + HotWords);
    }

    @Override
    public void GridViewItemClick(final View view, int listviewPos, int gridviewPos) {

        GridView gridView = (GridView) view;
        int position = gridviewPos;
        View v = gridView.getChildAt(gridviewPos);

        Intent intent = new Intent(getActivity(), PictureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("pic_url", (ArrayList<String>) blogDataList.get(listviewPos).getPic_url());
        bundle.putInt("position", gridviewPos);
        bundle.putString("activity", "MainActivity");
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
//        this.startActivity(intent, ActivityOptions.makeScaleUpAnimation(
//                v, v.getLeft(), v.getTop(), v.getLeft(), v.getTop()).toBundle());
//        this.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
//                getActivity(), gridView.getChildAt(gridviewPos).findViewById(R.id.img_gridview), "sharedView").toBundle());
    }

}
