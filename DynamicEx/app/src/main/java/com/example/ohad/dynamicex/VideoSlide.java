package com.example.ohad.dynamicex;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by Ohad on 26/12/2015.
 */

// Video slides are not supported by desktop app yet - Here I'm already supporting them for later versions

public class VideoSlide extends Slide {

    private Activity activity;

    protected VideoView mPlayer = null;
    protected Button mPlayButton = null;
    private String path = null;
    protected Integer videoPosition = 500;

    public VideoSlide(Activity a, String path) {
        this.activity = a;
        this.path = path;
    }


    @Override
    public void show() {

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        RelativeLayout layout = (RelativeLayout)activity.findViewById(R.id.content_main);
        layout.setBackgroundColor(Color.BLACK);

        if (mPlayButton == null || mPlayer == null)
        {
            mPlayer = new VideoView(activity);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mPlayer.setLayoutParams(lp);
            layout.addView(mPlayer);

            mPlayer.setVideoURI(Uri.parse(path));
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(videoPosition);
                }
            });
            mPlayer.setOnCompletionListener(mPlayerOnComplete);
            mPlayer.setOnTouchListener(mPlayerOnTouch);

            mPlayButton = new Button(activity);
            mPlayButton.setText("Play Video");
            lp = new RelativeLayout.LayoutParams(250, 250);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mPlayButton.setLayoutParams(lp);
            layout.addView(mPlayButton);

            mPlayButton.setOnClickListener(mButtonClick);
        }

        else {
            mPlayButton.setVisibility(View.VISIBLE);
            mPlayer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hide() {
        mPlayButton.setVisibility(View.INVISIBLE);
        mPlayer.setVisibility(View.INVISIBLE);
    }

    private MediaPlayer.OnCompletionListener mPlayerOnComplete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mPlayButton.setVisibility(View.VISIBLE);
            //finish();
        }
    };

    private View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPlayer.start();
            mPlayButton.setVisibility(View.GONE); // hide button once playback starts
        }
    };

    private Integer nonNegative(Integer value) {
        Integer backwardsFactor = 500;
        value -= backwardsFactor;
        if (value < 0) {
            value = 0;
        }
        return value;
    }

    protected View.OnTouchListener mPlayerOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mPlayer.isPlaying()) {
                videoPosition = nonNegative(mPlayer.getCurrentPosition());
                mPlayer.pause();
                mPlayButton.setVisibility(View.VISIBLE);
            }
            return false;
        }
    };

}
