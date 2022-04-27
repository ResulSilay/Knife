package io.github.mthli.knife.spans;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.style.ImageSpan;

public class ImageCustomSpan extends ImageSpan {
    private final Uri mUri;
    public ImageCustomSpan(Context context, Bitmap b, Uri uri) {
        super(context, b);
        mUri = uri;
    }

    @Override
    public String getSource() {
        return mUri.toString();
    }
}