package kr.co.gusalnim.template;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TestCustomViewRoundActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtTitle;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_view_round);

        initTitle();
        initUi();
    }

    private void initTitle() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("라운드뷰");
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