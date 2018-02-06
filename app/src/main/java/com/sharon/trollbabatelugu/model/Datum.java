package com.sharon.trollbabatelugu.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.sharon.trollbabatelugu.FullScreenImage;
import com.sharon.trollbabatelugu.PlayVideoJZ;
import com.sharon.trollbabatelugu.R;
import com.sharon.trollbabatelugu.helper.DownloadHelper;
import com.sharon.trollbabatelugu.helper.ShareHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.VIBRATOR_SERVICE;

public class Datum extends AbstractItem<Datum, Datum.ViewHolder> {

    @SerializedName("full_picture")
    @Expose
    private String fullPicture;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("permalink_url")
    @Expose
    private String permalinkUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pagepic")
    @Expose
    private int pagepic;
    private Bitmap bitmap;

    private static void openLinkInChromeCustom(Context context, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    private static void shakeItBaby(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(100);
        }
    }

    @AfterPermissionGranted(002)
    public static void downloadMethod(Context context, Datum item) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            String url = "";
            if ("status".equals(item.getItemType())) {
                Toast.makeText(context, R.string.datum_nothingtodownload_error, Toast.LENGTH_LONG).show();
                return;
            } else if ("photo".equals(item.getItemType())) {
                url = item.getFullPicture();
            } else if ("video".equals(item.getItemType())) {
                if (item.getSource().contains("https://www.youtube.com/")) {
                    Toast.makeText(context, R.string.datum_youtube_video_error, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    url = item.getSource();
                }
            }
            new DownloadHelper((Activity) context, url, item.getId(), item.getItemType()).startDownload();
        } else {
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.storage_permission_prompt_message),
                    002, perms);
        }
    }

    @AfterPermissionGranted(003)
    public static void shareMethod(Context context, Datum item) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {

            new ShareHelper().shareMain((Activity) context, item.getBitmap(), item.getMessage(), item.getId(), item.getPermalinkUrl(), item.getItemType());
        } else {
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.storage_permission_prompt_message),
                    003, perms);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private void onLinkClicked(Context context) {
        String url = getLink();
        openLinkInChromeCustom(context, url);
    }

    public int getPagepic() {
        return pagepic;
    }

    public void setPagepic(int pagepic) {
        this.pagepic = pagepic;
    }

    public String getFullPicture() {
        return fullPicture;
    }

    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.permalinkUrl = permalinkUrl;
    }

    public String getItemType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ViewHolder getViewHolder(@NonNull View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.fastadapter_item_adapter;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_row;
    }

    @Override
    public void bindView(final ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        Context ctx = holder.itemView.getContext();

        if (getMessage() != null) {
            holder.message.setText(getMessage());
            holder.message.setVisibility(View.VISIBLE);
        }
        holder.pagename.setText(getFrom().getName() != null ? getFrom().getName() : ctx.getString(R.string.app_name));
        holder.page_pic.setImageResource(getPagepic());

        if (!"status".equals(getItemType()) && getFullPicture() != null) {
            holder.pic.setImageBitmap(null);
            holder.pic.setVisibility(View.VISIBLE);
            Picasso.with(ctx)
                    .load(getFullPicture())
                    .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .into(holder.pic, new Callback() {
                        @Override
                        public void onSuccess() {
                            setBitmap(((BitmapDrawable) holder.pic.getDrawable()).getBitmap());
                        }

                        @Override
                        public void onError() {

                        }
                    })
            ;
        } else {
            holder.pic.setVisibility(View.GONE);
        }
        if (getItemType().equals("link") || (getLink() != null && !getLink().contains("https://www.facebook.com"))) {
            holder.post_url.setText(getName() != null ? getName() : getLink());
            holder.post_url.setVisibility(View.VISIBLE);
        }
        if ("video".equals(getItemType()) && getSource() != null) {
            holder.play_icon.setVisibility(View.VISIBLE);
        } else {
            holder.play_icon.setVisibility(View.GONE);
        }
        applyClickEvents(ctx, holder);
    }

    private void applyClickEvents(final Context ctx, ViewHolder holder) {
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemType().equals("video")) {
                    if (getSource().contains("https://www.youtube.com/")) {
                        onLinkClicked(ctx);
                    } else {
                        onVideoPlayRequest(ctx);
                    }
                } else if (getItemType().equals("photo")) {
                    onPhotoFullScreenRequest(ctx);
                } else if (getItemType().equals("link")) {
                    onLinkClicked(ctx);
                }
            }
        });
        holder.post_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLinkClicked(ctx);
            }
        });
        holder.play_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSource().contains("https://www.youtube.com/")) {
                    onLinkClicked(ctx);
                } else {
                    onVideoPlayRequest(ctx);
                }
            }
        });
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.message.setText(null);
        holder.pagename.setText(null);
        holder.post_url.setText(null);
        holder.pic.setImageBitmap(null);
        holder.page_pic.setImageBitmap(null);
