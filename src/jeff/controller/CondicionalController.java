package jeff.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import jeff.model.dao.AtendenteDAO;
import jeff.model.dao.ClienteDAO;
import jeff.model.dao.CondicionalDAO;
import jeff.model.dao.ItensCondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Cliente;
import jeff.model.domain.Condicional;
import jeff.util.Util;

public class CondicionalController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btAlterar;

    @FXML
    private Button btInserir;

    @FXML
    private Button btRemover;
    @FXML
    private TableView<Condicional> tbCondicional;
    @FXML
    private TableColumn<Condicional, Cliente> clCliente;
    @FXML
    private TableColumn<Condicional, Cliente> clAtendente;
    @FXML
    private TableColumn<Condicional, Integer> clID;

    @FXML
    private TableColumn<Condicional, LocalDate> clData;

    @FXML
    private Label lbCliente;

    @FXML
    private Label lbID;

    @FXML
    private Label lbData;

    @FXML
    private Label lbAtivo;

    @FXML
    private Label lbValor;

    private List<Condicional> listCondicional;
    private ObservableList<Condicional> observableListCondicional;

    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final CondicionalDAO CondicionalDAO = new CondicionalDAO();
    private final ItensCondicionalDAO itensCondicionalDAO = new ItensCondicionalDAO();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AtendenteDAO atendenteDAO = new AtendenteDAO();

    @FXML
    void actionAdd(ActionEvent event) throws IOException {
        Condicional Condicional = new Condicional();
        boolean fezACondicional = showCondicionalDialog(Condicional);
        if(fezACondicional){
            CondicionalDAO.inserir(Condicional);
            carregarTabelaViewCondicionals();
        }
    }

    private boolean showCondicionalDialog(Condicional condicional) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("view/ProcessosCondicionalsDialog.fxml"));
        AnchorPane pane = (AnchorPane) fxmlLoader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Processo Condicionals Dialog");
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        // ProcessosCondicionalsDialogController controller = fxmlLoader.getController();
        // controller.setDialogStage(dialogStage);
        return false;
    }

    @FXML
    void actionEdit(ActionEvent event) {
        System.out.println("EDIT");
    }

    @FXML
    void actionRemove(ActionEvent event) {
        Condicional condicional = tbCondicional.getSelectionModel().getSelectedItem();
        if (condicional != null){

        } else {
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        CondicionalDAO.setConnection(connection);
        carregarTabelaViewCondicionals();

        tbCondicional.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTab(newValue));
    }

    private void selecionarItemTab(Condicional condicional) {
        if (condicional == null) {
            lbID.setText("");
            lbData.setText("");
            lbAtivo.setText("");
            lbValor.setText("");
            lbCliente.setText("");
        } else {
            lbID.setText(String.valueOf(condicional.getId()));
            lbData.setText(Util.parseString(condicional.getData()));
            lbAtivo.setText(!condicional.isAtivo() ? "Não" : "Sim");
            lbValor.setText(Util.toStringDinheiro(condicional.getValor()));
            lbCliente.setText(condicional.getCliente().getNome());
        }
    }

    private void carregarTabelaViewCondicionals() {
        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clData.setCellValueFactory(new PropertyValueFactory<>("data"));
        clCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        listCondicional = CondicionalDAO.listar();

        observableListCondicional = FXCollections.observableArrayList(listCondicional);
        tbCondicional.setItems(observableListCondicional);
    }
}