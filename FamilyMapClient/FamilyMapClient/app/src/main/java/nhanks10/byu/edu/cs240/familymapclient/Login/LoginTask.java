package nhanks10.byu.edu.cs240.familymapclient.Login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import Request.LoginRequest;
import Result.LoginResult;
import nhanks10.byu.edu.cs240.familymapclient.DataCache;
import nhanks10.byu.edu.cs240.familymapclient.ServerProxy;

public class LoginTask implements Runnable {

    private static final String LOGIN_SUCCESS_KEY = "loginSuccess";
    private static final String AUTHTOKEN_KEY = "authToken";
    private static final String PERSON_ID_KEY = "personID";
    private final String address;
    private final String port;
    private final Handler handler;
    private final LoginRequest loginRequest;

    public LoginTask(Handler handler, LoginRequest loginRequest, String address, String port) {
        this.address = address;
        this.port = port;
        this.handler = handler;
        this.loginRequest = loginRequest;
    }

    @Override
    public void run() {
        ServerProxy proxy = new ServerProxy(address,port);
        LoginResult result = proxy.login(loginRequest);

        if (result.isSuccess()) {
            DataCache cache = DataCache.getInstance();
            cache.buildFamilyTree();
        }

        Boolean success = result.isSuccess();
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();

        messageBundle.putBoolean(LOGIN_SUCCESS_KEY, success);
        messageBundle.putString(AUTHTOKEN_KEY, result.getAuthtoken());
        messageBundle.putString(PERSON_ID_KEY, result.getPersonID());

        message.setData(messageBundle);
        handler.sendMessage(message);
    }
}
