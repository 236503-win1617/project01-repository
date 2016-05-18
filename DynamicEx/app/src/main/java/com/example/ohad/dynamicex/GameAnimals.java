package com.example.ohad.dynamicex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;


import java.util.Random;

//author: Avi Kozokin
public class GameAnimals extends GameFragment {

    int[] arr = new int[11];
    int[] images = {R.drawable.tiger, R.drawable.panda, R.drawable.snake ,R.drawable.turtle,
            R.drawable.giraffe, R.drawable.dog, R.drawable.cat, R.drawable.monkey,
            R.drawable.kangaroo, R.drawable.lion};
    int[] sounds = {R.raw.tiger, R.raw.panda, R.raw.snake,R.raw.turtle,
            R.raw.giraffe, R.raw.dog, R.raw.cat, R.raw.monkey, R.raw.kangaroo,
            R.raw.lion};
    int[] rand_nums = new int[4];

    int numOfChoice;
    View mainView;

    public void initNums(View v){
        Random rand = new Random();
        int a;
        for (int i = 0; i < 11; i++) arr[i]=0;
        for (int i = 0; i <4; i++) {
            a = rand.nextInt(9);
            while(arr[a]==1){
                a = rand.nextInt(10);
            }
            arr[a] = 1;
        }
        int j=0;
        for(int i=0;i<4;i++){
            while(arr[j]!=1){
                j++;
            }
            rand_nums[i]=j;
            j++;
        }
        ImageView view = (ImageView)getView().findViewById(R.id.animal1);
        view.setImageResource(images[rand_nums[0]]);
        view.setTag(rand_nums[0]);
        view = (ImageView)getView().findViewById(R.id.animal2);
        view.setImageResource(images[rand_nums[1]]);
        view.setTag(rand_nums[1]);
        view = (ImageView)getView().findViewById(R.id.animal3);
        view.setImageResource(images[rand_nums[2]]);
        view.setTag(rand_nums[2]);
        view = (ImageView)getView().findViewById(R.id.animal4);
        view.setImageResource(images[rand_nums[3]]);
        view.setTag(rand_nums[3]);
        numOfChoice = rand_nums[rand.nextInt(4)];
    }

    public void sayNum(View v){
        MediaPlayer mp = MediaPlayer.create(getActivity(), sounds[numOfChoice]);
        mp.start();
    }

    public void checkNum(View view){
        int num = (Integer)view.getTag();
        if (num == numOfChoice){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
            dlgAlert.setMessage("You did it!");
            dlgAlert.setTitle("Good Job");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        } else {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
            dlgAlert.setMessage("Please try again.");
            dlgAlert.setTitle("Wrong Answer");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_animals_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initNums(mainView);
    }

}
