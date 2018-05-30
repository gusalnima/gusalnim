package kr.co.gusalnim.template;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AlertDialogActivity extends Activity {


    private String notiMessage;
    private String link;
    private Handler handler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bun = getIntent().getExtras();
        if(null != bun && null != bun.getString("notiMessage")) {
            notiMessage = bun.getString("notiMessage");
        } else {
            notiMessage = null;
        }

        if(null != bun && null != bun.getString("notiLink")) {
            link = bun.getString("notiLink");
        } else {
            link = null;
        }


        setContentView(R.layout.activity_alertdialog);

        TextView adMessage = (TextView)findViewById(R.id.message);
        if(!StringUtils.isBlank(notiMessage)) adMessage.setText(notiMessage);

        Button adButton = (Button)findViewById(R.id.cancel);
        Button bdButton = (Button)findViewById(R.id.submit);

        adButton.setOnClickListener(new SubmitOnClickListener());
        bdButton.setOnClickListener(new SubmitOnClickListener());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(StringUtils.isEmpty(link) || StringUtils.equalsIgnoreCase("null", link)){
            bdButton.setVisibility(View.GONE);
        }

		/*handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				finish();
			}
		};
		handler.sendEmptyMessageDelayed(0, 5000);*/

    }

    private Map<String, Object> paramDisplayMap = new HashMap<String, Object>(); //  gacha map;

    private class SubmitOnClickListener implements View.OnClickListener {

        public void onClick(View v) {


            switch (v.getId()){
                case R.id.cancel:
                    finish();
                    break;
                case R.id.submit:
                    URI uri = null;
                    try {
                        uri = new URI(link);
                        if (uri.getScheme().equals("http") || uri.getScheme().equals("https")) {
                            final URI finalUri1 = uri;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(finalUri1)));
                            startActivity(intent);
                        } else if (uri.getScheme().equals("template")) {
                            final URI finalUri = uri;
                            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
                            sanitizer.setAllowUnregisteredParamaters(true);
                            sanitizer.parseUrl(String.valueOf(finalUri));
                            for (UrlQuerySanitizer.ParameterValuePair key : sanitizer.getParameterList()) {
                                String a = key.mParameter;
                                String b = key.mValue;
                                paramDisplayMap.put(a, b);
                            }
                            //ChatLinkHelper.linkGo(AlertDialogActivity.this, finalUri.getHost(), finalUri.getPath(), paramDisplayMap);

                        } else if (uri.getScheme().equals("tel")) {
                            final URI finalUri1 = uri;
                            Intent intentTelScg = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + finalUri1.getHost()));
                            startActivity(intentTelScg);
                        }
                        finish();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}