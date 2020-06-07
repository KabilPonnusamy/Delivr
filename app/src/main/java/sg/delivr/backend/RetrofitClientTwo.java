package sg.delivr.backend;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.delivr.BuildConfig;

/**
 * Created by Admin on 12/2/2017.
 */

public class RetrofitClientTwo {
    private static RetrofitClientTwo instance;
    private static Retrofit retrofit;
    private APIService apiService;

    public RetrofitClientTwo() {
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
                .baseUrl("https://delivr.sg/DelivrTheme/")
                .build();

        apiService = retrofit.create(APIService.class);
    }

    public static RetrofitClientTwo getInstance() {
        if (instance == null) {
            instance = new RetrofitClientTwo();
        }
        return instance;
    }

    public APIService getApiInterface() {
        return apiService;
    }
}
