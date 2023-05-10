package nhanks10.byu.edu.cs240.familymapclient.Login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import Request.RegisterRequest;
import Result.GenericResult;
import Result.LoginResult;
import nhanks10.byu.edu.cs240.familymapclient.DataCache;
import nhanks10.byu.edu.cs240.familymapclient.ServerProxy;

public class RegisterTask<RegisterResult> implements Runnable {
    private final String address;
    private final String port;
    private final Handler handler;
    private final RegisterRequest registerRequest;
    private LoginFragment.Listener listener;

    private static final String REGISTER_SUCCESS_KEY = "registerSuccess";
    private static final String AUTHTOKEN_KEY = "authToken";
    private static final String PERSON_ID_KEY = "personID";

    public RegisterTask (Handler handler, RegisterRequest registerRequest, String address, String port) {
        this.address = address;
        this.port = port;
        this.handler = handler;
        this.registerRequest = registerRequest;
    }

    @Override
    public void run() {
        ServerProxy proxy = new ServerProxy(address, port);
        LoginResult result = (LoginResult) proxy.register(registerRequest);
        if (result.isSuccess()) {
            DataCache cache = DataCache.getInstance();
        }

        Boolean success = ((GenericResult) result).isSuccess();
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();

        messageBundle.putBoolean(REGISTER_SUCCESS_KEY,success);
        messageBundle.putString(AUTHTOKEN_KEY, result.getAuthtoken());
        messageBundle.putString(PERSON_ID_KEY, result.getPersonID());

        message.setData(messageBundle);
        handler.sendMessage(message);
    }

    public void registerListener(LoginFragment.Listener listener) {
        this.listener = listener;
    }

}
