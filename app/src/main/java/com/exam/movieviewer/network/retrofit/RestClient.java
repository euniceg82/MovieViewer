package com.exam.movieviewer.network.retrofit;

import com.exam.movieviewer.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {
    private static RestClient mRestClient;
    private APIService mAPIService;

    private static final int CONNECT_TIMEOUT_MILLIS = 120 * 1000; // 2m
    private static final int READ_TIMEOUT_MILLIS =  180 * 1000; // 3m

    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // try the request
                Response response = chain.proceed(request);

                int count = 1;
                while (!response.isSuccessful() && count < 3) {
                    count++;
                    // retry the request
                    response = chain.proceed(request);
                }
                // otherwise just pass the original response on
                return response;
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {
            builder.client(httpClient.build());
        }

        Retrofit retrofit = builder.build();

        mAPIService = retrofit.create(APIService.class);
    }

    public synchronized static RestClient getInstance() {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        }
        return mRestClient;
    }

    public APIService getmAPIService() {
        return mAPIService;
    }
}