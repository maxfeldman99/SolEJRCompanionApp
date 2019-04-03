package com.example.maxfeldman.sole_jr_companionapp.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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
import com.example.maxfeldman.sole_jr_companionapp.Controller.KotlinNetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.InputTestFragment;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.RobotTTS;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.SpeechRecognitionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.DataListener;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;
import com.example.maxfeldman.sole_jr_companionapp.Models.Action;
import com.example.maxfeldman.sole_jr_companionapp.Models.DialogFragmentListener;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.Scenario;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.example.maxfeldman.sole_jr_companionapp.util.Utilities;
import com.mapzen.speakerbox.Speakerbox;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
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
    public static String IP = "192.168.43.12";
    MainController mainController;
    private int questionCounter = 0;
    private String myAnswer;
    private String correctAnswer;
    private boolean isAnswerTrue;
    private List<Scenario> scenarios;
    private String inputText;
    private Speakerbox speakerbox;
    private CountdownView countdownView;
    private TextView triesLeft;
    public boolean isPreformedClick = false;

    private TextToSpeech mTTS = null;

    private YouTubePlayerView youTubePlayerView;

    LottieAnimation lottieAnimation;

    FireBase fireBase;

    Scenario currentScenario;
    String nextcurrentScenario;
    int answersCounter;

    AppCompatActivity activity = Utilities.getInstance().currentActivity;

    boolean testLastScenario = false;


    @Override
    public void updateData(Object data, String type)
    {
        if(type.equals("lesson"))
        {

            Lesson lesson = (Lesson) data;
            //scenarios = lesson.getScenarios();
            Log.d("TAG", lesson.getTitle());
            //activateScenario((Scenario[]) scenarios.toArray(),questionCounter);

            final LottieAnimation tempLottieAnimation = new LottieAnimation();
            lottieAnimation = tempLottieAnimation;

            FragmentManager fragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();

            tempLottieAnimation.show(fragmentManager,"lottie");

            FireBase.getInstance().getScenario(lesson.getFirstScenarioName()
                    , new DataListener() {
                        @Override
                        public void onDataLoad(Object o)
                        {
                            lottieAnimation.dismiss();
                            Scenario scenario = (Scenario) o;
                            startScenario(scenario,false);
                        }
                    });

        }
        if(type.equals("finVideo"))
        {

            fireBase.getScenario(nextcurrentScenario, new DataListener()
            {
                @Override
                public void onDataLoad(Object o)
                {
                    lottieAnimation.dismiss();
                    Scenario nextScenario = (Scenario) o;

                    Log.d("onDataLoad",nextScenario.toString());

                        startScenario(nextScenario,false);
                }
            });

        }

    }


    public void setActivity(AppCompatActivity activity)
    {
        this.activity = activity;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientationLandscape();

        IP = MainController.getInstance().ip;

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
        triesLeft = view.findViewById(R.id.tries_tv);


        NetworkController.getInstance().setUpdateFragmentListener(this);

        fireBase = FireBase.getInstance();

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(!isPreformedClick)
               {
                   isPreformedClick = true;
                   switch (inputText)
                   {
                       case "speech":
                       {
                           FragmentManager fragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();

                           SpeechRecognitionFragment srf = new SpeechRecognitionFragment();
                           QuestionFragment testFragment = (QuestionFragment) Utilities.getInstance().currentActivity.getSupportFragmentManager().findFragmentByTag("QuestionFragment");
                           srf.setListener(testFragment);


                           srf.show(fragmentManager, "speech");


                           break;
                       }

                       case "inputText":
                       {

                           FragmentManager fragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();


                           InputTestFragment itf = new InputTestFragment();
                           QuestionFragment testFragment = (QuestionFragment) Utilities.getInstance().currentActivity.getSupportFragmentManager().findFragmentByTag("QuestionFragment");
                           itf.setListener(testFragment);
                           itf.show(fragmentManager,"inputText");

                       }
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
                    answersCounter-=10;
                    triesLeft.setText("Tries left: " + answersCounter);
                    if(answersCounter > 0)
                    {
                        startScenario(currentScenario,true);
                    }else
                    {
                        startNextScenario(currentScenario.getOnfailure().getNextScenarioID());
                    }

                    mainController.sendDataToIp(IP, currentScenario.getOnSuccess().getAction().getEffect());
                    //mainController.sendDataToIp(mainController.getIp(), "sadFace");

                }
            }


        });
        countdownView.setOnCountdownIntervalListener(1000, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
                if(cv.getRemainTime() < 10000 &&cv.getRemainTime() > 9000 )
                {
                    speakerBoxTTS("Hurry up! time is running up!");
                }
            }
        });


        return view;
    }



    private void startNextScenario(String nextScenario)
    {

        if(nextScenario.equals("exit"))
        {
            goBackToMenu();
        }else
        {
            nextcurrentScenario = nextScenario;
            final LottieAnimation tempLottieAnimation = new LottieAnimation();
            lottieAnimation = tempLottieAnimation;
            countdownView.stop();


            FragmentManager fragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();
            tempLottieAnimation.setListener(new updateFragment() {
                @Override
                public void updateData(Object data, String type) {

                }
            });
            tempLottieAnimation.show(fragmentManager,"lottie");
            //String nextScenarioID = currentScenario.getOnSuccess().getNextScenarioID();

        }


    }

    @Override
    public void onComplete(Object o, String sender)
    {
        isPreformedClick = false;
        String result = (String) o;
        String data;
        if(correctAnswer.toLowerCase().trim().equals(result.toLowerCase().trim()))
        {
            startNextScenario(currentScenario.getOnSuccess().getNextScenarioID());
            speakerBoxTTS(currentScenario.getOnSuccess().getAction().getTextOrWav());
            //mainController.sendDataToIp(IP, currentScenario.getOnSuccess().getAction().getEffect());
            data = currentScenario.getOnSuccess().getAction().getEffect()   ;
            mainController.sendDataToIp(mainController.getIp(), data);

            if(currentScenario.getOnSuccess().getNextScenarioID().equals("exit"))
            {
                testLastScenario = true;
                mainController.testLastScenario = true;
            }

        }else
        {
            answersCounter--;
            triesLeft.setText("Tries left: " + answersCounter);
            if(answersCounter > 0)
            {
                speakerBoxTTS(currentScenario.getOnfailure().getAction().getTextOrWav());
                startScenario(currentScenario,true);
            }else
            {
                speakerBoxTTS(currentScenario.getOnfailure().getAction().getTextOrWav());
                startNextScenario(currentScenario.getOnfailure().getNextScenarioID());
            }

           // mainController.sendDataToIp(IP, currentScenario.getOnSuccess().getAction().getEffect());
            data = currentScenario.getOnfailure().getAction().getEffect();
            mainController.sendDataToIp(mainController.getIp(), data);

            if(currentScenario.getOnfailure().getNextScenarioID().equals("exit"))
            {
                testLastScenario = true;
                mainController.testLastScenario = true;
            }
        }
    }

    @Override
    public void onError(String errorMsg) {

    }

    private boolean checkIndex(int index){
        return (index>=scenarios.size());
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
        Utilities.getInstance().currentActivity.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void getScenariosFromLesson(Lesson lesson)
    {
        final Lesson[] lessonFromUrl = new Lesson[1];
        final Scenario[] scenario = new Scenario[0];

        KotlinNetworkController networkTest = KotlinNetworkController.INSTANCE;


        //MainController.getInstance();
        //Scenario[] scenario = lesson.getScenarios();

    }


    private void startScenario(Scenario scenario,boolean isWrong)
    {

        List<Action> action = scenario.getActions();

        currentScenario = scenario;
        if(!isWrong)
        {
            answersCounter = scenario.getOnfailure().getNumOfRetries();
        }

        triesLeft.setText("Tries left: " + answersCounter);

        final String effect = action.get(0).getWhatToPlay();
        String text = action.get(0).getTextOrWav();
        String type = action.get(0).getWhatToPlay();
        String expectedAnswer = scenario.getWaitFor().getExpectedAnswer().getInput();
        inputText = scenario.getWaitFor().getTypeOfInput();
        correctAnswer = expectedAnswer;
        currentQuestion.setText(text);
        questionImage.setImageDrawable(null); // to refresh the picture
        final Long time = Long.valueOf(action.get(0).getTimeForAction());

       // if(!currentQuestion.equals("exit")) {
            speakerBoxTTS(text);
        //}
        youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
            @Override
            public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.pause();
            }
        });


        if(!type.startsWith("http"))
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

            Glide.with(Utilities.getInstance().currentActivity) // this section is for updating the image
                    .load(effect)
                    .into(questionImage);
            countdownView.start(time*1000);

        }

    }


        private void goBackToMenu(){

            questionCounter = 0;
            countdownView.stop();
            if(Utilities.getInstance().currentActivity != null)
            {
                FragmentManager supportFragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();
                MenuFragment menuFragment = new MenuFragment();
                supportFragmentManager.beginTransaction().replace(R.id.SplashActivity,menuFragment,"menuFragment").commitNow();
                //supportFragmentManager.beginTransaction().remove(this).commitNow();

            }
        }

    @Override
    public void speakerBoxTTS(String question)
    {
        speakerbox = new Speakerbox(Utilities.getInstance().currentActivity.getApplication());
        speakerbox.play(question);

        if(currentScenario.getActions().get(0).getTextOrWav().toLowerCase()
                .equals(question.toLowerCase()))
        {
                    new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                answerButton.performClick();

            }
                   },3000);


        }
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

    private int randAnswer(){
        Random rand = new Random();
        return rand.nextInt((2 - 1) + 1) + 1;
    }


    public void setUpAnswerScreen()
    {
        switch (inputText)
        {
            case "speech":
            {
                FragmentManager fragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();
                SpeechRecognitionFragment srf = new SpeechRecognitionFragment();
                QuestionFragment testFragment = (QuestionFragment) Utilities.getInstance().currentActivity.getSupportFragmentManager().findFragmentByTag("QuestionFragment");
                srf.setListener(testFragment);
                srf.show(fragmentManager, "speech");
                break;
            }

            case "inputText":
            {
                FragmentManager fragmentManager = Utilities.getInstance().currentActivity.getSupportFragmentManager();
                InputTestFragment itf = new InputTestFragment();
                QuestionFragment testFragment = (QuestionFragment) Utilities.getInstance().currentActivity.getSupportFragmentManager().findFragmentByTag("QuestionFragment");
                itf.setListener(testFragment);
                itf.show(fragmentManager,"inputText");

            }
        }

    }



}
