package com.weibo.Fragment;

import android.animation.Animator;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.weibo.R;

/**
 * Created by 丶 on 2017/4/20.
 */

public class MainFragment extends Fragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        container = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);
        imageView = (ImageView) container.findViewById(R.id.img);

        Animation animator = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_main);
        imageView.setAnimation(animator);


        return container;
    }
}
