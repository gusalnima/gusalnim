package kr.co.gusalnim.template;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.gusalnim.template.util.AlertHelper;
import kr.co.gusalnim.template.util.MessageHelper;
import kr.co.gusalnim.template.util.PermissionHelper;
import kr.co.gusalnim.template.util.PhoneNumberGetHelper;

import static android.media.AudioManager.RINGER_MODE_SILENT;

public class PopupPermissionAlertActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtTitle;
    private ImageButton btnBack;

    private Button btnPopup;
    private Button btnChoice;
    private Button btnPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_permission_alert);

        initTitle();
        initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            unregisterReceiver(smsBroadcastReceiver);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (MessageHelper.hasProgress()) {
            MessageHelper.closeProgress();
            return;
        }

        try {
            unregisterReceiver(smsBroadcastReceiver);
        } catch (Exception ignored) {
        }
    }

    private void initTitle() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("팝업&퍼미션&얼럿");
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    private void initUi() {

        btnPopup = findViewById(R.id.btnPopup);
        btnChoice = findViewById(R.id.btnChoice);
        btnPermission = findViewById(R.id.btnPermission);

        btnPopup.setOnClickListener(this);
        btnChoice.setOnClickListener(this);
        btnPermission.setOnClickListener(this);

        // 무음으로 바꾸기
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        // 권한 체크 폰번호 가져오기
        PhoneNumberGetHelper.getPhoneNumber(this, phoneNumber -> {
            if (phoneNumber.contains("+82")) {
                phoneNumber = phoneNumber.replace("+82", "0");
            }
            Log.i("gusalnim","phoneNumber : " + phoneNumber);
        });

        PhoneNumberGetHelper.getPhoneNumber(this, new PhoneNumberGetHelper.Callback() {
            @Override
            public void onData(String phoneNumber) {
                if (phoneNumber.contains("+82")) {
                    phoneNumber = phoneNumber.replace("+82", "0");
                }
                Log.i("gusalnim","phoneNumber : " + phoneNumber);
            }
        });


        loadData();
    }

    private void loadData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnPopup:
                AlertHelper.showMessage(
                        this,
                        BitmapFactory.decodeResource(getResources(), R.drawable.character_red),
                        "테스트 제목",
                        "테스트 내용",
                        new AlertHelper.Button(ContextCompat.getColor(PopupPermissionAlertActivity.this, R.color.text_4d4d4d), getString(R.string.label_enter), alertDialog -> {
                            alertDialog.cancel();
                        }),
                        new AlertHelper.Button(ContextCompat.getColor(PopupPermissionAlertActivity.this, R.color.colorPrimary), getString(R.string.label_enter))
                );
                break;
            case R.id.btnChoice:
                final String[] date_day = this.getResources().getStringArray(R.array.date_day);
                MessageHelper.bottomAlert(this, getString(R.string.label_choice), date_day, new MessageHelper.IBottomSheetItemClick() {
                    @Override
                    public void onClick(int position) {
                        Log.i("gusalnim",date_day[position]);
                    }


                });
                break;
            case R.id.btnPermission:
                checkPermission(new PermissionHelper.onPermissionListener() {
                    @Override
                    public void onPermission(String... permissions) {
                        registerReceiver(smsBroadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                        // 인증문자 받기 api 호출
                        //requestSmsAuth();
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        //requestSmsAuth();
                    }

                    @Override
                    public void onPermissionReject(String... permissions) {
                        //requestSmsAuth();
                    }
                }, Manifest.permission.READ_SMS);
                break;
        }
    }

    private final BroadcastReceiver smsBroadcastReceiver = new BroadcastReceiver() {
        private static final String TEL_1 = "15885788";
        private static final String TEL_2 = "1588-5788";

        @Override
        public void onReceive(Context context, Intent intent) {

            // 받은 문자에서 번호만 가져오기
            //if (null == editAuthNumber) return;

            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    if (null != smsMessage.getOriginatingAddress() && smsMessage.getOriginatingAddress().equals(TEL_1) || smsMessage.getOriginatingAddress().equals(TEL_2)) {
                        String body = smsMessage.getMessageBody().toString();

                        StringBuilder stringBuilder = new StringBuilder();
                        Pattern regex = Pattern.compile("\\d+");
                        Matcher regexMatcher = regex.matcher(body);
                        while (regexMatcher.find()) {
                            stringBuilder.append(regexMatcher.group());
                        }
                        //editAuthNumber.setText(stringBuilder.toString());
                    }
                }
            }
        }
    };
}
