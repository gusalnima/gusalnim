package kr.co.gusalnim.template.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.Set;

import kr.co.gusalnim.template.BuildConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserData {

    private static final String TITLE = UserData.class.getSimpleName();

    public static final String XVERSION = BuildConfig.VERSION_NAME;
    public static final String XPLATFORM = "0";
    public static final String XAPIVERSION = "2";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(TITLE, Activity.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    // cookie
    private static final String COOKIE = "cookie";

    public static Set<String> getCookie(Context context) {
        return getSharedPreferences(context).getStringSet(COOKIE, null);
    }

    public static void setCookie(Context context, Set<String> strings) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putStringSet(COOKIE, strings);
        editor.commit();
    }


    // api 호출시 사용되는 token
    private static final String USER_TOKEN = "userToken";

    public static String getUserToken(Context context) {
        String token = getSharedPreferences(context).getString(USER_TOKEN, null);

        if (null != token) {
            return token;
        } else {
            Set<String> cookie = getCookie(context);
            if (null != cookie) {
                for (String string : cookie) {
                    if (string.contains("TOKEN=")) {
                        return string.substring(6, string.indexOf(";"));
                    }
                }
            }
        }
        return null;
    }

    public static void setUserToken(Context context, String token) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(USER_TOKEN, token);
        editor.commit();

        String mKey = getUserMKey(context);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext());
                    String adid = null;
                    if (!advertisingIdInfo.isLimitAdTrackingEnabled())
                        adid = advertisingIdInfo.getId();
                   /* Api.getService(context, Api.AccountService.class).mkey(mKey, adid).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                        }
                    });*/
                } catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //fcm token
    private static final String USER_M_KEY = "userMKey";

    public static String getUserMKey(Context context) {
        return getSharedPreferences(context).getString(USER_M_KEY, null);
    }

    public static void setUserMKey(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(USER_M_KEY, value);
        editor.commit();

        // adid 가져오기
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext());
                    String adid = null;
                    if (!advertisingIdInfo.isLimitAdTrackingEnabled())
                        adid = advertisingIdInfo.getId();

                    /*Api.getService(context, Api.AccountService.class).mkey(value, adid).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                        }
                    });*/
                } catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
