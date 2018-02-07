package com.sharon.trollbabatelugu.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.Toast;

import com.sharon.trollbabatelugu.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareHelper {


    public void shareImageOnWhatsapp(Context context, Bitmap bitmap, String textBody, String id) {
        checkFolders();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage(Constants.whatsapp_package);
        intent.putExtra(Intent.EXTRA_TEXT, !TextUtils.isEmpty(textBody) ? textBody : "");

        Parcelable fileUri = null;
        String filePath = Constants.folder_main_path + Constants.folder_name + id + ".png";
        if (saveBitmap(bitmap, filePath)) {
            fileUri = Uri.parse(filePath);
        }

        intent.putExtra(Intent.EXTRA_TEXT, textBody + Constants.added_share_message);
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.setType("image/*");
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(context, context.getString(R.string.toast_whatsapp_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    public void shareMain(Context context, Bitmap bitmap, String textBody, String id, String link, String type) {
        checkFolders();
        Parcelable fileUri = null;
        String filePath = Constants.folder_main_path + Constants.folder_name + id + ".png";
        if (saveBitmap(bitmap, filePath)) {
            fileUri = Uri.parse(filePath);
        }
        Intent shareIntent = new Intent();
        if (type.equals("link") || type.equals("video")) {
            textBody = link + "\n\n" + textBody;
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, textBody + Constants.added_share_message);
        shareIntent.setType("text/plain");
        shareIntent.setAction(Intent.ACTION_SEND);
        if (type.equals("photo")) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.setType("image/*");
        }
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.intent_title_share)));
    }

    public void shareAppDetails(Context context) {
        checkFolders();
        saveBitmapToFile(context);
        Parcelable uri = Uri.parse(Constants.folder_main_path + Constants.folder_name + Constants.hidden_folder_for_app_share + "app_share_image.jpeg");
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.app_share_message));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_SEND);

        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.intent_title_share)));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean saveBitmapToFile(Context context) {

        File folder = new File(Constants.folder_main_path + Constants.folder_name + Constants.hidden_folder_for_app_share);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File imageFile = new File(folder, "app_share_image.jpeg");
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_share_image);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return true;
        } catch (IOException e) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean saveBitmap(Bitmap bitmap, String path) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            try {
                File file = new File(path);
                file.createNewFile();
                new FileOutputStream(file).write(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public void checkFolders() {
        File folder1 = new File(Constants.folder_main_path + Constants.folder_name);
        if (!folder1.exists()) {
            folder1.mkdirs();
        }
        File folder2 = new File(Constants.folder_main_path + Constants.folder_name + Constants.hidden_folder_for_app_share);
        if (!folder2.exists()) {
            folder2.mkdirs();
        }
    }
}
