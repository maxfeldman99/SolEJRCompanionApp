package com.example.maxfeldman.sole_jr_companionapp.Server;

import android.util.Log;

import com.example.maxfeldman.sole_jr_companionapp.Controller.MainController;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by MAX FELDMAN on 04/01/2019.
 */

public class Server implements Runnable
{

    private ServerSocket serverSocket;
    private String serverMessage = "standby";
    private updateFragment listener;

    public Server()
    {
    }


    public void setListener(updateFragment listener){
        this.listener = listener;
    }

    @Override
    public void run()
    {

        boolean SERVER_IS_RUNNING = true;
        while(SERVER_IS_RUNNING)
        {
            try
            {
                if(serverSocket == null)
                {
                    int PORT = 1234;
                    serverSocket = new ServerSocket(PORT);
                }
                Socket socket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String message = null;
                try {
                    message = (String) inputStream.readObject();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    if(message!=null)
                    {
                        Log.d("msgFromServer",message);

                        boolean testLastScenario = MainController.getInstance().testLastScenario;
                        if(!testLastScenario)
                        {
                            listener.updateData(message,message);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();

            } finally {

                try {
                    //serverSocket.close();

                    //SERVER_IS_RUNNING = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
