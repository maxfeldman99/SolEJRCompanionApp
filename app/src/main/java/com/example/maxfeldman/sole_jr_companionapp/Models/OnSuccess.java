package com.example.maxfeldman.sole_jr_companionapp.Models;

/**
 * Created by MAX FELDMAN on 17/01/2019.
 */

public class OnSuccess {

    private Action Action;
    private String nextScenarioID;

    public OnSuccess(Action action, String nextScenarioID) {
        Action = action;
        this.nextScenarioID = nextScenarioID;
    }

    public Action getAction() {
        return Action;
    }

    public void setAction(Action action) {
        Action = action;
    }

    public String getNextScenarioID() {
        return nextScenarioID;
    }

    public void setNextScenarioID(String nextScenarioID) {
        this.nextScenarioID = nextScenarioID;
    }
}
