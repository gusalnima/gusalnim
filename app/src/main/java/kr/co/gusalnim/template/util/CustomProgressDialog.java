package kr.co.gusalnim.template.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import kr.co.gusalnim.template.R;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
    }
}