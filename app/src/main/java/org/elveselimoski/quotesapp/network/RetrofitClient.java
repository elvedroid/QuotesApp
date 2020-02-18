package org.elveselimoski.quotesapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            createRetrofit();
        }
        return retrofit;
    }

    private static void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://programming-quotes-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
