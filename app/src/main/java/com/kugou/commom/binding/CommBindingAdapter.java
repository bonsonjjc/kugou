package com.kugou.commom.binding;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by zjw on 2018/1/3.
 */
public abstract class CommBindingAdapter {
    @BindingAdapter(value = {"android:url"})
    public static void setUrl(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        picasso(imageView.getContext())
                .load(url.replace("{size}", "400"))
                .into(imageView);
    }

    @BindingAdapter(value = {"android:url", "android:placeholder"}, requireAll = false)
    public static void setPlaceholder(ImageView imageView, String url, Drawable drawable) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(drawable);
            return;
        }
        picasso(imageView.getContext())
                .load(url.replace("{size}", "400"))
                .placeholder(drawable)
                .fit()
                .error(drawable)
                .into(imageView);
    }

    private static Picasso picasso;

    public static Picasso picasso(Context context) {
        if (picasso == null) {
            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(context))
                    .memoryCache(new LruCache(1024*10))
                    .defaultBitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        return picasso;
    }

    public static void setPlaceholder(ImageView imageView, String url, int drawable) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(drawable);
            return;
        }

        picasso(imageView.getContext())
                .load(url.replace("{size}", "400"))
                .placeholder(drawable)
                .fit()
                .error(drawable)
                .into(imageView);
    }
}
