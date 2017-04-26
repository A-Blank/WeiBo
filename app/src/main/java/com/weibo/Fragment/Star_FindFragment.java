package com.weibo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weibo.R;

/**
 * Created by 丶 on 2017/4/21.
 */

public class Star_FindFragment extends Fragment implements FindFragment.CallBack {

    private static final String TAG = "TAG";

    private TextView textView_Hotwords;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.fragment_find_star, null);
        textView_Hotwords= (TextView) container.findViewById(R.id.hotwords);
        Log.i(TAG, "onCreateView: Start_FindFragment");
        return container;
    }

    @Override
    public void onFinish(String HotWords) {
        textView_Hotwords.setText(textView_Hotwords.getText()+HotWords);
    }
}
