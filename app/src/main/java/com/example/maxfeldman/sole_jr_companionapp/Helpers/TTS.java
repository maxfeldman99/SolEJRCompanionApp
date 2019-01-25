package com.example.maxfeldman.sole_jr_companionapp.Helpers;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by MAX FELDMAN on 25/01/2019.
 */

public class TTS implements Runnable {

    private TextToSpeech mTTS = null;
    private String text;


    public TTS(Context context, String text){
        this.text = text;
        mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
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



    @Override
    public void run() {
        mTTS.setSpeechRate(0.2f);
        mTTS.setPitch(0.9f);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }
}
