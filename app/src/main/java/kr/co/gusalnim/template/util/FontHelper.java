package kr.co.gusalnim.template.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import kr.co.gusalnim.template.R;

public class FontHelper {
    private static Typeface nomal;

    public static Typeface getTypeface(Context context) {
        if (null == nomal) {
            nomal = ResourcesCompat.getFont(context, R.font.nanum_barun_gothic_r);
        }
        return nomal;
    }

    private static Typeface bold;

    public static Typeface getBoldTypeface(Context context) {
        if (null == bold) {
            bold = ResourcesCompat.getFont(context, R.font.nanum_barun_gothic_b);
        }

        return bold;
    }
}
