package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maxfeldman.sole_jr_companionapp.Fragments.QuestionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiChoiceFragment extends DialogFragment {

    private Button mulButton1;
    private Button mulButton2;
    private Button mulButton3;
    private Button mulButton4;
    private DialogFragmentListener listener;
    String answer;
    public String realAnswer;
    List<String> listOfAnswers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientationLandscape();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        mulButton1.setText(listOfAnswers.get(0));
        mulButton2.setText(listOfAnswers.get(1));
        mulButton3.setText(listOfAnswers.get(2));
        mulButton4.setText(listOfAnswers.get(3));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_multi_choice, container, false);

        mulButton1 = view.findViewById(R.id.button_1);
        mulButton2 = view.findViewById(R.id.button_2);
        mulButton3 = view.findViewById(R.id.button_3);
        mulButton4 = view.findViewById(R.id.button_4);
        //populateWithData(mulChoiceData);


        // Inflate the layout for this fragment



        //initializeTTS();

        mulButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = mulButton1.getText().toString();
                sendAnswer(answer);

            }
        });
        mulButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = mulButton2.getText().toString();
                sendAnswer(answer);
            }
        });
        mulButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = mulButton3.getText().toString();
                sendAnswer(answer);
            }
        });
        mulButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = mulButton4.getText().toString();
                sendAnswer(answer);
            }
        });




        return view;

    }

    private void sendAnswer(String answer){
        listener.onComplete(answer,"mulChoice");
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        QuestionFragment questionFragment = (QuestionFragment) listener;
        questionFragment.isPreformedClick = false;
    }

    private void setOrientationLandscape(){
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void setListener(DialogFragmentListener listener) {
        this.listener = listener;
    }

    public void populateWithData(String answers){
        final String[] tokens = answers.split(Pattern.quote(","));
        List<String> answersList = new ArrayList<>();
        realAnswer = tokens[0];
        for (int i = 0; i <4 ; i++) {

            if(tokens[i]!=null){
                answersList.add(tokens[i]);
            }else{
                answersList.add("");
            }

        }
        listOfAnswers = answersList;
        //Collections.shuffle(answersList);


    }

}
