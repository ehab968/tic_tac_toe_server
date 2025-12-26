package com.mycompany.tictactoeserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerMain {

    public static final List<UserSocket> onlineSockets = new CopyOnWriteArrayList<>();
    public static final List<UserStreamSocket> onlineStreamSockets = new CopyOnWriteArrayList<>();
    public static final List<GameSession> activeGames = new ArrayList<GameSession>();
    public static ServerSocket serverSocket;
    public static ServerSocket serverStreamSocket;
    public static volatile boolean run = false;

    public void startServer() {
        run = true;
        try {
            serverSocket = new ServerSocket(5005);
            serverStreamSocket = new ServerSocket(5006);
            System.out.println("Server started on port 5005 and 5006");

            new Thread(() -> {
                while (run) {
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println("New client connected in socket 5005");
                        onlineSockets.add(new UserSocket(socket));
                    } catch (IOException ex) {
                        if (run) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            ).start();

            new Thread(() -> {
                while (run) {
                    try {
                        Socket socket = serverStreamSocket.accept();
                        System.out.println("New client connected in socket 5006");
                        onlineStreamSockets.add(new UserStreamSocket(socket));
                    } catch (IOException ex) {
                        if (run) {
                            ex.printStackTrace();
                        }
                    }
                }
            }).start();

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
            for (UserStreamSocket us : onlineStreamSockets) {
                us.closeResources();
            }
            if (serverSocket != null && serverStreamSocket != null) {
                serverSocket.close();
                serverStreamSocket.close();
                System.out.println("Server stopped (port 5005 & 5006)");
            }
            onlineSockets.clear();
            onlineStreamSockets.clear();

        } catch (IOException ex) {
            System.getLogger(ServerMain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (SQLException ex) {
            System.getLogger(ServerMain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
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
