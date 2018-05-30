package kr.co.gusalnim.template;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import kr.co.gusalnim.template.util.AlertHelper;
import kr.co.gusalnim.template.util.BadgeHelper;

public class IntroActivity extends BaseActivity {

    private String token;

    @Override
    protected void onStart() {
        super.onStart();

        if (!isNetworkConnected()) {
            AlertHelper.showMessage(
                    this,
                    null,
                    //BitmapFactory.decodeResource(getResources(), R.drawable.character_red),
                    "popupTitle",
                    "popupText",
                    //getString(R.string.common_error_22),
                    new AlertHelper.Button(ContextCompat.getColor(this, R.color.colorPrimary), getString(R.string.label_enter), new AlertHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialog alertDialog) {
                            //확인 버튼 눌렀을때
                            finish();
                            alertDialog.cancel();
                        }
                    })
            );
            return;
        } else {

        }

        BadgeHelper.sendBroadcast(this, 0);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}