//        holder.play_icon.setImageBitmap(null);
    }

    public void onVideoPlayRequest(Context ctx) {
        Intent intent = new Intent(ctx, PlayVideoJZ.class);
        intent.putExtra("videourl", getSource());
        intent.putExtra("id", getId());
        ctx.startActivity(intent);
    }

    public void onPhotoFullScreenRequest(Context ctx) {
        String picurl = getFullPicture();
        String picid = getId();
        String picmessage = getMessage();
        Intent intent = new Intent(ctx, FullScreenImage.class);
        intent.putExtra("picurl", picurl);
        intent.putExtra("picid", picid);
        intent.putExtra("picmessage", picmessage);
        ctx.startActivity(intent);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pic, page_pic;
        ImageView play_icon;
        TextView message, pagename, post_url;
        Button share, download, visitfb;


        ViewHolder(View view) {
            super(view);
            pic = view.findViewById(R.id.thumbnail);
            page_pic = view.findViewById(R.id.pagepic);
            message = view.findViewById(R.id.caption);
            Linkify.addLinks(message, Linkify.WEB_URLS);
            pagename = view.findViewById(R.id.source_page_name);
            post_url = view.findViewById(R.id.post_url);
            Linkify.addLinks(post_url, Linkify.WEB_URLS);
            play_icon = view.findViewById(R.id.play_icon);

            share = view.findViewById(R.id.share_main_button);
            download = view.findViewById(R.id.download_main_button);
            visitfb = view.findViewById(R.id.visitfb_main_button);
        }
    }

    public static class VisitFB extends ClickEventHook<Datum> {
        @Nullable
        @Override
        public List<View> onBindMany(@NonNull RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof Datum.ViewHolder) {
                return ((ViewHolder) viewHolder).visitfb.getTouchables();
            }
            return super.onBindMany(viewHolder);
        }

        @Override
        public void onClick(View v, int position, FastAdapter<Datum> fastAdapter, Datum item) {
            v.setHapticFeedbackEnabled(true);
            shakeItBaby(v.getContext());
            String url = item.getPermalinkUrl();
            openLinkInChromeCustom(v.getContext(), url);
        }
    }

    public static class MainShare extends ClickEventHook<Datum> {
        @Nullable
        @Override
        public List<View> onBindMany(@NonNull RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof Datum.ViewHolder) {
                return ((ViewHolder) viewHolder).share.getTouchables();
            }
            return super.onBindMany(viewHolder);
        }

        @Override
        public void onClick(View v, int position, FastAdapter<Datum> fastAdapter, Datum item) {
            v.setHapticFeedbackEnabled(true);
            shakeItBaby(v.getContext());
//            new ShareHelper().shareMain((Activity) v.getContext(), item.getBitmap(), item.getMessage(), item.getId(), item.getPermalinkUrl(), item.getItemType());
            shareMethod(v.getContext(), item);
        }
    }

    public static class Download extends ClickEventHook<Datum> {
        @Nullable
        @Override
        public List<View> onBindMany(@NonNull RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof Datum.ViewHolder) {
                return ((ViewHolder) viewHolder).download.getTouchables();
            }
            return super.onBindMany(viewHolder);
        }

        @Override
        public void onClick(View v, int position, FastAdapter<Datum> fastAdapter, Datum item) {
            v.setHapticFeedbackEnabled(true);
            shakeItBaby(v.getContext());
            downloadMethod(v.getContext(), item);
        }
    }
}
