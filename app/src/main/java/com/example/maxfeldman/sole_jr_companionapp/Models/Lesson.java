package com.example.maxfeldman.sole_jr_companionapp.Models;

import com.google.firebase.firestore.Exclude;

import java.util.Arrays;

public class Lesson
{
    private int id;
    private String title;
    private String badge;
    private String category;
    @Exclude
    private String[] goals;
    @Exclude
    private Scenario[] scenarios;

    public Lesson(){}

    public Lesson(int id, String title, String badge, String category, String[] goals, Scenario[] scenarios) {
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

    @Exclude
    public String[] getGoals() {
        return goals;
    }
    @Exclude
    public void setGoals(String[] goals) {
        this.goals = goals;
    }

    @Exclude
    public Scenario[] getScenarios() {
        return scenarios;
    }

    @Exclude
    public void setScenarios(Scenario[] scenarios) {
        this.scenarios = scenarios;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", badge='" + badge + '\'' +
                ", category='" + category + '\'' +
                ", goals=" + Arrays.toString(goals) +
                ", scenarios=" + Arrays.toString(scenarios) +
                '}';
    }
}

