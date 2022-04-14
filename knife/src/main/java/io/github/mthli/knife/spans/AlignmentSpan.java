package io.github.mthli.knife.spans;

import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.style.ParagraphStyle;

import androidx.annotation.NonNull;

import io.github.mthli.knife.defaults.AligningDefault;

public interface AlignmentSpan extends ParagraphStyle {

    /**
     * Returns the alignment of the text.
     *
     * @return the text alignment
     */
    AligningDefault getAlignment();

    /**
     * Default implementation of the {@link android.text.style.AlignmentSpan}.
     * <p>
     * For example, a text written in a left to right language, like English, which is by default
     * aligned to the left, can be aligned opposite to the layout direction like this:
     * <pre>{@code SpannableString string = new SpannableString("Text with opposite alignment");
     *string.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0,
     *string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);}</pre>
     * <img src="{@docRoot}reference/android/images/text/style/ltralignmentspan.png" />
     * <figcaption>Align left to right text opposite to the layout direction.</figcaption>
     * <p>
     * A text written in a right to left language, like Hebrew, which is by default aligned to the
     * right, can be aligned opposite to the layout direction like this:
     * <pre>{@code SpannableString string = new SpannableString("טקסט עם יישור הפוך");
     *string.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0,
     *string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);}</pre>
     * <img src="{@docRoot}reference/android/images/text/style/rtlalignmentspan.png" />
     * <figcaption>Align right to left text opposite to the layout direction.</figcaption>
     */
    class Custom implements android.text.style.AlignmentSpan, ParcelableSpan {

        private final AligningDefault mAlignment;

        /**
         * Constructs a {@link Standard} from an alignment.
         */
        public Custom(@NonNull int align) {
            mAlignment = AligningDefault.get(align);
        }

        /**
         * Constructs a {@link Standard} from a parcel.
         */
        public Custom(@NonNull Parcel src) {
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

        @Override
        public Layout.Alignment getAlignment() {
            return null;
        }
    }
}
