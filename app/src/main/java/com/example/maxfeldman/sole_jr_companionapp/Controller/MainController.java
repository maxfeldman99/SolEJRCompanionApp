package com.example.maxfeldman.sole_jr_companionapp.Controller;

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.google.gson.Gson;

public class MainController
{
    private static final MainController ourInstance = new MainController();

    public String ip = null;
    public boolean ipValidated = false;
    public boolean isFirstRun = true;

    public boolean testLastScenario = false;

    NetworkController networkController;

    LessonController lessonController;

    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
        networkController = NetworkController.getInstance();
        lessonController = LessonController.getInstance();
    }

    public void setIpValidated(boolean validated)
    {
        this.ipValidated = validated;
    }

    public boolean isIpValidated()
    {
        return this.ipValidated;
    }

    public void sendDataToIp(String ip, String data)
    {
        networkController.openSocket(ip,data);
    }


    public void setIp(String ip)
    {
        this.ip = ip;
    }
    public String getIp()
    {
        return this.ip;
    }

    /*
    public Lesson getLesson(int lessonId)
    {
        Lesson temp = lessonController.getLessonById(lessonId);

        Gson gson = new Gson();
        String s = gson.toJson(temp);

        System.out.println(s);

        return temp;
    }
    */
}
