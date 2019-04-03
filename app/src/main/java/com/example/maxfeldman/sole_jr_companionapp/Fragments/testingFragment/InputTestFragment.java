package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.QuestionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputTestFragment extends DialogFragment {

    private TextToSpeech mTTS;
    private DialogFragmentListener listener;


    NetworkController networkController = NetworkController.getInstance();

    public InputTestFragment() {
        // Required empty public constructor
    }

    public void setListener(DialogFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_test, container, false);
        final EditText answerEditText = view.findViewById(R.id.question_answer_text);
        Button submitBtn = view.findViewById(R.id.submit_btn);


        //initializeTTS();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String answer = answerEditText.getText().toString();

                listener.onComplete(answer,"input");
                dismiss();
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mTTS==null){
            initializeTTS();
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        QuestionFragment questionFragment = (QuestionFragment) listener;

        questionFragment.isPreformedClick = false;
    }

}



