package kr.co.gusalnim.template.net.interceptor;

import android.os.Build;

import java.io.IOException;

import kr.co.gusalnim.template.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String devicesInfo = "";
        try {
            devicesInfo = Build.MODEL + "; " + Build.VERSION.RELEASE;
        } catch (Exception ignored) {
        }

        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
                .header("User-Agent", "Msc/" + BuildConfig.VERSION_NAME + " (" + devicesInfo + ")")
                .build();

        return chain.proceed(requestWithUserAgent);
    }

}