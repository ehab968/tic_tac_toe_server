/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;
/**
 *
 * @author Ahmed Sayed
 */
public class UserDAO {
    
    static{
    
        try {
            DriverManager.registerDriver(new ClientDriver());
            Connection cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/user_info", "root", "root");
        } catch (SQLException ex) {
            System.getLogger(UserDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    
    
    }    
    
    
    
    
    
}
