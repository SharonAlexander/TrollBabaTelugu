package com.sharon.trollbabatelugu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.GsonBuilder;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;
import com.sharon.trollbabatelugu.helper.Constants;
import com.sharon.trollbabatelugu.helper.InternetConnectionChecker;
import com.sharon.trollbabatelugu.model.Datum;
import com.sharon.trollbabatelugu.model.FirstResponse;

public class ContentActivity extends Fragment {

    AccessToken accessToken;
    RecyclerView recyclerView;
    FastItemAdapter<Datum> fastAdapter;
    FirstResponse fr, f;
    String after;
    String id;
    int page_pic;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    InternetConnectionChecker internetConnectionChecker;
    Preferences preferences;
    private AdView mAdViewBannerMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.troll_layout, container, false);

        preferences = new Preferences(getActivity());
        mAdViewBannerMain = rootView.findViewById(R.id.adViewBannerMain);
        if (!preferences.getPremiumInfo()) {
            adsInitialise();
        } else {
            mAdViewBannerMain.setVisibility(View.GONE);
        }

        getActivity().setTitle(getString(R.string.app_name));

        internetConnectionChecker = new InternetConnectionChecker(getActivity());
        if (!internetConnectionChecker.isOnline()) {
            internetConnectionChecker.showNotConnectedDialog();
        }

        id = getArguments().getString("id");
        page_pic = getArguments().getInt("pic");

        accessToken = new AccessToken(Constants.facebook_app_token, Constants.facebook_app_id, Constants.facebook_app_id, null, null, null, null, null);

        recyclerView = rootView.findViewById(R.id.troll_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fastAdapter = new FastItemAdapter<>();
        fastAdapter.setHasStableIds(true);
        fastAdapter.withSelectable(true);
        fastAdapter.withMultiSelect(true);
        fastAdapter.withSelectOnLongClick(true);

        fastAdapter.withEventHook(new Datum.VisitFB());
        fastAdapter.withEventHook(new Datum.MainShare());
        fastAdapter.withEventHook(new Datum.Download());

        recyclerView.setAdapter(fastAdapter);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(final int currentPage) {
                if (internetConnectionChecker.isOnline()) {
                    makeTheCall();
                } else {
                    internetConnectionChecker.showNotConnectedDialog();
                }
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        makeFirstCall();
        return rootView;
    }

    private void makeFirstCall() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                id,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        fr = new GsonBuilder().create().fromJson(response.getRawResponse(), FirstResponse.class);
                        fastAdapter.clear();
                        fastAdapter.add(fr.getData());
                        for (int i = 0; i < fr.getData().size(); i++) {
                            fr.getData().get(i).setPagepic(page_pic);
                        }
                        after = fr.getPaging().getCursors().getAfter();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", Constants.feed_fields);
        parameters.putString("limit", Constants.feed_limit);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void makeTheCall() {
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                id,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        f = new GsonBuilder().create().fromJson(response.getRawResponse(), FirstResponse.class);
                        fastAdapter.add(f.getData());
                        for (int i = 0; i < f.getData().size(); i++) {
                            f.getData().get(i).setPagepic(page_pic);
                        }
                        after = f.getPaging().getCursors().getAfter();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("pretty", "0");
        parameters.putString("fields", Constants.feed_fields);
        parameters.putString("limit", Constants.feed_limit);
        parameters.putString("after", after);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void adsInitialise() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("EFE01EDD6C65F47A8B03AFD4526C76C9").build();
        mAdViewBannerMain.loadAd(adRequest);
        mAdViewBannerMain.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdViewBannerMain.setVisibility(View.VISIBLE);
            }
        });
    }
}
