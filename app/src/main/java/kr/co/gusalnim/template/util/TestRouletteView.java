package kr.co.gusalnim.template.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import kr.co.gusalnim.template.R;

public class TestRouletteView extends View {

    private static int CIRCLE_SIZE = 92;

    private int color;
    private PointF circle;

    private Paint paint;

    private float size = 20;



    public TestRouletteView(Context context) {
        super(context);
        init(null, 0);
    }

    public TestRouletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TestRouletteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TestRouletteView, defStyle, 0);
        color = a.getColor(R.styleable.TestRouletteView_circleColor, Color.RED);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float density = getResources().getDisplayMetrics().density;

        float width = canvas.getWidth();
        float height = canvas.getHeight();
        size = Math.min(width,height);

        size = density * CIRCLE_SIZE;

        circle = new PointF(canvas.getWidth() / 2, canvas.getHeight()/2);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawCircle(size/2, size/2, size/2, paint);

    }
}
