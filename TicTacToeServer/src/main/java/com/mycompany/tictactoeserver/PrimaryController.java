/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.tictactoeserver;


import com.iti.group3.tic_tac_toe_shared.UserData;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
/**
 * FXML Controller class
 *
 * @author User
 */
public class PrimaryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
        try {
            UserDAO us = new UserDAO();
            UserData ud = new UserData("ahmed_sayed", "root", 0, 0, 0, 0, 0);
            // TODO

            us.insertContact(ud);
        } catch (SQLException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }    
    
}
