/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.AuthData;
import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.sql.SQLException;

/**
 *
 * @author mahmo
 */
public class AuthHandler {

    public static Response login(ClientSocket cs, Request request) {
        Response<UserData> response;

        AuthData loginData = (AuthData) request.getData();

        try {
            UserDAO userDao = new UserDAO();
            UserData userData = userDao.login(loginData);
            if (userData != null) {
                userData.setStatus(1);
                response = new Response(true, ResponseType.LOGIN_SUCCESS, userData);
                cs.setUser(userData);
                UserDAO.updateUserOnlineStatus(userData, 1);
            } else {
                response = new Response(false, ResponseType.Invalid_Username_or_password, null);
            }
        } catch (SQLException ex) {
            response = new Response(false, ResponseType.SERVER_FAILURE, null);
        }
        return response;
    }

    public static Response register(ClientSocket cs, Request request) {
        AuthData newUser = (AuthData) request.getData();
        Response<UserData> response;
        UserDAO userDao = new UserDAO();
        if (userDao.usernameExists(newUser.getUserName())) {
            response = new Response<>(false, ResponseType.USERNAME_EXISTS, null);
            System.out.println("Username exists");
        } else {
            try {
                userDao.insertContact(newUser);

                UserData createdUser = new UserData(newUser.getUserName(), newUser.getPassword(), 0, 0, 0, 0, 0);
                response = new Response<>(true, ResponseType.REGISTER_SUCCESS, createdUser);
                System.out.println("User registered");
            } catch (SQLException ex) {
                response = new Response<>(false, ResponseType.SERVER_FAILURE, null);
                System.getLogger(AuthHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return response;
    }
}
