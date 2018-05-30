package kr.co.gusalnim.template.fcm;

import android.app.Activity;
import android.content.Intent;


public class FCMHelper {
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static void init(Activity activity) {
            Intent intent = new Intent(activity, RegistrationIntentService.class);
            activity.startService(intent);
    }

    /*private static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                //gcm이 지원하나 버전이 낮아 업데이트 필요
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //gcm이 지원하지 않는 디바이스
            }
            return false;
        }
        return true;
    }*/
}
