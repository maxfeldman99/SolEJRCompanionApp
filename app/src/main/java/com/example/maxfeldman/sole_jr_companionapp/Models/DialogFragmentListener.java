package com.example.maxfeldman.sole_jr_companionapp.Models;

public interface DialogFragmentListener<T>
{
    void onComplete(T t, String sender);
    void onError(String errorMsg);

}
