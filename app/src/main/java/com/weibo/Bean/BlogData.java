package com.weibo.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 丶 on 2017/3/22.
 */

public class BlogData implements Serializable{

    private String id;
    private String user_pic_url;
    private String name;
    private String time;
    private String device;
    private String text;
    private List<String> pic_url;
    private List<String> pic_original_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPic_original_url() {
        return pic_original_url;
    }

    public void setPic_original_url(List<String> pic_original_url) {
        this.pic_original_url = pic_original_url;
    }

    public List<String> getPic_url() {
        return pic_url;
    }

    public void setPic_url(List<String> pic_url) {
        this.pic_url = pic_url;
    }

    public String getUser_pic_url() {
        return user_pic_url;
    }

    public void setUser_pic_url(String user_pic_url) {
        this.user_pic_url = user_pic_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
