package com.whh.mymvvm.utils;

import android.util.Log;

import com.whh.mymvvm.bean.JsonUser;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * author:wuhuihui 2021.05.20
 * desc:网络请求配置
 */
public class HttpUtils {

    private static final int DEFAULT_TIMEOUT = 8; //连接超时的时间，单位：秒
    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static RetrofitInterface retrofitInterface;

    private synchronized static RetrofitInterface getRetrofit() {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        try {
                            Log.i(ContantUtils.loadDataTag, message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i(ContantUtils.loadDataTag, message);
                        }
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();


        //初始化retrofit的配置
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ContantUtils.URL_BASE)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }
        return retrofitInterface;
    }

    //获取数据
    public static Observable<JsonUser> getUserData() {
        return getRetrofit().getUserData();
    }

    public interface RetrofitInterface {
        @GET(ContantUtils.URL_PATH)
        Observable<JsonUser> getUserData();
    }
}
