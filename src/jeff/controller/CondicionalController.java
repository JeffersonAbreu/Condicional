package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jeff.model.dao.CondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Condicional;
import jeff.model.domain.ItensCondicional;

public class CondicionalController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    // tela conponentes Principal
    @FXML
    private Button btAlterar, btExcluir, btRemoverItem;
    @FXML
    private JFXComboBox<String> nomeClienteBox;
    @FXML
    private JFXComboBox<String> nomeAtendenteBox;
    @FXML
    private TableView<Condicional> tabCondicional;
    @FXML
    private TableColumn<Condicional, Integer> colIDCond, colNItens;
    @FXML
    private TableColumn<Condicional, String> colAtendente, colCliente, colData, colTotalCondi;

    @FXML
    private TableView<ItensCondicional> tabItens;
    @FXML
    private TableColumn<ItensCondicional, Integer> colIdItem, colQtd;
    @FXML
    private TableColumn<ItensCondicional, String> colRoupa, colTotalItem, colValUni;
    @FXML
    private TableView<ItensCondicional> tabItens1;
    @FXML
    private TableColumn<ItensCondicional, Integer> colIdItem1, colQtd1;
    @FXML
    private TableColumn<ItensCondicional, String> colRoupa1, colTotalItem1, colValUni1;

    @FXML
    private TextField tfPesquisa, tfNomeCliente, tfNomeAtendente;

    @FXML
    private TextField tfItemNomeRoupa, tfItemValorUni;
    @FXML
    private VBox telaPrincipal, telaDialog, telaAddItem;
    @FXML
    private Spinner<Integer> spinnerQtd;
    @FXML
    private Label itemTitulo, iTitulo, lbData, lbTotal, lbItemTotal, lbMaxDisponivel, lbNItens;

    List<Condicional> listaCondicional;
    ObservableList<Condicional> obsListaCondicional;
    ObservableList<ItensCondicional> obsListaItens;
    ObservableList<ItensCondicional> obsListaItens1;

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();

    // Transferencia de dados entre telas
    private Condicional condicional;
    private TranslateTransition transitionTelaCondicional, transitionTelaAddItem;
    private boolean ativeTelaCondicional = false;
    private boolean ativeTelaAddItem = false;

    @FXML
    void initialize() {
        condicionalDAO.setConnection(connection);

        colIDCond.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colAtendente.setCellValueFactory(new PropertyValueFactory<>("nomeAtendente"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataString"));
        colNItens.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        colTotalCondi.setCellValueFactory(new PropertyValueFactory<>("valor"));

        colIdItem.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRoupa.setCellValueFactory(new PropertyValueFactory<>("nomeRoupa"));
        colValUni.setCellValueFactory(new PropertyValueFactory<>("valorUni"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        colTotalItem.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        colIdItem1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRoupa1.setCellValueFactory(new PropertyValueFactory<>("nomeRoupa"));
        colValUni1.setCellValueFactory(new PropertyValueFactory<>("valorUni"));
        colQtd1.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        colTotalItem1.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        carregarTabViewCondicionalTag();

        setStyles();

        tabCondicional.setOnMouseClicked(event -> {
            Condicional condicional = tabCondicional.getSelectionModel().getSelectedItem();
            if (condicional != null) {
                carregarTabViewItens(condicional.getItensDeCondicional());
                btAlterar.setDisable(false);
                btExcluir.setDisable(false);
            }
        });
        tabItens1.setOnMouseClicked(event -> {
            ItensCondicional itensCondicional = tabItens1.getSelectionModel().getSelectedItem();
            if (itensCondicional != null) {
                btRemoverItem.setDisable(false);
            }
        });
        telaPrincipal.setDisable(false);
    }

    private void setStyles() {
        tabCondicional.setStyle("-fx-control-inner-background: #FFFFFF;");
        colIDCond.setStyle("-fx-alignment: CENTER;");
        colData.setStyle("-fx-alignment: CENTER;");
        colNItens.setStyle("-fx-alignment: CENTER;");
        colTotalCondi.setStyle("-fx-alignment: baseline-right;");

        tabItens.setStyle("-fx-control-inner-background: #FFFFFF;");
        colIdItem.setStyle("-fx-alignment: CENTER;");
        colValUni.setStyle("-fx-alignment: baseline-right;");
        colQtd.setStyle("-fx-alignment: CENTER;");
        colTotalItem.setStyle("-fx-alignment: baseline-right;");

        tabItens1.setStyle("-fx-control-inner-background: #FFFFFF;");
        colIdItem1.setStyle("-fx-alignment: CENTER;");
        colValUni1.setStyle("-fx-alignment: baseline-right;");
        colQtd1.setStyle("-fx-alignment: CENTER;");
        colTotalItem1.setStyle("-fx-alignment: baseline-right;");
    }

    private void carregarTabViewItens(List<ItensCondicional> itensDeCondicional) {
        obsListaItens = FXCollections.observableArrayList(itensDeCondicional);
        tabItens.setItems(obsListaItens);
    }

    private void carregarTabViewItens1(List<ItensCondicional> itensDeCondicional) {
        obsListaItens1 = FXCollections.observableArrayList(itensDeCondicional);
        tabItens1.setItems(obsListaItens1);
    }

    private void carregarTabViewCondicionalTag() {
        listaCondicional = condicionalDAO.listarPorNomeCliente(tfPesquisa.getText());
        obsListaCondicional = FXCollections.observableArrayList(listaCondicional);
        tabCondicional.setItems(obsListaCondicional);
    }

    @FXML
    void acaoClickedBtCancel(ActionEvent event) {
        showTelaCondicionalTrasition();
    }

    @FXML
    void acaoClickedBtOK(ActionEvent event) {

    }

    @FXML
    void acaoClickedItemBtCancel(ActionEvent event) {
        showTelaAddItemTrasition();
    }

    @FXML
    void acaoClickedItemBtOK(ActionEvent event) {

    }

    @FXML
    void actionAddItem(MouseEvent event) {
        itemShowDialog();
    }

    @FXML
    void actionNova(MouseEvent event) {
        condicionalShowDialog(null);
    }

    @FXML
    void actionAlterar(MouseEvent event) {
        condicional = tabCondicional.getSelectionModel().getSelectedItem();
        if (condicional != null) {
            condicionalShowDialog(condicional);
        }
    }

    private void condicionalShowDialog(Condicional condicional) {
        setDados(condicional);
        showTelaCondicionalTrasition();
    }

    @FXML
    void actionExcluir(MouseEvent event) {
        condicionalDAO.remover(condicional);
        tfPesquisa.setText("");
        carregarTabViewCondicionalTag();
    }

    @FXML
    void actionPesquisar(MouseEvent event) {
        carregarTabViewCondicionalTag();
    }

    @FXML
    void actionRemoverItem(MouseEvent event) {
        ItensCondicional itensCondicional = tabItens1.getSelectionModel().getSelectedItem();
        obsListaItens1.remove(itensCondicional);
        condicional.getItensDeCondicional().remove(itensCondicional);
        condicional.setQtd(condicional.getQtd() - itensCondicional.getQtd());
        condicional.setValor(condicional.getValorDouble() - itensCondicional.getValorTotalDouble());
        if (tabItens1.getItems().size() == 0) {
            btRemoverItem.setDisable(true);
        }
    }

    private void showTelaCondicionalTrasition() {
        transitionTelaCondicional = new TranslateTransition(Duration.seconds(0.3), telaDialog);
        double x = -(telaPrincipal.getWidth() / 2) - (telaDialog.getWidth() / 2) - 17;
        double y = +(telaPrincipal.getHeight() / 2) - (telaDialog.getHeight() / 2) - 42;
        transitionTelaCondicional.setToX(ativeTelaCondicional ? 0 : x);
        transitionTelaCondicional.setToY(ativeTelaCondicional ? 0 : y);
        transitionTelaCondicional.setOnFinished(event -> {
            ativeTelaCondicional = !ativeTelaCondicional;
            telaPrincipal.setDisable(ativeTelaCondicional);
        });
        transitionTelaCondicional.play();
    }

    public void setDados(Condicional condicional) {
        this.condicional = condicional;
        btRemoverItem.setDisable(true);
        if (condicional != null) {
            tfNomeCliente.setText(condicional.getNomeCliente());
            tfNomeAtendente.setText(condicional.getNomeAtendente());
            lbData.setText(condicional.getDataString());
            lbNItens.setText(condicional.getQtd() + "");
            lbTotal.setText(condicional.getValor());
            iTitulo.setText("Alterar Condicional - ID: " + condicional.getId());
            carregarTabViewItens1(condicional.getItensDeCondicional());
        } else {
            tfNomeCliente.setText("");
            tfNomeAtendente.setText("");
            lbData.setText("");
            lbNItens.setText("");
            lbTotal.setText("");
            iTitulo.setText("Inserir Condicional");
            tabItens1.setItems(null);
        }
    }

    private void itemShowDialog() {
        spinnerQtd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        itemTitulo.setText("Inserir Item");
        showTelaAddItemTrasition();
    }

    private void showTelaAddItemTrasition() {
        transitionTelaAddItem = new TranslateTransition(Duration.seconds(0.3), telaAddItem);
        double x = -(telaPrincipal.getWidth() / 2) - (telaAddItem.getWidth()/2) - 17;
        double y = -(telaPrincipal.getHeight() / 2) - (telaAddItem.getHeight());
        transitionTelaAddItem.setToX(ativeTelaAddItem ? 0 : x);
        transitionTelaAddItem.setToY(ativeTelaAddItem ? 0 : y);
        transitionTelaAddItem.setOnFinished(event -> {
            ativeTelaAddItem = !ativeTelaAddItem;
            telaDialog.setDisable(ativeTelaAddItem);
        });
        transitionTelaAddItem.play();
    }
}