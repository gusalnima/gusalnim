package kr.co.gusalnim.template.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
    // pixel -> dp
    public static int DPFromPixel(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

	/*public static int ConvertPixelToDp(int pixels) {
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		return ((int) (pixels / displayMetrics.density));
	}*/


    // dp -> px
    public static int PixelFromDP(Context ctx, int dp) {
        return (int) (dp * ctx.getResources().getDisplayMetrics().density);
    }



}
