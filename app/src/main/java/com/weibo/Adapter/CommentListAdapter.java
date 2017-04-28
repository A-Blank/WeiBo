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
import com.weibo.Bean.CommentData;
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

public class CommentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentData> mDatasList;
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


    public CommentListAdapter(Context context, List<CommentData> datas, onClickListenerCallBack callBack) {
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
        CommentListAdapter.IsListViewIDLE = IsListViewIDLE;
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
            convertView = mInflater.inflate(R.layout.item_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.user_pic = (RoundImageView) convertView.findViewById(R.id.img_pic);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textview_name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textview_time);
            viewHolder.text = (TextView) convertView.findViewById(R.id.textview_text);
            viewHolder.like_counts = (TextView) convertView.findViewById(R.id.textview_like_counts);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.user_pic.setImageUrl(mDatasList.get(position).getUser_pic_url(), imageLoader);
        viewHolder.name.setText(mDatasList.get(position).getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = new Date(mDatasList.get(position).getTime()).getTime();
        String date = simpleDateFormat.format(new Date(time));
        viewHolder.time.setText(date);

        SpannableString text = TextUtil.UrlHandle(mDatasList.get(position).getText());
        text = TextUtil.TopicHandle(text);
        viewHolder.text.setText(text);
        viewHolder.text.setMovementMethod(LinkMovementMethod.getInstance());

        int like_counts = mDatasList.get(position).getLike_counts();
        if (like_counts == 0) {
            viewHolder.like_counts.setText("");
        } else {
            viewHolder.like_counts.setText(mDatasList.get(position).getLike_counts() + "");
        }
        return convertView;
    }


    class ViewHolder {
        private RoundImageView user_pic;
        private TextView name;
        private TextView time;
        private TextView text;
        private TextView like_counts;
    }

}
