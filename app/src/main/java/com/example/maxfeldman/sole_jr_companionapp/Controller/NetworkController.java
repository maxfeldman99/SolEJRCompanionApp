package com.example.maxfeldman.sole_jr_companionapp.Controller;

import android.util.Log;

import com.example.maxfeldman.sole_jr_companionapp.Models.InputListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

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


}
