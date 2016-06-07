package com.example.ohad.dynamicex;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Samuel on 28/03/2016.
 */
public class GameFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.game_fragment,container,false);
        return view;
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            //do when hidden
//        } else {
//            //do when show
//            ((TextView)getView().findViewById(R.id.slide_num)).setText((0 + 1) + "/" +2);
//
//        }
//    }

}
