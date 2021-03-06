package com.example.ohad.dynamicex;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ohad on 11/12/2015.
 */
public class PictureSlide extends Slide {

    private Activity activity;

    private String path;
    private ArrayList<Button> buttons;
    private ArrayList<TextView> texts;
    private ArrayList<DynamicButton> dynamicButtons;
    private ArrayList<DynamicText> dynamicTexts;    // Not supported in desktop yet
    private boolean firstShow;
    private Rotation rotation;

    private MediaPlayer mp;


    public PictureSlide(Activity a, String path, ArrayList<DynamicButton> dynamicButtons, ArrayList<DynamicText> dynamicTexts, Rotation rotation) {
        activity = a;
        buttons = new ArrayList<>();
        texts = new ArrayList<>();
        firstShow = true;

        this.path = path;
        this.dynamicButtons = dynamicButtons != null ? dynamicButtons : new ArrayList<DynamicButton>();
        this.dynamicTexts = dynamicTexts != null ? dynamicTexts : new ArrayList<DynamicText>();
        this.rotation = rotation != null ? rotation : Rotation.NO_ROTATION; // portrait by default
    }

    public void show()
    {
        switch (rotation) {
            case NO_ROTATION:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case ROTATE_LEFT:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case ROTATE_RIGHT:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
            case UPSIDE_DOWN:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
        }


        RelativeLayout layout = (RelativeLayout)activity.findViewById(R.id.content_main);

        // Set background
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = decodeSampledBitmapFromFile(imgFile.getAbsolutePath(), 250, 250);
            setBackground(layout, myBitmap);
        } else {
            Toast.makeText(activity.getApplicationContext(), "Error! Picture file doesn't exist.", Toast.LENGTH_SHORT).show();
        }

        // On the first show of the slide - create the buttons and texts, otherwise - just show them
        if (firstShow)
            createElements(layout);
        else showElements();
    }


    private void createElements(RelativeLayout layout) {
        for (final DynamicText dynamicText : dynamicTexts) {
            TextView tv = createTV(dynamicText);
            texts.add(tv);
            layout.addView(tv);
        }

        for (final DynamicButton dynamicButton : dynamicButtons) {
            Button b = createButton(dynamicButton);
            buttons.add(b);
            layout.addView(b);
        }

        firstShow = false;
    }

    private void showElements() {
        for (final TextView tv : texts)
            tv.setVisibility(View.VISIBLE);

        for (final Button button : buttons)
            button.setVisibility(View.VISIBLE);
    }


    public void hide() {
        clearSound();

        for (final TextView tv : texts)
            tv.setVisibility(View.INVISIBLE);

        for (final Button button : buttons)
            button.setVisibility(View.INVISIBLE);
    }


    @SuppressWarnings("deprecation")
    private void setBackground(RelativeLayout rl, Bitmap bitmap)
    {
        if(Build.VERSION.SDK_INT >= 16)
            rl.setBackground(new BitmapDrawable(activity.getResources(), bitmap));
        else
            rl.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    private TextView createTV(final DynamicText text)
    {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(text.getStartX(), text.getStartY(), 0, 0);
        TextView tv = new TextView(activity);
        tv.setLayoutParams(lp);
        tv.setText(text.getText());
        tv.setTextSize(text.getTextSize());

        return tv;
    }

    private Button createButton(final DynamicButton button)
    {
        Button myButton = new Button(activity);
        myButton.setText(button.getText());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(button.getWidth(), button.getHeight());
        lp.setMargins(button.getStartX(), button.getStartY(), 0, 0); // right/left switched.. dont know why
        myButton.setLayoutParams(lp);

        // Add sound
        final Context context = activity;
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearSound();

                if (new File(button.getPath()).exists()) {
                    mp = MediaPlayer.create(context, Uri.parse(button.getPath()));
                    mp.start();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error! Sound file doesn't exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myButton;
    }

    protected void clearSound() {
        if (mp != null) {
            if (mp.isPlaying())
                mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }
    }


    //////////////////// Loading Large Bitmaps ///////////////////

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
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

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    {
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
