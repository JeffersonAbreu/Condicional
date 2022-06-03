package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import jeff.model.dao.ClienteDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Cliente;

public class ListaClienteController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btAdd;

    @FXML
    private Button btRemove;

    @FXML
    private ImageView foto;

    @FXML
    private Label lbBairro;

    @FXML
    private Label lbCEP;

    @FXML
    private Label lbCidade;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbLogradouro;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbUF;

    @FXML
    private TableView<Cliente> tbCliente;

    @FXML
    private TableColumn<Cliente, Integer> clID;

    @FXML
    private TableColumn<Cliente, String> clNome;

    @FXML
    private TableColumn<Cliente, String> clTelefone;

    @FXML
    private TableColumn<Cliente, String> clCelular;

    @FXML
    private TableColumn<Cliente, String> clLimite;

    private List<Cliente> listaClientes;

    private ObservableList<Cliente> observableListClienteTabView;

    private ClienteDAO clienteDAO;

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();

    @FXML
    void initialize() {
        clienteDAO = new ClienteDAO();
        clienteDAO.setConnection(connection);
        listaClientes = clienteDAO.listar();
        carregaTabView();
        clID.setStyle("-fx-alignment: CENTER;");
        clLimite.setStyle("-fx-alignment: baseline-right;");
        tbCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldV, newV) -> selectedItemTabViewClient(newV));
        selectedItemTabViewClient(null);
    }

    private void selectedItemTabViewClient(Cliente cliente) {
        if (cliente == null) {
            lbNome.setText("");
            lbBairro.setText("");
            lbCEP.setText("");
            lbCidade.setText("");
            lbEmail.setText("");
            lbLogradouro.setText("");
            lbUF.setText("");
        } else {
            lbNome.setText(cliente.getNome());
            lbBairro.setText(cliente.getBairro());
            lbCEP.setText(cliente.getCep());
            lbCidade.setText(cliente.getCidade());
            lbEmail.setText(cliente.getEmail());
            lbLogradouro.setText(cliente.getLogradouro());
            lbUF.setText(cliente.getUF());
        }
    }

    private void carregaTabView() {
        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        clCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        clLimite.setCellValueFactory(new PropertyValueFactory<>("limite"));

        observableListClienteTabView = FXCollections.observableArrayList(listaClientes);
        tbCliente.setItems(observableListClienteTabView);
    }

}
