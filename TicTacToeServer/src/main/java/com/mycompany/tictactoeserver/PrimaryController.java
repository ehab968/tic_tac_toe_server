/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.tictactoeserver;


import com.iti.group3.tic_tac_toe_shared.LoginData;
import com.iti.group3.tic_tac_toe_shared.LoginResponse;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

  
    ServerSocket serverSocket;
    ObjectInputStream ear;
    ObjectOutputStream mouth;
    UserDAO userDAO=new UserDAO();
    public PrimaryController() {
            new Thread(() -> startLoginServer()).start();

    }
    
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
    
            private void startLoginServer() {
                try {
                    serverSocket = new ServerSocket(5005);
                    while(true)
                    {
                        Socket s = serverSocket.accept();
                        ear = new ObjectInputStream(s.getInputStream());
                        mouth = new ObjectOutputStream(s.getOutputStream());
                        LoginData request=(LoginData) ear.readObject();
                        UserData user =userDAO.login(request);
                        LoginResponse response = (user != null)
                                ? new LoginResponse(true, "Login successful", user)
                                : new LoginResponse(false, "Invalid username or password", null);
                        
                        mouth.writeObject(response);
                        if (response.getUser() != null) {
                            System.out.println("server response: " + response.getMessage() + ", " + response.getUser().getUserName());
                        } else {
                            System.out.println("server response: " + response.getMessage());
                        }
                        
                        
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (SQLException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            }
     

   
}
