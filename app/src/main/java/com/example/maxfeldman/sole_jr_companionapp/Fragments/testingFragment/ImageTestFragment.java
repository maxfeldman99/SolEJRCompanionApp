package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.google.gson.Gson;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageTestFragment extends DialogFragment
{

    private TextToSpeech mTTS = null;
    private int counter = 3;
    private String taskSpeech = "Choose the sun color";

    private DialogFragmentListener listener;




    public ImageTestFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_test, container, false);
        ImageButton button1 = view.findViewById(R.id.im_btn_1);
        ImageButton button2 = view.findViewById(R.id.im_btn_2);
        ImageButton button3 = view.findViewById(R.id.im_btn_3);
        ImageButton button4 = view.findViewById(R.id.im_btn_4);

        Gson gson = new Gson();
        final String tosend = gson.toJson("");


        initializeTTS();



        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                listener.onComplete("1","multiImage");
                dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                listener.onComplete("2","multiImage");
                dismiss();


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onComplete("3","multiImage");
                dismiss();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onComplete("4","multiImage");
                dismiss();

            }
        });


        return view;
    }


    public void setListener(DialogFragmentListener listener) {
        this.listener = listener;
    }

    private void test_execute(String tosend, String face, String speechText){
        //VideoFragment videoFragment = VideoFragment.newInstance(face);
        //speak(speechText,0.2f,0.9f);
        NetworkController controller = NetworkController.getInstance();
        controller.sendData(tosend);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder,videoFragment).commit();

    }

    private void checkCounterStatus(String tosend){
        if(counter!=0) {
            speak("try again", 0.2f, 0.9f);
        }else{
            counter = 3;
            test_execute(tosend,"sad","Maybe next time");
        }
    }

    private void speak(String speechText,float pitch,float speed){
        mTTS.setSpeechRate(speed);
        mTTS.setPitch(pitch);
        mTTS.speak(speechText,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mTTS==null){
            initializeTTS();
        }
        speak(taskSpeech,0.4f,0.9f);
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
