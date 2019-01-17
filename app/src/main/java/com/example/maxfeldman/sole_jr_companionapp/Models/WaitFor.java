package com.example.maxfeldman.sole_jr_companionapp.Models;

/**
 * Created by MAX FELDMAN on 17/01/2019.
 */

public class WaitFor {

    private int typeOfWaiting;
    private ExpectedAnswer expectedAnswer;

    public WaitFor(int typeOfWaiting, ExpectedAnswer expectedAnswer) {
        this.typeOfWaiting = typeOfWaiting;
        this.expectedAnswer = expectedAnswer;
    }

    public int getTypeOfWaiting() {
        return typeOfWaiting;
    }

    public void setTypeOfWaiting(int typeOfWaiting) {
        this.typeOfWaiting = typeOfWaiting;
    }

    public ExpectedAnswer getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(ExpectedAnswer expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}
