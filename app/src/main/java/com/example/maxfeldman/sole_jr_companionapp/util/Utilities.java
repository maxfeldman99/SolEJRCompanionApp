package com.example.maxfeldman.sole_jr_companionapp.util;

import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;
import com.example.maxfeldman.sole_jr_companionapp.Server.Server;

import androidx.appcompat.app.AppCompatActivity;

public class Utilities {

    //private FireBase fireBase;

    public AppCompatActivity currentActivity;
    private Server server;

    private static Utilities ourInstance = null;
    public static Utilities getInstance() {
        if(ourInstance == null)
        {
            ourInstance = new Utilities();
        }
        return ourInstance;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void startServer()
    {
        Thread thread = new Thread(server);
        thread.start();
    }

    private Utilities() {
     // fireBase = FireBase.getInstance();
    }











}
