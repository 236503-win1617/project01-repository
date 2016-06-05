package com.example.ohad.dynamicex;

public class GameAnimals extends GameBase {

    public GameAnimals() {
        images = new int[]
                {R.drawable.tiger, R.drawable.panda, R.drawable.snake, R.drawable.turtle,
                        R.drawable.giraffe, R.drawable.dog, R.drawable.cat, R.drawable.monkey,
                        R.drawable.kangaroo, R.drawable.lion};
        sounds = new int[]
                {R.raw.tiger, R.raw.panda, R.raw.snake, R.raw.turtle,
                        R.raw.giraffe, R.raw.dog, R.raw.cat, R.raw.monkey,
                        R.raw.kangaroo, R.raw.lion};

        backgroundID = R.drawable.animals_back;
    }

}
