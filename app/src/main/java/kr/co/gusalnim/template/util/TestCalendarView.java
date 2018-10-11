package kr.co.gusalnim.template.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import kr.co.gusalnim.template.R;

public class TestCalendarView extends View {

    private int color;
    private int dimmedColor;
    private Calendar currentDay;

    private Paint paint;
    private TextPaint textPaint;

    private int colorEnabled;

    private PointF btnPrev;
    private PointF btnNext;
    private ArrayList<PointF> btnDays;

    public TestCalendarView(Context context) {
        super(context);
        init(null, 0);
    }

    public TestCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TestCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TestCalendarView, defStyle, 0);
        color = a.getColor(R.styleable.TestCalendarView_bgColor, Color.GRAY);
        a.recycle();

        // dimmed color 구하기
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        dimmedColor = Color.HSVToColor(hsv);

        // color
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        // text color
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);

        // 현재 날짜
        currentDay = Calendar.getInstance();
        currentDay.clear();
        currentDay.setTimeInMillis(System.currentTimeMillis()); // 기기의 현재 시간
        currentDay.set(Calendar.DAY_OF_MONTH, 1);

        btnDays = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(color);

        onDrawTitle(getContext(), canvas);

        onDrawDate(getContext(), canvas);
    }

    private void onDrawTitle(Context context, Canvas canvas) {
        final float density = getResources().getDisplayMetrics().density;

        // 년도 그리기
        int year = currentDay.get(Calendar.YEAR);
        Rect textRect = new Rect();
        textPaint.setTextAlign(Paint.Align.CENTER); // 중앙정렬
        textPaint.setColor(Color.WHITE);
        //textPaint.setTypeface(FontHelper.getTypeface(context)); // 기본폰트는 style 에 적용했으나 커스텀은 이렇게 적용해줘야함.
        textPaint.setTextSize(20 * density);
        textPaint.getTextBounds("2016", 0, 4, textRect);
        float y = textRect.height() + 12 * density;
        canvas.drawText(year + "", canvas.getWidth() / 2, y, textPaint);// 해당 위치에 그리기

        // 월 그리기
        int month = currentDay.get(Calendar.MONTH) + 1;
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(54 * density);
        textPaint.getTextBounds("12월", 0, 3, textRect);
        y += textRect.height() + 6 * density;
        canvas.drawText(month + "월", canvas.getWidth() / 2, y, textPaint);

        // 좌우 원 그리기 pointF 로 점을 찍은 후 radius 값으로 크기 구해서 칠하는 방법
        float padding = 26.5f * density;
        paint.setColor(dimmedColor);
        y -= 15 * density;
        btnPrev = new PointF(padding, y);
        canvas.drawCircle(btnPrev.x, btnPrev.y, 15 * density, paint);
        btnNext = new PointF(canvas.getWidth() - padding, y);
        canvas.drawCircle(btnNext.x, btnNext.y, 15 * density, paint);
        //canvas.drawRect(0,0, 50*density, 50*density,paint);// 사각형

        // 좌우 원 안에 화살표 그리기
        Path path = new Path();
        path.moveTo(padding + 3 * density, y - 7 * density);
        path.lineTo(padding - 4 * density, y);
        path.lineTo(padding + 3*density, y + 7 * density);

        path.moveTo(canvas.getWidth() - padding - 3 * density, y - 7 * density);
        path.lineTo(canvas.getWidth() - padding + 4 * density, y);
        path.lineTo(canvas.getWidth() - padding - 3*density, y + 7 * density);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.5f *density);
        canvas.drawPath(path,paint);

    }

    private void onDrawDate(Context context, Canvas canvas) {
        final long time = currentDay.getTimeInMillis();
        final float density = getResources().getDisplayMetrics().density;

        //일 ~ 토
        float padding = 26.5f * density;
        float monthGap = (canvas.getWidth() - (padding*2)) / 6;
        textPaint.setTextSize(13f*density);
        float dayY = 118 *density;
        String[] days = {"일", "월", "화", "수", "목", "금", "토"};
        float[] xStep = new float[7];
        for(int index=0; index<days.length; index++) {
            if(index == 0) {
                xStep[0] = padding;
                textPaint.setColor(dimmedColor);
            } else {
                textPaint.setColor(Color.WHITE);
                xStep[index] = padding + monthGap * index;
            }
            canvas.drawText(days[index], xStep[index], dayY, textPaint);
        }

        // 1 ~ 31
        btnDays.clear();
        String key;
        Rect textRect = new Rect();
        paint.setColor(dimmedColor);
        textPaint.setTextSize(14f * density);
        textPaint.getTextBounds("8",0,1,textRect);
        final int year = currentDay.get(Calendar.YEAR);
        final int month = currentDay.get(Calendar.MONTH);
        int xStepIndex;
        float x, y;
        int day;
        for (int index = 0; index < 31; index++){
            xStepIndex = currentDay.get(Calendar.DAY_OF_WEEK) - 1;
            x= xStep[xStepIndex];
            y = (157*density) + ((currentDay.get(Calendar.WEEK_OF_MONTH) - 1) * 40 * density);
            day = currentDay.get(Calendar.DAY_OF_MONTH);

            key = year + getNum(month + 1) + getNum(day);
            btnDays.add(new PointF(x,y));
            //Log.i();
            //Log.i("gusalnim","xStepIndex : " + xStepIndex + " / x : " + x + " / y : " + y + " / day : " + day + " / key : " + key + " / new PointF(x,y) : " + btnDays);
            /*if( null == enabledList) {

            }*/
            ArrayList<Integer> colorList = new ArrayList<>();
            colorList.add(colorEnabled);
            onDrawDay(canvas, day + "" , key, new PointF(x,y),colorList);
            currentDay.set(Calendar.DAY_OF_MONTH, day + 1);
            if(currentDay.get(Calendar.MONTH) != month) return;;
        }
        currentDay.setTimeInMillis(time);
    }

    private String getNum(int num){
        String result = num + "";
        if(result.length() == 1) result = "0" + result;
        return result;
    }

    private void onDrawDay(Canvas canvas, String day, String key, PointF point, ArrayList<Integer> colorList) {
        canvas.drawText(day,point.x,point.y,textPaint);
    }
}
