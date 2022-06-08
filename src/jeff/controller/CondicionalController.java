package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private Button btAlterar, btExcluir, btRemoverItem, btOkAddItem;
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
    private ItensCondicional itensCondicional;
    private TranslateTransition transitionTelaCondicional, transitionTelaAddItem;
    private boolean ativeTelaCondicional = false;
    private boolean ativeTelaAddItem = false, btCancelClicked = false;

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
                carregarTabViewItens(condicional.getItensCondicional());
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
        actionsTelaCondicional();
    }

    private void actionsTelaCondicional() {
        cbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbCliente.getSelectionModel().getSelectedItem().equals(oldValue)) {
                try {
                    Cliente cliente = new Cliente();
                    cliente.setKey(newValue);
                    cliente = clienteDAO.buscar(cliente);
                    System.out.println(cliente);
                    if (cliente != null)
                        this.condicional.setCliente(cliente);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar cliente");
                } finally {
                    Cliente cliente = this.condicional.getCliente();
                    if (cliente == null)
                        cbCliente.getSelectionModel().clearSelection();
                    else
                        cbCliente.getSelectionModel().select(cliente.getKey());
                }
            }
        });

        cbAtendente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbAtendente.getSelectionModel().getSelectedItem().equals(oldValue)) {
                try {
                    Atendente atendente = new Atendente();
                    atendente.setKey(newValue);
                    atendente = atendenteDAO.buscar(atendente);
                    System.out.println(atendente);
                    if (atendente != null)
                        this.condicional.setAtendente(atendente);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar atendente");
                } finally {
                    Atendente atendente = this.condicional.getAtendente();
                    if (atendente == null)
                        cbAtendente.getSelectionModel().clearSelection();
                    else
                        cbAtendente.getSelectionModel().select((atendente.getKey()));
                }
            }
        });
    }

    private void actionsTelaAddItem() {
        // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
        // new UtilAutoCompleteComboBox<>(cbRoupa);
        cbRoupa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbRoupa.getSelectionModel().getSelectedItem().equals(oldValue)) {
                try {
                    Roupa roupa = new Roupa();
                    roupa.setKey(newValue);
                    roupa = getRoupaNaLista(roupa);
                    if (roupa != null) {
                        this.itensCondicional.setRoupa(roupa);
                        int max = roupa.getQtd() - roupa.getQtd_em_condicional();
                        spinnerQtd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max, 1));
                        lbMaxDisponivel.setText(String.valueOf(max));
                        tfItemValorUni.setText(String.valueOf(roupa.getValorDouble()));
                        lbItemTotal.setText(Util.toStringDinheiro(roupa.getValorDouble() * spinnerQtd.getValue()));
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao buscar roupa");
                } finally {
                    Roupa roupa = this.itensCondicional.getRoupa();
                    if (roupa == null) {
                        cbRoupa.getSelectionModel().clearSelection();
                        btOkAddItem.setDisable(true);
                        spinnerQtd.setDisable(true);
                        tfItemValorUni.setDisable(true);
                    } else {
                        cbRoupa.getSelectionModel().select(roupa.getKey());
                        btOkAddItem.setDisable(false);
                        spinnerQtd.setDisable(false);
                        tfItemValorUni.setDisable(false);
                    }
                }
            }
        });
        spinnerQtd.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && oldValue != null) {
                if (newValue.intValue() == Integer.parseInt(lbMaxDisponivel.getText())) {
                    lbMaxDisponivel.setStyle("-fx-text-fill: red;");
                } else {
                    lbMaxDisponivel.setStyle("-fx-text-fill: black;");
                }
                this.itensCondicional.setQtd(spinnerQtd.getValue());
                lbItemTotal.setText(this.itensCondicional.getValorTotal());
            }
        });
        tfItemValorUni.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                try {
                    Double valor = Double.parseDouble(newValue);
                    this.itensCondicional.setValorUni(valor);
                    lbItemTotal.setText(this.itensCondicional.getValorTotal());
                } catch (Exception e) {
                    tfItemValorUni.setText(oldValue);
                }
            }
        });
    }

    private Roupa getRoupaNaLista(Roupa roupa) {
        for (Roupa r : listaRoupa) {
            if (r.getId() == (roupa.getId())) {
                return r;
            }
        }
        return null;
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

    private void limpaTelaItens() {
        cbRoupa.getSelectionModel().clearSelection();
        this.itensCondicional = new ItensCondicional();
        tfItemValorUni.setText("0.0");
        spinnerQtd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0));
        lbMaxDisponivel.setText("0");
        lbItemTotal.setText("0");
        btOkAddItem.setDisable(true);
        btCancelClicked = false;
    }

    @FXML
    void acaoClickedItemBtOK(ActionEvent event) {
        boolean jaExiste = false, update = false;
        int index = -1;
        for (ItensCondicional item : obsListaItens1) {
            index++;
            if (item.getRoupa().getId() == this.itensCondicional.getRoupa().getId()) {
                jaExiste = true;
                break;
            }
        }
        if (!jaExiste) {
            obsListaItens1.add(this.itensCondicional);
            update = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText("Já existe um item com essa roupa");
            alert.setContentText("O item já existe na lista, deseja atualizar?");
            alert.initModality(Modality.APPLICATION_MODAL);
            Stage stage = (Stage) telaAddItem.getScene().getWindow();
            alert.initOwner(stage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println(obsListaItens1.get(index));
                ItensCondicional item = obsListaItens1.get(index);
                item.setQtd(item.getQtd() + this.itensCondicional.getQtd());
                item.setValorUni(this.itensCondicional.getValorUniDouble());
                obsListaItens1.set(index, item);
                update = true;
            }
        }

        if (update) {
            this.condicional.updateItens(obsListaItens1);
            lbNItens.setText(condicional.getQtd() + "");
            lbTotal.setText(condicional.getValor());
            // carregarTabViewItens1(condicional.getItensCondicional());
            updateListaRoupas(itensCondicional);
            showTelaAddItemTrasition();
        }
    }

    private void updateListaRoupas(ItensCondicional item) {
        int index = -1;
        boolean estaNaLista = false;
        Roupa roupa = new Roupa();
        for (Roupa r : listaRoupa) {
            index++;
            if (r.getId() == item.getRoupa().getId()) {
                roupa = r;
                estaNaLista = true;
                break;
            }
        }
        if (estaNaLista) {
            roupa.setQtd_em_condicional(roupa.getQtd_em_condicional() + item.getQtd());
            if (roupa.temEstoqueDisponinel()) {
                listaRoupa.set(index, roupa);
            } else {
                listaRoupa.remove(index);
            }
        } else {
            // add roupa na lista
            roupa = roupaDAO.buscar(item.getRoupa());
            System.out.println("disponivel antes: " + (roupa.getQtd() - roupa.getQtd_em_condicional()));
            int disponivel = roupa.getQtd() - roupa.getQtd_em_condicional() + item.getQtd();

            System.out.println("disponivel agora: " + (disponivel));
            roupa.setQtd_em_condicional(disponivel);
            if (roupa.temEstoqueDisponinel()) {
                listaRoupa.add(roupa);
                listaRoupa.sort(Comparator.comparing(Roupa::getId));
            }
        }

    }

    @FXML
    void actionAddItem(MouseEvent event) {
        limpaTelaItens();
        carregaCBoxRoupa();
        showTelaAddItemTrasition();
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
        tfPesquisa.requestFocus();
    }

    @FXML
    void actionRemoverItem(MouseEvent event) {
        ItensCondicional itensCondicional = tabItens1.getSelectionModel().getSelectedItem();
        obsListaItens1.remove(itensCondicional);
        condicional.updateItens(obsListaItens1);
        updateListaRoupas(itensCondicional);
        lbNItens.setText(condicional.getQtd() + "");
        lbTotal.setText(condicional.getValor());
        if (tabItens1.getItems().size() == 0) {
            btRemoverItem.setDisable(true);
            iTitulo.requestFocus();
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
        this.listaRoupa = null;
        btRemoverItem.setDisable(true);
        iTitulo.requestFocus();
        if (condicional != null) {
            iTitulo.setText("Alterar Condicional - ID: " + condicional.getId());
            String key = condicional.getCliente().getKey();
            cbCliente.selectionModelProperty().get().select(key);
            key = condicional.getAtendente().getKey();
            cbAtendente.selectionModelProperty().get().select(key);
            lbData.setText(condicional.getDataString());
            lbNItens.setText(condicional.getQtd() + "");
            lbTotal.setText(condicional.getValor());
            carregarTabViewItens1(condicional.getItensCondicional());
        } else {
            iTitulo.setText("Inserir Condicional");
            this.condicional = new Condicional();
            lbData.setText(this.condicional.getDataString());
            lbNItens.setText(this.condicional.getQtd() + "");
            lbTotal.setText(this.condicional.getValor());
            carregarTabViewItens1(this.condicional.getItensCondicional());
        }
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
            if (ativeTelaAddItem)
                cbRoupa.requestFocus();
            else
                iTitulo.requestFocus();
        });
        transitionTelaAddItem.play();
    }

    private void carregaCBoxRoupa() {
        List<String> roupaList = new ArrayList<>();
        if (listaRoupa == null) {
            listaRoupa = new ArrayList<>();
            roupaDAO.listar().forEach(roupa -> {
                if (roupa.temEstoqueDisponinel()) {
                    listaRoupa.add(roupa);
                    roupaList.add(roupa.getKey());
                }
            });
        } else {
            listaRoupa.forEach(roupa -> roupaList.add(roupa.getKey()));
        }
        if (listaRoupa.size() > 0) {
            cbRoupa.setItems(FXCollections.observableArrayList(roupaList));
            new UtilAutoCompleteComboBox<String>(cbRoupa);
        }
    }

    private void carregaCBoxAtendente() {
        listaAtendente = atendenteDAO.listar();
        if (listaAtendente.size() > 0) {
            List<String> atendenteList = new ArrayList<>();
            listaAtendente.forEach(atendente -> atendenteList.add(atendente.getKey()));
            cbAtendente.setItems(FXCollections.observableArrayList(atendenteList));
            // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
            new UtilAutoCompleteComboBox<String>(cbAtendente);
        }

    }

    private void carregaCBoxCliente() {
        listaCliente = clienteDAO.listar();
        if (listaCliente.size() > 0) {
            List<String> clienteList = new ArrayList<>();
            listaCliente.forEach(cliente -> clienteList.add(cliente.getKey()));
            cbCliente.setItems(FXCollections.observableArrayList(clienteList));
            // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
            new UtilAutoCompleteComboBox<String>(cbCliente);
        }
    }
}