package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jeff.model.dao.RoupaDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Roupa;
import jeff.util.Util;

public class ListaRoupaController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    // Dialog Stage
    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOK;

    @FXML
    private Label iTitulo;

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
    @FXML
    private VBox slide;
    @FXML
    private VBox root;
    @FXML
    private TextField nome;
    @FXML
    private TextField qtd;
    @FXML
    private TextField valor;

    private List<Roupa> listRoupas;
    private ObservableList<Roupa> observableListRoupas;
    private String msgErro = "";
    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final RoupaDAO roupaDAO = new RoupaDAO();
    private Roupa roupa;
    private TranslateTransition transition;
    private boolean ative = false;

    @FXML
    public void initialize() {
        roupaDAO.setConnection(connection);
        carregarTableViewCliente();
        clID.setStyle("-fx-alignment: CENTER;");
        clQtd.setStyle("-fx-alignment: CENTER;");
        clQtd_em_condicional.setStyle("-fx-alignment: CENTER;");
        clValor.setStyle("-fx-alignment: baseline-right;");

        tbRoupa.setOnMouseClicked(event -> {
            roupa = tbRoupa.getSelectionModel().getSelectedItem();
            buttonAlterar.setDisable(false);
            buttonRemover.setDisable(false);    
        });
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
        roupaDialog(null);
    }

    @FXML
    private void buttonAlterar() {
        Roupa roupa = tbRoupa.getSelectionModel().getSelectedItem();
        roupaDialog(roupa);
    }

    @FXML
    private void buttonRemover() {
        Roupa roupa = tbRoupa.getSelectionModel().getSelectedItem();
        roupaDAO.remover(roupa);
        carregarTableViewCliente();
    }

    private void roupaDialog(Roupa roupa) {
        setDados(roupa);
        showTrasition();
    }

    private void showTrasition() {
        transition = new TranslateTransition(Duration.seconds(0.5), slide);
        transition.setToX(ative ? 0 : -root.getWidth() / 2 - slide.getWidth() / 2);
        transition.setToY(ative ? 0 : +root.getHeight() / 2 - slide.getHeight());
        transition.setOnFinished(event -> {
            ative = !ative;
            root.setDisable(ative);
        });
        transition.play();
    }

    @FXML
    public void acaoClickedButtonOK() {
        if (validation()) {
            boolean novo = roupa == null ? true : false;
            if (novo) {
                roupa = new Roupa();
            }
            roupa.setNome(nome.getText());
            roupa.setValor(Util.converterStringParaDouble(valor.getText()));
            roupa.setQtd(Integer.parseInt(qtd.getText()));
            if (novo)
                roupaDAO.inserir(roupa);
            else
                roupaDAO.alterar(roupa);

            carregarTableViewCliente();
            showTrasition();
        }
    }

    private void addMsgErro(TextField t) {
        msgErro = msgErro.concat(t.getId() + " inválida\n");
    }

    private boolean validation() {
        msgErro = "";
        if (nome.getText().length() < 3)
            addMsgErro(nome);
        if (qtd.getText().length() < 1) {
            addMsgErro(qtd);
        } else {
            try {
                if (Integer.parseInt(qtd.getText()) <= 0)
                    addMsgErro(qtd);
            } catch (NumberFormatException e) {
                addMsgErro(qtd);
            }
        }
        if (valor.getText().length() < 1) {
            addMsgErro(this.valor);
        } else {
            try {
                if (Double.parseDouble(valor.getText()) <= 0)
                    addMsgErro(this.valor);
            } catch (NumberFormatException e) {
                addMsgErro(valor);
            }
        }
        if (!msgErro.isBlank()) {
            Alert alert = new Alert(AlertType.ERROR, msgErro);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    public void acaoClickedBottunCancel() {
        showTrasition();
    }

    public void setDados(Roupa roupa) {
        this.roupa = roupa;
        if (roupa != null) {
            nome.setText(roupa.getNome());
            valor.setText(String.valueOf(roupa.getValorDouble()));
            qtd.setText(String.valueOf(roupa.getQtd()));
            iTitulo.setText("Alterar Roupa");
        } else {
            nome.setText("");
            valor.setText("");
            qtd.setText("");
            iTitulo.setText("Inserir Roupa");
        }
    }
}