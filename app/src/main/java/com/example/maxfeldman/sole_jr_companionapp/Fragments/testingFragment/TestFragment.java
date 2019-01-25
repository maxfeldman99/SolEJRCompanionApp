package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.google.gson.Gson;

import java.util.Locale;



public class TestFragment extends Fragment implements VideoFragment.OnFragmentInteractionListener,
        DialogFragmentListener
{

    TextToSpeech mTTS;
    private VideoFragment.OnFragmentInteractionListener mListener;
    final Fragment imageTestFragment = new ImageTestFragment();
    final Fragment inputTestFragment = new InputTestFragment();
    //final Fragment speechTestRecognition = new SpeechRecognitionFragment();
    NetworkController networkController = NetworkController.getInstance();

    final public static int SPEECH_REOGNITION_REQUEST = 1;

    final public static String IP = "192.168.43.12";


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);

        

        Gson gson = new Gson();



        Button button1 = view.findViewById(R.id.test_btn1);
        Button button2 = view.findViewById(R.id.test_btn2);
        Button button3 = view.findViewById(R.id.test_btn3);
        final EditText speechEditText = view.findViewById(R.id.speech_edit_text);
        Button buttonSpeak = view.findViewById(R.id.test_btn_tts);
        Button buttonImage = view.findViewById(R.id.image_test_btn);
        Button buttonInput = view.findViewById(R.id.input_test_btn);
        Button buttonSpeech = view.findViewById(R.id.speech_rec_btn);

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



        /*
            Send Emotion To Robot
         */
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NetworkController.getInstance().openSocket(IP,"happy");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkController.getInstance().openSocket(IP,"sad");

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NetworkController.getInstance().openSocket(IP,"..");
            }
        });




        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = null;
                if(speechEditText.getText()!=null) {
                     text = speechEditText.getText().toString();
                }
                speak(text,0.2f,0.9f);
            }
        });

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                ImageTestFragment imageTestFragment = new ImageTestFragment();

                TestFragment testFragment = (TestFragment) getActivity().getSupportFragmentManager().findFragmentByTag("TestFragment");

                imageTestFragment.setListener(testFragment);

                imageTestFragment.show(fragmentManager,"input");

            }
        });

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                InputTestFragment inputTestFragment = new InputTestFragment();

                TestFragment testFragment = (TestFragment) getActivity().getSupportFragmentManager().findFragmentByTag("TestFragment");

                inputTestFragment.setListener(testFragment);

                inputTestFragment.show(fragmentManager,"input");


            }
        });

        buttonSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                SpeechRecognitionFragment srf = new SpeechRecognitionFragment();

                TestFragment testFragment = (TestFragment) getActivity().getSupportFragmentManager().findFragmentByTag("TestFragment");

                srf.setListener(testFragment);

                srf.show(fragmentManager,"speech");

            }
        });



        return view;
    }




    private void speak(String speechText,float pitch,float speed){
        mTTS.setSpeechRate(speed);
        mTTS.setPitch(pitch);
        mTTS.speak(speechText,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    public void onDestroy()
    {
        if(mTTS!=null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onComplete(Object o, String sender)
    {
        switch (sender)
        {
            case "speech":
            {
                String result = (String) o;

                Log.d("TestFrag",result);

                break;
            }

            case "input":
            {
                String result = (String) o;

                Log.d("TestFrag",result);

                break;

            }



        }
    }

    @Override
    public void onError(String errorMsg)
    {

    }
}
