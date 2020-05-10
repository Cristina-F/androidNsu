package ru.nsu.template.data.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// https://www.androidhive.info/RxJava/android-rxjava-networking-with-retrofit-gson-notes-app/
// https://github.com/Sanaebadi97/Github-Api-Single-User/blob/master/app/src/main/java/github/com/githubuser/api/RetrofitClient.kt
// https://medium.com/@guendouz/room-livedata-and-recyclerview-d8e96fb31dfe

// https://medium.com/@atifmukhtar/recycler-view-with-mvvm-livedata-a1fd062d2280

public class GithubApiClient {

    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;
    private static String BASE_URL = "https://api.github.com/";
    private static OkHttpClient okHttpClient;

    public static Retrofit getClient(Context context) {
        if (okHttpClient == null)
            initOkHttp(context);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
    }
}
