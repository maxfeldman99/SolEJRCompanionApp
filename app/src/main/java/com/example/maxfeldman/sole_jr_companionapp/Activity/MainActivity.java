package com.example.maxfeldman.sole_jr_companionapp.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.QuestionFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        NetworkController.getInstance().openSocket("192.168.137.136","max");


        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentPlaceHolder
                ,new QuestionFragment(),"QuestionFragment");
                 //,new SpotClickFragment(),"SpotClickFragment") // just to test the spot click fragment
               // .commitNow();




        Intent intent = new Intent(this,SplashScreenActivity.class); ////////////////////////////////////////////////////////////////////
        startActivity(intent);

    }

}
