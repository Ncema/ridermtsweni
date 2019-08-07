package com.rider.mtsweni.repository;

import android.annotation.SuppressLint;
import android.content.Context;

import com.rider.mtsweni.ApiGateway;
import com.rider.mtsweni.Constance;
import com.rider.mtsweni.core.Content;
import com.rider.mtsweni.pojo.SportArticleResponseModel;
import com.rider.mtsweni.pojo.SportNewsResponseModel;
import com.rider.mtsweni.service.SportsNewsServices;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsNewsRepository {

    @SuppressLint("StaticFieldLeak")
    private static SportsNewsRepository ourInstance;
    private SportsNewsServices mSportsNewsServices;
    private MutableLiveData<List<SportNewsResponseModel>> sportNewsResponseModelLiveData;
    private List<SportNewsResponseModel> body;
    private SportArticleResponseModel articleBody;
    private MutableLiveData<SportArticleResponseModel> sportArticleResponseModelLiveData;
    private MutableLiveData<Content> networkFailure;

    public static SportsNewsRepository getInstance(Context context) {
        if (ourInstance == null) ourInstance = new SportsNewsRepository(context);
        return ourInstance;
    }

    public SportsNewsRepository(Context context) {

        mSportsNewsServices = ApiGateway.getSportsNewsCall();
        sportNewsResponseModelLiveData = new MutableLiveData<>();
        sportArticleResponseModelLiveData = new MutableLiveData<>();
        networkFailure = new MutableLiveData<>();
    }

    public void getSportNews() {
        clearLiveData();
        Call<List<SportNewsResponseModel>> apiCall = mSportsNewsServices.getSportsNews();
        apiCall.enqueue(new Callback<List<SportNewsResponseModel>>() {
            @Override
            public void onResponse(Call<List<SportNewsResponseModel>> call, Response<List<SportNewsResponseModel>> response) {

                if (response.code() == 200) {
                    body = response.body();
                    sportNewsResponseModelLiveData.postValue(body);
                }
            }

            @Override
            public void onFailure(Call<List<SportNewsResponseModel>> call, Throwable t) {
                networkFailure.postValue(new Content(Content.Status.FAILED, Constance.NETWORK_ISSUES));
            }
        });

    }

    public MutableLiveData<List<SportNewsResponseModel>> getSportNewsResponseModeLiveData() {
        return sportNewsResponseModelLiveData;
    }

    public void getSportsArticles(String siteName, String urlName, String urlFriendlyDate, String urlFriendlyHeadline) {
        clearLiveData();
        Call<SportArticleResponseModel> apiCall = mSportsNewsServices.getSportsArticles(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);
        apiCall.enqueue(new Callback<SportArticleResponseModel>() {
            @Override
            public void onResponse(Call<SportArticleResponseModel> call, Response<SportArticleResponseModel> response) {

                if (response.code() == 200) {
                    articleBody = response.body();
                    sportArticleResponseModelLiveData.postValue(articleBody);
                }
            }

            @Override
            public void onFailure(Call<SportArticleResponseModel> call, Throwable t) {
                networkFailure.postValue(new Content(Content.Status.FAILED, Constance.NETWORK_ISSUES));
            }
        });

    }

    public MutableLiveData<SportArticleResponseModel> getSportArticleResponseModeLiveData() {
        return sportArticleResponseModelLiveData;
    }

    public MutableLiveData<Content> getNetworkFailure() {
        return networkFailure;
    }


    public void clearLiveData() {
        sportArticleResponseModelLiveData = new MutableLiveData<>();
        sportArticleResponseModelLiveData.postValue(null);

        sportNewsResponseModelLiveData = new MutableLiveData<>();
        sportNewsResponseModelLiveData.postValue(null);

        networkFailure = new MutableLiveData<>();
        networkFailure.postValue(null);
    }

}

