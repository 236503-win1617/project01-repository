package com.example.dan.iteration2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class GameNumbers extends Activity {


    int[] arr = new int[11];
    int[] images = {R.drawable.number0, R.drawable.number1, R.drawable.number2 ,R.drawable.number3,
            R.drawable.number4, R.drawable.number5, R.drawable.number6, R.drawable.number7,
            R.drawable.number8, R.drawable.number9};
    int[] sounds = {R.drawable.s0, R.drawable.s1, R.drawable.s2,R.drawable.s3,
            R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7, R.drawable.s8,
            R.drawable.s9, R.drawable.s10};
    int[] rand_nums = new int[4];

    int numOfChoice;

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
        ImageView view = (ImageView)findViewById(R.id.num1);
        view.setImageResource(images[rand_nums[0]]);
        view.setTag(rand_nums[0]);
        view = (ImageView)findViewById(R.id.num2);
        view.setImageResource(images[rand_nums[1]]);
        view.setTag(rand_nums[1]);
        view = (ImageView)findViewById(R.id.num3);
        view.setImageResource(images[rand_nums[2]]);
        view.setTag(rand_nums[2]);
        view = (ImageView)findViewById(R.id.num4);
        view.setImageResource(images[rand_nums[3]]);
        view.setTag(rand_nums[3]);
        numOfChoice = rand_nums[rand.nextInt(4)];
    }

    public void sayNum(View v){
        MediaPlayer mp = MediaPlayer.create(this, sounds[numOfChoice]);
        mp.start();
    }

    public void checkNum(View view){
        int num = (Integer)view.getTag();
        if (num == numOfChoice){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
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
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNums(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
