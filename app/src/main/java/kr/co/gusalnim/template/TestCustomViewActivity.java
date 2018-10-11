package kr.co.gusalnim.template;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.co.gusalnim.template.util.CircleView;
import kr.co.gusalnim.template.util.CircleView2;
import kr.co.gusalnim.template.util.CustomLoginView;

public class TestCustomViewActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtTitle;
    private ImageButton btnBack;
    private CircleView circleView;
    private CircleView2 circleView2;

    private CustomLoginView kakao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_view);

        initTitle();
        initUi();
    }

    private void initTitle() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("커스텀뷰기초");
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    private void initUi() {
        kakao = findViewById(R.id.kakao);
        kakao.setBg(R.drawable.loading_05);
        circleView2 = findViewById(R.id.circleView2);
        circleView2.setSize(90);
        circleView2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        circleView = findViewById(R.id.circleView);
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
