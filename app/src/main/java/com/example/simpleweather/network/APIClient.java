package com.example.simpleweather.network;

import android.content.Context;

import com.example.simpleweather.utils.Constants;
import com.example.simpleweather.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    private static final String BASE_URL = Constants.BASE_URL;
    private static APIClient mInstance;
    private Retrofit retrofit;

    private Context context;

    private APIClient(Context context) {

        this.context=context;

        File cachDir=new File(context.getCacheDir(),"http-cache");
        long cacheSize=10*1024*1024;
        Cache cache=new Cache(cachDir,cacheSize);

        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(logging)
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offLineInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


    }

    public static   synchronized APIClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new APIClient(context);
        }

        return mInstance;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }

    private   Interceptor  offLineInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {


                Request request=chain.request();
                if (!NetworkUtil.isNetworkConnected(context)){
                    CacheControl cacheControl=new CacheControl.Builder()
                            .maxStale(7,TimeUnit.DAYS)
                            .build();

                    request=request.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    private   Interceptor  networkInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {


                Response response=chain.proceed(chain.request());

                CacheControl cacheControl=new CacheControl.Builder()
                        .maxAge(7,TimeUnit.MINUTES)
                        .build();




                    response=response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control",cacheControl.toString())
                            .build();

                return response;
            }
        };
    }
}

