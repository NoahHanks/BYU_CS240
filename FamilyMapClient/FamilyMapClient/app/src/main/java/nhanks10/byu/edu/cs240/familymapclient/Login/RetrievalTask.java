package nhanks10.byu.edu.cs240.familymapclient.Login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import Result.EventIDResult;
import Result.EventResult;
import Result.PersonResult;
import nhanks10.byu.edu.cs240.familymapclient.DataCache;
import nhanks10.byu.edu.cs240.familymapclient.ServerProxy;

public class RetrievalTask implements Runnable {
    private static final String DATA_SUCCESS_KEY = "dataSuccess";
    private final String address;
    private final String port;
    private final String authToken;
    private final String personID;

    private final Handler handler;

    public RetrievalTask(Handler handler, String address, String port, String authToken
            , String personID) {
        this.handler = handler;
        this.address = address;
        this.port = port;
        this.authToken = authToken;
        this.personID = personID;
    }

    @Override
    public void run() {
        ServerProxy proxy = new ServerProxy(address, port);
        EventResult eventResult = proxy.getEvents(authToken);
        PersonResult personsResult = proxy.getPeople(authToken);

        if (eventResult.isSuccess() && personsResult.isSuccess()) {
            DataCache cache = DataCache.getInstance();
            cache.buildFamilyTree();
            sendMessage(true);
        } else {
            sendMessage(false);
        }
    }

    private void sendMessage(Boolean success){
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();
        messageBundle.putBoolean(DATA_SUCCESS_KEY, success);
        message.setData(messageBundle);
        handler.sendMessage(message);
    }
}