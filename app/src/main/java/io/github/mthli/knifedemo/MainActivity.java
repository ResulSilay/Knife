package io.github.mthli.knifedemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import io.github.mthli.knife.KnifeText;
import io.github.mthli.knife.defaults.AligningDefault;
import io.github.mthli.knife.defaults.HeadingTagDefault;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements IPickResult {
    private static final String TEXT = "The story follows a young prince who visits various planets in space, including Earth, and addresses themes of loneliness, friendship, love, and loss. Despite its style as a children's book, The Little Prince makes observations about life, adults and human nature. The story follows a young prince who visits various planets in space, including Earth, and addresses themes of loneliness, friendship, love, and loss. Despite its style as a children's book, The Little Prince makes observations about life, adults and human nature. The story follows a young prince who visits various planets in space, including Earth, and addresses themes of loneliness, friendship, love, and loss. Despite its style as a children's book, The Little Prince makes observations about life, adults and human nature. The story follows a young prince who visits various planets in space, including Earth, and addresses themes of loneliness, friendship, love, and loss. Despite its style as a children's book, The Little Prince makes observations about life, adults and human nature.";
    private static final String BOLD = TEXT + TEXT + TEXT + TEXT + TEXT + "<b>Bold</b><br><br>";
    private static final String ITALIC = "<i>Italic</i><br><br>";
    private static final String UNDERLINE = "<u>Underline</u><br><br>";
    private static final String STRIKETHROUGH = "<s>Strikethrough</s><br><br>"; // <s> or <strike> or <del>
    private static final String BULLET = "<ul><li>Bullet</li></ul>";
    private static final String QUOTE = "<blockquote>Quote</blockquote>";
    private static final String LINK = "<a href=\"https://github.com/mthli/Knife\">Link</a><br><br>";
    private static final String EXAMPLE = TEXT + TEXT + TEXT + TEXT + TEXT + TEXT + TEXT;

    private KnifeText knife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        knife = findViewById(R.id.knife);

        // ImageGetter coming soon...
        knife.fromHtml(EXAMPLE);
        //knife.setSelection(knife.getEditableText().length());
        //knife.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        setupBold();
        setupItalic();
        setupUnderline();
        setupStrikethrough();
        setupBullet();
        setupQuote();
        setupLink();
        setupTextColor();
        setupHeadingTags();
        setupLine();
        setupAlign();
        setupImage();
        setupClear();
    }

    private void setupBold() {
        ImageButton bold = findViewById(R.id.bold);

        bold.setOnClickListener(v -> knife.bold(!knife.contains(KnifeText.FORMAT_BOLD)));

        bold.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_bold, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupItalic() {
        ImageButton italic = findViewById(R.id.italic);

        italic.setOnClickListener(v -> knife.italic(!knife.contains(KnifeText.FORMAT_ITALIC)));

        italic.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_italic, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupUnderline() {
        ImageButton underline = findViewById(R.id.underline);

        underline.setOnClickListener(v -> knife.underline(!knife.contains(KnifeText.FORMAT_UNDERLINED)));

        underline.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_underline, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupStrikethrough() {
        ImageButton strikethrough = findViewById(R.id.strikethrough);

        strikethrough.setOnClickListener(v -> knife.strikethrough(!knife.contains(KnifeText.FORMAT_STRIKETHROUGH)));

        strikethrough.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_strikethrough, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupBullet() {
        ImageButton bullet = findViewById(R.id.bullet);

        bullet.setOnClickListener(v -> knife.bullet(!knife.contains(KnifeText.FORMAT_BULLET)));


        bullet.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_bullet, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupQuote() {
        ImageButton quote = findViewById(R.id.quote);

        quote.setOnClickListener(v -> knife.quote(!knife.contains(KnifeText.FORMAT_QUOTE)));

        quote.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_quote, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupLink() {
        ImageButton link = findViewById(R.id.link);

        link.setOnClickListener(v -> showLinkDialog());

        link.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_insert_link, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupImage() {
        TextView image = findViewById(R.id.image);

        image.setOnClickListener(v -> startPictures());
        image.setOnLongClickListener(v -> {
            startCameraSelect();
            return false;
        });
    }

    private void setupClear() {
        ImageButton clear = findViewById(R.id.clear);

        clear.setOnClickListener(v -> knife.clearFormats());

        clear.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_format_clear, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void showLinkDialog() {
        final int start = knife.getSelectionStart();
        final int end = knife.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, (dialog, which) -> {
            String link = editText.getText().toString().trim();
            if (TextUtils.isEmpty(link)) {
                return;
            }

            // When KnifeText lose focus, use this method
            knife.link(link, start, end);
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> {
            // DO NOTHING HERE
        });

        builder.create().show();
    }

    private void setupTextColor() {
        TextView textColor = findViewById(R.id.txtColor);

        textColor.setOnClickListener(v -> knife.textColor("#ff0000", !knife.contains(KnifeText.TEXT_COLOR)));

        textColor.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_bold, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupHeadingTags() {
        TextView headingTag1 = findViewById(R.id.h1);
        TextView headingTag2 = findViewById(R.id.h2);
        TextView headingTag3 = findViewById(R.id.h3);
        TextView headingTag4 = findViewById(R.id.h4);
        TextView headingTag5 = findViewById(R.id.h5);
        TextView headingTag6 = findViewById(R.id.h6);

        headingTag1.setOnClickListener(v -> knife.headingTag(HeadingTagDefault.H1, !knife.contains(KnifeText.HEADING_TAG)));
        headingTag2.setOnClickListener(v -> knife.headingTag(HeadingTagDefault.H2, !knife.contains(KnifeText.HEADING_TAG)));
        headingTag3.setOnClickListener(v -> knife.headingTag(HeadingTagDefault.H3, !knife.contains(KnifeText.HEADING_TAG)));
        headingTag4.setOnClickListener(v -> knife.headingTag(HeadingTagDefault.H4, !knife.contains(KnifeText.HEADING_TAG)));
        headingTag5.setOnClickListener(v -> knife.headingTag(HeadingTagDefault.H5, !knife.contains(KnifeText.HEADING_TAG)));
        headingTag6.setOnClickListener(v -> knife.headingTag(HeadingTagDefault.H6, !knife.contains(KnifeText.HEADING_TAG)));
    }

    private void setupLine() {
        TextView line = findViewById(R.id.line);

        line.setOnClickListener(v -> {
            knife.lineColor(Color.parseColor("#000000"));
            knife.setLine(!knife.isLine());
        });

        line.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_quote, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupAlign() {
        TextView left = findViewById(R.id.left);
        TextView center = findViewById(R.id.center);
        TextView right = findViewById(R.id.right);
        TextView justify = findViewById(R.id.justify);

        left.setOnClickListener(v -> knife.aligning(AligningDefault.LEFT, !knife.contains(KnifeText.TEXT_ALIGN)));
        center.setOnClickListener(v -> knife.aligning(AligningDefault.CENTER, !knife.contains(KnifeText.TEXT_ALIGN)));
        right.setOnClickListener(v -> knife.aligning(AligningDefault.RIGHT, !knife.contains(KnifeText.TEXT_ALIGN)));
        justify.setOnClickListener(v -> knife.aligning(AligningDefault.JUSTIFY, !knife.contains(KnifeText.TEXT_ALIGN)));
    }

    PickSetup setup = new PickSetup();

    private void startPictures() {
        PickImageDialog.build(setup).show(this);
    }

    private void startCameraSelect() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }

    private void startImageSelect() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            Log.e("selectImagePath", "" + new Gson().toJson(r));

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());
            Log.e("selectImagePath(path)", r.getPath());

            knife.image(r.getPath());

            //If you want the Bitmap.
            //getImageView().setImageBitmap(r.getBitmap());

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    Log.e("selectImagePath", "(camera):" + new Gson().toJson(imageReturnedIntent));
                    //knife.image(selectedImage.toString(), !knife.contains(KnifeText.IMAGE));
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    Log.e("selectImagePath", "(image):" + new Gson().toJson(imageReturnedIntent));
                    //knife.image(selectedImage.getPath(), !knife.contains(KnifeText.IMAGE));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                knife.undo();
                break;
            case R.id.redo:
                knife.redo();
                break;
            case R.id.github:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_repo)));
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }
}
