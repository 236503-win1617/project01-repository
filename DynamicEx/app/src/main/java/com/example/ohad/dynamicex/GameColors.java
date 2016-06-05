package com.example.ohad.dynamicex;

public class GameColors extends GameBase {

    public GameColors() {
        images = new int[]
                {R.drawable.grey, R.drawable.yellow, R.drawable.purple ,R.drawable.blue,
                R.drawable.green, R.drawable.orange, R.drawable.red, R.drawable.pink,
                R.drawable.black, R.drawable.white};
        sounds = new int[]
                {R.raw.gray, R.raw.yellow, R.raw.purple, R.raw.blue,
                R.raw.green, R.raw.orange, R.raw.red, R.raw.pink, R.raw.black,
                R.raw.white};

        backgroundID = R.drawable.colors_back;

    }

}
