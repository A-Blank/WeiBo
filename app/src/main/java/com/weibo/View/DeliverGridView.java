package com.weibo.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

/**
 * Created by 丶 on 2017/4/23.
 */

public class DeliverGridView extends GridView {

    private Paint paint;

    public DeliverGridView(Context context) {
        super(context);
        Init();
    }

    public DeliverGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public DeliverGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    public DeliverGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void Init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        View view = getChildAt(0);
        if (view == null) {
            return;
        }
        int column = getWidth() / view.getWidth();
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (count % (i + 1) != 0 || i == 0) {
                canvas.drawLine(childView.getRight(), childView.getTop() + 10, childView.getRight() + 0.5f, childView.getBottom() - 10, paint);
            } else if (count > i + column) {
                canvas.drawLine(10, childView.getBottom(), childView.getRight() - 10, childView.getBottom() + 0.5f, paint);
            }
        }

    }
}
