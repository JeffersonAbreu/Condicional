package jeff.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class RelatorioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart pie;

    @FXML
    void initialize() {
        assert pie != null : "fx:id=\"pie\" was not injected: check your FXML file 'dashBoard.fxml'.";

    }

}
