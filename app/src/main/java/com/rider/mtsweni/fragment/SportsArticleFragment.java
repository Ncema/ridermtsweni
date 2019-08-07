package com.rider.mtsweni.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rider.mtsweni.Constance;
import com.rider.mtsweni.R;
import com.rider.mtsweni.viewmodel.SportsNewsViewModel;

import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class




SportsArticleFragment extends BaseFragment {

    public static final String TAG = SportsArticleFragment.class.getName();
    private SportsNewsViewModel mSportsNewsViewModel;
    private View rootView;

    private String siteName, urlName, urlFriendlyDate, urlFriendlyHeadline;

    @BindView(R.id.imageViewArticle)
    ImageView imageViewArticle;
    @BindView(R.id.textViewTitleArticle)
    TextView textViewTitleArticle;
    @BindView(R.id.textViewSubtitleArticle)
    TextView textViewSubtitleArticle;
    @BindView(R.id.textViewBody)
    TextView textViewBody;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSportsNewsViewModel = ViewModelProviders.of(this).get(SportsNewsViewModel.class);
        mSportsNewsViewModel.clearLiveData();
        changeTitle(Constance.ARTICLE);
        actionBarColor();
        showBackButton();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isNetworkAvailable()) {
            //In future we will have a progressbar here as well
            Bundle mBundle = getArguments();
            if (mBundle != null) {
                siteName = mBundle.getString(Constance.SITE_NAME);
                urlName = mBundle.getString(Constance.URL_NAME);
                urlFriendlyDate = mBundle.getString(Constance.URL_FRIENDLY_DATE);
                urlFriendlyHeadline = mBundle.getString(Constance.URL_FRIENDLY_HEADLINE);
            }

            mSportsNewsViewModel.getSportsArticles(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);

            mSportsNewsViewModel.getSportArticleResponseModeLiveData().observe(getViewLifecycleOwner(), sportArticleResponseModel -> {
                if (sportArticleResponseModel != null) {

                    String url = sportArticleResponseModel.imageUrlLocal + sportArticleResponseModel.largeImageName;
                    Glide
                            .with(this)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageViewArticle);
                    textViewBody.setText(HtmlCompat.fromHtml(sportArticleResponseModel.body, 0));
                    textViewTitleArticle.setText(sportArticleResponseModel.headline);
                    textViewSubtitleArticle.setText(sportArticleResponseModel.urlFriendlyDate);
                }
            });

            mSportsNewsViewModel.getNetworkFailure().observe(getViewLifecycleOwner(), content -> {
                if (content != null)
                    showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, content.getMsg(), (dialogInterface, i) -> connectionSettingsButton());
            });

        } else {
            showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, getResources().getString(R.string.connect_to_wifi_or_turn_on_mobile_data), (dialogInterface, i) -> connectionSettingsButton());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sports_article, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSportsNewsViewModel.clearLiveData();
    }

}

