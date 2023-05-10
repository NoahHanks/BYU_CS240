package com.example.familymapclient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.LoginResult;
import Result.PersonResult;
import nhanks10.byu.edu.cs240.familymapclient.ServerProxy;

// Server must be cleared manually after each test

public class ServerProxyTest {
    ServerProxy serverProxy = new ServerProxy("localhost", "8080");

    @Test
    public void RegisterPass() {
        RegisterRequest registerRequest = new RegisterRequest("nhanks10", "password",
                "noahhanks10@gmail.com", "Noah", "Hanks", "m");
        LoginResult resultFirstTime = (LoginResult) serverProxy.register(registerRequest);
        assertTrue(resultFirstTime.isSuccess());
        assertNotNull(resultFirstTime.getAuthtoken());
    }

    @Test
    public void RegisterFail() {
        RegisterRequest registerRequest = new RegisterRequest("bad", "register",
                "badRegister@gmail.com", "Bad", "Register", "m");
        LoginResult resultFirstTime = (LoginResult) serverProxy.register(registerRequest);
        assertTrue(resultFirstTime.isSuccess());
        assertNotNull(resultFirstTime.getAuthtoken());

        LoginResult reRegister = (LoginResult) serverProxy.register(registerRequest);
        assertFalse(reRegister.isSuccess());
        assertNull(reRegister.getAuthtoken());
    }

    @Test
    public void LoginPass() {
        RegisterRequest registerRequest = new RegisterRequest("good", "register",
                "goodRegsiter@gmail.com", "Good", "Register", "m");
        LoginRequest loginRequest = new LoginRequest("good", "register");
        serverProxy.register(registerRequest);
        LoginResult loginResult = serverProxy.login(loginRequest);
        assertTrue(loginResult.isSuccess());
        assertNotNull(loginResult.getAuthtoken());
    }

    @Test
    public void LoginFail() {
        LoginRequest notRegistered = new LoginRequest("not", "registered");
        LoginResult loginResult = serverProxy.login(notRegistered);
        assertFalse(loginResult.isSuccess());
        assertNull(loginResult.getAuthtoken());
    }

    @Test
    public void GetPeoplePass() {
        RegisterRequest registerRequest = new RegisterRequest("good", "register",
                "goodRegsiter@gmail.com", "Good", "Register", "m");
        LoginRequest loginRequest = new LoginRequest("good", "register");
        serverProxy.register(registerRequest);
        LoginResult loginResult = serverProxy.login(loginRequest);
        PersonResult result = serverProxy.getPeople(loginResult.getAuthtoken());
        assertTrue(result.isSuccess());
        assertNotNull(result.getPersons());
    }

    @Test
    public void GetPeopleFail() {
        RegisterRequest registerRequest = new RegisterRequest("bad", "register",
                "badRegister@gmail.com", "Bad", "Register", "m");
        serverProxy.register(registerRequest);
        PersonResult result = serverProxy.getPeople("badAuth");
        assertFalse(result.isSuccess());
        assertNull(result.getPersons());
    }

    @Test
    public void GetEventsPass() {
        RegisterRequest registerRequest = new RegisterRequest("good", "register",
                "goodRegsiter@gmail.com", "Good", "Register", "m");
        LoginRequest loginRequest = new LoginRequest("good", "register");
        serverProxy.register(registerRequest);
        LoginResult loginResult = serverProxy.login(loginRequest);
        EventResult result = serverProxy.getEvents(loginResult.getAuthtoken());
        assertTrue(result.isSuccess());
        assertNotNull(result.getEvents());
    }

    @Test
    public void GetEventsFail() {
        RegisterRequest registerRequest = new RegisterRequest("bad", "register",
                "badRegister@gmail.com", "Bad", "Register", "m");
        serverProxy.register(registerRequest);
        EventResult result = serverProxy.getEvents("badAuth");
        assertFalse(result.isSuccess());
        assertNull(result.getEvents());
    }
}
