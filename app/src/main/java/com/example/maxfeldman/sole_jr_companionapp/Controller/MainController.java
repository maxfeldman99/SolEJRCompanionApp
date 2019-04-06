package com.example.maxfeldman.sole_jr_companionapp.Controller;

public class MainController
{
    private static final MainController ourInstance = new MainController();

    public String ip = null;
    public boolean ipValidated = false;

    public boolean testLastScenario = false;

    private final NetworkController networkController;


    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
        networkController = NetworkController.getInstance();
    }

    public void setIpValidated(boolean validated)
    {
        this.ipValidated = validated;
    }

    public boolean isIpValidated()
    {
        return this.ipValidated;
    }

    public void sendDataToIp(String ip, String data)
    {
        networkController.openSocket(ip,data);
    }


    public void setIp(String ip)
    {
        this.ip = ip;
    }
    public String getIp()
    {
        return this.ip;
    }

}
