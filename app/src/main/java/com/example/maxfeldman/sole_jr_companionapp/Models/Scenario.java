package com.example.maxfeldman.sole_jr_companionapp.Models;

import java.util.List;

public class Scenario
{
    private String name;
    private List<Action> actions;
    private String level;
    private OnFailure onfailure;
    private OnSuccess onSuccess;
    private WaitFor waitFor;

    public Scenario(){}

    public Scenario(String name, List<Action> actions, String level
            , OnFailure onfailure, OnSuccess onSuccess, WaitFor waitFor) {
        this.name = name;
        this.actions = actions;
        this.level = level;
        this.onfailure = onfailure;
        this.onSuccess = onSuccess;
        this.waitFor = waitFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
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


    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", actions=" + actions +
                ", level='" + level + '\'' +
                ", onfailure=" + onfailure +
                ", onSuccess=" + onSuccess +
                ", waitFor=" + waitFor +
                '}';
    }
}
