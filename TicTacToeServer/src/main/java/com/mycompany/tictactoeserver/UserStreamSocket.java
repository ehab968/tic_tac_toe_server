/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.GameMove;
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
class UserStreamSocket extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public UserData user;

    public UserStreamSocket(Socket socket) {
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

    @Override
    public void run() {
        try {
            OnlineUsersHandler onlineUserHandler = new OnlineUsersHandler();
            while (true) {
                Object obj = in.readObject();
                if (!(obj instanceof Request)) {
                    continue;
                }
                // request -> INVITE_USER & userData2 (userName2)
                Request request = (Request) obj;
                Response response = null;
                System.out.println("Server Receiver request " + request);

                switch (request.getType()) {
                    case SET_USER:
                        user = (UserData) request.getData();
                        break;
                    case INVITE_USER:
                        response = onlineUserHandler.sendGameInvite(this, request);
                        break;
                    case ACCEPT_INVITE:
                        response = onlineUserHandler.acceptGameInvite(this, request);
                        break;
                    case REJECT_INVITE:
                        response = onlineUserHandler.rejectGameInvite(this, request);
                        break;
                    case START_GAME:
                    case MOVE:      
                        onlineUserHandler.sendMove(this, request);
                        break;
                    case RESTART_GAME:
                        onlineUserHandler.restartGame(this);
                        break;
                    case END_GAME:
                        onlineUserHandler.endGame(this);
                        break;
                    case UPDATE_SCORE:
                        onlineUserHandler.updateScore(this, request);
                        break;
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
        } catch (java.net.SocketException e) {
            System.out.println("client socket stopped");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ServerMain.onlineStreamSockets.remove(this);
            System.out.println("Client disconnected");
            closeResources();
            System.out.println("resources closed");
        }
    }

    public void write(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    public Request read() throws IOException, ClassNotFoundException {
        Request request = (Request) in.readObject();
        return request;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            System.getLogger(UserStreamSocket.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
