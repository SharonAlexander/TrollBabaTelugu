package com.sharon.trollbabatelugu;

import android.Manifest;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sharon.trollbabatelugu.helper.DownloadHelper;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PlayVideoJZ extends AppCompatActivity {

    FloatingActionButton downloadVideo;
    Preferences preferences;
    private String VIDEO_URL, ID;
    private AdView mAdViewBannerMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_jz);

        preferences = new Preferences(this);
        mAdViewBannerMain = findViewById(R.id.bannerAdVideoPage);
        if (!preferences.getPremiumInfo()) {
            adsInitialise();
        } else {
            mAdViewBannerMain.setVisibility(View.GONE);
        }

        VIDEO_URL = getIntent().getStringExtra("videourl");
        ID = getIntent().getStringExtra("id");

        downloadVideo = findViewById(R.id.downloadVideo);
        downloadVideo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_dark)));
        downloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadMethod();
            }
        });


        JZVideoPlayerStandard jzVideoPlayerStandard = findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(VIDEO_URL, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, getString(R.string.app_name));
        jzVideoPlayerStandard.startVideo();
        jzVideoPlayerStandard.mRetryBtn.setText("Retry");
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @AfterPermissionGranted(004)
    public void downloadMethod() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            new DownloadHelper(this, VIDEO_URL, ID, "video").startDownload();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.storage_permission_prompt_message),
                    004, perms);
        }
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
