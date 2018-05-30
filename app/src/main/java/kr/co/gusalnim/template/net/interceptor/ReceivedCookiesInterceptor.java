package kr.co.gusalnim.template.net.interceptor;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import kr.co.gusalnim.template.data.UserData;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }

            if (!cookies.isEmpty()) {
                UserData.setCookie(context, cookies);
            }
        }
        return response;
    }
}
