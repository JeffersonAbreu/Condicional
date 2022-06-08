package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
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
import jeff.model.dao.AtendenteDAO;
import jeff.model.dao.ClienteDAO;
import jeff.model.dao.CondicionalDAO;
import jeff.model.dao.RoupaDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Atendente;
import jeff.model.domain.Cliente;
import jeff.model.domain.Condicional;
import jeff.model.domain.ItensCondicional;
import jeff.model.domain.Roupa;
import jeff.util.Util;
import jeff.util.UtilAutoCompleteComboBox;

public class CondicionalController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    // tela conponentes Principal
    @FXML
    private Button btAlterar, btExcluir, btRemoverItem;
    @FXML
    private JFXComboBox<String> cbCliente;
    @FXML
    private JFXComboBox<String> cbAtendente;
    @FXML
    private JFXComboBox<String> cbRoupa;
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
    private TextField tfPesquisa;

    @FXML
    private TextField tfItemValorUni;
    @FXML
    private VBox telaPrincipal, telaDialog, telaAddItem;
    @FXML
    private Spinner<Integer> spinnerQtd;
    @FXML
    private Label iTitulo, lbData, lbTotal, lbItemTotal, lbMaxDisponivel, lbNItens;

    List<Condicional> listaCondicional;
    List<Cliente> listaCliente;
    List<Atendente> listaAtendente;
    List<Roupa> listaRoupa;
    ObservableList<Condicional> obsListaCondicional;
    ObservableList<ItensCondicional> obsListaItens;
    ObservableList<ItensCondicional> obsListaItens1;

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AtendenteDAO atendenteDAO = new AtendenteDAO();
    private final RoupaDAO roupaDAO = new RoupaDAO();

    // Transferencia de dados entre telas
    private Condicional condicional;
    private TranslateTransition transitionTelaCondicional, transitionTelaAddItem;
    private boolean ativeTelaCondicional = false;
    private boolean ativeTelaAddItem = false;

    @FXML
    void initialize() {
        condicionalDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        atendenteDAO.setConnection(connection);
        roupaDAO.setConnection(connection);

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

        actionsTelaAddItem();
    }

    private void actionsTelaAddItem() {
        // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
        // new UtilAutoCompleteComboBox<>(cbRoupa);
        cbRoupa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String key = newValue.toString().split(" : ")[0];
                if (!key.isEmpty()) {
                    Roupa roupa = new Roupa();
                    roupa.setId(Integer.parseInt(key));
                    roupa = roupaDAO.buscar(roupa);
                    System.out.println(roupa.getNome());
                    int max = roupa.getQtd() - roupa.getQtd_em_condicional();
                    spinnerQtd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max, 1));
                    lbMaxDisponivel.setText(String.valueOf(--max));
                    tfItemValorUni.setText(String.valueOf(roupa.getValorDouble()));
                    lbItemTotal.setText(Util.toStringDinheiro(roupa.getValorDouble() * spinnerQtd.getValue()));
                }
            }
        });
        spinnerQtd.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && oldValue != null) {
                lbItemTotal.setText(Util.toStringDinheiro(Double.parseDouble(tfItemValorUni.getText()) * newValue));
                lbMaxDisponivel
                        .setText(String.valueOf(Integer.parseInt(lbMaxDisponivel.getText()) - (newValue - oldValue)));
            }
        });
        tfItemValorUni.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                lbItemTotal.setText(Util.toStringDinheiro(Double.parseDouble(newValue) * spinnerQtd.getValue()));
            }
        });
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
        carregaCBoxCliente();
        carregaCBoxAtendente();
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
            String key = condicional.getCliente().getId() + " : " + condicional.getCliente().getNome();
            cbCliente.selectionModelProperty().get().select(key);
            key = condicional.getAtendente().getId() + " : " + condicional.getAtendente().getNome();
            cbAtendente.selectionModelProperty().get().select(key);
            lbData.setText(condicional.getDataString());
            lbNItens.setText(condicional.getQtd() + "");
            lbTotal.setText(condicional.getValor());
            iTitulo.setText("Alterar Condicional - ID: " + condicional.getId());
            carregarTabViewItens1(condicional.getItensDeCondicional());
        } else {
            lbData.setText("");
            lbNItens.setText("");
            lbTotal.setText("");
            iTitulo.setText("Inserir Condicional");
            tabItens1.setItems(null);
        }
    }

    private void itemShowDialog() {
        carregaCBoxRoupa();
        showTelaAddItemTrasition();
    }

    private void showTelaAddItemTrasition() {
        transitionTelaAddItem = new TranslateTransition(Duration.seconds(0.3), telaAddItem);
        double x = -(telaPrincipal.getWidth() / 2) - (telaAddItem.getWidth() / 2) - 17;
        double y = -(telaPrincipal.getHeight() / 2) - (telaAddItem.getHeight());
        transitionTelaAddItem.setToX(ativeTelaAddItem ? 0 : x);
        transitionTelaAddItem.setToY(ativeTelaAddItem ? 0 : y);
        transitionTelaAddItem.setOnFinished(event -> {
            ativeTelaAddItem = !ativeTelaAddItem;
            telaDialog.setDisable(ativeTelaAddItem);
        });
        transitionTelaAddItem.play();
    }

    private void carregaCBoxRoupa() {
        listaRoupa = roupaDAO.listar();
        if (listaRoupa.size() > 0) {
            List<String> roupaList = new ArrayList<>();
            listaRoupa.forEach(roupa -> roupaList.add(roupa.getId() + " : " + roupa.getNome()));
            cbRoupa.setItems(FXCollections.observableArrayList(roupaList));
            new UtilAutoCompleteComboBox(cbRoupa);
        }
    }

    private void carregaCBoxAtendente() {
        listaAtendente = atendenteDAO.listar();
        if (listaAtendente.size() > 0) {
            List<String> atendenteList = new ArrayList<>();
            listaAtendente.forEach(atendente -> atendenteList.add(atendente.getId() + " : " + atendente.getNome()));
            cbAtendente.setItems(FXCollections.observableArrayList(atendenteList));
            // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
            new UtilAutoCompleteComboBox<>(cbAtendente);
        }

    }

    private void carregaCBoxCliente() {
        listaCliente = clienteDAO.listar();
        if (listaCliente.size() > 0) {
            List<String> clienteList = new ArrayList<>();
            listaCliente.forEach(cliente -> clienteList.add(cliente.getId() + " : " + cliente.getNome()));
            cbCliente.setItems(FXCollections.observableArrayList(clienteList));
            // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
            new UtilAutoCompleteComboBox<>(cbCliente);
            cbCliente.requestFocus();
        }
    }
}