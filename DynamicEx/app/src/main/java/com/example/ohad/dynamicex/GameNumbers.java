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
public class GameNumbers extends GameFragment {


    int[] arr = new int[11];
    int[] images = {R.drawable.number0, R.drawable.number1, R.drawable.number2 ,R.drawable.number3,
            R.drawable.number4, R.drawable.number5, R.drawable.number6, R.drawable.number7,
            R.drawable.number8, R.drawable.number9};
    int[] sounds = {R.raw.s0, R.raw.s1, R.raw.s2,R.raw.s3,
            R.raw.s4, R.raw.s5, R.raw.s6, R.raw.s7, R.raw.s8,
            R.raw.s9, R.raw.s10};
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
        ImageView view = (ImageView)getView().findViewById(R.id.num1);
        view.setImageResource(images[rand_nums[0]]);
        view.setTag(rand_nums[0]);
        view = (ImageView)getView().findViewById(R.id.num2);
        view.setImageResource(images[rand_nums[1]]);
        view.setTag(rand_nums[1]);
        view = (ImageView)getView().findViewById(R.id.num3);
        view.setImageResource(images[rand_nums[2]]);
        view.setTag(rand_nums[2]);
        view = (ImageView)getView().findViewById(R.id.num4);
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
        View view = inflater.inflate(R.layout.game_numbers_layout,container,false);
        mainView = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initNums(mainView);
    }

}
