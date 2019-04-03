package com.example.maxfeldman.sole_jr_companionapp.Fragments;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LottieAnimation extends DialogFragment implements updateFragment
{

    private LottieAnimationView animationView;
    private int animationToPlay;
    private TextView animaionText;

    private updateFragment listener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        View view = null;

        view = inflater.inflate(R.layout.lottie_animation_fragment,container,false);

        animationView = view.findViewById(R.id.lottie_Animation);
        animaionText = view.findViewById(R.id.lottie_text);



        animationToPlay = R.raw.lego_loader;

        String string;

//        if(getArguments().getString("text") != null)
//        {
//            string = getArguments().getString("text");
//            animaionText.setVisibility(View.VISIBLE);
//            animaionText.setText(string+" Test!");
//            animaionText.setTextColor(getResources().getColor(R.color.grey_3));
//        }

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                //listener.updateData("done");
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        this.setCancelable(false);


        animationView.setAnimation(animationToPlay);

        animationView.playAnimation();


        return view;
    }

    public void setListener(updateFragment listener)
    {
        this.listener = listener;
    }

    @Override
    public void onStop()
    {
        animationView.cancelAnimation();
        super.onStop();
    }

    @Override
    public void updateData(Object data,String type) {

    }
}