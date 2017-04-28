package com.weibo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.weibo.Adapter.CommentListAdapter;
import com.weibo.Bean.BlogData;
import com.weibo.Bean.CommentData;
import com.weibo.Fragment.Trending_FindFragment;
import com.weibo.Interface.impl.HttpCallback;
import com.weibo.Interface.onClickListenerCallBack;
import com.weibo.R;
import com.weibo.Utils.HttpUtil;
import com.weibo.Utils.RequestUrls;
import com.weibo.Utils.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丶 on 2017/4/27.
 */

public class BlogActivity extends AppCompatActivity implements onClickListenerCallBack {

    private final static String TAG = "TAG";

    private ListView listView;
    private static View blogView;
    private View headerView;
    private BlogData blogData;

    private CommentListAdapter adapter;

    /**
     * 微博评论数据
     */
    private List<CommentData> commentDataList;

    /***/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        blogData = (BlogData) getIntent().getExtras().getSerializable("data");

        listView = (ListView) findViewById(R.id.listview);
        blogView = Trending_FindFragment.getItem();
        headerView = LayoutInflater.from(this).inflate(R.layout.header_activity_blog, null);
        listView.addHeaderView(blogView);
        listView.addHeaderView(headerView);

        commentDataList = new ArrayList<CommentData>();
        adapter = new CommentListAdapter(this, commentDataList, this);
        listView.setAdapter(adapter);

        getDatas();


    }

    private void getDatas() {

        String url = RequestUrls.getComment_Url() + "&id=" + blogData.getId() + "&lcardid=" + blogData.getId();
        HttpUtil.sendJsonObjectRequest(url, new HttpCallback() {

            @Override
            public void onFinish(JSONObject jsonObject) {
                commentDataList.addAll(Utility.handleCommentResponse(jsonObject));
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void GridViewItemClick(View view, int listviewPos, int gridviewPos) {

    }
}
