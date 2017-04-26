package com.weibo.Interface;

import android.graphics.Bitmap;


import com.weibo.Bean.TrendingTopData;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by 丶 on 2017/3/19.
 */

public interface HttpCallbackListener {

    void onFinish(String Response);

    void onFinish(List<TrendingTopData> trendingTopDataList);

    void onFinish(JSONObject jsonObject);

    void onFinish(Bitmap bitmap);

    void onError(Exception e);


}
