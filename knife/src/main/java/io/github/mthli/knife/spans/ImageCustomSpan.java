package io.github.mthli.knife.spans;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.style.ImageSpan;

import io.github.mthli.knife.type.MediaImageType;

public class ImageCustomSpan extends ImageSpan {

    private Uri uri;
    private String filePath;
    private final MediaImageType mediaImageType;

    public ImageCustomSpan(Context context, Bitmap b, Uri uri, MediaImageType mediaImageType) {
        super(context, b);
        this.uri = uri;
        this.mediaImageType = mediaImageType;
    }

    public ImageCustomSpan(Context context, Bitmap b, String filePath, MediaImageType mediaImageType) {
        super(context, b);
        this.filePath = filePath;
        this.mediaImageType = mediaImageType;
    }

    @Override
    public String getSource() {
        if (mediaImageType == MediaImageType.FILE) {
            return filePath;
        }
        return uri.toString();
    }

    public MediaImageType getMediaImageType() {
        return mediaImageType;
    }
}