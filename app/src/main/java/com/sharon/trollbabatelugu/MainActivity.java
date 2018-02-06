package com.sharon.trollbabatelugu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sharon.trollbabatelugu.helper.Constants;
import com.sharon.trollbabatelugu.helper.ShareHelper;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    static Drawer result;
    static PrimaryDrawerItem item_page1, item_page2, item_page3, item_page4, item_page5, item_page6, item_page7,
            item_page8, item_page9, item_page10, item_page11, item_page12, item_page13,
            item_page14, item_page15;
    //            item_page16, item_page17, item_page18, item_page19,
//            item_page20, item_page26, item_page22, item_page23, item_page24, item_page25, item_page21;
    Toolbar toolbar;
    AccountHeader headerResult;
    DividerDrawerItem item_divider;
    SecondaryDrawerItem item_settings, item_about, item_shareTheApp, item_addRemove;
    Preferences preferences;
    boolean isPremium = false;
    int adCount = 0;
    private InterstitialAd mInterstitialAdforPages, mInterstitialAdforAddRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        preferences = new Preferences(this);

        isPremium = preferences.getPremiumInfo();
        if (!isPremium) {
            MobileAds.initialize(getApplicationContext(), Constants.admob_app_id);
            mInterstitialAdforPages = new InterstitialAd(this);
            mInterstitialAdforPages.setAdUnitId(Constants.admob_interstitialpages);
            mInterstitialAdforPages.loadAd(new AdRequest.Builder().addTestDevice("EFE01EDD6C65F47A8B03AFD4526C76C9").build());

            mInterstitialAdforAddRemove = new InterstitialAd(this);
            mInterstitialAdforAddRemove.setAdUnitId(Constants.admob_interstitialaddremove);
            mInterstitialAdforAddRemove.loadAd(new AdRequest.Builder().addTestDevice("EFE01EDD6C65F47A8B03AFD4526C76C9").build());

            mInterstitialAdforPages.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mInterstitialAdforPages.loadAd(new AdRequest.Builder().build());
                }

            });
            mInterstitialAdforAddRemove.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mInterstitialAdforAddRemove.loadAd(new AdRequest.Builder().build());
                    startActivity(new Intent(MainActivity.this, AddRemovePagesActivity.class));
                }
            });
        }

        if (preferences.isFirstTimeLaunch()) {
            setDefaultDrawerItemChecks();
        }
        checkingFolders(MainActivity.this);
        completeNavigationDrawer();
    }

    private void setDefaultDrawerItemChecks() {
        preferences.putCheckPref("page1", true);
        preferences.putCheckPref("page2", true);
        preferences.putCheckPref("page3", true);
        preferences.putCheckPref("page4", true);
        preferences.putCheckPref("page5", true);
        preferences.putCheckPref("page6", true);
        preferences.putCheckPref("page7", true);
        preferences.putCheckPref("page8", true);
        preferences.putCheckPref("page9", true);
        preferences.putCheckPref("page10", true);
        preferences.putCheckPref("page11", true);
        preferences.putCheckPref("page12", true);
        preferences.putCheckPref("page13", true);
        preferences.putCheckPref("page14", true);
        preferences.putCheckPref("page15", true);

        preferences.setFirstTimeLaunch(false);
    }

    private void completeNavigationDrawer() {
        createNavigationHeader();
        createDrawerLayout();
        createDrawerItems();
        addDrawerItems();
        createDrawerClicks();

        //The First Page is ICU. And cannot be changed from addremove
        result.setSelection(1);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Fragment frag = getFragmentManager().findFragmentByTag("settings");
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else if (frag != null) {
            Fragment fragment = new ContentActivity();
            Bundle bundle = new Bundle();
            bundle.putString("id", Constants.id_page1);
            bundle.putInt("pic", R.drawable.icon_page4);
            fragment.setArguments(bundle);
            this.getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
            result.closeDrawer();
        } else {
            CheckPurchase.dispose();
            alertExitPic();
        }
    }

    private void createNavigationHeader() {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.nav_header)
                .addProfiles(
                        new ProfileDrawerItem().withName(getString(R.string.app_name)).withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    private void createDrawerLayout() {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .build();
    }

    private void createDrawerItems() {
        item_page1 = new PrimaryDrawerItem().withIdentifier(1).withName(getString(R.string.pagename_1)).withIcon(R.drawable.icon_page1);
        item_page2 = new PrimaryDrawerItem().withIdentifier(2).withName(getString(R.string.pagename_2)).withIcon(R.drawable.icon_page2);
        item_page3 = new PrimaryDrawerItem().withIdentifier(3).withName(getString(R.string.pagename_3)).withIcon(R.drawable.icon_page3);
        item_page4 = new PrimaryDrawerItem().withIdentifier(4).withName(getString(R.string.pagename_4)).withIcon(R.drawable.icon_page4);
        item_page5 = new PrimaryDrawerItem().withIdentifier(5).withName(getString(R.string.pagename_5)).withIcon(R.drawable.icon_page5);
        item_page6 = new PrimaryDrawerItem().withIdentifier(6).withName(getString(R.string.pagename_6)).withIcon(R.drawable.icon_page6);
        item_page7 = new PrimaryDrawerItem().withIdentifier(7).withName(getString(R.string.pagename_7)).withIcon(R.drawable.icon_page7);
        item_page8 = new PrimaryDrawerItem().withIdentifier(8).withName(getString(R.string.pagename_8)).withIcon(R.drawable.icon_page8);
        item_page9 = new PrimaryDrawerItem().withIdentifier(9).withName(getString(R.string.pagename_9)).withIcon(R.drawable.icon_page9);
        item_page10 = new PrimaryDrawerItem().withIdentifier(10).withName(getString(R.string.pagename_10)).withIcon(R.drawable.icon_page10);
        item_page11 = new PrimaryDrawerItem().withIdentifier(11).withName(getString(R.string.pagename_11)).withIcon(R.drawable.icon_page11);
        item_page12 = new PrimaryDrawerItem().withIdentifier(12).withName(getString(R.string.pagename_12)).withIcon(R.drawable.icon_page12);
        item_page13 = new PrimaryDrawerItem().withIdentifier(13).withName(getString(R.string.pagename_13)).withIcon(R.drawable.icon_page13);
        item_page14 = new PrimaryDrawerItem().withIdentifier(14).withName(getString(R.string.pagename_14)).withIcon(R.drawable.icon_page14);
        item_page15 = new PrimaryDrawerItem().withIdentifier(15).withName(getString(R.string.pagename_15)).withIcon(R.drawable.icon_page15);
//        item_page16 = new PrimaryDrawerItem().withIdentifier(16).withName(getString(R.string.pagename_16)).withIcon(R.drawable.icon_thengakola);
//        item_page17 = new PrimaryDrawerItem().withIdentifier(17).withName(getString(R.string.pagename_17)).withIcon(R.drawable.icon_trollmollywood);
//        item_page18 = new PrimaryDrawerItem().withIdentifier(18).withName(getString(R.string.pagename_18)).withIcon(R.drawable.icon_trollclasherskerala);
//        item_page19 = new PrimaryDrawerItem().withIdentifier(19).withName(getString(R.string.pagename_19)).withIcon(R.drawable.icon_outspoken);
//        item_page20 = new PrimaryDrawerItem().withIdentifier(20).withName(getString(R.string.pagename_20)).withIcon(R.drawable.icon_btechtrolls);
//        item_page21 = new PrimaryDrawerItem().withIdentifier(21).withName(getString(R.string.pagename_21)).withIcon(R.drawable.icon_onlinetrollmedia);
//        item_page22 = new PrimaryDrawerItem().withIdentifier(22).withName(getString(R.string.pagename_22)).withIcon(R.drawable.icon_trollkerala);
//        item_page23 = new PrimaryDrawerItem().withIdentifier(23).withName(getString(R.string.pagename_23)).withIcon(R.drawable.icon_trollreligion);
//        item_page24 = new PrimaryDrawerItem().withIdentifier(24).withName(getString(R.string.pagename_24)).withIcon(R.drawable.icon_trollktu);
//        item_page25 = new PrimaryDrawerItem().withIdentifier(25).withName(getString(R.string.pagename_25)).withIcon(R.drawable.icon_pravasitrolls);
//        item_page26 = new PrimaryDrawerItem().withIdentifier(26).withName(getString(R.string.pagename_26)).withIcon(R.drawable.icon_malayalampling);

        item_divider = new DividerDrawerItem();

        item_settings = new SecondaryDrawerItem().withIdentifier(100).withName(getString(R.string.action_settings)).withIcon(R.drawable.settings).withSelectable(false);
        item_about = new SecondaryDrawerItem().withIdentifier(101).withName(getString(R.string.about)).withIcon(R.drawable.about).withSelectable(false);
        item_shareTheApp = new SecondaryDrawerItem().withIdentifier(102).withName(R.string.sharetheapp).withIcon(R.drawable.share).withSelectable(false);
        item_addRemove = new SecondaryDrawerItem().withIdentifier(103).withName(R.string.add_remove).withIcon(R.drawable.add_remove).withSelectable(false);
    }

    private void addDrawerItems() {
        Preferences preferences = new Preferences(this);
        if (preferences.getCheckPref("page1")) {
            result.addItem(item_page1);
        }
        if (preferences.getCheckPref("page2")) {
            result.addItem(item_page2);
        }
        if (preferences.getCheckPref("page3")) {
            result.addItem(item_page3);
        }
        if (preferences.getCheckPref("page4")) {
            result.addItem(item_page4);
        }
        if (preferences.getCheckPref("page5")) {
            result.addItem(item_page5);
        }
        if (preferences.getCheckPref("page6")) {
            result.addItem(item_page6);
        }
        if (preferences.getCheckPref("page7")) {
            result.addItem(item_page7);
        }
        if (preferences.getCheckPref("page8")) {
            result.addItem(item_page8);
        }
        if (preferences.getCheckPref("page9")) {
            result.addItem(item_page9);
        }
        if (preferences.getCheckPref("page10")) {
            result.addItem(item_page10);
        }
        if (preferences.getCheckPref("page11")) {
            result.addItem(item_page11);
        }
        if (preferences.getCheckPref("page12")) {
            result.addItem(item_page12);
        }
        if (preferences.getCheckPref("page13")) {
            result.addItem(item_page13);
        }
        if (preferences.getCheckPref("page14")) {
            result.addItem(item_page14);
        }
        if (preferences.getCheckPref("page15")) {
            result.addItem(item_page15);
        }
//        if (preferences.getCheckPref("page16")) {
//            result.addItem(item_page16);
//        }
//        if (preferences.getCheckPref("page17")) {
//            result.addItem(item_page17);
//        }
//        if (preferences.getCheckPref("page18")) {
//            result.addItem(item_page18);
//        }
//        if (preferences.getCheckPref("page19")) {
//            result.addItem(item_page19);
//        }
//        if (preferences.getCheckPref("page20")) {
//            result.addItem(item_page20);
//        }
//        if (preferences.getCheckPref("page21")) {
//            result.addItem(item_page21);
//        }
//        if (preferences.getCheckPref("page22")) {
//            result.addItem(item_page22);
//        }
//        if (preferences.getCheckPref("page23")) {
//            result.addItem(item_page23);
//        }
//        if (preferences.getCheckPref("page24")) {
//            result.addItem(item_page24);
//        }
//        if (preferences.getCheckPref("page25")) {
//            result.addItem(item_page25);
//        }
//        if (preferences.getCheckPref("page26")) {
//            result.addItem(item_page26);
//        }
        result.addItems(item_divider, item_addRemove, item_settings, item_about, item_shareTheApp);
    }

    private void createDrawerClicks() {
        result.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Fragment fragment = new ContentActivity();
                Bundle bundle = new Bundle();
                adCount = adCount + 1;
                switch ((int) drawerItem.getIdentifier()) {
                    case 1:
                        bundle.putString("id", Constants.id_page1);
                        bundle.putInt("pic", R.drawable.icon_page3);
                        break;
                    case 2:
                        bundle.putString("id", Constants.id_page2);
                        bundle.putInt("pic", R.drawable.icon_page2);
                        break;
                    case 3:
                        bundle.putString("id", Constants.id_page3);
                        bundle.putInt("pic", R.drawable.icon_page4);
                        break;
                    case 4:
                        bundle.putString("id", Constants.id_page4);
                        bundle.putInt("pic", R.drawable.icon_page1);
                        break;
                    case 5:
                        bundle.putString("id", Constants.id_page5);
                        bundle.putInt("pic", R.drawable.icon_page5);
                        break;
                    case 6:
                        bundle.putString("id", Constants.id_page6);
                        bundle.putInt("pic", R.drawable.icon_page6);
                        break;
                    case 7:
                        bundle.putString("id", Constants.id_page7);
                        bundle.putInt("pic", R.drawable.icon_page7);
                        break;
                    case 8:
                        bundle.putString("id", Constants.id_page8);
                        bundle.putInt("pic", R.drawable.icon_page8);
                        break;
                    case 9:
                        bundle.putString("id", Constants.id_page9);
                        bundle.putInt("pic", R.drawable.icon_page9);
                        break;
                    case 10:
                        bundle.putString("id", Constants.id_page10);
                        bundle.putInt("pic", R.drawable.icon_page10);
                        break;
                    case 11:
                        bundle.putString("id", Constants.id_page11);
                        bundle.putInt("pic", R.drawable.icon_page11);
                        break;
                    case 12:
                        bundle.putString("id", Constants.id_page12);
                        bundle.putInt("pic", R.drawable.icon_page12);
                        break;
                    case 13:
                        bundle.putString("id", Constants.id_page13);
                        bundle.putInt("pic", R.drawable.icon_page13);
                        break;
                    case 14:
                        bundle.putString("id", Constants.id_page14);
                        bundle.putInt("pic", R.drawable.icon_page14);
                        break;
                    case 15:
                        bundle.putString("id", Constants.id_page15);
                        bundle.putInt("pic", R.drawable.icon_page15);
                        break;
//                    case 16:
//                        bundle.putString("id", Constants.id_page16);
//                        bundle.putInt("pic", R.drawable.icon_thengakola);
//                        break;
//                    case 17:
//                        bundle.putString("id", Constants.id_page17);
//                        bundle.putInt("pic", R.drawable.icon_trollmollywood);
//                        break;
//                    case 18:
//                        bundle.putString("id", Constants.id_page18);
//                        bundle.putInt("pic", R.drawable.icon_trollclasherskerala);
//                        break;
//                    case 19:
//                        bundle.putString("id", Constants.id_page19);
//                        bundle.putInt("pic", R.drawable.icon_outspoken);
//                        break;
//                    case 20:
//                        bundle.putString("id", Constants.id_page20);
//                        bundle.putInt("pic", R.drawable.icon_btechtrolls);
//                        break;
//                    case 21:
//                        bundle.putString("id", Constants.id_page21);
//                        bundle.putInt("pic", R.drawable.icon_onlinetrollmedia);
//                        break;
//                    case 22:
//                        bundle.putString("id", Constants.id_page22);
//                        bundle.putInt("pic", R.drawable.icon_trollkerala);
//                        break;
//                    case 23:
//                        bundle.putString("id", Constants.id_page23);
//                        bundle.putInt("pic", R.drawable.icon_trollreligion);
//                        break;
//                    case 24:
//                        bundle.putString("id", Constants.id_page24);
//                        bundle.putInt("pic", R.drawable.icon_trollktu);
//                        break;
//                    case 25:
//                        bundle.putString("id", Constants.id_page25);
//                        bundle.putInt("pic", R.drawable.icon_pravasitrolls);
//                        break;
//                    case 26:
//                        bundle.putString("id", Constants.id_page26);
//                        bundle.putInt("pic", R.drawable.icon_malayalampling);
//                        break;

                    case 100://settings
                        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new Settings(), "settings").commit();
                        result.closeDrawer();
                        return true;
                    case 101://about
                        showAlertAboutUs();
                        return true;
                    case 102://share the app
                        shareApp(MainActivity.this);
                        return true;
                    case 103: //add remove pages
                        if (!isPremium && mInterstitialAdforAddRemove.isLoaded()) {
                            mInterstitialAdforAddRemove.show();
                        } else {
                            startActivity(new Intent(MainActivity.this, AddRemovePagesActivity.class));
                        }
                        return true;
                    default:
                        showPageError();
                        return true;
                }
                //check this implementation for on adclosed
                if (adCount == 5 && !isPremium) {
                    if (mInterstitialAdforPages.isLoaded()) {
                        mInterstitialAdforPages.show();
                    }
                    adCount = 0;
                }
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
                result.closeDrawer();
                return true;
            }
        });
    }

    private void alertExitPic() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure? Champestha")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, R.string.exit_toast, Toast.LENGTH_SHORT).show();
                        MainActivity.super.onBackPressed();
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    private void showPageError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.page_error_title)
                .setMessage(R.string.page_error_description)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    private void showAlertAboutUs() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo != null ? pInfo.versionName : "";
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Version:" + version + "\n" + Constants.alert_developer_info)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    @AfterPermissionGranted(005)
    public void shareApp(Context context) {
        String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            new ShareHelper().shareAppDetails((Activity) context);
        } else {
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.storage_permission_prompt_message),
                    005, perms);
        }
    }

    @AfterPermissionGranted(006)
    public void checkingFolders(Context context) {
        String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            new ShareHelper().checkFolders();
        } else {
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.storage_permission_prompt_message),
                    006, perms);
        }
    }
}
