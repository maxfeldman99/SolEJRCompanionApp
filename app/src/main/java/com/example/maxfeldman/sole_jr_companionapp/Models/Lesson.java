package com.example.maxfeldman.sole_jr_companionapp.Models;

import java.util.List;

public class Lesson
{
    private int id;
    private String title;
    private String badge;
    private String category;

    private List<String> goals;

    private List<String> scenariosInLesson;

    public Lesson(){}

    public Lesson(int id, String title, String badge, String category,
                  List<String> goals) {
        this.id = id;
        this.title = title;
        this.badge = badge;
        this.category = category;
        this.goals = goals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public List<String> getScenariosInLesson() {
        return scenariosInLesson;
    }

    public void setScenariosInLesson(List<String> scenariosInLesson) {
        this.scenariosInLesson = scenariosInLesson;
    }

}

