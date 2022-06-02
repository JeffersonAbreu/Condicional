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
import jeff.model.dao.ProdutoDAO;
import jeff.model.dao.RoupaDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Cliente;
import jeff.model.domain.Roupa;
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
    private TableView<Roupa> tbRoupas;
    @FXML
    private TableColumn<Roupa, Cliente> clCliente;

    @FXML
    private TableColumn<Roupa, Integer> clCodigo;

    @FXML
    private TableColumn<Roupa, LocalDate> clData;

    @FXML
    private Label lbCliente;

    @FXML
    private Label lbCodigo;

    @FXML
    private Label lbData;

    @FXML
    private Label lbPago;

    @FXML
    private Label lbValor;

    private List<Roupa> listRoupas;
    private ObservableList<Roupa> observableListRoupas;

    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final RoupaDAO roupaDAO = new RoupaDAO();
    private final ItensCondicionalDAO itensCondicionalDAO = new ItensCondicionalDAO();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AtendenteDAO atendenteDAO = new AtendenteDAO();

    @FXML
    void actionAdd(ActionEvent event) throws IOException {
        Roupa roupa = new Roupa();
        boolean fezARoupa = showRoupaDialog(roupa);
        if(fezARoupa){
            roupaDAO.inserir(roupa);
            carregarTabelaViewRoupas();
        }
    }

    private boolean showRoupaDialog(Roupa roupa) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("view/ProcessosRoupasDialog.fxml"));
        AnchorPane pane = (AnchorPane) fxmlLoader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Processo Roupas Dialog");
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        // ProcessosRoupasDialogController controller = fxmlLoader.getController();
        // controller.setDialogStage(dialogStage);
        return false;
    }

    @FXML
    void actionEdit(ActionEvent event) {
        System.out.println("EDIT");
    }

    @FXML
    void actionRemove(ActionEvent event) {
        Roupa roupa = tbRoupas.getSelectionModel().getSelectedItem();
        if (roupa != null){

        } else {
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        roupaDAO.setConnection(connection);
        carregarTabelaViewRoupas();

        tbRoupas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTab(newValue));
    }

    private void selecionarItemTab(Roupa roupa) {
        if (roupa == null) {
            lbCodigo.setText("");
            lbData.setText("");
            lbPago.setText("");
            lbValor.setText("");
            lbCliente.setText("");
        } else {
            lbCodigo.setText(String.valueOf(roupa.getCdRoupa()));
            lbData.setText(Util.parseString(roupa.getData()));
            lbPago.setText(!roupa.getPago() ? "Não" : "Sim");
            lbValor.setText(Util.toStringDinheiro(roupa.getValor()));
            lbCliente.setText(roupa.getCliente().getNome());
        }
    }

    private void carregarTabelaViewRoupas() {
        clCodigo.setCellValueFactory(new PropertyValueFactory<>("cdRoupa"));
        clData.setCellValueFactory(new PropertyValueFactory<>("data"));
        clCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        listRoupas = roupaDAO.listar();

        observableListRoupas = FXCollections.observableArrayList(listRoupas);
        tbRoupas.setItems(observableListRoupas);
    }
}