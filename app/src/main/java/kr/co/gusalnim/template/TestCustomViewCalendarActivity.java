package kr.co.gusalnim.template;

import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TestCustomViewCalendarActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtTitle;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_view_calendar);


        initTitle();
        initUi();
    }

    private void initTitle() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("달력");
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    private void initUi() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }
}

