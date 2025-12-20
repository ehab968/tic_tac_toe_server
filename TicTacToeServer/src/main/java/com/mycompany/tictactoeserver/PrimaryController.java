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
    //UserDAO userDAO=new UserDAO();
    public PrimaryController() {
         //   new Thread(() -> startLoginServer()).start();

    }
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            new Thread(() -> {
                     server = new ServerMain();
                     server.startServer();
                 }).start();
 
    }    
        /*    private void startLoginServer() {

                try {
                    while(true)
                    {
                        Socket s = server.getServerSocket().accept();
                        new Thread(() -> handleClient(s)).start();
                    }
                } catch (IOException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            }
     */
    
    /*void handleClient( Socket s){

            try {
               ObjectInputStream  ear = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream  mouth = new ObjectOutputStream(s.getOutputStream());
                LoginData request=(LoginData) ear.readObject();
                UserData user =userDAO.login(request);
                Response<UserData> response = (user != null)
                        ? new Response(true, CommandType.LOGIN_SUCCESS, user)
                        : new Response(false,CommandType.Invalid_Username_or_password, null);

           mouth.writeObject(response);
           mouth.close();
            ear.close();
            s.close();
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }


            }

*/
}