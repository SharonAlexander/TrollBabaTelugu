package com.sharon.trollbabatelugu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.joaquimley.faboptions.FabOptions;
import com.sharon.trollbabatelugu.helper.Constants;
import com.sharon.trollbabatelugu.helper.DownloadHelper;
import com.sharon.trollbabatelugu.helper.ShareHelper;
import com.squareup.picasso.Picasso;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class FullScreenImage extends AppCompatActivity implements View.OnClickListener {

    FabOptions fabOptions;
    String picurl, picid, picmessage;
    String imageBasepath;
    PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreenimage);

        picurl = getIntent().getStringExtra("picurl");
        picid = getIntent().getStringExtra("picid");
        picmessage = getIntent().getStringExtra("picmessage");
        imageBasepath = Constants.folder_main_path + Constants.folder_name + picid + Constants.image_extention;

        photoView = findViewById(R.id.img);
        Picasso.with(this).load(picurl).into(photoView);

        fabOptions = findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.fab_buttons);
        fabOptions.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.faboptions_favorite:
                Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show();
                break;

            case R.id.faboptions_whatsapp:
                new ShareHelper().shareImageOnWhatsapp(this,((BitmapDrawable)photoView.getDrawable()).getBitmap() , picmessage, picid);
                break;

            case R.id.faboptions_download:
                downloadMethod(this);
                break;

            case R.id.faboptions_share:
                new ShareHelper().shareMain(this,((BitmapDrawable)photoView.getDrawable()).getBitmap() , picmessage, picid,null,"photo");
                break;
        }
    }

    @AfterPermissionGranted(003)
    public void downloadMethod(Context context) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {

            new DownloadHelper((Activity) context, picurl, picid, "photo").startDownload();
        } else {
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.storage_permission_prompt_message),
                    003, perms);
        }
    }

}
