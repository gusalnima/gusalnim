package kr.co.gusalnim.template.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView2 extends View {

    private static int CIRCLE_SIZE = 92;
    private int color;
    private Paint paint;

    private int strokeWidth = 20;
    private int strokeColor = 0xFFFF8C00;
    private float size = 20;

    public CircleView2(Context context) {
        super(context);
    }

    public CircleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(null == paint) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            //paint.setColor(color);
        }

        paint.setColor(color);

        float density = getResources().getDisplayMetrics().density;


        float width = canvas.getWidth();
        float height = canvas.getHeight();
        size = Math.min(width,height);

        size = density * CIRCLE_SIZE;


//		canvas.drawCircle((width-size/2), (height-size/2), size/2, paint);
        canvas.drawCircle(size/2,size/2, size/2, paint);
    }

    private Paint getStroke()
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(strokeWidth);
        p.setColor(strokeColor);
        p.setStyle(Paint.Style.STROKE);
        return p;
    }


    @Override
    public void setBackgroundColor(int color) {
        if(this.color == color) {
            return;
        }
        this.color = color;
        invalidate();
    }

    public void setSize(int circleSize){
        CIRCLE_SIZE = circleSize;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
