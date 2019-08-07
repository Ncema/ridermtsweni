package com.rider.mtsweni;

import com.rider.mtsweni.service.SportsNewsServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiGateway {

    public static SportsNewsServices getSportsNewsCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constance.SPORTS_NEWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SportsNewsServices.class);
    }
}
