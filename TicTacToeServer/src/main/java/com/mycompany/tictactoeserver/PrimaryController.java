/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.tictactoeserver;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
/**
 * FXML Controller class
 *
 * @author User
 */
public class PrimaryController implements Initializable {


    ServerMain server;
    public PrimaryController() {

    }
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            new Thread(() -> {
                     server = new ServerMain();
                     server.startServer();
                 }).start();
 
    }    
      
}