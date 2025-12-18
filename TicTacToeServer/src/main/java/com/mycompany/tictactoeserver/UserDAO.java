/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;
import com.iti.group3.tic_tac_toe_shared.LoginData;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;

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

