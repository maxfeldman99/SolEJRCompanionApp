package com.example.maxfeldman.sole_jr_companionapp.Controller;

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;

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
       return lessonController.getLessonById(lessonId);
    }
}
