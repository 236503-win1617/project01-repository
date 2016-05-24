package com.example.ohad.dynamicex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.LayoutInflater;

import java.util.Random;

public class GameOrder extends GameFragment {

    private final int CHOSEN_NUM_AMOUNT = 4;

    int[] chosen;
    int[] rand_nums = new int[CHOSEN_NUM_AMOUNT];

    int[] sounds = {R.raw.s0, R.raw.s1, R.raw.s2,R.raw.s3,
            R.raw.s4, R.raw.s5, R.raw.s6, R.raw.s7, R.raw.s8,
            R.raw.s9, R.raw.s10, R.raw.s11, R.raw.s12, R.raw.s13, R.raw.s14, R.raw.s15, R.raw.s16,
            R.raw.s17, R.raw.s18, R.raw.s19, R.raw.s20};


    int playlistPos = 0;
    int[] editTextIds ={R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4};
    MediaPlayer mp = null;
    int maxNum;
    View mainView;

    public void initNums(View v){

        chosen = new int[maxNum+1];

        Random rand = new Random();
        int randNum;
        for (int i = 0; i < maxNum+1; i++) chosen[i]=0;
        for (int i = 0; i < CHOSEN_NUM_AMOUNT; i++) {
            randNum = rand.nextInt(maxNum+1);
            while(chosen[randNum]==1){
                randNum = rand.nextInt(maxNum+1);
            }
            chosen[randNum] = 1;
        }

        int j=0;
        for(int i=0;i<4;i++){
            while(chosen[j]!=1){
                j++;
            }
            rand_nums[i]=j;
            j++;
        }

        for (int id : editTextIds) {
            EditText et = (EditText)getView().findViewById(id);
            et.setText("");
        }
    }

    public void setMaxNum(int maxNum){
        this.maxNum = maxNum;
    }

    public void sayNum(View v) {
        playlistPos = 0;
        mp = MediaPlayer.create(getActivity(), sounds[rand_nums[playlistPos]]);
        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playlistPos++;
                if (playlistPos == rand_nums.length)
                    return;

                AssetFileDescriptor afd = getResources().openRawResourceFd(sounds[rand_nums[playlistPos]]);

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

    private void wrongAnswer() {
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

    public void checkNum(View view){

        for (int i=0; i < CHOSEN_NUM_AMOUNT; ++i) {
            EditText v = (EditText)getView().findViewById(editTextIds[i]);
            String val = v.getText().toString();
            if (val.equals("") || Integer.parseInt(val) != rand_nums[i]) {
                wrongAnswer();
                return;
            }
        }

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
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_order_layout,container,false);
        mainView = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initNums(mainView);
    }

}
