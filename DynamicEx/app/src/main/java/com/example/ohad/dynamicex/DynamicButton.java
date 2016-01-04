package com.example.ohad.dynamicex;

import android.media.MediaPlayer;

/**
 * Created by Ohad on 11/12/2015.
 */
public class DynamicButton {

    private String text;
    private int start_x, start_y;
    private int width, height;
    private int soundId;
    MediaPlayer mp;

    public DynamicButton(String text, int start_x, int start_y, int width, int height, int soundId) {
        this.text = text;
        this.start_x = start_x;
        this.start_y = start_y;
        this.width = width;
        this.height = height;
        this.soundId = soundId;
        this.mp = null;
    }

    public String getText() {
        return text;
    }

    public int getStartX() {
        return start_x;
    }

    public int getStartY() {
        return start_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSoundId() {
        return soundId;
    }

    public MediaPlayer getMp() {
        return mp;
    }
}
