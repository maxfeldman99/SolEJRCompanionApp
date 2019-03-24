package com.example.maxfeldman.sole_jr_companionapp.util;

import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;

public class Utilities {

    //private FireBase fireBase;

    private static Utilities ourInstance = null;
    public static Utilities getInstance() {
        if(ourInstance == null)
        {
            ourInstance = new Utilities();
        }
        return ourInstance;
    }
    private Utilities() {
     // fireBase = FireBase.getInstance();
    }











}
