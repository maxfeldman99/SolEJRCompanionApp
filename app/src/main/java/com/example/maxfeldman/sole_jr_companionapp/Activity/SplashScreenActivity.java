package com.example.maxfeldman.sole_jr_companionapp.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.maxfeldman.sole_jr_companionapp.Fragments.MenuFragment;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.DataListener;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.example.maxfeldman.sole_jr_companionapp.Server.Server;
import com.example.maxfeldman.sole_jr_companionapp.util.Utilities;
import com.google.firebase.FirebaseApp;

public class SplashScreenActivity extends AppCompatActivity {

    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splash_screen);

        Utilities.getInstance().currentActivity = this;

        Server server = new Server();

        Utilities.getInstance().setServer(server);
        Utilities.getInstance().startServer();

        FireBase.getInstance().setContext(this);
       // FireBase.getInstance().iniFirebase();

        MenuFragment menuFragment = new MenuFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.SplashActivity
                        ,menuFragment,"MenuFragment")
                //,new SpotClickFragment(),"SpotClickFragment") // just to test the spot click fragment
                .commitNow();

        menuFragment.setAppCompatActivity(this);

        FireBase.getInstance().getLesson("sole_jr_comp_app_lessons",
                "Animals Lesson", new DataListener() {
                    @Override
                    public void onDataLoad(Object o)
                    {
                        Lesson lesson = (Lesson) o;

                        System.out.println(lesson);

                    }
                });
    }


    @Override
    public void onBackPressed()
    {
        //Do Nothing
    }



}
