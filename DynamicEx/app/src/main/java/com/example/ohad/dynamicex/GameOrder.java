package com.example.ohad.dynamicex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Random;

public class GameOrder extends GameFragment {

    private int AMOUNT_OF_OBJECTS = 10;
    private final int AMOUNT_TO_CHOOSE = 4;

    int[] sounds = {R.raw.s0, R.raw.s1, R.raw.s2,R.raw.s3,
            R.raw.s4, R.raw.s5, R.raw.s6, R.raw.s7, R.raw.s8,
            R.raw.s9, R.raw.s10, R.raw.s11, R.raw.s12, R.raw.s13, R.raw.s14, R.raw.s15, R.raw.s16,
            R.raw.s17, R.raw.s18, R.raw.s19, R.raw.s20};

    int[] editTextIds ={R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4};

    boolean[] alreadyChosen;
    int[] chosenGroup = new int[AMOUNT_TO_CHOOSE];

    int playlistPos = 0;
    MediaPlayer mp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        int maxNum = bundle.getInt("param", 10); // 10 - default maxNum
        AMOUNT_OF_OBJECTS = maxNum+1;
        alreadyChosen = new boolean[AMOUNT_OF_OBJECTS];
    }


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

        for (int id : editTextIds) {
            EditText et = (EditText)getView().findViewById(id);
            et.setText("");
        }
    }

    public void sayNum() {
        playlistPos = 0;
        mp = MediaPlayer.create(getActivity(), sounds[chosenGroup[playlistPos]]);
        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playlistPos++;
                if (playlistPos == chosenGroup.length)
                    return;

                AssetFileDescriptor afd = getResources().openRawResourceFd(sounds[chosenGroup[playlistPos]]);

                try {
                    mp.reset();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    mp.prepare();
                    mp.start();
                    afd.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void checkNum(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);

        for (int i=0; i < AMOUNT_TO_CHOOSE; ++i) {
            EditText v = (EditText)getView().findViewById(editTextIds[i]);
            String val = v.getText().toString();
            if (val.equals("") || Integer.parseInt(val) != chosenGroup[i]) {
                dlgAlert.setMessage("Please try again.");
                dlgAlert.setTitle("Wrong Answer");
                dlgAlert.create().show();
                return;
            }
        }

        dlgAlert.setMessage("You did it!");
        dlgAlert.setTitle("Good Job");
        dlgAlert.create().show();
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_order_layout, container,false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNum();
            }
        });

        getView().findViewById(R.id.playAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNums();
            }
        });

        getView().findViewById(R.id.sayAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayNum();
            }
        });

        initNums();
    }

}
