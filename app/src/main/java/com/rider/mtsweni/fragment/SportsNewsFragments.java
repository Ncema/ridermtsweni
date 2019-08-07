package com.rider.mtsweni.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rider.mtsweni.Constance;
import com.rider.mtsweni.R;
import com.rider.mtsweni.adapter.SportsNewsAdapter;
import com.rider.mtsweni.pojo.SportNewsResponseModel;
import com.rider.mtsweni.viewmodel.SportsNewsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SportsNewsFragments extends BaseFragment {

    public static final String TAG = SportsArticleFragment.class.getName();
    private SportsNewsViewModel mSportsNewsViewModel;

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewSportName)
    TextView textViewSportName;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewSubtitle)
    TextView textViewSubtitle;
    @BindView(R.id.recyclerViewSportsItem)
    RecyclerView recyclerViewSportsItem;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.constraintLayoutTitle)
    ConstraintLayout constraintLayoutTitle;

    private List<SportNewsResponseModel> sportNewsResponseModelList;
    private SportsNewsAdapter sportsNewsAdapter;

    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSportsNewsViewModel = ViewModelProviders.of(this).get(SportsNewsViewModel.class);
        mSportsNewsViewModel.clearLiveData();
        changeTitle(Constance.NEWS);
        actionBarColor();
        hideBackButton();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        sportNewsResponseModelList = new ArrayList<>();

        if (isNetworkAvailable()) {
            //In future we will do one call and save the data in a room db using data access objects to save and retrieve so that we do not make multiple calls, only call the Api if data changed.
            mSportsNewsViewModel.getSportNews();

            progressBar.setVisibility(View.VISIBLE);

            mSportsNewsViewModel.getSportNewsResponseModeLiveData().observe(getViewLifecycleOwner(), model -> {
                if (model != null) {

                    progressBar.setVisibility(View.GONE);

                    if (getActivity() == null) return;
                    recyclerViewSportsItem.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorInactiveText));
                    constraintLayoutTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorTertiaryTint60));
                    //textViewSportName.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.oval_shape));

                    textViewSportName.setText(model.get(4).category);
                    textViewTitle.setText(model.get(4).headline);
                    textViewSubtitle.setText(model.get(4).urlFriendlyDate);

                    String url = model.get(4).imageUrlRemote + model.get(4).largeImageName;
                    Glide.with(getActivity())
                            .load(url)
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageView);

                    imageView.setOnClickListener((View v) -> {

                        Bundle bundle = new Bundle();
                        bundle.putString(Constance.SITE_NAME, model.get(2).siteName);
                        bundle.putString(Constance.URL_NAME, model.get(2).urlName);
                        bundle.putString(Constance.URL_FRIENDLY_DATE, model.get(2).urlFriendlyDate);
                        bundle.putString(Constance.URL_FRIENDLY_HEADLINE, model.get(2).urlFriendlyHeadline);

                        SportsArticleFragment sportsArticleFragment = new SportsArticleFragment();
                        sportsArticleFragment.setArguments(bundle);

                        if (getActivity() == null) return;
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.addToBackStack(SportsArticleFragment.TAG);
                        fragmentTransaction.replace(R.id.sports_news_container, sportsArticleFragment, SportsArticleFragment.TAG);
                        fragmentTransaction.commit();
                    });

                    sportNewsResponseModelList = model;

                    sportsNewsAdapter = new SportsNewsAdapter(getContext(), sportNewsResponseModelList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerViewSportsItem.setLayoutManager(layoutManager);
                    recyclerViewSportsItem.setAdapter(sportsNewsAdapter);

                    sportsNewsAdapter.setOnItemClickListener((view, position) -> {

                        Bundle mBundle = new Bundle();
                        mBundle.putString(Constance.SITE_NAME, sportNewsResponseModelList.get(position).siteName);
                        mBundle.putString(Constance.URL_NAME, sportNewsResponseModelList.get(position).urlName);
                        mBundle.putString(Constance.URL_FRIENDLY_DATE, sportNewsResponseModelList.get(position).urlFriendlyDate);
                        mBundle.putString(Constance.URL_FRIENDLY_HEADLINE, sportNewsResponseModelList.get(position).urlFriendlyHeadline);

                        SportsArticleFragment sportsArticleFragment = new SportsArticleFragment();
                        sportsArticleFragment.setArguments(mBundle);

                        if (getActivity() == null) return;
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.addToBackStack(SportsArticleFragment.TAG);
                        fragmentTransaction.replace(R.id.sports_news_container, sportsArticleFragment, SportsArticleFragment.TAG);
                        fragmentTransaction.commit();

                    });
                }
            });

            mSportsNewsViewModel.getNetworkFailure().observe(getViewLifecycleOwner(), content -> {
                if (content != null)
                    showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, content.getMsg(), (dialogInterface, i) -> connectionSettingsButton());
            });
        } else {
            progressBar.setVisibility(View.GONE);
            showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, getResources().getString(R.string.connect_to_wifi_or_turn_on_mobile_data), (dialogInterface, i) -> connectionSettingsButton());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sports_news_fragments, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        mSportsNewsViewModel.clearLiveData();
    }

}

