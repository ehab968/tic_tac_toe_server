package com.mycompany.tictactoeserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerMain {

    public static final List<UserSocket> onlineSockets = new CopyOnWriteArrayList<>();
    public static ServerSocket serverSocket;
    public static volatile boolean run = false;

    public void startServer() {
        run = true;
        try {
            serverSocket = new ServerSocket(5005);
            System.out.println("Server started on port 5005");
            while (run) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");
                    onUserConnected(new UserSocket(socket));
                } catch (IOException ex) {
                    if (run) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void stopServer() {
        run = false;
        UserDAO dao = new UserDAO();
        try {
            for (UserSocket us : onlineSockets) {
                us.closeResources();
                dao.updateUserOnlineStatus(us.user, 0);
            }
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server stopped");
            }

        } catch (IOException ex) {
            System.getLogger(ServerMain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (SQLException ex) {
            System.getLogger(ServerMain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void onUserConnected(UserSocket cs) {
        onlineSockets.add(cs);
    }

    public static void onUserDisconnected(UserSocket cs) {
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
