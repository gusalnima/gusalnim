package kr.co.gusalnim.template.net;

import android.content.Context;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import kr.co.gusalnim.template.data.Urls;
import kr.co.gusalnim.template.net.interceptor.InsertHeaderInterceptor;
import kr.co.gusalnim.template.net.interceptor.ReceivedCookiesInterceptor;
import kr.co.gusalnim.template.net.interceptor.UserAgentInterceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Api {
    public static <T> T getService(Context context, Class<T> className) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new InsertHeaderInterceptor(context))
                .addNetworkInterceptor(new UserAgentInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.API)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(className);
    }

    public interface AccountService {
        @GET("rest/profile")
        Call<String> profile();

        @FormUrlEncoded
        @POST("rest/sms/auth")
        Call<String> auth(
                @Field("name") String name,
                @Field("handphone") String handphone
        );

        @GET("rest/cash/authInfo/{joinApp}")
        Call<String> niceAuth(
                @Path("joinApp") String joinApp
        );

        @GET("rest/cash/summaryAndHistory")
        Call<String> cashSummary(
                @Query("endId") long endId,
                @Query("limit") int limit
        );

        @FormUrlEncoded
        @POST("rest/chatbot/result")
        Call<String> result(
                @FieldMap Map<String, Object> params
        );

        @Multipart
        @POST("rest/relay/indication/{useContractNum}/self/meter")
        Call<String> meter(
                @Path("useContractNum") String useContractNum,
                @Part("thisMonthIndicatorCustomer") String thisMonthIndicatorCustomer,
                @Part MultipartBody.Part file
        );
    }
}
