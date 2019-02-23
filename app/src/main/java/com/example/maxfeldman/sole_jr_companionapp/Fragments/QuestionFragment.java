package com.example.maxfeldman.sole_jr_companionapp.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maxfeldman.sole_jr_companionapp.Controller.MainController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.InputTestFragment;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.RobotTTS;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.SpeechRecognitionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Models.Action;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkTest;
import com.example.maxfeldman.sole_jr_companionapp.Models.Scenario;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.mapzen.speakerbox.Speakerbox;

import java.util.Locale;

public class QuestionFragment extends Fragment implements DialogFragmentListener,RobotTTS
{
    private int QUESTION_TIME = 20;
    private int myTime = 20;
    private TextView timerText;
    private TextView currentQuestion;
    private ImageView questionImage;
    private Button answerButton;
    final public static String IP = "192.168.43.12";
    MainController mainController;
    private int questionCounter = 0;
    private String myAnswer;
    private String correctAnswer;
    private boolean isAnswerTrue;
    private Scenario[] scenarios;
    private String inputText;
    private Speakerbox speakerbox;

    private TextToSpeech mTTS = null;



    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
      
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientationLandscape();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.question_fragment, container, false);
        timerText = view.findViewById(R.id.question_timer_tv);
        currentQuestion = view.findViewById(R.id.question_tv);
        questionImage = view.findViewById(R.id.question_image);
        answerButton = view.findViewById(R.id.question_answer_button);
        mainController = MainController.getInstance();
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (inputText)
                {
                    case "speech":
                    {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        SpeechRecognitionFragment srf = new SpeechRecognitionFragment();
                        QuestionFragment testFragment = (QuestionFragment) getActivity().getSupportFragmentManager().findFragmentByTag("QuestionFragment");
                        srf.setListener(testFragment);
                        srf.show(fragmentManager, "speech");
                        break;
                    }

                    case "inputText":
                    {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        InputTestFragment itf = new InputTestFragment();
                        QuestionFragment testFragment = (QuestionFragment) getActivity().getSupportFragmentManager().findFragmentByTag("QuestionFragment");
                        itf.setListener(testFragment);
                        itf.show(fragmentManager,"inputText");

                    }
                }


            }
        });

        activateTimer(QUESTION_TIME);


        //scenarios = getScenariosFromLesson();

        //activateScenario(scenarios,0);

//        for (int i = 0; i < scenarios.length; i++)
//        {
//            if(questionCounter<4) {
//                correctAnswer = activateScenario(scenarios, questionCounter);
//
//            }
//        }



        getScenariosFromLesson();
        return view;
    }

    private void activateTimer(int time) {
        myTime = time;
        new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
                myTime--;
                if (myTime == 5) {
                    // timerText.setTextColor(getResources().getColor(R.color.colorRed));
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

                if (result.equals(correctAnswer)) {
                    mainController.sendDataToIp(IP, "happy");
                    questionCounter++;
                    activateScenario(scenarios,questionCounter );
                    //goToNextFragment();
                } else {
                    mainController.sendDataToIp(IP, "sad");
                    questionCounter++;
                     activateScenario(scenarios, questionCounter);
                    //goToNextFragment();
                }
                Log.d("TestFrag", result);
            }


            break;

            case "input": {
                String result = (String) o;

                if (result.equals(correctAnswer)) {
                    mainController.sendDataToIp(IP, "happy");
                    questionCounter++;
                    activateScenario(scenarios, questionCounter);
                    //goToNextFragment();
                } else {
                    mainController.sendDataToIp(IP, "sad");
                    questionCounter++;
                    activateScenario(scenarios, questionCounter);
                    //goToNextFragment();
                }

                Log.d("TestFrag", result);

                break;

            }
        }
    }

    public void goToNextFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        TimerFragment timerFragment = new TimerFragment();
        QuestionFragment testFragment = (QuestionFragment) getActivity().getSupportFragmentManager().findFragmentByTag("QuestionFragment");
        //timerFragment.setListener(testFragment);
        timerFragment.setCancelable(false);
        timerFragment.show(fragmentManager, "speech");

    }

    @Override
    public void onError(String errorMsg) {

    }


    private void initializeTTS() {
        mTTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "not supported language");

                    } else {
                        Log.e("TTS", "failed to initalize");
                    }
                }
            }
        });


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
        speak("can you say the name of this animal?",0.4f,0.9f);
    }

    private void setOrientationLandscape(){
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void getScenariosFromLesson()
    {
        final Lesson[] lessonFromUrl = new Lesson[1];
        final Scenario[] scenario = new Scenario[0];

        NetworkTest networkTest = NetworkTest.INSTANCE;

        networkTest.getLessonFromUrl(new updateFragment<Object>()
        {
            @Override
            public void updateData(Object data)
            {
                Lesson lesson = (Lesson) data;

                //System.out.println(lesson);

                scenarios = lesson.getScenarios();

                activateScenario(scenarios,0);


            }
        });

        //MainController.getInstance();
        //Scenario[] scenario = lesson.getScenarios();

    }

    private String activateScenario(Scenario[] scenario,int i){

            Action[] action = scenario[i].getActions();

            System.out.println();

            String effect = action[0].getEffect();
            String text = action[0].getTextOrWav();
            String expectedAnswer = scenario[i].getWaitFor().getExpectedAnswer().getInput();
            inputText = scenario[i].getWaitFor().getTypeOfInput();
            correctAnswer = expectedAnswer;
            currentQuestion.setText(text);
            questionImage.setImageDrawable(null); // to refresh the picture
            speakerBoxTTS(text);


            Glide.with(getContext()) // this section is for updating the image
                .load(effect)
                    .into(questionImage);



            return expectedAnswer;

        }

    @Override
    public void speakerBoxTTS(String question) {
        speakerbox = new Speakerbox(getActivity().getApplication());
        speakerbox.play(question);
    }

    @Override
    public void androidTTS(String question) {
        initializeTTS();
        speak(question,0.2f,0.9f);
    }
    }

