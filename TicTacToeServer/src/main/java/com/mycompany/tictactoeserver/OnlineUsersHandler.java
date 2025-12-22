/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.RequestType;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ehab
 */
public class OnlineUsersHandler {

    public Response getOnlineUsers(UserSocket cs, Request request) {
        UserDAO userDAO = new UserDAO();
        Response<List<UserData>> responseList;

        try {
            List<UserData> onlineUsers = userDAO.getOnlineUsers();
            System.out.println("online users are" + onlineUsers);
            if (onlineUsers.isEmpty()) {
                responseList = new Response<>(false, ResponseType.NO_ONLINEUSERS, null);
            } else {
                responseList = new Response<>(true, ResponseType.GET_ONLINE_USERS_SUCCESS, onlineUsers);
            }
        } catch (SQLException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            responseList = new Response(false, ResponseType.SERVER_FAILURE, null);
        }

        return responseList;
    }

    public Response sendGameInvite(UserSocket userSocket1, Request request) {

        UserSocket userSocket2 = null;
        UserData userData2 = (UserData) request.getData();

        for (int i = 0; i < ServerMain.onlineSockets.size(); i++) {

            if (userData2.getUserName() == ServerMain.onlineSockets.get(i).user.getUserName()) {
                userSocket2 = ServerMain.onlineSockets.get(i);
                break;
            }

        }

        if (userSocket2 == null) {
            return new Response(false, ResponseType.INVITE_DROPPED, null);
        } else {
            try {
                userSocket2.write(new Response(true, ResponseType.REQUEST_GAME, userSocket1.user));
                Request userRequest = userSocket2.read();

                if (userRequest.getType() == RequestType.ACCEPT_INVITE) {

                    userSocket1.write(
                            new Response(true, ResponseType.START_GAME, userSocket2.user)
                    );

                    userSocket2.write(
                            new Response(true, ResponseType.START_GAME, userSocket1.user)
                    );

                    return null;
                }
                if (userRequest.getType() == RequestType.REJECT_INVITE) {
                    return new Response(true, ResponseType.INVITE_REJECTED, null);
                }
            } catch (IOException ex) {
                System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (ClassNotFoundException ex) {
                System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return new Response(false, ResponseType.INVITE_DROPPED, null);
    }
}
