module com.mycompany.tictactoeserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
	requires tic.tac.toe.shared;

    opens com.mycompany.tictactoeserver to javafx.fxml;
    exports com.mycompany.tictactoeserver;
}
