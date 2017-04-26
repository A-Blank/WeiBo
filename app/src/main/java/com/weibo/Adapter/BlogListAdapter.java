package com.weibo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.weibo.Bean.BlogData;
import com.weibo.Interface.impl.HttpCallback;
import com.weibo.Interface.onClickListenerCallBack;
import com.weibo.R;
import com.weibo.Utils.BitmapCache;
import com.weibo.Utils.ExecutorsControl;
import com.weibo.Utils.TextUtil;
import com.weibo.Utils.VolleyControl;
import com.weibo.View.MyGridView;
import com.weibo.View.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by 丶 on 2017/3/20.
 */

public class BlogListAdapter extends BaseAdapter {

    private Context mContext;
    private List<BlogData> mDatasList;
    private List<String> NoDataList = new ArrayList<String>();
    private LayoutInflater mInflater;

    private VolleyControl volleyControl;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private Bitmap bitmap;

    private BitmapCache cache;

    private SimpleAdapter simpleAdapter;

    /**
     * 线程池操作
     */
    private ExecutorsControl executorsControl;
    private ExecutorService executorService;

    private GridView gridView;

    private onClickListenerCallBack callBack;

    private static boolean IsListViewIDLE = true;

    private List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    private int[] imageId = new int[]{R.drawable.defult_pictrue, R.drawable.defult_pictrue, R.drawable.defult_pictrue,
            R.drawable.defult_pictrue, R.drawable.defult_pictrue, R.drawable.defult_pictrue,
            R.drawable.defult_pictrue, R.drawable.defult_pictrue, R.drawable.defult_pictrue};


    public BlogListAdapter(Context context, List<BlogData> datas, onClickListenerCallBack callBack) {
        mContext = context;
        mDatasList = datas;
        this.callBack = callBack;
        mInflater = LayoutInflater.from(context);
        volleyControl = VolleyControl.getInstance();
        requestQueue = volleyControl.getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, BitmapCache.getInstace());
        executorsControl = ExecutorsControl.getInstance();
        cache = BitmapCache.getInstace();
    }


    public static void setIsListViewIDLE(boolean IsListViewIDLE) {
        BlogListAdapter.IsListViewIDLE = IsListViewIDLE;
    }

    @Override
    public int getCount() {
        return mDatasList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_blog, null);
            viewHolder = new ViewHolder();
            viewHolder.user_pic = (RoundImageView) convertView.findViewById(R.id.img_pic);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textview_name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textview_time);
            viewHolder.device = (TextView) convertView.findViewById(R.id.textview_device);
            viewHolder.text = (TextView) convertView.findViewById(R.id.textview_text);
            viewHolder.gridView = (MyGridView) convertView.findViewById(R.id.gridview);
            viewHolder.adapter = new TopicPictureListAdapter(mContext, position);
            viewHolder.gridView.setAdapter(viewHolder.adapter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.user_pic.setImageUrl(mDatasList.get(position).getUser_pic_url(), imageLoader);
        viewHolder.name.setText(mDatasList.get(position).getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = new Date().getTime();
        long time = new Date(mDatasList.get(position).getTime()).getTime();
        String date = simpleDateFormat.format(new Date(time));
        viewHolder.time.setText(date);

        String[] str = mDatasList.get(position).getDevice().split(">");
        String device;
        if (str.length > 1) {
            device = str[1].substring(0, str[1].length() - 3);
            device = "来自: " + device;
        } else {
            device = "";
        }
        viewHolder.device.setText(device);

        SpannableString text = TextUtil.UrlHandle(mDatasList.get(position).getText());
        text = TextUtil.TopicHandle(text);
        viewHolder.text.setText(text);
        viewHolder.text.setMovementMethod(LinkMovementMethod.getInstance());

        /**
         * 对ListView嵌套GridView，双方的Adapter中先返回ListView中的convertView后返回GridView中的convertView
         * 造成ListView复用Item导致列表图片问题
         */
        mapList.clear();
        viewHolder.gridView.setTag(position);
        for (int i = 0; i < mDatasList.get(position).getPic_url().size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
//            getBitmap(mDatasList.get(position).getPic_url().get(i),bitmap,position);
            map.put("img", imageId);
            mapList.add(map);
        }
        simpleAdapter = new SimpleAdapter(mContext, mapList, R.layout.item_my_gridview, new String[]{"img"}, new int[]{R.id.img_gridview});
//        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//            @Override
//            public boolean setViewValue(View view, Object data, String textRepresentation) {
//                if (view instanceof ImageView && data instanceof Bitmap) {
//                    ImageView iv = (ImageView) view;
//                    iv.setImageBitmap((Bitmap) data);
//                    return true;
//                }
//                return false;
//            }
//        });
        viewHolder.gridView.setAdapter(simpleAdapter);

        //若ListVIew停止滑动，则更新数据加载图片
        if (IsListViewIDLE) {
            viewHolder.adapter.setGridView(viewHolder.gridView);
            viewHolder.adapter.setDatas(mDatasList.get(position).getPic_url());
            viewHolder.gridView.setAdapter(viewHolder.adapter);
            viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int gridviewPos, long id) {
                    callBack.GridViewItemClick(parent, position, gridviewPos);
                }

            });
        }
//        Log.i("TAG", "TopListAdapter");
        return convertView;
    }

//    public Bitmap getBitmap(final String url, Bitmap bitmap, int position) {
//
//        if (cache.getBitmap(url) != null) {
//            return cache.getBitmap(url);
//        }
////        final Bitmap[] bm = new Bitmap[1];
//        HttpUtil.sendImageRequest(url, new HttpCallback() {
//            @Override
//            public void onFinish(Bitmap b) {
//
//            }
//        });
//
//        return bitmap[0];
//    }

    class ViewHolder {
        private RoundImageView user_pic;
        private TextView name;
        private TextView time;
        private TextView device;
        private TextView text;
        private MyGridView gridView;
        private NetworkImageView icon;
        private TopicPictureListAdapter adapter;
    }

}
