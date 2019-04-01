package com.smq.commerce.utils;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils;
    private RetrofitUtils(){}

    public static RetrofitUtils getInstance(){
        if (retrofitUtils==null){
            synchronized (RetrofitUtils.class){
                if (retrofitUtils==null){
                    retrofitUtils=new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    private static OkHttpClient okHttpClient;
    private static synchronized OkHttpClient getOkHttpClient(final String uid, final String sid) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("lj", "log: " + message);
            }
        });
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)

                .build();
        return okHttpClient;
    }

    private static synchronized OkHttpClient getOkHttpClientHead(final String uid, final String sid) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("lj", "log: " + message);
            }
        });
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request build = chain.request().newBuilder()
                                .addHeader("userId", uid)
                                .addHeader("sessionId", sid)
                                .build();
                        return chain.proceed(build);
                    }
                })
                .build();
        return okHttpClient;
    }

   // private static Retrofit retrofit;

    public static   Retrofit getRetrofit(String ShowUrl){
        Retrofit build = new Retrofit.Builder()
                .baseUrl(ShowUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return build;
    }

    public static   Retrofit getRetrofittttt(String ShowUrl){
        Retrofit build = new Retrofit.Builder()
                .baseUrl(ShowUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        return build;
    }

    //请求方式 带请求头
    public static Retrofit getretrofinHand(String url,String uid,String sid){
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClientHead(uid,sid))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return builder;
    }

    public <T>  T getApiService(String ShowUrl,Class<T> service){
       Retrofit retrofit=getRetrofit(ShowUrl);
        T t=retrofit.create(service);
        return t;
    }



    public <T>  T getApiServiceeeee(String ShowUrl,Class<T> service){
        Retrofit retrofit=getRetrofittttt(ShowUrl);
        T t=retrofit.create(service);
        return t;
    }

    public <T>T getserviserHand(String url,String uid,String sid,Class<T>service){
        Retrofit retrofit = getretrofinHand(url, uid, sid);
        T t = retrofit.create(service);
        return t;
    }
}
