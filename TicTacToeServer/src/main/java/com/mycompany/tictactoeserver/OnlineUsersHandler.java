/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.GameData;
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

    public Response getLeaderBoard(UserSocket cs, Request request) {
        UserDAO userDAO = new UserDAO();
        Response<List<UserData>> responseList;

        try {
            List<UserData> onlineUsers = userDAO.getLeaderBoard();
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

    private UserStreamSocket getUserStreamSocket(UserData userData) {
        for (int i = 0; i < ServerMain.onlineStreamSockets.size(); i++) {
            if (userData.getUserName().equals(ServerMain.onlineStreamSockets.get(i).user.getUserName())) {
                return ServerMain.onlineStreamSockets.get(i);

            }
        }
        return null;
    }

    public Response sendGameInvite(UserStreamSocket userSocket1, Request request) {

        UserStreamSocket userSocket2 = getUserStreamSocket((UserData) request.getData());

        if (userSocket2 == null) {
            return new Response(false, ResponseType.INVITE_DROPPED, null);
        } else {
            try {
                userSocket2.write(new Response(true, ResponseType.REQUEST_GAME, userSocket1.user));
                return null;
            } catch (IOException ex) {
                System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return new Response(false, ResponseType.INVITE_DROPPED, null);

    }

    public Response acceptGameInvite(UserStreamSocket userSocket2, Request request) {
        try {
            UserStreamSocket userSocket1 = getUserStreamSocket((UserData) request.getData());
            GameData game = new GameData("1", userSocket1.user, userSocket1.user);

            userSocket1.write(new Response(true, ResponseType.START_GAME, game));
            userSocket2.write(new Response(true, ResponseType.START_GAME, game));
            return null;
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return new Response(false, ResponseType.SERVER_FAILURE, null);
    }

    public Response rejectGameInvite(UserStreamSocket userSocket2, Request request) {
        try {
            UserStreamSocket userSocket1 = getUserStreamSocket((UserData) request.getData());
            userSocket1.write(new Response(true, ResponseType.INVITE_REJECTED, null));
            return null;
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return new Response(false, ResponseType.SERVER_FAILURE, null);

    }

}
