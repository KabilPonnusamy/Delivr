package sg.delivr.backend;


import sg.delivr.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 12/2/2017.
 */

public class RetrofitClient {
    private static RetrofitClient instance;
    private static Retrofit retrofit;
    private APIService apiService;

    public RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(40, TimeUnit.SECONDS);
        builder.writeTimeout(40, TimeUnit.SECONDS);
        builder.readTimeout(40, TimeUnit.SECONDS);


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(RewardsGson.getInstance()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();

        apiService = retrofit.create(APIService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public APIService getApiInterface() {
        return apiService;
    }

}
