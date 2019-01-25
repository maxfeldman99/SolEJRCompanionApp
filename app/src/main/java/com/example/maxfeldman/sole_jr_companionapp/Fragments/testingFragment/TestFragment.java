package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements VideoFragment.OnFragmentInteractionListener{


    TextToSpeech mTTS;
    private VideoFragment.OnFragmentInteractionListener mListener;
    final Fragment imageTestFragment = new ImageTestFragment();
    final Fragment inputTestFragment = new InputTestFragment();
    final Fragment speechTestRecognition = new SpeechRecognitionFragment();
    NetworkController networkController = NetworkController.getInstance();




    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);





//        final ArrayList<MotorRequest> motorRequests = new ArrayList<>();
//        MotorRequest motorRequest1 = new MotorRequest("1","A","200","100","0");
//        MotorRequest motorRequest2 = new MotorRequest("2","B","200","100","1000");
//        MotorRequest motorRequest3 = new MotorRequest("3","A","200","-100","0");
//        MotorRequest motorRequest4 = new MotorRequest("4","B","200","-100","1000");
//        MotorRequest motorRequest5 = new MotorRequest("5","A","200","100","0");
//        MotorRequest motorRequest6 = new MotorRequest("6","B","200","100","0");
//        motorRequests.add(motorRequest1);
//        motorRequests.add(motorRequest2);
//        motorRequests.add(motorRequest3);
//        motorRequests.add(motorRequest4);
//        motorRequests.add(motorRequest5);
//        motorRequests.add(motorRequest6);
//
//        final Request request = new Request("1",motorRequests,motorRequests.size());

        Gson gson = new Gson();
        final String tosend = gson.toJson("");




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



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NetworkController.getInstance().openSocket("192.168.137.136","happy");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkController.getInstance().openSocket("192.168.137.136","sad");

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NetworkController.getInstance().openSocket("192.168.137.136","..");
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
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder,imageTestFragment).commit();

            }
        });

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder,inputTestFragment).commit();
            }
        });

        buttonSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder,speechTestRecognition).commit();
            }
        });



        return view;
    }

    private void test_execute(String tosend,String face,String speechText){
        VideoFragment videoFragment = VideoFragment.newInstance(face);
        speak(speechText,0.2f,0.9f);
        NetworkController controller = NetworkController.getInstance();
        controller.sendData(tosend);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder,videoFragment).commit();

    }




    private void speak(String speechText,float pitch,float speed){
        mTTS.setSpeechRate(speed);
        mTTS.setPitch(pitch);
        mTTS.speak(speechText,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    public void onDestroy() {
        if(mTTS!=null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
