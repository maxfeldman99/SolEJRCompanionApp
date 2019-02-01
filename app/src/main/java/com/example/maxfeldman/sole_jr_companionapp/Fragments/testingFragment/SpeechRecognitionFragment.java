package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeechRecognitionFragment extends DialogFragment
{

    private SpeechRecognizer speechRecognizer;
    private TextView textViewOutput;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private RecognitionProgressView recognitionProgressView;
    int[] colors = new int[5];
    private String testAcc = "horse";
    private TextView accTextView;
    DialogFragmentListener listener;


    public SpeechRecognitionFragment()
    {
        // Required empty public constructor
    }

    public void setListener(DialogFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        setOrientationLandscape();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //setOrientationLandscape();
        View view = inflater.inflate(R.layout.fragment_speech_recognition, container, false);


         //speech recognition view
        colors[0] = ContextCompat.getColor(getContext(), R.color.color1);
        colors[1] = ContextCompat.getColor(getContext(), R.color.color2);
        colors[2] = ContextCompat.getColor(getContext(), R.color.color3);
        colors[3] = ContextCompat.getColor(getContext(), R.color.color4);
        colors[4] = ContextCompat.getColor(getContext(), R.color.color5);




        recognitionProgressView = view.findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                recognitionProgressView.setColors(colors);
//
//                recognitionProgressView.setBarMaxHeightsInDp(heights);
//
//                recognitionProgressView.setCircleRadiusInDp(2);
//
//
//
//                recognitionProgressView.setRotationRadiusInDp(10);

                recognitionProgressView.play();
                showResults(results);
            }
        });
        recognitionProgressView.setColors(colors);
//
//        recognitionProgressView.setBarMaxHeightsInDp(heights);
//
//        recognitionProgressView.setCircleRadiusInDp(2);
//
//        recognitionProgressView.setSpacingInDp(2);
//
//        recognitionProgressView.setIdleStateAmplitudeInDp(2);
//
//        recognitionProgressView.setRotationRadiusInDp(10);

        recognitionProgressView.play();

        recognitionProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),

                        Manifest.permission.RECORD_AUDIO)

                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermission();

                } else {

                    startRecognition();

                    recognitionProgressView.postDelayed(new Runnable() {

                        @Override

                        public void run() {

                            startRecognition();

                        }

                    }, 50);

                }

            }
        });


        return view;
    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName()); /////////
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizer.startListening(intent);
    }

    private void showResults(Bundle results){
        final String CONFIDENCE_SCORES;






        ArrayList<String> matches = results


                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        Log.e("speech",matches.toString());
        Toast.makeText(getContext(), matches.get(0), Toast.LENGTH_LONG).show();

        float [] confidence = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
        for (int i = 0; i < matches.size(); i++) {
            Log.d("blabla", "result=" + matches.get(i));
            Log.d("bla",String.valueOf(confidence[i]));
        }



        String recognized = matches.get(0);
        int acc = percentageOfTextMatch(testAcc,recognized);

        if(acc >= 85)
        {
            NetworkController.getInstance().openSocket("192.168.137.136","happy");
        }else
        {
            NetworkController.getInstance().openSocket("192.168.137.136","sad");

        }


        //accTextView.setText("Answer Accuracy: " + String.valueOf(acc) + " %");

        //textViewOutput.setText(matches.get(0));

        listener.onComplete(matches.get(0),"speech");

        recognitionProgressView.stop();
        recognitionProgressView.play();

        dismiss();
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),

                Manifest.permission.RECORD_AUDIO)) {

            Toast.makeText(getContext(), "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();

        } else {

            ActivityCompat.requestPermissions((Activity) getContext(),

                    new String[] { Manifest.permission.RECORD_AUDIO },

                    REQUEST_RECORD_AUDIO_PERMISSION_CODE);

        }

    }
    @Override
    public void onDestroy() {

        if (speechRecognizer != null) {

            speechRecognizer.destroy();

        }

        super.onDestroy();

    }

    public int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public int percentageOfTextMatch(String s0, String s1) {
        int percentage = 0;
        // Trim and remove duplicate spaces
        s0 = s0.trim().replaceAll("\\s+", " ");
        s1 = s1.trim().replaceAll("\\s+", " ");
        percentage=(int) (100 - (float) levenshteinDistance(s0, s1) * 100 / (float) (s0.length() + s1.length()));
        return percentage;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),

                Manifest.permission.RECORD_AUDIO)

                != PackageManager.PERMISSION_GRANTED) {

            requestPermission();

        } else {

            startRecognition();

            recognitionProgressView.postDelayed(new Runnable() {

                @Override

                public void run() {

                    startRecognition();

                }

            }, 50);

        }


    }

    private void setOrientationLandscape(){
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
