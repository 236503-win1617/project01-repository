package Factories;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ohad on 11/12/2015.
 */
public class Lesson {

    public List<Slide> slides;
    public int currSlideIdx;

    public Lesson() {
        slides = new ArrayList<Slide>();
        currSlideIdx = 0;
    }

    public void addSlide(Slide slide) {
        slides.add(slide);
    }



}
