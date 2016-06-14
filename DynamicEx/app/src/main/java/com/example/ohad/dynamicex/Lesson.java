package com.example.ohad.dynamicex;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ohad on 11/12/2015.
 */
public class Lesson {

    List<Slide> slides;
    int currSlideIdx;
    Activity activity;

    public Lesson(Activity activity) {
        slides = new ArrayList<>();
        currSlideIdx = 0;
        this.activity = activity;
    }

    protected void addSlide(Slide slide) {
        slides.add(slide);
    }

    protected void showFirstSlide() {
        slides.get(0).show();
        updateSlideNum();
    }

    protected boolean showNextSlide() {
        if (currSlideIdx >= slides.size() - 1)
            return false;

        slides.get(currSlideIdx).hide();
        slides.get(++currSlideIdx).show();
        updateSlideNum();
        return true;
    }

    protected boolean showPrevSlide() {
        if (currSlideIdx <= 0)
            return false;

        slides.get(currSlideIdx).hide();
        slides.get(--currSlideIdx).show();
        updateSlideNum();
        return true;
    }

    protected void clearSounds() {
        for (Slide s : slides) {
            if (s instanceof PictureSlide) {
                PictureSlide ps = (PictureSlide)s;
                ps.clearSound();
            }
        }
    }

    private void updateSlideNum() {
        ((TextView)activity.findViewById(R.id.slide_num)).setText((currSlideIdx + 1) + "/" + slides.size());
    }
}
