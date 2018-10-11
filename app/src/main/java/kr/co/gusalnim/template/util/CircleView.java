package kr.co.gusalnim.template.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import kr.co.gusalnim.template.R;

public class CircleView extends View {

    private int strokeWidth = 20;
    private int strokeColor = 0xFFFF8C00;
    private int fillColor = 0XFFFFAB00;
    private int circleRadius = 20;
    private int circleGap = 20;


    private int color;
    private Paint paint;
    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        getAttrs(attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(attrs,defStyleAttr);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleView,defStyleAttr,0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray){
        strokeColor = typedArray.getColor(R.styleable.CircleView_strokeColor, strokeColor);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleView_strokeWidth, strokeWidth);
        fillColor = typedArray.getColor(R.styleable.CircleView_fillColor, fillColor);
        circleRadius = typedArray.getDimensionPixelSize(R.styleable.CircleView_circleRadius, circleRadius);
        circleGap = typedArray.getDimensionPixelSize(R.styleable.CircleView_circleGap, circleGap);

        typedArray.recycle();
    }

    private void init() {
        Log.i("gusalnim","?????");
        //this.setMinimumWidth(circleRadius*2 + strokeWidth);
        //this.setMinimumHeight(circleRadius*2 + strokeWidth);
        //this.setSaveEnabled(true);


    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = this.getWidth();
        int h = this.getHeight();

        int ox = w/2;
        int oy = h/2;

        canvas.drawCircle(ox, oy, circleRadius, getStroke());
        canvas.drawCircle(ox, oy, circleRadius - circleGap, getFill());
    }

    private Paint getStroke()
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(strokeWidth);
        p.setColor(strokeColor);
        p.setStyle(Paint.Style.STROKE);
        return p;
    }

    private Paint getFill()
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(fillColor);
        p.setStyle(Paint.Style.FILL);
        return p;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
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

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getCircleGap() {
        return circleGap;
    }

    public void setCircleGap(int circleGap) {
        this.circleGap = circleGap;
    }
}
