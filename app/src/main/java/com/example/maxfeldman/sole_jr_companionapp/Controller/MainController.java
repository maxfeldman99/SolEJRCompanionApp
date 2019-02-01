package com.example.maxfeldman.sole_jr_companionapp.Controller;

import com.example.maxfeldman.sole_jr_companionapp.Models.Action;
import com.example.maxfeldman.sole_jr_companionapp.Models.ExpectedAnswer;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.OnFailure;
import com.example.maxfeldman.sole_jr_companionapp.Models.OnSuccess;
import com.example.maxfeldman.sole_jr_companionapp.Models.Scenario;
import com.example.maxfeldman.sole_jr_companionapp.Models.WaitFor;

public class MainController {
    private static final MainController ourInstance = new MainController();

    NetworkController networkController;

    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
        networkController = NetworkController.getInstance();
    }

    public void sendDataToIp(String ip, String data)
    {
        networkController.openSocket(ip,data);
    }

    public Lesson getLesson()
    {
        Lesson animalLesson = new Lesson();

        animalLesson.setId(1);
        animalLesson.setBadge("jpn file");
        animalLesson.setCategory("English Animals Lesson");

        animalLesson.setTitle("Animals Lesson");

        String[] goals = {"Teach animals", "Teach english", "Teach colors"};
        animalLesson.setGoals(goals);

        Scenario scenario1 = new Scenario();
        scenario1.setId(1);
        scenario1.setLevel("Level 3");


        Action action1 = new Action();
        action1.setEffect("Dog.png");
        action1.setTextOrWav("Can you say the name of this animals?");
        action1.setWhatToPlay("");

        OnFailure failure = new OnFailure();

        Action onFailureAction = new Action();
        onFailureAction.setEffect("sadFace");
        onFailureAction.setTextOrWav("Oh Oh, Try again!");
        onFailureAction.setWhatToPlay("");

        failure.setAction(onFailureAction);
        failure.setNumOfRetries(2);
        failure.setNextScenarioID("2");


        OnSuccess success = new OnSuccess();

        Action onSuccessAction = new Action();
        onSuccessAction = new Action();
        onSuccessAction.setEffect("Smile");
        onSuccessAction.setTextOrWav("Good job!!!");
        onSuccessAction.setWhatToPlay("");

        success.setAction(onSuccessAction);
        success.setNextScenarioID("2");

        scenario1.setOnSuccess(success);
        scenario1.setOnfailure(failure);

        WaitFor waitFor = new WaitFor();
        ExpectedAnswer expectedAnswer = new ExpectedAnswer();
        expectedAnswer.setInput("Dog");
        expectedAnswer.setSuccessRating(80);

        waitFor.setTypeOfWaiting(1);
        waitFor.setExpectedAnswer(expectedAnswer);


        Scenario[] scenarios = new Scenario[3];
        scenarios[1] = scenario1;

        animalLesson.setScenarios(scenarios);

        return animalLesson;
    }
}
