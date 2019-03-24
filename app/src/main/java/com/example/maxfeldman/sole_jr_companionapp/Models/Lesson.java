package com.example.maxfeldman.sole_jr_companionapp.Models;

import com.google.firebase.firestore.Exclude;

import java.util.Arrays;
import java.util.List;

public class Lesson
{
    private int id;
    private String title;
    private String badge;
    private String category;
    @Exclude
    private List<String> goals;
    @Exclude
    private List<Scenario> scenarios;

    public Lesson(){}

    public Lesson(int id, String title, String badge, String category, List<String> goals, List<Scenario> scenarios) {
        this.id = id;
        this.title = title;
        this.badge = badge;
        this.category = category;
        this.goals = goals;
        this.scenarios = scenarios;
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

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }


}

