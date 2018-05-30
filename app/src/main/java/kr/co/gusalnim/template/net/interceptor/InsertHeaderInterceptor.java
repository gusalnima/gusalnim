package kr.co.gusalnim.template.net.interceptor;

import android.content.Context;

import java.io.IOException;

import kr.co.gusalnim.template.data.UserData;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InsertHeaderInterceptor implements Interceptor {

    private Context context;

    public InsertHeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        builder.addHeader("X-VERSION", UserData.XVERSION);
        builder.addHeader("X-PLATFORM", UserData.XPLATFORM);
        builder.addHeader("X-API-VERSION", UserData.XAPIVERSION);
        String token = UserData.getUserToken(context);
        if (null != token) {
            builder.addHeader("X-TOKEN", token);
            //builder.addHeader("X-USRNO", String.valueOf(UserData.getUserId(context)));
        }

        return chain.proceed(builder.build());
    }

}