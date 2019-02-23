package com.example.maxfeldman.sole_jr_companionapp.Controller;

import android.util.Log;

import com.example.maxfeldman.sole_jr_companionapp.Models.InputListener;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class NetworkController
{
    private static final NetworkController ourInstance = new NetworkController();
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    boolean socketIsOpen = false;

    public static NetworkController getInstance() {
        return ourInstance;
    }

    private NetworkController() {
    }

    public void openSocket(final String ip,final String data)
    {
        Thread netWorkThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    socket = new Socket(ip,1234);

                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());

                    outputStream.writeObject(data);
                    outputStream.flush();

                    String str = (String) inputStream.readObject();

                    Log.d("msgFromServer",str);

                    socketIsOpen = true;


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    if(inputStream != null)
                    {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(outputStream != null)
                    {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        netWorkThread.start();

    }

    public void closeSocket()
    {
        try {
            socketIsOpen = false;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(final String data)
    {
        Thread sendDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while(socketIsOpen)
                    {
                        outputStream = new ObjectOutputStream(socket.getOutputStream());
                        outputStream.writeObject(data);
                        outputStream.flush();


                        inputStream = new ObjectInputStream(socket.getInputStream());
                        String str = (String) inputStream.readObject();
                        Log.d("msgFromServer",str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        sendDataThread.start();
    }

    public void getData()
    {
        Thread inputThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    inputStream = new ObjectInputStream(socket.getInputStream());

                    String str = (String) inputStream.readObject();

                    Log.d("msgFromServer","getData: "+str);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        inputThread.start();
    }

    public void getLessonFromUrl(final updateFragment update)
    {
        final Lesson[] lesson = new Lesson[1];
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                URL url = null;
                try {
                    url = new URL("https://api.myjson.com/bins/1bzfpa");
                    InputStreamReader reader = new InputStreamReader(url.openStream());

                    Gson gson = new Gson();
                    lesson[0] = gson.fromJson(reader, Lesson.class);

                    update.updateData(lesson[0]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();

    }

}
