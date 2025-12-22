package com.mycompany.tictactoeserver;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ehab
 */
public class PrimaryController implements Initializable {

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private PieChart usersPieChart;

    @FXML
    private Label onlineUsersLabel;

    @FXML
    private Label offlineUsersLabel;

    @FXML
    private Label serverStatus;

    private int onlineUsers;
    private int offlineUsers;
    ServerMain server = new ServerMain();
    UserDAO dao = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getOlineUsers();
            getOfflineUsers();
            updateUI();
        } catch (SQLException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @FXML
    private void startServer(ActionEvent event) {
        if (server.run) {
            showAlert("Server Status", "server already run");
            return;
        }
        new Thread(() -> {
            server.startServer();
        }).start();
        Platform.runLater(() -> {
            serverStatus.setText("Server Online");
            serverStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        });
        System.out.println("Server Started");
    }

    @FXML
    private void stopServer(ActionEvent event) {
        new Thread(() -> {
            server.stopServer();
        }).start();
        Platform.runLater(() -> {
            serverStatus.setText("Server Offline");
            serverStatus.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        });
    }

    @FXML
    private void refreshServer(ActionEvent event) throws SQLException {
        onlineUsers = getOlineUsers();
        offlineUsers = getOfflineUsers();
        updateUI();
    }

    private int getOlineUsers() throws SQLException {
        return onlineUsers = dao.getOnlineUsers().size();
    }

    private int getOfflineUsers() throws SQLException {
        return offlineUsers = dao.getOfflineUsers();
    }

    private void updateUI() {
        onlineUsersLabel.setText(String.valueOf(onlineUsers));
        offlineUsersLabel.setText(String.valueOf(offlineUsers));

        usersPieChart.setData(
                FXCollections.observableArrayList(
                        new PieChart.Data("Online", onlineUsers),
                        new PieChart.Data("Offline", offlineUsers)
                )
        );

        Platform.runLater(() -> {
            for (PieChart.Data data : usersPieChart.getData()) {
                if (data.getName().equals("Online")) {
                    data.getNode().setStyle("-fx-pie-color: green;");
                } else if (data.getName().equals("Offline")) {
                    data.getNode().setStyle("-fx-pie-color: red;");
                }
            }
        });
    }

    private void showAlert(String title, String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
            }
        }
        );
    }
}
