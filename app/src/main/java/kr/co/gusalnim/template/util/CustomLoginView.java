package kr.co.gusalnim.template.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.gusalnim.template.R;

public class CustomLoginView extends LinearLayout {
    LinearLayout bg;
    ImageView symbol;
    TextView text;
    public CustomLoginView(Context context) {
        super(context);
        initView();
    }

    public CustomLoginView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomLoginView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs,defStyleAttr);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li= (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_login,this,false);
        addView(v);

        bg = findViewById(R.id.bg);
        symbol = findViewById(R.id.symbol);
        text = findViewById(R.id.text);
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.LoginButton);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.LoginButton,defStyle,0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray){

        int bg_resID = typedArray.getResourceId(R.styleable.LoginButton_bg, R.drawable.character_red);
        bg.setBackgroundResource(bg_resID);

        int symbol_ID = typedArray.getResourceId(R.styleable.LoginButton_symbol, R.drawable.menu_ic_cash_1);
        symbol.setImageResource(symbol_ID);

        int textColor = typedArray.getColor(R.styleable.LoginButton_textColor, 0);
        text.setTextColor(textColor);

        String text_string = typedArray.getString(R.styleable.LoginButton_text);
        text.setText(text_string);

        typedArray.recycle();
    }

    public void setBg(int bg_resID) {
        bg.setBackgroundResource(bg_resID);
    }

    public void setSymbol(int symbol_ID) {
        symbol.setImageResource(symbol_ID);
    }

    public void setText(String text_string) {
        text.setText(text_string);
    }

    public void setText(int text_resID) {
        text.setText(text_resID);
    }

    public void setTextColor(int textColor) {
        text.setTextColor(textColor);
    }
}
