/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.Command;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.iti.group3.tic_tac_toe_shared.CommandType;


/**
 *
 * @author Ahmed Sayed
 */
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

    public static void main(String[] args) {
        new ServerMain().startServer();
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


    //@Override
//    public void run() {
//        while (true) {
//            try {
//                Object obj = in.readObject();
//                if (!(obj instanceof UserData)) {
//                    continue;
//                }
//                UserData user = (UserData) obj;
//
//                if (userDAO.usernameExists(user.getUserName())) {
//                    out.writeObject("USERNAME_EXISTS");
//                    out.flush();
//                    System.out.println("**************** Ahmed Sayed Exists **************");
//                    return;
//
//                } else {
//                    userDAO.insertContact(user);
//                    out.writeObject("REGISTER_SUCCESS");
//                    out.flush();
//                    System.out.println("**************** User Registered **************");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error processing client request: " + e.getMessage());
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (in != null) {
//                        in.close();
//                    }
//                    if (out != null) {
//                        out.close();
//                    }
//                    if (socket != null) {
//                        socket.close();
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
    
 @Override
public void run() {
    try {
        while (true) {
            Object obj = in.readObject();

            if (!(obj instanceof UserData)) {
                continue;
            }

            UserData user = (UserData) obj;

            if (userDAO.usernameExists(user.getUserName())) {
                out.writeObject(new Command(CommandType.USERNAME_EXISTS));
                out.flush();
                System.out.println("Username exists");
            } else {
                userDAO.insertContact(user);
                out.writeObject(new Command(CommandType.REGISTER_SUCCESS));
                out.flush();
                System.out.println("User registered");
            }
        }
    } catch (Exception e) {
        System.out.println("Client disconnected or error occurred");
        e.printStackTrace();
    } finally {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




}
