/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;
//import java.sql.PreparedStatement;

import com.iti.group3.tic_tac_toe_shared.LoginData;
import com.iti.group3.tic_tac_toe_shared.UserData;

/**
 *
 * @author COMPUMARTS
 */
public class UserDAO {
    
    UserData login(LoginData request){
         if (request.getUserName().equals("shahd")
                && request.getPassword().equals("123")) {

            return new UserData(1, "shahd");
        }

        return null;
    }
 /*       
   PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");

      
            ps.setString(1, request.getUserName());
            ps.setString(2, request.getPassword()); 

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserData user = new UserData(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role")
                );
                return user;
            } else {
                return null;
          }
    }*/
}