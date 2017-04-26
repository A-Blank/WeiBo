package com.weibo.Utils;

import com.weibo.Bean.BlogData;
import com.weibo.Bean.TrendingTopData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丶 on 2017/3/19.
 */

public class Utility {

    /**
     * 解析和处理服务器返回的实时热搜榜数据
     */
    public synchronized static List<TrendingTopData> handleTrendingTopResponse(final JSONObject response) {

        if (response != null) {
            try {
                JSONArray jsonArray = response.getJSONArray("cards");
                JSONObject Trendind_Title = jsonArray.getJSONObject(0);

                JSONObject jsonObject = jsonArray.getJSONObject(1);
                /**
                 * 获取实时热搜数据
                 */
                JSONArray array = jsonObject.getJSONArray("card_group");
                List<TrendingTopData> list = new ArrayList<TrendingTopData>();
                for (int i = 0; i < array.length(); i++) {
                    TrendingTopData data = new TrendingTopData();
                    String pic_url = array.getJSONObject(i).getString("pic");
                    String desc = array.getJSONObject(i).getString("desc");
                    String desc_ext = array.getJSONObject(i).getString("desc_extr");
                    String icon_url = array.getJSONObject(i).getString("icon");
                    data.setPicture_Url(pic_url);
                    data.setDescribe(desc);
                    data.setDescribe_Extra(desc_ext);
                    data.setIcon_Url(icon_url);
                    list.add(data);
                }
                return list;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析和处理服务器返回的微博数据
     */
    public synchronized static List<BlogData> handlemBlogDataResponse(final JSONArray jsonArray) {

        if (jsonArray != null) {
            try {

                /**
                 * 获取实时热搜数据
                 */
                List<BlogData> list = new ArrayList<BlogData>();
                for (int i = 1; i < jsonArray.length(); i++) {
                    if (!jsonArray.getJSONObject(i).has("show_type")) {
                        continue;
                    }
                    BlogData data = new BlogData();
                    List<String> urlList = new ArrayList<String>();
                    JSONObject object = jsonArray.getJSONObject(i).getJSONObject("mblog");
                    String user_pic_url = object.getJSONObject("user").getString("profile_image_url");
                    String name = object.getJSONObject("user").getString("name");
                    String time = object.getString("created_at");
                    String device = object.getString("source");
                    String text = object.getString("text");
                    if (object.has("pic_ids")) {
                        JSONArray pic = object.getJSONArray("pic_ids");
                        for (int k = 0; k < pic.length(); k++) {
                            JSONObject pic_infos = object.getJSONObject("pic_infos");
                            String url = pic_infos.getJSONObject(pic.getString(k)).getJSONObject("original").getString("url");
                            urlList.add(url);
                        }
                    }
                    data.setUser_pic_url(user_pic_url);
                    data.setName(name);
                    data.setTime(time);
                    data.setDevice(device);
                    data.setText(text);
                    data.setPic_url(urlList);
                    list.add(data);

                }
                return list;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析和处理服务器返回的热门微博数据
     */
    public synchronized static List<BlogData> handleHotTopicResponse(final JSONObject response) {

        if (response != null) {
            try {
                JSONArray jsonArray = response.getJSONArray("cards");

                /**
                 * 获取实时热搜数据
                 */
                List<BlogData> list = new ArrayList<BlogData>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (!jsonArray.getJSONObject(i).has("show_type")) {
                        continue;
                    }
//                    if (!jsonArray.getJSONObject(i).has("card_group")) {
//                        continue;
//                    }
                    JSONObject card_detail = jsonArray.getJSONObject(i);
                    if (!card_detail.has("mblog")) {
                        continue;
                    }
                    BlogData data = new BlogData();
                    List<String> urlList = new ArrayList<String>();
                    JSONObject object = card_detail.getJSONObject("mblog");
                    String user_pic_url = object.getJSONObject("user").getString("profile_image_url");
                    String name = object.getJSONObject("user").getString("name");
                    String time = object.getString("created_at");
                    String device = object.getString("source");
                    String text = object.getString("text");
                    if (object.has("pic_ids")) {
                        JSONArray pic = object.getJSONArray("pic_ids");
                        for (int k = 0; k < pic.length(); k++) {
                            JSONObject pic_infos = object.getJSONObject("pic_infos");
                            String url = pic_infos.getJSONObject(pic.getString(k)).getJSONObject("original").getString("url");
                            urlList.add(url);
                        }
                    }
                    data.setUser_pic_url(user_pic_url);
                    data.setName(name);
                    data.setTime(time);
                    data.setDevice(device);
                    data.setText(text);
                    data.setPic_url(urlList);
                    list.add(data);
                }

                return list;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析和处理服务器返回的热门词语数据
     */
    public synchronized static String handleHotWordsResponse(final JSONObject response) {

        if (response != null) {
            try {
                JSONArray jsonArray = response.getJSONArray("hotwords");

                /**
                 * 获取实时热搜数据
                 */
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String words = jsonObject.getString("word");
                return words;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
