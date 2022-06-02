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
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Cliente;

public class CadastroRoupaController {
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
    private Label idCPF;

    @FXML
    private Label idCodiigo;

    @FXML
    private Label idNome;

    @FXML
    private Label idTelefone;

    @FXML
    private TableView<Cliente> tableCliente;

    @FXML
    private TableColumn<Cliente, String> tableColumnClienteCPF;

    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;

    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        clienteDAO.setConnection(connection);
        carregarTableViewCliente();

        tableCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldV, newV) -> selectedItemTabViewClient(newV));
    }

    private void selectedItemTabViewClient(Cliente cliente) {
        if (cliente == null) {
            idCodiigo.setText("");
            idNome.setText("");
            idCPF.setText("");
            idTelefone.setText("");
        } else {
            idCodiigo.setText(String.valueOf(cliente.getID()));
            idNome.setText(cliente.getNome());
            idCPF.setText(cliente.getCpf());
            idTelefone.setText(cliente.getTelefone());
        }
    }

    private void carregarTableViewCliente() {
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        listClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableCliente.setItems(observableListClientes);
    }

    @FXML
    private void buttonInserir() {
        Cliente cliente = new Cliente();
        boolean clickedOK = clienteDialog(cliente);
        if (clickedOK) {
            clienteDAO.inserir(cliente);
            carregarTableViewCliente();
        }
    }

    @FXML
    private void buttonAlterar() {
        Cliente cliente = tableCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean clickedOK = clienteDialog(cliente);
            if (clickedOK) {
                clienteDAO.alterar(cliente);
                carregarTableViewCliente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione um cliente na tabela para alterar!");
            alert.show();
        }
    }

    @FXML
    private void buttonRemover() {
        Cliente cliente = tableCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            clienteDAO.remover(cliente);
            carregarTableViewCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione um cliente na tabela para remover!");
            alert.show();
        }
    }

    private boolean clienteDialog(Cliente cliente) {

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("view/cadastroClienteDialog.fxml"));
        AnchorPane pane = null;
        try {
            pane = (AnchorPane) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Stage dialog = new Stage();
        dialog.setTitle("Cadastro de Cliente");
        Scene scene = new Scene(pane);
        dialog.setScene(scene);

        CadastroClienteDialogController controller = fxmlLoader.getController();
        controller.setStageDialog(dialog);
        controller.setCliente(cliente);
    
        dialog.showAndWait();

        return controller.isClicadoOK();
    }
}
