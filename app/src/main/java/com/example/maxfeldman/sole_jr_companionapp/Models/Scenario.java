package com.example.maxfeldman.sole_jr_companionapp.Models;

public class Scenario
{
    private int id;
    private Action[] actions;
    private String level;
    private OnFailure onfailure;
    private OnSuccess onSuccess;
    private WaitFor waitFor;

    public Scenario(){}

    public Scenario(int id, Action[] actions, String level, OnFailure onfailure, OnSuccess onSuccess, WaitFor waitFor) {
        this.id = id;
        this.actions = actions;
        this.level = level;
        this.onfailure = onfailure;
        this.onSuccess = onSuccess;
        this.waitFor = waitFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Action[] getActions() {
        return actions;
    }

    public void setActions(Action[] actions) {
        this.actions = actions;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public OnFailure getOnfailure() {
        return onfailure;
    }

    public void setOnfailure(OnFailure onfailure) {
        this.onfailure = onfailure;
    }

    public OnSuccess getOnSuccess() {
        return onSuccess;
    }

    public void setOnSuccess(OnSuccess onSuccess) {
        this.onSuccess = onSuccess;
    }

    public WaitFor getWaitFor() {
        return waitFor;
    }

    public void setWaitFor(WaitFor waitFor) {
        this.waitFor = waitFor;
    }
}
