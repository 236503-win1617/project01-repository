package com.example.ohad.dynamicex;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by Ohad on 26/12/2015.
 */

// Video slides are not supported by desktop app yet - Here I'm already supporting them for later versions

public class VideoSlide extends Slide {

    protected VideoView mPlayer = null;
    protected Button mPlayButton = null;
    private Integer videoID = null;
    protected Integer videoPosition = 500;

    public VideoSlide(Integer videoID) {
        this.videoID = videoID;
    }


    @Override
    public void show(Activity activity) {

        RelativeLayout layout = (RelativeLayout)activity.findViewById(R.id.content_main);
        layout.setBackgroundColor(Color.BLACK);

        if (mPlayButton == null || mPlayer == null)
        {
            mPlayButton = new Button(activity.getApplicationContext());
            mPlayButton.setText("Play");
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(200, 200);
            lp.setMargins(200, 300, 0, 0);
            mPlayButton.setLayoutParams(lp);

            layout.addView(mPlayButton);

            mPlayer = new VideoView(activity.getApplicationContext());
            mPlayer.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));

            layout.addView(mPlayer);

            mPlayer.setVideoURI(Uri.parse("android.resource://" + activity.getPackageName() + "/" + videoID));
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
