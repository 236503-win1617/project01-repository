package com.example.ohad.dynamicex;

import android.app.Activity;
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
        slides = new ArrayList<Slide>();
        currSlideIdx = 0;
        this.activity = activity;
    }

    public void addSlide(Slide slide) {
        slides.add(slide);
    }

    public void showFirstSlide() {
        slides.get(0).show(activity);
        ((TextView)activity.findViewById(R.id.slide_num)).setText("1/" + slides.size());
    }

    public boolean showNextSlide() {
        if (currSlideIdx >= slides.size() - 1)
            return false;

        slides.get(currSlideIdx).hide();
        slides.get(++currSlideIdx).show(activity);

        ((TextView)activity.findViewById(R.id.slide_num)).setText((currSlideIdx + 1) + "/" + slides.size());
        return true;
    }

    public boolean showPrevSlide() {
        if (currSlideIdx <= 0)
            return false;

        slides.get(currSlideIdx).hide();
        slides.get(--currSlideIdx).show(activity);

        ((TextView)activity.findViewById(R.id.slide_num)).setText((currSlideIdx + 1) + "/" + slides.size());
        return true;
    }

}
