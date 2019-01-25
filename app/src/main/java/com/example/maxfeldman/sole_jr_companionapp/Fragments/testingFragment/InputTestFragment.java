package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputTestFragment extends Fragment {

    private String answer = "android";
    private final int QUESTION_TIME = 15;
    private TextView timerText;
    private int myTime;
    private boolean correct = false;
    private TextToSpeech mTTS;
    private String taskText = "spell correct the content of the image";

    NetworkController networkController = NetworkController.getInstance();

    public InputTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_test, container, false);
        final EditText answerEditText = view.findViewById(R.id.question_answer_text);
        timerText = view.findViewById(R.id.timer_text);
        Button submitBtn = view.findViewById(R.id.submit_btn);
        TextView question = view.findViewById(R.id.question_text);
        activateTimer(QUESTION_TIME);


        initializeTTS();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerEditText.getText().toString().equals(answer)) {
                    correct=true;
                    test_execute(null, "happy", null);

                    //networkController.sayTTS("All Right!!",getActivity().getApplication());


                } else {

                }
            }
        });



        return view;
    }

    private void activateTimer(int time) {
        myTime = time;
        new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
                myTime--;
                if (myTime == 5) {
                    //timerText.setTextColor(getResources().getColor(R.color.colorRed));
                    speak("Hurry!,time is running up!",0.4f,0.9f);
                }
            }

            public void onFinish() {
                timerText.setText("0");
                if(correct!=true)
                {

                    test_execute(null, "sad", null);
                }
            }

        }.start();
    }

    private void test_execute(String tosend, String face, String speechText) {
        VideoFragment videoFragment = VideoFragment.newInstance(face);
        TestFragment testFragment = new TestFragment();
        //speak(speechText,0.2f,0.9f);
        NetworkController controller = NetworkController.getInstance();
        NetworkController.getInstance().openSocket("192.168.137.136",face);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder, testFragment).commit();
    }

    private void speak(String speechText,float pitch,float speed)
    {


        //networkController.sayTTS("Testig",getActivity().getApplication());

        //mTTS.setSpeechRate(speed);
       // mTTS.setPitch(pitch);
       // mTTS.speak(speechText, TextToSpeech.QUEUE_FLUSH,null);


    }



    @Override
    public void onResume() {
        super.onResume();
        if(mTTS==null){
            initializeTTS();
        }
        speak(taskText,0.4f,0.9f);
    }

    private void initializeTTS(){
        mTTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.US);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","not supported language");

                    }else{
                        Log.e("TTS","failed to initalize");
                    }
                }
            }
        });
    }

}



