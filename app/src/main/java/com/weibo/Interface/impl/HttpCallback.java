package com.weibo.Interface.impl;

import android.graphics.Bitmap;

import com.weibo.Bean.TrendingTopData;
import com.weibo.Interface.HttpCallbackListener;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by 丶 on 2017/3/20.
 */

public class HttpCallback implements HttpCallbackListener {
    @Override
    public void onFinish(String Response) {

    }

    @Override
    public void onFinish(List<TrendingTopData> trendingTopDataList) {

    }

    @Override
    public void onFinish(JSONObject jsonObject) {

    }

    @Override
    public void onFinish(Bitmap bitmap) {

    }

    @Override
    public void onError(Exception e) {

    }
}
