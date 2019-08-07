package com.rider.mtsweni.viewmodel;

import android.app.Application;

import com.rider.mtsweni.core.Content;
import com.rider.mtsweni.pojo.SportArticleResponseModel;
import com.rider.mtsweni.pojo.SportNewsResponseModel;
import com.rider.mtsweni.repository.SportsNewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SportsNewsViewModel extends AndroidViewModel {

    private SportsNewsRepository sportsNewsRepository;
    private LiveData<List<SportNewsResponseModel>> sportNewsResponseModelLiveData;
    private LiveData<SportArticleResponseModel> sportArticleResponseModelLiveData;

    public SportsNewsViewModel(@NonNull Application application) {
        super(application);
        sportsNewsRepository = SportsNewsRepository.getInstance(application.getApplicationContext());
    }

    public void getSportNews() {
        sportsNewsRepository.getSportNews();
    }

    public LiveData<List<SportNewsResponseModel>> getSportNewsResponseModeLiveData() {

        if (sportNewsResponseModelLiveData == null) {
            sportNewsResponseModelLiveData = new MutableLiveData<>();
            sportNewsResponseModelLiveData = sportsNewsRepository.getSportNewsResponseModeLiveData();
        }

        return sportNewsResponseModelLiveData;
    }

    public void getSportsArticles(String siteName, String urlName, String urlFriendlyDate, String urlFriendlyHeadline) {
        sportsNewsRepository.getSportsArticles(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);
    }

    public LiveData<SportArticleResponseModel> getSportArticleResponseModeLiveData() {

        if (sportArticleResponseModelLiveData == null) {
            sportArticleResponseModelLiveData = new MutableLiveData<>();
            sportArticleResponseModelLiveData = sportsNewsRepository.getSportArticleResponseModeLiveData();
        }
        return sportArticleResponseModelLiveData;
    }

    public MutableLiveData<Content> getNetworkFailure() {
        return sportsNewsRepository.getNetworkFailure();
    }

    public void clearLiveData() {
        sportsNewsRepository.clearLiveData();
    }

}
