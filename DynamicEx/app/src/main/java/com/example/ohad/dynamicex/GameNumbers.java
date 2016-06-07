package com.example.ohad.dynamicex;

public class GameNumbers extends GameBase {

    public GameNumbers() {
        images = new int[]
                {R.drawable.number0, R.drawable.number1, R.drawable.number2 ,R.drawable.number3,
                R.drawable.number4, R.drawable.number5, R.drawable.number6, R.drawable.number7,
                R.drawable.number8, R.drawable.number9};
        sounds = new int[]
                {R.raw.s0, R.raw.s1, R.raw.s2,R.raw.s3,
                R.raw.s4, R.raw.s5, R.raw.s6, R.raw.s7, R.raw.s8,
                R.raw.s9, R.raw.s10};

        backgroundID = R.drawable.numbers_back;
    }



}
