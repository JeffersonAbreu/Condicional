package jeff.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jeff.model.dao.ClienteDAO;
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
    private TableColumn<Cliente, Integer> clIdade;

    @FXML
    private TableColumn<Cliente, String> clMatricula;

    @FXML
    private TableColumn<Cliente, String> clNome;

    @FXML
    private ListView<Cliente> listView;

    @FXML
    private TableView<Cliente> tabView;

    private List<Cliente> listaClientes;
    private ClienteDAO clienteDAO;
    private ObservableList<Cliente> observableListClienteTabView;
    private ObservableList<Cliente> observableListClienteListView;

    @FXML
    void actionAdd(ActionEvent event) {
        Cliente a = tabView.getSelectionModel().getSelectedItem();
        if(a == null){
            Alert alert = new Alert(AlertType.ERROR, "Por favor, escolha um Cliente na Tabela.");
            alert.showAndWait();
        } else {
            observableListClienteListView.add(a);
            observableListClienteTabView.remove(a);
        }
    }

    @FXML
    void actionRemove(ActionEvent event) {
        Cliente a = listView.getSelectionModel().getSelectedItem();
        if(a == null){
            Alert alert = new Alert(AlertType.ERROR, "Por favor, escolha um Cliente na Lista");
            alert.showAndWait();
        } else {
            observableListClienteTabView.add(a);
            observableListClienteListView.remove(a);
        }
    }

    @FXML
    void initialize() {
        carregaTabView();
    }

    private void carregaTabView() {
        clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        clIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        clienteDAO = new ClienteDAO();
        listaClientes = clienteDAO.listar();
        
        observableListClienteTabView = FXCollections.observableArrayList(listaClientes);
        observableListClienteListView = FXCollections.observableArrayList();
        tabView.setItems(observableListClienteTabView);
        listView.setItems(observableListClienteListView);

    }

}
