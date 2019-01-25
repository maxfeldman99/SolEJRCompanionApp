package com.example.maxfeldman.sole_jr_companionapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.MainFragment;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.QuestionFragment;
import com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment.TestFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkController.getInstance().openSocket("192.168.137.136","max");


        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentPlaceHolder
                ,new QuestionFragment(),"QuestionFragment")
                .commitNow();
    }
}
