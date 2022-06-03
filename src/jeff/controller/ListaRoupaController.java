package jeff.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jeff.Home;
import jeff.model.dao.ClienteDAO;
import jeff.model.dao.RoupaDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Roupa;

public class ListaRoupaController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonRemover;

    @FXML
    private TableView<Roupa> tbRoupa;

    @FXML
    private TableColumn<Roupa, String> clID;

    @FXML
    private TableColumn<Roupa, String> clNome;

    @FXML
    private TableColumn<Roupa, String> clValor;

    @FXML
    private TableColumn<Roupa, String> clQtd;

    @FXML
    private TableColumn<Roupa, String> clQtd_em_condicional;

    private List<Roupa> listRoupas;
    private ObservableList<Roupa> observableListRoupas;

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final RoupaDAO roupaDAO = new RoupaDAO();

    @FXML
    public void initialize() {
        roupaDAO.setConnection(connection);
        carregarTableViewCliente();
        clID.setStyle("-fx-alignment: CENTER;");
        clQtd.setStyle("-fx-alignment: CENTER;");
        clQtd_em_condicional.setStyle("-fx-alignment: CENTER;");
        clValor.setStyle("-fx-alignment: baseline-right;");
    }

    private void carregarTableViewCliente() {
        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        clQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        clQtd_em_condicional.setCellValueFactory(new PropertyValueFactory<>("qtd_em_condicional"));

        listRoupas = roupaDAO.listar();

        observableListRoupas = FXCollections.observableArrayList(listRoupas);
        tbRoupa.setItems(observableListRoupas);
    }

    @FXML
    private void buttonInserir() {
        Roupa roupa = new Roupa();
        boolean clickedOK = roupaDialog(roupa);
        if (clickedOK) {
            roupaDAO.inserir(roupa);
            carregarTableViewCliente();
        }
    }

    @FXML
    private void buttonAlterar() {
        Roupa roupa = tbRoupa.getSelectionModel().getSelectedItem();
        if (roupa != null) {
            boolean clickedOK = roupaDialog(roupa);
            if (clickedOK) {
                roupaDAO.alterar(roupa);
                carregarTableViewCliente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione um roupa na tabela para alterar!");
            alert.show();
        }
    }

    @FXML
    private void buttonRemover() {
        Roupa roupa = tbRoupa.getSelectionModel().getSelectedItem();
        if (roupa != null) {
            roupaDAO.remover(roupa);
            carregarTableViewCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione um roupa na tabela para remover!");
            alert.show();
        }
    }

    private boolean roupaDialog(Roupa roupa) {

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("view/cadastroRoupaDialog.fxml"));
        AnchorPane pane = null;
        try {
            pane = (AnchorPane) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Stage dialog = new Stage();
        dialog.setTitle("Cadastro de Roupa");
        Scene scene = new Scene(pane);
        dialog.setScene(scene);

        CadastroRoupaDialogController controller = fxmlLoader.getController();
        controller.setStageDialog(dialog);
        controller.setRoupa(roupa);

        dialog.showAndWait();

        return controller.isClicadoOK();
    }
}
