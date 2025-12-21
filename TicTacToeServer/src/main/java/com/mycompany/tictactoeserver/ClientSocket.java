/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author mahmo
 */
class ClientSocket extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public UserData user;

    public ClientSocket(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();
                if (!(obj instanceof Request)) {
                    continue;
                }
                Request request = (Request) obj;
                Response response = null;
                switch (request.getType()) {
                    case REGISTER:
                        response = AuthHandler.register(this, request);
                        break;
                    case LOGIN:
                        response = AuthHandler.login(this, request);
                        break;
                    case GetOnlineUsers:
                        response = OnlineUsersHandler.getOnlineUsers(this, request);
                    case LOGOUT:
                    case START_GAME:
                    case MOVE:
                    case GAME_OVER:

                    default:
                        response = new Response(false, ResponseType.UNSUPPORTED_REQUESt, null);
                }

                if (response != null) {
                    try {
                        out.writeObject(response);
                        out.flush();
                    } catch (IOException ex) {
                        System.getLogger(AuthHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected or error occurred");
            e.printStackTrace();
        } finally {
            System.out.println("Client disconnected-- counter =");
            ServerMain.onUserDisconnected(this);
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
