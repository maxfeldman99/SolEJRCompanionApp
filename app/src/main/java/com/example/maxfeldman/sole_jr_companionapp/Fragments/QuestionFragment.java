package com.example.maxfeldman.sole_jr_companionapp.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.SpeechRecognitionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.TestFragment;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.R;

public class QuestionFragment extends Fragment implements DialogFragmentListener
{
    private int QUESTION_TIME = 20;
    private int myTime = 20;
    private TextView timerText;
    private ImageView questionImage;
    private Button answerButton;
    private String questionAnswer = "dog";
    final public static String IP = "192.168.43.12";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment,container,false);
        timerText = view.findViewById(R.id.question_timer_tv);
        questionImage = view.findViewById(R.id.question_image);
        answerButton = view.findViewById(R.id.question_answer_button);

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SpeechRecognitionFragment srf = new SpeechRecognitionFragment();
                TestFragment testFragment = (TestFragment) getActivity().getSupportFragmentManager().findFragmentByTag("TestFragment");
                srf.setListener(testFragment);
                srf.show(fragmentManager,"speech");
            }
        });

        activateTimer(QUESTION_TIME);




        return view;
    }

    private void activateTimer(int time) {
        myTime = time;
        new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
                myTime--;
                if (myTime == 5) {
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
            }

        }.start();
    }

    @Override
    public void onComplete(Object o, String sender) {
        switch (sender) {
            case "speech": {
                String result = (String) o;

                if (result.equals(questionAnswer)) {
                    NetworkController.getInstance().openSocket(IP,"happy");
                }else{
                    NetworkController.getInstance().openSocket(IP,"sad");
                }
                Log.d("TestFrag", result);
            }


            break;

            case "input": {
                String result = (String) o;

                Log.d("TestFrag", result);

                break;

            }
        }
    }

    @Override
    public void onError(String errorMsg) {

    }
}
