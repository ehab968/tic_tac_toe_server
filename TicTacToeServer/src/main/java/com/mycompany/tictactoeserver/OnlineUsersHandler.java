/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ehab
 */
public class OnlineUsersHandler {

    public static Response getOnlineUsers(UserSocket cs, Request request) {
        UserDAO userDAO = new UserDAO();
        Response<List<UserData>> responseList;
        
        try {
            List<UserData> onlineUsers = userDAO.getOnlineUsers();
            System.out.println("online users are"+onlineUsers);
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
}
