package com.example.maxfeldman.sole_jr_companionapp.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ClickableAreasImage;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import cn.iwgang.countdownview.CountdownView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class QuestionFragment extends Fragment implements DialogFragmentListener,RobotTTS,OnClickableAreaClickedListener,
        updateFragment
{
    private int QUESTION_TIME = 20;
    private int myTime = 20;
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
    private CountdownView countdownView;

    private TextToSpeech mTTS = null;

    private YouTubePlayerView youTubePlayerView;

    @Override
    public void updateData(Object data)
    {
        Lesson lesson = (Lesson) data;
        scenarios = lesson.getScenarios();
        activateScenario(scenarios,questionCounter);

    }


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
        currentQuestion = view.findViewById(R.id.question_tv);
        questionImage = view.findViewById(R.id.question_image);
        answerButton = view.findViewById(R.id.question_answer_button);
        countdownView = view.findViewById(R.id.question_timer_tv);
        mainController = MainController.getInstance();
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);

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

        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if(checkIndex(questionCounter+1)) {
                    goBackToMenu();
                }else{
                    activateScenario(scenarios, ++questionCounter);
                }
            }


        });
        countdownView.setOnCountdownIntervalListener(1000, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
                if(cv.getSecond()==10){
                    speakerBoxTTS("Hurry up! time is running up!");
                }
            }
        });
        //activateTimer(QUESTION_TIME);


        //scenarios = getScenariosFromLesson();

        //activateScenario(scenarios,0);

//        for (int i = 0; i < scenarios.length; i++)
//        {
//            if(questionCounter<4) {
//                correctAnswer = activateScenario(scenarios, questionCounter);
//
//            }
//        }



        //getScenariosFromLesson();

        return view;
    }

//    private void activateTimer(int time) {
//        myTime = time;
//        new CountDownTimer(time * 1000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                timerText.setText("" + millisUntilFinished / 1000);
//                myTime--;
//                if (myTime == 5) {
//                    timerText.setTextColor(getResources().getColor(R.color.colorRed));
//                    speakerBoxTTS("Hurry!,time is running up!");
//                }
//            }
//
//            public void onFinish() {
//                timerText.setText("0");
//                questionCounter++;
//                activateScenario(scenarios,questionCounter);
//            }
//
//        }.start();
//    }

    @Override
    public void onComplete(Object o, String sender) {
        switch (sender) {
            case "speech": {
                String result = (String) o;

                if (result.equals(correctAnswer)) {
                    mainController.sendDataToIp(IP, "happy");
                    questionCounter++;
                    if(checkIndex(questionCounter))
                    {
                        goBackToMenu();
                    }else
                    {
                        activateScenario(scenarios,questionCounter );

                    }
                    //goToNextFragment();
                } else {
                    mainController.sendDataToIp(IP, "sad");
                    questionCounter++;
                    if(checkIndex(questionCounter))
                    {
                        goBackToMenu();
                    }
                    else
                    {
                        activateScenario(scenarios,questionCounter );

                    }                    //goToNextFragment();
                }
                Log.d("TestFrag", result);
            }


            break;

            case "input": {
                String result = (String) o;

                if (result.equals(correctAnswer)) {
                    mainController.sendDataToIp(IP, "happy");
                    questionCounter++;
                    if(checkIndex(questionCounter))
                    {
                        goBackToMenu();
                    }
                    else
                    {
                        activateScenario(scenarios,questionCounter );

                    }
                    //goToNextFragment();
                } else {
                    mainController.sendDataToIp(IP, "sad");
                    questionCounter++;
                    if(checkIndex(questionCounter))
                    {
                        goBackToMenu();
                    }
                    else
                    {
                        activateScenario(scenarios,questionCounter );

                    }                    //goToNextFragment();
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

    private boolean checkIndex(int index){
        return (index>=scenarios.length);
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

    }

    private void setOrientationLandscape(){
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void getScenariosFromLesson(Lesson lesson)
    {
        final Lesson[] lessonFromUrl = new Lesson[1];
        final Scenario[] scenario = new Scenario[0];

        NetworkTest networkTest = NetworkTest.INSTANCE;


        //MainController.getInstance();
        //Scenario[] scenario = lesson.getScenarios();

    }

    private String activateScenario(Scenario[] scenario,int i){
            Action[] action = scenario[i].getActions();

            youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                @Override
                public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.pause();
                }
            });

            System.out.println();

            final String effect = action[0].getEffect();
            String text = action[0].getTextOrWav();
            String type = action[0].getWhatToPlay();
            String expectedAnswer = scenario[i].getWaitFor().getExpectedAnswer().getInput();
            inputText = scenario[i].getWaitFor().getTypeOfInput();
            correctAnswer = expectedAnswer;
            currentQuestion.setText(text);
            questionImage.setImageDrawable(null); // to refresh the picture

            final Long time = Long.valueOf(action[0].getTimeForAction());


        speakerBoxTTS(text);

        if(type.equals("youtube"))
        {
            youTubePlayerView.setVisibility(View.VISIBLE);

            questionImage.setVisibility(View.GONE);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener()
            {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer)
                {
                    youTubePlayer.loadVideo(effect,0);
                    youTubePlayer.play();
                    countdownView.start(time*1000);
                }

            });



        }else
        {
            youTubePlayerView.setVisibility(View.GONE);

            questionImage.setVisibility(View.VISIBLE);

            Glide.with(getContext()) // this section is for updating the image
                    .load(effect)
                    .into(questionImage);
            countdownView.start(time*1000);

        }

            //setUpSpotClick();
            return expectedAnswer;

        }

        private void goBackToMenu(){

            questionCounter = 0;
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            MenuFragment menuFragment = new MenuFragment();
            fragmentManager.beginTransaction().replace(R.id.SplashActivity,menuFragment,"menuFragment").commitNow();

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

    @Override
    public void onClickableAreaTouched(Object item) {
//        if (item instanceof Character) {
//            String text = ((Character) item).getFirstName() + " " + ((Character) item).getLastName();
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//        }
        if (item instanceof String) {
            String text = "test";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpSpotClick(){


            questionImage.setImageResource(R.drawable.flamingo);

            ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(questionImage), this);
            // Initialize your clickable area list
            List<ClickableArea> clickableAreas = new ArrayList<>();

            // Define your clickable areas
            // parameter values (pixels): (x coordinate, y coordinate, width, height) and assign an object to it
            clickableAreas.add(new ClickableArea(500, 200, 125, 200, new String("ssss")));
            clickableAreas.add(new ClickableArea(400, 200, 125, 200, new String("sss")));
            clickableAreas.add(new ClickableArea(300, 200, 125, 200, new String("ss")));
            //clickableAreas.add(new ClickableArea(600, 440, 130, 160, new Character("Bart", "Simpson")));

            // Set your clickable areas to the image
            clickableAreasImage.setClickableAreas(clickableAreas);


    }


}

