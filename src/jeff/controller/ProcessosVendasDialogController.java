package jeff.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jeff.model.domain.Cliente;
import jeff.model.domain.Produto;

public class ProcessosVendasDialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Cliente> cbClientes;

    @FXML
    private ComboBox<Produto> cbProdutos;

    @FXML
    private CheckBox ckPago;

    @FXML
    private TableColumn<Produto, String> clNomeProduto;

    @FXML
    private TableColumn<Produto, Integer> clQuantidade;

    @FXML
    private TableColumn<Produto, Double> clValor;

    @FXML
    private DatePicker dpData;

    @FXML
    private TableView<Produto> tabItens;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TextField tfValor;

    @FXML
    void actionCancela(ActionEvent event) {

    }

    @FXML
    void actionConfirma(ActionEvent event) {

    }

    @FXML
    void actionDown(ActionEvent event) {

    }

    @FXML
    void actionUp(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
    }

}
