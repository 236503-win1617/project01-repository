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

public class GameMissing extends GameFragment {
    private final int AMOUNT_OF_OBJECTS = 10;
    private final int AMOUNT_TO_CHOOSE = 4;

    int[] images = new int[]
    {R.drawable.number0, R.drawable.number1, R.drawable.number2 ,R.drawable.number3,
            R.drawable.number4, R.drawable.number5, R.drawable.number6, R.drawable.number7,
            R.drawable.number8, R.drawable.number9};

    int[] initialImages = {R.id.object1, R.id.object3, R.id.object2, R.id.object4};
    int[] toFillImages = {R.id.missing1, R.id.missing2, R.id.missing3, R.id.missing4};
    boolean[] alreadyChosen = new boolean[AMOUNT_OF_OBJECTS];
    int[] chosenInitial = new int[AMOUNT_TO_CHOOSE];
    int[] chosenToFill = new int[AMOUNT_TO_CHOOSE];

    int chosenToHide;

    public void initNums(){
        getView().findViewById(R.id.chooseMissing).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.play).setVisibility(View.VISIBLE);

        for (int i = 0; i < AMOUNT_OF_OBJECTS; i++)
            alreadyChosen[i] = false;

        Random rand = new Random();
        int randNum;

        // Choose the 4 numbers that will be shown at the beginning
        for (int i = 0; i < AMOUNT_TO_CHOOSE; i++) {
            randNum = rand.nextInt(AMOUNT_OF_OBJECTS);
            while(alreadyChosen[randNum])
                randNum = rand.nextInt(AMOUNT_OF_OBJECTS);

            chosenInitial[i] = randNum;
            alreadyChosen[randNum] = true;
        }

        chosenToHide = chosenInitial[rand.nextInt(AMOUNT_TO_CHOOSE)];

        int hidedNumIdx = rand.nextInt(AMOUNT_TO_CHOOSE);
        chosenToFill[hidedNumIdx] = chosenToHide;

        // Choose 3 more numbers that the kid has to choose which would fill the hole
        for (int i = 0; i < AMOUNT_TO_CHOOSE; i++) {
            if (i == hidedNumIdx)
                continue;
            randNum = rand.nextInt(AMOUNT_OF_OBJECTS);
            while(alreadyChosen[randNum])
                randNum = rand.nextInt(AMOUNT_OF_OBJECTS);

            chosenToFill[i] = randNum;
            alreadyChosen[randNum] = true;
        }


        ImageView view;
        for (int i = 0; i < AMOUNT_TO_CHOOSE; i++) {
            view = (ImageView)getView().findViewById(initialImages[i]);
            view.setTag(chosenInitial[i]);
            view.setImageResource(images[chosenInitial[i]]);

            view = (ImageView)getView().findViewById(toFillImages[i]);
            view.setImageResource(images[chosenToFill[i]]);
            view.setTag(chosenToFill[i]);
            view.setVisibility(View.INVISIBLE);
        }

    }

    public void say(){

        getView().findViewById(R.id.play).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.replay).setVisibility(View.INVISIBLE);

        mp = MediaPlayer.create(getActivity(), R.raw.close_your_eyes);
        if(sound_flag==1) {
            mp.start();
        }

        new Thread() {
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int id : initialImages) {
                                    ImageView v = (ImageView)getView().findViewById(id);
                                    if ((Integer)v.getTag() == chosenToHide) {
                                        v.setImageResource(R.drawable.question_mark);
                                        break;
                                    }
                                }

                                getView().findViewById(R.id.chooseMissing).setVisibility(View.VISIBLE);
                                for (int i = 0; i < AMOUNT_TO_CHOOSE; i++)
                                    getView().findViewById(toFillImages[i]).setVisibility(View.VISIBLE);

                                getView().findViewById(R.id.replay).setVisibility(View.VISIBLE);

                                mp = MediaPlayer.create(getActivity(), R.raw.open_your_eyes);
                                if(sound_flag==1) {
                                    mp.start();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();


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
        if (num == chosenToHide){
            dlgAlert.setMessage("You did it!");
            dlgAlert.setTitle("Good Job");
            LayoutInflater factory = LayoutInflater.from(getActivity());
            View viewAlrt = factory.inflate(R.layout.good, null);
            dlgAlert.setView(viewAlrt);
            mp = MediaPlayer.create(getActivity(), R.raw.whatcha_say);
        } else {
            dlgAlert.setMessage("Please try again.");
            dlgAlert.setTitle("Wrong Answer");
            LayoutInflater factory = LayoutInflater.from(getActivity());
            View viewAlrt = factory.inflate(R.layout.bad, null);
            dlgAlert.setView(viewAlrt);
            mp = MediaPlayer.create(getActivity(), R.raw.sitar);
        }
        mp.start();
        dlgAlert.create().show();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_missing_layout, container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        for (int id : toFillImages) {
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
                say();
            }
        });

        initNums();
    }
}