package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.AuthData;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private ServerSocket serverSocket;

    public void startServer() {

        try {
            serverSocket = new ServerSocket(5005);
            System.out.println("Server started on port 5005");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new ServerHandler(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerHandler extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private UserDAO userDAO = new UserDAO();

    public ServerHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            Object obj = in.readObject();

            if (!(obj instanceof Request)) {
                return;
            }
            Request request = (Request) obj;
            Response<UserData> response;
            switch (request.getType()) {
                case LOGIN:
                    AuthData loginData = (AuthData) request.getData();
                    UserData userData = userDAO.login(loginData);

                    response = (userData != null)
                            ? new Response(true, ResponseType.LOGIN_SUCCESS, userData)
                            : new Response(false, ResponseType.Invalid_Username_or_password, null);
                    out.writeObject(response);
                    out.flush();
                    break;

                case REGISTER:
                    AuthData newUser = (AuthData) request.getData();
                    if (userDAO.usernameExists(newUser.getUserName())) {
                        response = new Response<>(false, ResponseType.USERNAME_EXISTS, null);

                        System.out.println("Username exists");
                    } else {
                        userDAO.insertContact(newUser);
                        UserData createdUser =new UserData(newUser.getUserName(),newUser.getPassword(),0,0,0,0,0);
                        response = new Response<>(true, ResponseType.REGISTER_SUCCESS, createdUser);
                        System.out.println("User registered");
                    }
                    out.writeObject(response);
                    out.flush();
                    break;
            }

        } catch (Exception e) {
            System.out.println("Client disconnected or error occurred");
            e.printStackTrace();
        } finally {
            System.out.println("Client disconnected-- counter =");
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
