package jeff.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jeff.Home;

public class HomeController {
    boolean closeAtivo = false;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Button close;
    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    void actionClientes(MouseEvent event) {
        loadUI("listaCliente");
    }

    @FXML
    void actionCondicional(MouseEvent event) {
        loadUI("condicional");
    }

    @FXML
    void actionHome(MouseEvent event) {
        loadUI("dashBoard");
    }

    @FXML
    void actionRelatorio(MouseEvent event) {
        loadUI("relatorio");
    }

    @FXML
    void actionRoupa(MouseEvent event) {
        loadUI("listaRoupa");
    }

    @FXML
    void onClose(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closePrint(MouseEvent event) {
        if (!closeAtivo) {

        } else {
            System.out.println("Saiu daqui");
        }
        closeAtivo = !closeAtivo;
    }

    @FXML
    void initialize() {
        loadUI("dashBoard");
    }

    private void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Home.class.getResource("view/" + ui + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
    }
}
