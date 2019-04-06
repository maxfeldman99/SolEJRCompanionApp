package com.example.maxfeldman.sole_jr_companionapp.Controller

import android.content.Context
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.net.Socket
import java.net.URL
import java.util.*

object KotlinNetworkController
{

    var contxt: Context ?= null

    fun setContext(incontext: Context)
    {
        contxt = incontext
    }

    fun getLessonFromUrl(url: String,update: updateFragment<Any>)
    {

        GlobalScope.launch(Dispatchers.Default)
        {
            //val lesson1 = MainController.getInstance().getLesson(1)
            try
            {
                FirebaseApp.initializeApp(contxt)
                val url = URL(url)
                val inputStreamReader = InputStreamReader(url.openStream())

                val gson = Gson()
                val toJson = gson.fromJson(inputStreamReader, Lesson::class.java)

                GlobalScope.launch(Dispatchers.Main)
                {
                    FirebaseApp.initializeApp(contxt)
                    update.updateData(toJson,"json")
                }


            }catch (e: Exception)
            {
                e.printStackTrace()
            }


        }

    }


    fun validateIp(ip: String, listeners: updateFragment<Any>)
    {
        GlobalScope.launch(Dispatchers.Default)
        {
            val id = UUID.randomUUID().toString()
            try {
                val socket = Socket(ip, 1234)
                val objectOutputStream = ObjectOutputStream(socket.getOutputStream())

                objectOutputStream.writeObject("ack$id")
                println("ack$id")
                val objectInputStream = ObjectInputStream(socket.getInputStream())
                val inputId = objectInputStream.readObject() as String

                GlobalScope.launch(Dispatchers.Main)
                {
                    if (inputId == id) {
                        listeners.updateData("valid","String")
                    } else {
                        listeners.updateData("invalid","String")
                    }
                }


            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }

    }


}