/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.UserData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;
/**
 *
 * @author Ahmed Sayed
 */
public class UserDAO {
    static Connection cnn;
    
    static{
    
        try {
            DriverManager.registerDriver(new ClientDriver());
            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/users", "root", "root");
        } catch (SQLException ex) {
            System.getLogger(UserDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    
    
    }   
    
    
    
    public int insertContact(UserData c) throws SQLException {
        PreparedStatement ps = cnn.prepareStatement(
            "INSERT INTO USERS (USERNAME, PASSWORD, SCORE, STATUS, WINS , LOSSES , DRAWS) VALUES (?, ? ,?, ?, ?, ?,?)"
        );
        ps.setString(1, c.getUserName());
        ps.setString(2, c.getPassword());
        ps.setInt(3, c.getScore());
        ps.setInt(4, c.getStatus());
        ps.setInt(5, c.getWins());
        ps.setInt(6, c.getLosses());
        ps.setInt(7, c.getDraws());

        return ps.executeUpdate();
    }
    
    
    public boolean usernameExists(String username)  {
    PreparedStatement ps;
        try {
            ps = cnn.prepareStatement(
                    "SELECT USERNAME FROM USERS WHERE USERNAME = ?"
            );
           ps.setString(1, username);
           
           if(ps.executeQuery().next())return true;
           else return false;
           //return ps.executeQuery().next();

        
        } catch (SQLException ex) {
            System.getLogger(UserDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;

    }
}
    
    
    

