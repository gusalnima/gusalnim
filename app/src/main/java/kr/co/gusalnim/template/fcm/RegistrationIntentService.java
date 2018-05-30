package kr.co.gusalnim.template.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import kr.co.gusalnim.template.R;
import kr.co.gusalnim.template.data.UserData;

public class RegistrationIntentService extends IntentService {

    public RegistrationIntentService() {
        super(RegistrationIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("gusalnim","refreshedToken : ");
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.i("gusalnim","refreshedToken : " + refreshedToken);
            UserData.setUserMKey(this, refreshedToken);
        } catch (Exception ignored) {
        }
    }

}