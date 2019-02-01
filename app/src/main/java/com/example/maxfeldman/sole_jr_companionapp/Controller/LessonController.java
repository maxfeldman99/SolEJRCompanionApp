package com.example.maxfeldman.sole_jr_companionapp.Controller;

import com.example.maxfeldman.sole_jr_companionapp.Models.Action;
import com.example.maxfeldman.sole_jr_companionapp.Models.ExpectedAnswer;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.OnFailure;
import com.example.maxfeldman.sole_jr_companionapp.Models.OnSuccess;
import com.example.maxfeldman.sole_jr_companionapp.Models.Scenario;
import com.example.maxfeldman.sole_jr_companionapp.Models.WaitFor;

public class LessonController {
    private static final LessonController ourInstance = new LessonController();

    public static LessonController getInstance() {
        return ourInstance;
    }

    private LessonController() {
    }

    public Lesson getLessonById(int lessonId)
    {

        //TODO database interaction
        return getCurrentLesson();

    }

    public Lesson getCurrentLesson()
    {
        Lesson animalLesson = new Lesson();


        animalLesson.setId(1);
        animalLesson.setBadge("jpn file");
        animalLesson.setCategory("English Animals Lesson");

        animalLesson.setTitle("Animals Lesson");

        String[] goals = {"Teach animals", "Teach english", "Teach colors"};
        animalLesson.setGoals(goals);


        //Start of scenario1

        Scenario scenario1 = new Scenario();
        scenario1.setId(1);
        scenario1.setLevel("Level 3");


        Action[] actions1 = new Action[1];
        Action action1 = new Action();
        action1.setEffect("https://i.kym-cdn.com/entries/icons/mobile/000/013/564/doge.jpg");
        action1.setTextOrWav("Can you say the name of this animals?");
        action1.setWhatToPlay("");

        actions1[0] = action1;

        scenario1.setActions(actions1);

        OnFailure failure1 = new OnFailure();

        Action onFailureAction1 = new Action();
        onFailureAction1.setEffect("sadFace");
        onFailureAction1.setTextOrWav("Oh Oh, Try again!");
        onFailureAction1.setWhatToPlay("");

        failure1.setAction(onFailureAction1);
        failure1.setNumOfRetries(2);
        failure1.setNextScenarioID("2");


        OnSuccess success1 = new OnSuccess();

        Action onSuccessAction1 = new Action();
        onSuccessAction1 = new Action();
        onSuccessAction1.setEffect("Smile");
        onSuccessAction1.setTextOrWav("Good job!!!");
        onSuccessAction1.setWhatToPlay("");

        success1.setAction(onSuccessAction1);
        success1.setNextScenarioID("2");

        scenario1.setOnSuccess(success1);
        scenario1.setOnfailure(failure1);

        WaitFor waitFor1 = new WaitFor();
        ExpectedAnswer expectedAnswer1 = new ExpectedAnswer();
        expectedAnswer1.setInput("dog");
        expectedAnswer1.setSuccessRating(80);

        waitFor1.setTypeOfWaiting(1);
        waitFor1.setExpectedAnswer(expectedAnswer1);
        waitFor1.setTypeOfInput("speech");

        scenario1.setWaitFor(waitFor1);

        //Start of scenario2
        Scenario scenario2 = new Scenario();


        scenario2.setId(2);
        scenario2.setLevel("2");


        Action[] actions2 = new Action[1];
        Action action2 = new Action();
        action2.setEffect("http://stoopidthings.com/wp-content/uploads/2012/04/flamingos_02.jpg");
        action2.setTextOrWav("What color is the animal in the picture?");
        action2.setWhatToPlay("");
        actions2[0] = action2;


        scenario2.setActions(actions2);

        OnFailure failure2 = new OnFailure();

        Action onFailureAction2 = new Action();
        onFailureAction2.setEffect("sadFace");
        onFailureAction2.setTextOrWav("No worries, Try again!");
        onFailureAction2.setWhatToPlay("");

        failure2.setAction(onFailureAction2);
        failure2.setNextScenarioID("2");
        failure2.setNumOfRetries(2);

        OnSuccess success2 = new OnSuccess();

        Action onSuccessAction2 = new Action();
        onSuccessAction2 = new Action();
        onSuccessAction2.setEffect("Smile");
        onSuccessAction2.setTextOrWav("Good job!!!");
        onSuccessAction2.setWhatToPlay("");

        success2.setAction(onSuccessAction2);
        success2.setNextScenarioID("3");

        WaitFor waitFor2= new WaitFor();
        ExpectedAnswer expectedAnswer2 = new ExpectedAnswer();
        expectedAnswer2.setInput("pink");
        expectedAnswer2.setSuccessRating(75);


        waitFor2.setTypeOfWaiting(1);
        waitFor2.setExpectedAnswer(expectedAnswer2);
        waitFor2.setTypeOfInput("inputText");

        scenario2.setWaitFor(waitFor2);
        scenario2.setOnfailure(failure2);
        scenario2.setOnSuccess(success2);



        //Start of scenario3
        Scenario scenario3 = new Scenario();


        scenario2.setId(3);
        scenario2.setLevel("3");


        Action[] actions3 = new Action[1];
        Action action3 = new Action();
        action3.setEffect("https://ichef.bbci.co.uk/news/660/cpsprodpb/025B/production/_85730600_monkey2.jpg");
        action3.setTextOrWav("can you say the name of this animal?");
        action3.setWhatToPlay("");
        actions3[0] = action3;


        scenario3.setActions(actions3);

        OnFailure failure3 = new OnFailure();

        Action onFailureAction3 = new Action();
        onFailureAction3.setEffect("sadFace");
        onFailureAction3.setTextOrWav("No worries, Try again!");
        onFailureAction3.setWhatToPlay("");

        failure3.setAction(onFailureAction2);
        failure3.setNextScenarioID("3");
        failure3.setNumOfRetries(2);

        OnSuccess success3 = new OnSuccess();

        Action onSuccessAction3 = new Action();
        onSuccessAction3 = new Action();
        onSuccessAction3.setEffect("Smile");
        onSuccessAction3.setTextOrWav("Good job!!!");
        onSuccessAction3.setWhatToPlay("");

        success3.setAction(onSuccessAction2);
        success3.setNextScenarioID("4");

        WaitFor waitFor3= new WaitFor();
        ExpectedAnswer expectedAnswer3 = new ExpectedAnswer();
        expectedAnswer3.setInput("monkey");
        expectedAnswer3.setSuccessRating(75);


        waitFor3.setTypeOfWaiting(1);
        waitFor3.setExpectedAnswer(expectedAnswer3);
        waitFor3.setTypeOfInput("speech");

        scenario3.setWaitFor(waitFor3);
        scenario3.setOnfailure(failure3);
        scenario3.setOnSuccess(success3);





        //getting things together
        Scenario[] scenarios = new Scenario[3];

        scenarios[0] = scenario1;

        scenarios[1] = scenario2;

        scenarios[2] = scenario3;

        animalLesson.setScenarios(scenarios);

        return animalLesson;
    }
}
