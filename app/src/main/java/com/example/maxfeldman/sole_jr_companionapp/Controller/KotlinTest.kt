package com.example.maxfeldman.sole_jr_companionapp.Controller

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.URL

object NetworkTest
{

    fun getLessonFromUrl(update: updateFragment<Any>)
    {

        GlobalScope.launch(Dispatchers.Default)
        {

            val url = URL("https://api.myjson.com/bins/8xmwm")
            val inputStreamReader = InputStreamReader(url.openStream())

            val gson = Gson()
            val toJson = gson.fromJson(inputStreamReader, Lesson::class.java)

           GlobalScope.launch(Dispatchers.Main)
            {
                update.updateData(toJson)
            }

        }

    }

}