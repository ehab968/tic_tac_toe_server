package com.mycompany.tictactoeserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ServerMain {

    private ServerSocket serverSocket;
    public static final List<ClientSocket> onlineSockets = new CopyOnWriteArrayList<>();

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
        onlineSockets.add(cs);
    }


    public static void onUserDisconnected(ClientSocket cs) {
        onlineSockets.remove(cs);
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
