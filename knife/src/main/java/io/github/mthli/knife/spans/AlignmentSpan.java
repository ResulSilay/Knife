package io.github.mthli.knife.spans;

import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

import androidx.annotation.NonNull;

import io.github.mthli.knife.defaults.AligningDefault;

public class AlignmentSpan extends CharacterStyle implements android.text.style.AlignmentSpan, ParcelableSpan {

    private final AligningDefault mAlignment;

    /**
     * Constructs a {@link Standard} from an alignment.
     */
    public AlignmentSpan(int mAlignment) {
        this.mAlignment = AligningDefault.get(mAlignment);
    }

    /**
     * Constructs a {@link Standard} from a parcel.
     */
    public AlignmentSpan(@NonNull Parcel src) {
        mAlignment = AligningDefault.valueOf(src.readString());
    }

    @Override
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public int getSpanTypeIdInternal() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    public void writeToParcelInternal(@NonNull Parcel dest, int flags) {
        dest.writeString(mAlignment.name());
    }


    public int getValue(){
        return this.mAlignment.getValue();
    }

    public AligningDefault getAlignmentData(){
        return this.mAlignment;
    }

    @Override
    public Layout.Alignment getAlignment() {
        return null;
    }

    @Override
    public void updateDrawState(TextPaint tp) {

    }
}