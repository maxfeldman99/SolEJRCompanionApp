package com.example.maxfeldman.sole_jr_companionapp.Helpers;

import android.util.Log;

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

public class FireBase {


    public static FirebaseFirestore db=FirebaseFirestore.getInstance();


    private Lesson lesson;


    public void addLesson(Lesson lesson,String id){


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

        DocumentReference docRef = db.collection(collection).document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Lesson lesson = documentSnapshot.toObject(Lesson.class);

                listener.onDataLoad(lesson);
            }
        });


    }




}
