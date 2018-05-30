package kr.co.gusalnim.template.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class BadgeHelper {

    public static final String ACTION = "android.intent.action.BADGE_COUNT_UPDATE";

    public static void sendBroadcast(Context context, long count) {
        int data = Integer.MAX_VALUE < count ? Integer.MAX_VALUE : (int) count;

        String packageName = context.getPackageName();
//		Log.w("ROOEX", packageName + " : " + data);

        Intent badgeIntent = new Intent(ACTION);
        badgeIntent.putExtra("badge_count", data);
        badgeIntent.putExtra("badge_count_package_name", packageName);
        badgeIntent.putExtra("badge_count_class_name", "kr.co.gusalnim.template.IntroActivity");
        context.sendBroadcast(badgeIntent);

        //local
        Intent localIntent = new Intent(ACTION);
        localIntent.putExtra("badge_count", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
    }

}