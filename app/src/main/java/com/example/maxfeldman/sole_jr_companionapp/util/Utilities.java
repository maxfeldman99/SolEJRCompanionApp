package com.example.maxfeldman.sole_jr_companionapp.util;

import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;

public class Utilities {

    private FireBase fireBase;

    private static final Utilities ourInstance = new Utilities();
    public static Utilities getInstance() {
        return ourInstance;
    }
    private Utilities() {
       fireBase = new FireBase();
    }











}
