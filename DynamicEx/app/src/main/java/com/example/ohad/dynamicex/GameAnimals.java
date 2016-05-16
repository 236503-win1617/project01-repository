package com.example.ohad.dynamicex;

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


public class GameAnimals extends Activity {


    int[] arr = new int[11];
    int[] images = {R.drawable.tiger, R.drawable.panda, R.drawable.snake ,R.drawable.turtle,
            R.drawable.giraffe, R.drawable.dog, R.drawable.cat, R.drawable.monkey,
            R.drawable.kangaroo, R.drawable.lion};
    int[] sounds = {R.drawable.Tiger, R.drawable.Panda, R.drawable.Snake,R.drawable.Turtle,
            R.drawable.Giraffe, R.drawable.Dog, R.drawable.Cat, R.drawable.Monkey, R.drawable.Kangaroo,
            R.drawable.Lion};
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
        ImageView view = (ImageView)findViewById(R.id.color1);
        view.setImageResource(images[rand_nums[0]]);
        view.setTag(rand_nums[0]);
        view = (ImageView)findViewById(R.id.color2);
        view.setImageResource(images[rand_nums[1]]);
        view.setTag(rand_nums[1]);
        view = (ImageView)findViewById(R.id.color3);
        view.setImageResource(images[rand_nums[2]]);
        view.setTag(rand_nums[2]);
        view = (ImageView)findViewById(R.id.color4);
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
