package com.example.maxfeldman.sole_jr_companionapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Models.InputListener;
import com.example.maxfeldman.sole_jr_companionapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment
{

    EditText dataEt;

    public MainFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
       View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button button = view.findViewById(R.id.mainFragmentSubmitBtn);

        dataEt = view.findViewById(R.id.edit_text_name);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NetworkController controller = NetworkController.getInstance();

                controller.openSocket("192.168.137.136",dataEt.getText().toString());
            }
        });

       return view;
    }

}
