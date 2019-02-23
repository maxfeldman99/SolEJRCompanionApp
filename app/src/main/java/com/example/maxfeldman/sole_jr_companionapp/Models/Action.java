package com.example.maxfeldman.sole_jr_companionapp.Models;

public class Action
{
    private String effect;
    private String textOrWav;
    private String whatToPlay;
    private String timeForAction;

    public Action(){}

    public Action(String effect, String textOrWav, String whatToPlay,String timeForAction ) {
        this.effect = effect;
        this.textOrWav = textOrWav;
        this.whatToPlay = whatToPlay;
        this.timeForAction = timeForAction;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getTextOrWav() {
        return textOrWav;
    }

    public void setTextOrWav(String textOrWav) {
        this.textOrWav = textOrWav;
    }

    public String getWhatToPlay() {
        return whatToPlay;
    }

    public void setWhatToPlay(String whatToPlay) {
        this.whatToPlay = whatToPlay;
    }

    public String getTimeForAction() {
        return timeForAction;
    }

    public void setTimeForAction(String timeForAction) {
        this.timeForAction = timeForAction;
    }
}
