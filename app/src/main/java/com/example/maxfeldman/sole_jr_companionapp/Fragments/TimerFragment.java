package com.example.maxfeldman.sole_jr_companionapp.Fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxfeldman.sole_jr_companionapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends DialogFragment {

    private int myTime = 3;
    private TextView timerText;


    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        timerText = view.findViewById(R.id.timer_text);
        activateTimer(myTime);

        return view;
    }

    private void activateTimer(int time) {
        myTime = time;
        new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
                myTime--;
                if (myTime == 1) {
                    timerText.setTextColor(getResources().getColor(R.color.colorRed));
                    //speak("Hurry!,time is running up!",0.4f,0.9f);
                }
            }

            public void onFinish() {
                timerText.setText("0");
//                if(correct!=true)
//                {
//
//                    //test_execute(null, "sad", null);
//                }
                dismiss();
            }

        }.start();
    }

}
