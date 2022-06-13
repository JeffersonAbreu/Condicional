package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
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
import javafx.util.Duration;
import jeff.model.dao.CondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Condicional;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class RelatorioController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btImprimir;
    @FXML
    private JFXComboBox<String> cbFiltro;
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
    private final Connection connection = database.conectar();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private final List<String> opcoes = List.of("Selecione uma opção...", "Opção 1", "Opção 2");

    @FXML
    void initialize() {
        condicionalDAO.setConnection(connection);
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
            switch (newValue.intValue()) {
                case 1:
                    obsListaCondicional.setAll(condicionalDAO.listar());
                    break;
                case 2:
                    int id = 1;

                    // obsListaCondicional.setAll(condicionalDAO.listarPorIdCliente(id));
                    break;
                default:
                    obsListaCondicional.clear();
                    break;
            }
            if (obsListaCondicional.isEmpty()) {
                tabCondicional.setPlaceholder(new Label("Nenhum registro encontrado!\n\tby Jefferson Abreu"));
                btImprimir.setDisable(true);
            } else {
                btImprimir.setDisable(false);
            }
        });
        cbFiltro.getSelectionModel().select(0);
    }

    @FXML
    void actionImprimir(ActionEvent event) {
        TrayNotification tray = new TrayNotification("FOI QUASE - Reletório", "A função que gera o relatório eu não consegui fazer!", NotificationType.WARNING);
        tray.showAndDismiss(Duration.seconds(15));
    }
}
