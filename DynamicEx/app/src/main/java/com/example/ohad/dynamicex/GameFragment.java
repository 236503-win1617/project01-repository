package com.example.ohad.dynamicex;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Samuel on 28/03/2016.
 */
public class GameFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_fragment,container,false);
        return view;
    }

    public void sayNum(View v){}
    public void checkNum(View view){}
    public void initNums(View v){}

}
