package com.example.ohad.dynamicex;

/**
 * Created by Ohad on 02/06/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

public class GameBase extends GameFragment {
    private final int AMOUNT_OF_OBJECTS = 10;
    private final int AMOUNT_TO_CHOOSE = 4;

    // Fields that must be defined by the extending class
    int[] images = null;
    int[] sounds = null;
    int backgroundID;
    /////

    int[] imageViewIds = {R.id.object1, R.id.object3, R.id.object2, R.id.object4};
    boolean[] alreadyChosen = new boolean[AMOUNT_OF_OBJECTS];
    int[] chosenGroup = new int[AMOUNT_TO_CHOOSE];
    int chosenToSay;



    public void initNums(){
        for (int i = 0; i < AMOUNT_OF_OBJECTS; i++)
            alreadyChosen[i] = false;

        Random rand = new Random();
        int nextFreeIdx = 0;
        int randNum;
        for (int i = 0; i < AMOUNT_TO_CHOOSE; i++) {
            randNum = rand.nextInt(AMOUNT_OF_OBJECTS);
            while(alreadyChosen[randNum])
                randNum = rand.nextInt(AMOUNT_OF_OBJECTS);

            chosenGroup[nextFreeIdx++] = randNum;
            alreadyChosen[randNum] = true;
        }

        ImageView view;
        for (int i = 0; i < AMOUNT_TO_CHOOSE; i++) {
            view = (ImageView)getView().findViewById(imageViewIds[i]);
            view.setImageResource(images[chosenGroup[i]]);
            view.setTag(chosenGroup[i]);
        }

        chosenToSay = chosenGroup[rand.nextInt(AMOUNT_TO_CHOOSE)];
    }

    public void sayNum(){
        MediaPlayer mp = MediaPlayer.create(getActivity(), sounds[chosenToSay]);
        mp.start();
    }

    public void checkNum(View view){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);

        int num = (Integer)view.getTag();
        if (num == chosenToSay){
            dlgAlert.setMessage("You did it!");
            dlgAlert.setTitle("Good Job");
        } else {
            dlgAlert.setMessage("Please try again.");
            dlgAlert.setTitle("Wrong Answer");
        }
        dlgAlert.create().show();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_base_layout, container,false);
        view.setBackgroundResource(backgroundID);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        for (int id : imageViewIds) {
            getView().findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNum(v);
                }
            });
        }

        getView().findViewById(R.id.replay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNums();
            }
        });

        getView().findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayNum();
            }
        });

        initNums();
    }
}