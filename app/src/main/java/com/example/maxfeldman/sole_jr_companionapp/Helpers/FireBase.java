package com.example.maxfeldman.sole_jr_companionapp.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.Scenario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FireBase {


    private FirebaseFirestore db;
    private static FireBase ourInstance = null;

    private Context context;

    private Lesson lesson;

    public static FireBase getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new FireBase();
        }

        return ourInstance;
    }

    private FireBase() { }


    public void setContext(Context context)
    {
        this.context = context;
    }

    public void addLesson(Lesson lesson,String id){


        FirebaseApp.initializeApp(context);

        db = FirebaseFirestore.getInstance();

        db.collection("sole_jr_comp_app_lessons").document(id)
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("success", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fail", "Error writing document", e);
                    }
                });
    }

    public void getLesson(String collection, String id,final DataListener listener){


        FirebaseApp.initializeApp(context);

        db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection(collection).document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Lesson lesson = documentSnapshot.toObject(Lesson.class);

                listener.onDataLoad(lesson);
            }
        });


    }


    public void addScenario(Scenario scenario)
    {
        //FirebaseApp.initializeApp(context);

        db = FirebaseFirestore.getInstance();

        db.collection("Scenarios")
                .add(scenario)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(context,"Scenario Added",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(context,"Scenario Failed",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    public void getAllLessons(final DataListener listener){


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = rootRef.collection("sole_jr_comp_app_lessons");
        questionsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Lesson> lessons = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult())
                    {
                        Lesson lesson = document.toObject(Lesson.class);

                        Gson gson = new Gson();
                        String s = gson.toJson(lesson);

                        Log.d("JSON", s);

                        lessons.add(lesson);

                        Log.d("TAG", lesson.toString());
                    }
                    listener.onDataLoad(lessons);
                }
            }
        });

    }

    public void getScenario(final String scenarioName, final DataListener listener)
    {

        db = FirebaseFirestore.getInstance();

        CollectionReference docRef = db.collection("Scenarios");
        docRef.whereEqualTo("name", scenarioName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty())
                {
                    Log.d("TAG", "Scenario not found");
                } else {
                    List<Scenario> scenarios = queryDocumentSnapshots.toObjects(Scenario.class);
                    for (int i = 0; i < scenarios.size(); i++) {
                        Scenario scenario = scenarios.get(i);
                        if(scenario != null)
                        {
                            if (scenario.getName().equals(scenarioName)) {
                                listener.onDataLoad(scenario);
                            }
                        }
                    }

                }
            }
        });
    }


}
