package com.example.maxfeldman.sole_jr_companionapp.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.maxfeldman.sole_jr_companionapp.Fragments.MenuFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.SplashActivity
                        ,new MenuFragment(),"MenuFragment")
                //,new SpotClickFragment(),"SpotClickFragment") // just to test the spot click fragment
                .commitNow();
    }
}
