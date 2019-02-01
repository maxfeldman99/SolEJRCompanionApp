package com.example.maxfeldman.sole_jr_companionapp.Controller;

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

}
