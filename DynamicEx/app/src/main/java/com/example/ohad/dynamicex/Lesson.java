package com.example.ohad.dynamicex;

import android.app.Activity;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

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


        // ** TODO: Remove

//        String p = Environment.getExternalStorageDirectory().getPath();
//        //Toast.makeText(activity.getApplicationContext(), p, Toast.LENGTH_LONG).show();
//
//        ArrayList<DynamicText> texts1 = new ArrayList<>();
//        texts1.add(new DynamicText("Cat", 0, 0, 50));
//        texts1.add(new DynamicText("Meow", 70, 200, 80));
//        PictureSlide s1 = new PictureSlide(p + "/AAImages/cat.png", new ArrayList<DynamicButton>(), texts1);
//        slides.add(s1);
//
//        ArrayList<DynamicText> texts2 = new ArrayList<>();
//        texts2.add(new DynamicText("Dog", 20, 20, 80));
//        ArrayList<DynamicButton> buttons2 = new ArrayList<>();
//        buttons2.add( new DynamicButton("Push Me", 30, 200, 100, 100, "/AASounds/dog.wma") );
//        buttons2.add( new DynamicButton("Push Me", 300, 300, 200, 150,"/AASounds/tiger.wma") );
//        PictureSlide s2 = new PictureSlide(p + "/AAImages/dog.jpg", buttons2, texts2);
//        slides.add(s2);
//
//        ArrayList<DynamicText> texts3 = new ArrayList<>();
//        texts3.add(new DynamicText("Tiger", 10, 40, 30));
//        ArrayList<DynamicButton> buttons3 = new ArrayList<>();
//        buttons3.add( new DynamicButton("Push Me", 300, 300, 200, 150, "/AASounds/tiger.wma") );
//        PictureSlide s3 = new PictureSlide(p + "/AAImages/tiger.jpg", buttons3, texts3);
//        slides.add(s3);
//
//        VideoSlide s4 = new VideoSlide(R.raw.chapter_1_song);
//        slides.add(s4);
//
//        showFirstSlide();
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
