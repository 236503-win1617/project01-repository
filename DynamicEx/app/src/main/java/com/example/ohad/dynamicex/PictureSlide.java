package com.example.ohad.dynamicex;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ohad on 11/12/2015.
 */
public class PictureSlide extends Slide {

    String path;
    ArrayList<Button> buttons = new ArrayList<>();
    ArrayList<TextView> texts = new ArrayList<>();
    ArrayList<DynamicButton> dynamicButtons = new ArrayList<>();
    ArrayList<DynamicText> dynamicTexts = new ArrayList<>();
    boolean firstShow = true;

    public PictureSlide(String path, ArrayList<DynamicButton> dynamicButtons, ArrayList<DynamicText> dynamicTexts) {
        this.path = path;
        this.dynamicButtons = dynamicButtons;
        this.dynamicTexts = dynamicTexts;
    }

    public void show(Activity activity) {

        RelativeLayout layout = (RelativeLayout)activity.findViewById(R.id.content_main);
        //layout.removeAllViews();

        // Set background

        File imgFile = new File(path);
        if(imgFile.exists()){
            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap myBitmap = decodeSampledBitmapFromFile(imgFile.getAbsolutePath(), 250, 250);
            setBackground(activity, layout, myBitmap);
        }

        // On the first show of the slide - create the buttons and texts
        if (firstShow) {
            // Create all dynamic texts
            for (final DynamicText dynamicText : dynamicTexts) {
                TextView tv = createTV(activity, dynamicText);
                texts.add(tv);
                layout.addView(tv);
            }

            // Create all dynamic audio buttons
            for (final DynamicButton dynamicButton : dynamicButtons) {
                Button b = createButton(activity, dynamicButton);
                buttons.add(b);
                layout.addView(b);
            }

            firstShow = false;
        }

        // If elements are already created, just show them
        else {
            for (final TextView tv : texts)
                tv.setVisibility(View.VISIBLE);

            for (final Button button : buttons)
                button.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        for (final TextView tv : texts)
            tv.setVisibility(View.INVISIBLE);

        for (final Button button : buttons)
            button.setVisibility(View.INVISIBLE);
    }


    @SuppressWarnings("deprecation")
    private void setBackground(Activity activity, RelativeLayout rl, Bitmap bitmap) {
        if(Build.VERSION.SDK_INT >= 16)
            rl.setBackground(new BitmapDrawable(activity.getResources(), bitmap));
        else
            rl.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    private TextView createTV(Activity activity, final DynamicText text) {

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(text.getStartX(), text.getStartY(), 0, 0);
        TextView tv = new TextView(activity.getApplicationContext());
        tv.setLayoutParams(lp);
        tv.setText(text.getText());
        tv.setTextSize(text.getTextSize());

        return tv;
    }

    private Button createButton(Activity activity, final DynamicButton button) {

        Button myButton = new Button(activity.getApplicationContext());
        myButton.setText(button.getText());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(button.getWidth(), button.getHeight());
        lp.setMargins(button.getStartX(), button.getStartY(), 0, 0);
        myButton.setLayoutParams(lp);

        // Add sound
        final Context context = activity.getApplicationContext();
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MediaPlayer mp = button.getMp();
                if (mp != null) {
                    mp.reset();
                    mp.release();
                }
                mp = MediaPlayer.create(context, Uri.parse(button.getPath()));
                mp.start();
            }
        });

        return myButton;
    }


    //////////////////// Loading Large Bitmaps ///////////////////

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

}
