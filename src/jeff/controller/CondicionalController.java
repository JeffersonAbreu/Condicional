package jeff.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import jeff.model.domain.Condicional;

public class CondicionalController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    
    @FXML
    private TreeTableColumn<Condicional, String> descrition;

    @FXML
    private TreeTableColumn<Condicional, Integer> id;

    @FXML
    private TreeTableView<Condicional> root;

    @FXML
    private TreeTableColumn<Condicional, Integer> uni;

    @FXML
    private TreeTableColumn<Condicional, Double> valor;

    @FXML
    void initialize() {
    }
}