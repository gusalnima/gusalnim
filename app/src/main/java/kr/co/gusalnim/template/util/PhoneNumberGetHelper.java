package kr.co.gusalnim.template.util;

import android.Manifest;
import android.content.Context;
import android.telephony.TelephonyManager;

import kr.co.gusalnim.template.BaseActivity;

public class PhoneNumberGetHelper {
    public static void getPhoneNumber(BaseActivity activity, Callback callback) {
        activity.checkPermission(new PermissionHelper.onPermissionListener() {
            @Override
            public void onPermission(String... permission) {
                TelephonyManager telephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                String phoneNumber = "";

                try {
                    if (telephony.getLine1Number() != null) {
                        phoneNumber = telephony.getLine1Number();
                    } else {
                        if (telephony.getSimSerialNumber() != null) {
                            phoneNumber = telephony.getSimSerialNumber();
                        }
                    }
                } catch (Exception ignored) {
                }

                callback.onData(phoneNumber);
            }

            @Override
            public void onPermissionDenied(String... permission) {
            }

            @Override
            public void onPermissionReject(String... permission) {
            }
        }, Manifest.permission.READ_PHONE_STATE);
    }

    public interface Callback {
        void onData(String phoneNumber);
    }
}