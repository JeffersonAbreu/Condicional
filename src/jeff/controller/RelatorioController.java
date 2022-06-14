package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import jeff.model.dao.ClienteDAO;
import jeff.model.dao.CondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Cliente;
import jeff.model.domain.Condicional;
import jeff.util.UtilAutoCompleteComboBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btImprimir;
    @FXML
    private JFXComboBox<String> cbFiltro, cbCliente;
    @FXML
    private TableView<Condicional> tabCondicional;
    @FXML
    private TableColumn<Condicional, Integer> colIDCond, colNItens;
    @FXML
    private TableColumn<Condicional, String> colAtendente, colCliente, colData, colTotalCondi;

    @FXML
    private VBox telaPrincipal;

    // minhas instancias
    ObservableList<Condicional> obsListaCondicional;
    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private Connection connection = database.conectar();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final List<String> opcoes = List.of("Selecione uma opção...", "Relatório de Condicionais Ativas",
            "Relatório de Condicionais Ativas : por Cliente");
    private Cliente cliente = new Cliente();

    @FXML
    void initialize() {
        colIDCond.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colAtendente.setCellValueFactory(new PropertyValueFactory<>("nomeAtendente"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataString"));
        colNItens.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        colTotalCondi.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tabCondicional.setStyle("-fx-control-inner-background: #FFFFFF;");
        colIDCond.setStyle("-fx-alignment: CENTER;");
        colData.setStyle("-fx-alignment: CENTER;");
        colNItens.setStyle("-fx-alignment: CENTER;");
        colTotalCondi.setStyle("-fx-alignment: baseline-right;");
        obsListaCondicional = FXCollections.observableArrayList();
        tabCondicional.setItems(obsListaCondicional);
        cbFiltro.setItems(FXCollections.observableArrayList(opcoes));
        cbFiltro.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cbCliente.setVisible(false);
            switch (newValue.intValue()) {
                case 1:
                    connection = database.conectar();
                    condicionalDAO.setConnection(connection);
                    obsListaCondicional.setAll(condicionalDAO.listar());
                    database.desconectar(connection);
                    break;
                case 2:
                    obsListaCondicional.clear();
                    cbCliente.setVisible(true);
                    carregaComboBoxCliente();
                    break;
                default:
                    obsListaCondicional.clear();
                    break;
            }
            if (obsListaCondicional.isEmpty()) {
                tabCondicional.setPlaceholder(new Label("Nenhum registro encontrado!\n\t\nby Jefferson Abreu"));
                btImprimir.setDisable(true);
            } else {
                btImprimir.setDisable(false);
            }
        });
        cbFiltro.getSelectionModel().select(0);

        cbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null && !cbCliente.getSelectionModel().getSelectedItem().equals(oldValue)) {
                    cliente = new Cliente();
                    cliente.setKey(newValue);
                    connection = database.conectar();
                    condicionalDAO.setConnection(connection);
                    obsListaCondicional.setAll(condicionalDAO.listarPorCliente(cliente));
                    database.desconectar(connection);
                }
            } catch (Exception e) {
                cbCliente.getEditor().setText("");
                cliente = null;
            } finally {
                if (cliente == null)
                    cbCliente.getSelectionModel().clearSelection();
            }
            if (obsListaCondicional.isEmpty()) {
                tabCondicional.setPlaceholder(new Label("Nenhum registro encontrado!\n\t\nby Jefferson Abreu"));
                btImprimir.setDisable(true);
            } else {
                btImprimir.setDisable(false);
            }
        });
    }

    @FXML
    void actionImprimir(ActionEvent event) {
        if (cbFiltro.getSelectionModel().getSelectedIndex() == 1) {
            URL url = getClass().getResource("/jeff/relatorios/RelatorioCondicionaisEmAberto.jasper");
            try {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
                connection = database.conectar();
                // null = caso não exista filtro
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
                database.desconectar(connection);

                // false não deixar fechar a aplicação principal
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setTitle("Relatório de Condicionais em Aberto");
                jasperViewer.setVisible(true);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else if (cbFiltro.getSelectionModel().getSelectedIndex() == 2) {
            URL url = getClass().getResource("/jeff/relatorios/RelatorioCondicionaisEmAbertoPorCliente.jasper");
            try {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("id_cliente", cliente.getId());
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

                connection = database.conectar();
                // null = caso não exista filtro
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);
                database.desconectar(connection);

                // false não deixar fechar a aplicação principal
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setTitle("Relatório de Condicionais em Aberto por Cliente");
                jasperViewer.setVisible(true);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }

    private void carregaComboBoxCliente() {
        connection = database.conectar();
        clienteDAO.setConnection(connection);
        List<Cliente> listaCliente = clienteDAO.listarTodosComConticionalAtiva();
        database.desconectar(connection);
        if (listaCliente.size() > 0) {
            List<String> clienteList = new ArrayList<>();
            listaCliente.forEach(cliente -> clienteList.add(cliente.getKey()));
            cbCliente.setItems(FXCollections.observableArrayList(clienteList));
            // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
            new UtilAutoCompleteComboBox<String>(cbCliente);
        }
    }
}
