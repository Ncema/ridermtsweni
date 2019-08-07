package com.rider.mtsweni.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SportNewsResponseModel {

    @SerializedName("Headline")
    @Expose
    public String headline;
    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("Blurb")
    @Expose
    public String blurb;
    @SerializedName("SmallImageName")
    @Expose
    public String smallImageName;
    @SerializedName("SmallImageAlt")
    @Expose
    public String smallImageAlt;
    @SerializedName("LargeImageName")
    @Expose
    public String largeImageName;
    @SerializedName("LargeImageAlt")
    @Expose
    public String largeImageAlt;
    @SerializedName("ExtraImageName")
    @Expose
    public String extraImageName;
    @SerializedName("ExtraImageAlt")
    @Expose
    public String extraImageAlt;
    @SerializedName("ImageUrlLocal")
    @Expose
    public String imageUrlLocal;
    @SerializedName("ImageUrlRemote")
    @Expose
    public String imageUrlRemote;
    @SerializedName("DateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("Category")
    @Expose
    public String category;
    @SerializedName("CategoryDisplayName")
    @Expose
    public String categoryDisplayName;
    @SerializedName("CategoryId")
    @Expose
    public Integer categoryId;
    @SerializedName("SiteId")
    @Expose
    public Integer siteId;
    @SerializedName("SiteName")
    @Expose
    public String siteName;
    @SerializedName("Author")
    @Expose
    public String author;
    @SerializedName("Credit")
    @Expose
    public Object credit;
    @SerializedName("CreditImageUrl")
    @Expose
    public Object creditImageUrl;
    @SerializedName("CreditUrl")
    @Expose
    public Object creditUrl;
    @SerializedName("UrlName")
    @Expose
    public String urlName;
    @SerializedName("LiveChat")
    @Expose
    public Boolean liveChat;
    @SerializedName("WebOnly")
    @Expose
    public Boolean webOnly;
    @SerializedName("UrlFriendlyHeadline")
    @Expose
    public String urlFriendlyHeadline;
    @SerializedName("UrlFriendlyDate")
    @Expose
    public String urlFriendlyDate;
    @SerializedName("IsMainStory")
    @Expose
    public Boolean isMainStory;
    @SerializedName("UpdatedDate")
    @Expose
    public String updatedDate;
    @SerializedName("Keywords")
    @Expose
    public String keywords;
    @SerializedName("Active")
    @Expose
    public Boolean active;
    @SerializedName("ValidFrom")
    @Expose
    public String validFrom;
    @SerializedName("ValidTo")
    @Expose
    public String validTo;
    @SerializedName("relatedArticles")
    @Expose
    public Object relatedArticles;
}