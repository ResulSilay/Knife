package io.github.mthli.knife.glide;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashSet;

import io.github.mthli.knife.util.CommonUtil;

public class GlideImageGetter implements Html.ImageGetter {

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "rawtypes"})
    private final HashSet<Target> targets;
    private final HashSet<GifDrawable> gifDrawables;
    private final TextView textView;
    private final GlideRequest<GifDrawable> gifLoadRequest;
    private final GlideRequest<Bitmap> bitmapLoadRequest;

    public void recycle() {
        targets.clear();
        for (GifDrawable gifDrawable : gifDrawables) {
            gifDrawable.setCallback(null);
            gifDrawable.recycle();
        }
        gifDrawables.clear();
    }

    public GlideImageGetter(TextView textView, GlideRequests glideRequests) {
        this.textView = textView;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
        gifLoadRequest = glideRequests.asGif();
        bitmapLoadRequest = glideRequests.asBitmap();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Drawable getDrawable(String url) {
        if (url == null) {
            return null;
        }

        final UrlDrawable urlDrawable = new UrlDrawable();
        //noinspection rawtypes
        final Target target;
        if (isGif(url)) {
            target = new GifTarget(urlDrawable, textView, gifDrawables);
            gifLoadRequest.load(url).into(target);
        } else {
            target = new BitmapTarget(urlDrawable, textView);
            bitmapLoadRequest.load(url).into(target);
        }
        targets.add(target);
        return urlDrawable;
    }

    private static boolean isGif(String path) {
        int index = path.lastIndexOf('.');
        return index > 0 && "gif".equalsIgnoreCase(path.substring(index + 1));
    }

    @SuppressWarnings("deprecation")
    private static class GifTarget extends SimpleTarget<GifDrawable> {
        private final UrlDrawable urlDrawable;
        private final TextView textView;
        private final HashSet<GifDrawable> gifDrawables;

        private GifTarget(
                UrlDrawable urlDrawable, TextView textView, HashSet<GifDrawable> gifDrawables) {
            this.urlDrawable = urlDrawable;
            this.textView = textView;
            this.gifDrawables = gifDrawables;
        }

        @Override
        public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> transition) {
            int w = CommonUtil.getScreenWidth(textView.getContext())
                    - textView.getPaddingRight()
                    - textView.getPaddingLeft();
            int hh = resource.getIntrinsicHeight();
            int ww = resource.getIntrinsicWidth();
            int high = hh * w / ww;
            Rect rect = new Rect(0, 0, w, high);
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            gifDrawables.add(resource);
            resource.setCallback(textView);
            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
            resource.start();
            textView.setText(textView.getText());
            textView.invalidate();
        }
    }

    @SuppressWarnings("deprecation")
    private static class BitmapTarget extends SimpleTarget<Bitmap> {
        private final UrlDrawable urlDrawable;
        private final TextView textView;

        private BitmapTarget(UrlDrawable urlDrawable, TextView textView) {
            this.urlDrawable = urlDrawable;
            this.textView = textView;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
            Drawable drawable = new BitmapDrawable(textView.getContext().getResources(), resource);
            int screenWidth = CommonUtil.getScreenWidth(textView.getContext())
                    - textView.getPaddingRight()
                    - textView.getPaddingLeft();
            int drawableHeight = drawable.getIntrinsicHeight();
            int drawableWidth = drawable.getIntrinsicWidth();
            int targetHeight = drawableHeight * screenWidth / drawableWidth;
            Rect rect = new Rect(0, 0, screenWidth, targetHeight);
            drawable.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(drawable);
            textView.setText(textView.getText());
            textView.invalidate();
        }
    }
}

