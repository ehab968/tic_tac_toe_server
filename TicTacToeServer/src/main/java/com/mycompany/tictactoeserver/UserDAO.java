/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.AuthData;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;

public class UserDAO {

    static Connection cnn;

    static {

        try {
            DriverManager.registerDriver(new ClientDriver());
            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/users", "root", "root");
        } catch (SQLException ex) {
            System.getLogger(UserDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public static int insertContact(AuthData c) throws SQLException {
        PreparedStatement ps = cnn.prepareStatement(
                "INSERT INTO USERS (USERNAME, PASSWORD, SCORE, STATUS, WINS , LOSSES , DRAWS) VALUES (?, ? ,?, ?, ?, ?,?)"
        );
        ps.setString(1, c.getUserName());
        ps.setString(2, c.getPassword());
        ps.setInt(3, 0);
        ps.setInt(4, 0);
        ps.setInt(5, 0);
        ps.setInt(6, 0);
        ps.setInt(7, 0);

        return ps.executeUpdate();
    }

    public static UserData login(AuthData request) throws SQLException {

        PreparedStatement pst = cnn.prepareStatement("SELECT * FROM USERS WHERE username = ? AND password = ?");
        pst.setString(1, request.getUserName());
        pst.setString(2, request.getPassword());

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            UserData user = new UserData(
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD"),
                    rs.getInt("SCORE"),
                    rs.getInt("STATUS"),
                    rs.getInt("WINS"),
                    rs.getInt("LOSSES"),
                    rs.getInt("DRAWS")
            );
            return user;
        } else {
            return null;
        }

    }

    public static boolean usernameExists(String username) {
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement(
                    "SELECT USERNAME FROM USERS WHERE USERNAME = ?"
            );
            ps.setString(1, username);

            if (ps.executeQuery().next()) {
                return true;
            } else {
                return false;
            }
            //return ps.executeQuery().next();

        } catch (SQLException ex) {
            System.getLogger(UserDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;

    }

    public static int updateUserOnlineStatus(UserData user, int status) throws SQLException {
        PreparedStatement ps = cnn.prepareStatement(
                "UPDATE USERS SET STATUS = ? WHERE USERNAME = ?"
        );
        
        ps.setInt(1, status);
        ps.setString(2, user.getUserName());

        return ps.executeUpdate();
    }
}
