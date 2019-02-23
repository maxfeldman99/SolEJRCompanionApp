package com.example.maxfeldman.sole_jr_companionapp.Controller;

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.google.gson.Gson;

public class MainController
{
    private static final MainController ourInstance = new MainController();

    NetworkController networkController;

    LessonController lessonController;

    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
        networkController = NetworkController.getInstance();
        lessonController = LessonController.getInstance();
    }

    public void sendDataToIp(String ip, String data)
    {
        networkController.openSocket(ip,data);
    }



    public Lesson getLesson(int lessonId)
    {
        Lesson temp = lessonController.getLessonById(lessonId);

        Gson gson = new Gson();
        String s = gson.toJson(temp);

        System.out.println(s);

        return temp;
    }
}
