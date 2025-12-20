package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.AuthData;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerMain {

    private ServerSocket serverSocket;
    public static final List<ClientSocket> onlineUsers = new CopyOnWriteArrayList<>();

    public void startServer() {

        try {
            serverSocket = new ServerSocket(5005);
            System.out.println("Server started on port 5005");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                onUserConnected(new ClientSocket(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUserConnected(ClientSocket cs) {
        onlineUsers.add(cs);
    }

    public static void onUserDisconnected(ClientSocket cs) {
        onlineUsers.remove(cs);
        if (cs.user != null) {
            try {
                UserDAO.updateUserOnlineStatus(cs.user, 0);
            } catch (SQLException ex) {
                System.out.println("Faild to update user online status.. username is: " + cs.user.getUserName());
                System.getLogger(ServerMain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

    }
}

class AuthHandler {

    public static Response login(ClientSocket cs, Request request) {
        Response<UserData> response;

        AuthData loginData = (AuthData) request.getData();

        try {
            UserData userData = UserDAO.login(loginData);
            if (userData != null) {
                userData.setStatus(1);
                response = new Response(true, ResponseType.LOGIN_SUCCESS, userData);
                cs.setUser(userData);
                UserDAO.updateUserOnlineStatus(userData, 1);
            } else {
                response = new Response(false, ResponseType.Invalid_Username_or_password, null);
            }
        } catch (SQLException ex) {
            response = new Response(false, ResponseType.SERVER_FAILURE, null);
        }
        return response;
    }

    public static Response register(ClientSocket cs, Request request) {
        AuthData newUser = (AuthData) request.getData();
        Response<UserData> response;

        if (UserDAO.usernameExists(newUser.getUserName())) {
            response = new Response<>(false, ResponseType.USERNAME_EXISTS, null);
            System.out.println("Username exists");
        } else {
            try {
                UserDAO.insertContact(newUser);

                UserData createdUser = new UserData(newUser.getUserName(), newUser.getPassword(), 0, 0, 0, 0, 0);
                response = new Response<>(true, ResponseType.REGISTER_SUCCESS, createdUser);
                System.out.println("User registered");
            } catch (SQLException ex) {
                response = new Response<>(false, ResponseType.SERVER_FAILURE, null);
                System.getLogger(AuthHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return response;
    }
}
