package main;

import connection.Credentials;
import connection.JBDCConnection;
import framespackage.LoginFrame;

public class Main {
    public static void main(String[] args) {
        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        new JBDCConnection(url, username, password);
        LoginFrame.CreateWindow();
    }
}
