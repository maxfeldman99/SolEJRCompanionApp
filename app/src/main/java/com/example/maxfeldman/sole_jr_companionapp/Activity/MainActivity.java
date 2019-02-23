package com.example.maxfeldman.sole_jr_companionapp.Activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.QuestionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.SpotClickFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        NetworkController.getInstance().openSocket("192.168.137.136","max");


        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentPlaceHolder
                ,new QuestionFragment(),"QuestionFragment")
                 //,new SpotClickFragment(),"SpotClickFragment") // just to test the spot click fragment
                .commitNow();


    }
}
