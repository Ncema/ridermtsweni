package com.rider.mtsweni.service;

import com.rider.mtsweni.pojo.SportArticleResponseModel;
import com.rider.mtsweni.pojo.SportNewsResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SportsNewsServices {

    @GET("news?format=json")
    Call<List<SportNewsResponseModel>> getSportsNews();

    @GET("{SiteName}/{UrlName}/news/{UrlFriendlyDate}/{UrlFriendlyHeadline}?format=json")
    Call<SportArticleResponseModel> getSportsArticles(@Path("SiteName") String siteName,
                                                      @Path("UrlName") String urlName,
                                                      @Path("UrlFriendlyDate") String urlFriendlyDate,
                                                      @Path("UrlFriendlyHeadline") String urlFriendlyHeadline);

}
