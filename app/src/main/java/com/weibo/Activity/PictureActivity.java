package com.weibo.Activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.weibo.Adapter.PictureViewPagerAdapter;
import com.weibo.R;
import com.weibo.Utils.BitmapCache;
import com.weibo.Utils.VolleyControl;
import com.weibo.View.MyImageView;
import com.weibo.View.SlideLayoutView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丶 on 2017/3/24.
 */

public class PictureActivity extends Activity implements ViewPager.OnPageChangeListener, SlideLayoutView.ScrollFinishCallback, MyImageView.CallBack {

    private ViewPager viewPager;
    private PictureViewPagerAdapter adapter;
    private List<MyImageView> viewList = new ArrayList<MyImageView>();

    private TextView textView_index;

    private List<String> list;
    private List<String> original_list;
    private ImageLoader imageLoader;

    private RequestQueue requestQueue;
    private VolleyControl volleyControl;
    private int position;

    private Transition transition;

    private Activity activity;
    private String activityName;

    private CallBack callBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        list = getIntent().getExtras().getStringArrayList("pic_url");
        original_list = getIntent().getExtras().getStringArrayList("original_pic_url");
        position = getIntent().getExtras().getInt("position");
        activityName = getIntent().getExtras().getString("activity");
        Init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAfterTransition();
    }

    public void Init() {

        transition = getWindow().getSharedElementExitTransition();

        //设置顶级布局无状态栏
//        if(Build.VERSION.SDK_INT > 16) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    |View.SYSTEM_UI_LAYOUT_FLAGS|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//            getWindow().getDecorView().setFitsSystemWindows(true);
//        }
//        this.getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        volleyControl = VolleyControl.getInstance();
        requestQueue = volleyControl.getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, BitmapCache.getInstace());

        for (int i = 0; i < original_list.size(); i++) {
            final String str_1 = list.get(i);
            final String str_2 = original_list.get(i);
            final MyImageView imageView = new MyImageView(this);
            imageView.setPosition(viewList.size());
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    int width = imageView.getWidth();
                    int height = imageView.getHeight();
                    imageView.setWidth(width);
                    imageView.setHeight(height);
                    imageView.InitBitmap(str_1);
                    imageView.InitBitmap(str_2);
                }
            });
            viewList.add(imageView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new PictureViewPagerAdapter(this, viewList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
        textView_index = (TextView) findViewById(R.id.textview_index);
        textView_index.setText(position + 1 + "/" + viewList.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        textView_index.setText(position + 1 + "/" + viewList.size());
        SlideLayoutView.setCurrentPos(position);
        boolean flag = viewList.get(position).IsLargePicture();
        SlideLayoutView.setSlideEnable(!flag);

        /**
         * 图片位置改变接口回调通知Activity改变相应参数
         */
//        this.position = position;
//        callBack.pageChanged(position);
//        int betyCounts=((BitmapDrawable)((NetworkImageView)viewList.get(position)).getDrawable()).getBitmap().getByteCount();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onFinish() {
        finishAfterTransition();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//             finish();
//                overridePendingTransition(0, 0);
//            }
//        }, 666);
    }

    @Override
    public void onClick() {
        finish();
        overridePendingTransition(0, R.anim.zoom_out);
    }

    public interface CallBack {
        void pageChanged(int position);
    }

}
