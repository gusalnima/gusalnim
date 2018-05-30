package kr.co.gusalnim.template;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.co.gusalnim.template.util.PermissionHelper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 어날리틱스 적용할것.
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        // 하키앱 적용
        /*if (BuildConfig.BUILD_TYPE.equals("release")) {
        } else {
            checkForUpdates();
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 하키앱 적용
        /*if (BuildConfig.BUILD_TYPE.equals("release")) {
        } else {
            checkForCrashes();
        }*/
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 하키앱 적용
        /*if (BuildConfig.BUILD_TYPE.equals("release")) {
        } else {
            unregisterManagers();
        }*/
    }

    private void checkForCrashes() {
        //CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        if (BuildConfig.BUILD_TYPE.equals("release")) {
        } else {
            //UpdateManager.register(this);
        }
    }

    private void unregisterManagers() {
        //UpdateManager.unregister();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (BuildConfig.BUILD_TYPE.equals("release")) {
        } else {
            //unregisterManagers();
        }
    }

    @Override
    public void finish() {
        // 푸시로 왔을때 앱 메인으로 연결
        /*if (UserData.getIsPush(this)) {
            UserData.setPush(this, false);
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            Intent loadIntent = new Intent(MainActivity.LOCAL_BROADCAST_LOADINT_END);
            LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(loadIntent);
        }*/
        super.finish();
    }

    private static final int PERMISSION_REQUEST = 1024;

    private PermissionHelper.onPermissionListener onPermissionListener;

    public final void checkPermission(PermissionHelper.onPermissionListener onPermissionListener, String... permission) {
        this.onPermissionListener = onPermissionListener;
        if (null == onPermissionListener) return;

        if (PermissionHelper.checkPermission(this, PERMISSION_REQUEST, permission)) {
            onPermissionListener.onPermission(permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (null == onPermissionListener) return;

        if (requestCode == PERMISSION_REQUEST) {
            List<String> successList = new ArrayList<>();
            List<String> failList = new ArrayList<>();
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) successList.add(permissions[index]);
                else failList.add(permissions[index]);
            }

            if (grantResults.length == successList.size()) {
                onPermissionListener.onPermission(permissions);
            } else {
                if (!successList.isEmpty()) {
                    onPermissionListener.onPermission(successList.toArray(new String[successList.size()]));
                }

                List<String> deniedList = new ArrayList<>();
                List<String> rejectList = new ArrayList<>();
                for (String permission : failList) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) deniedList.add(permission);
                    else rejectList.add(permission);
                }

                if (!deniedList.isEmpty()) onPermissionListener.onPermissionDenied(deniedList.toArray(new String[deniedList.size()]));
                if (!rejectList.isEmpty()) onPermissionListener.onPermissionReject(rejectList.toArray(new String[rejectList.size()]));
            }
        }
    }

}