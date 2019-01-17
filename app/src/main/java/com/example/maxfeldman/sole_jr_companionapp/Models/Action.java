package com.example.maxfeldman.sole_jr_companionapp.Models;

public class Action
{
    private String effect;
    private String textOrWav;
    private String whatToPlay;

    public Action(){}

    public Action(String effect, String textOrWav, String whatToPlay) {
        this.effect = effect;
        this.textOrWav = textOrWav;
        this.whatToPlay = whatToPlay;
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
}
