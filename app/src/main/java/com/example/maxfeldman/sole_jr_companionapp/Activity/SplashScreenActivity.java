package com.example.maxfeldman.sole_jr_companionapp.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.maxfeldman.sole_jr_companionapp.Fragments.MenuFragment;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.google.firebase.FirebaseApp;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splash_screen);
        FireBase.getInstance().setContext(this);
        FireBase.getInstance().iniFirebase();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.SplashActivity
                        ,new MenuFragment(),"MenuFragment")
                //,new SpotClickFragment(),"SpotClickFragment") // just to test the spot click fragment
                .commitNow();


    }


    @Override
    public void onBackPressed()
    {
        //Do Nothing
    }
}
