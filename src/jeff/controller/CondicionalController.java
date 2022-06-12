package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

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
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import jeff.model.dao.AtendenteDAO;
import jeff.model.dao.ClienteDAO;
import jeff.model.dao.CondicionalDAO;
import jeff.model.dao.ItensCondicionalDAO;
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
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

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
    private Label iTitulo, lbNomeCliente, lbData, lbTotal, lbItemTotal, lbMaxDisponivel, lbNItens,
            lbCLienteValorMaxDisponivel;

    // minhas instancias
    List<Cliente> listaCliente;
    List<Atendente> listaAtendente;
    ObservableList<Condicional> obsListaCondicional;
    ObservableList<ItensCondicional> obsListaItens;
    ObservableList<ItensCondicional> obsListaItensDialog, copyItensCondicionals;
    Map<Integer, Roupa> mapRoupas = null;
    Map<Integer, Roupa> mapRoupasLixo = new TreeMap<Integer, Roupa>();

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private final ItensCondicionalDAO itensCondicionalDAO = new ItensCondicionalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AtendenteDAO atendenteDAO = new AtendenteDAO();
    private final RoupaDAO roupaDAO = new RoupaDAO();

    // Transferencia de dados entre telas
    private Condicional condicional;
    private ItensCondicional itensCondicional;
    private TranslateTransition transitionTelaCondicional, transitionTelaAddItem;
    private boolean ativeTelaCondicional = false;
    private boolean ativeTelaAddItem = false;
    private Double valorDisponivel = 0.0;

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

        carregarTabViewCondicional();

        stylesDasTabelas();

        tabCondicional.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                carregarTabViewItens(newValue);
                btAlterar.setDisable(false);
                btExcluir.setDisable(false);
                this.condicional = newValue;
            }
        });
        tabItens.setOnMouseClicked(event -> {
            btAlterar.setDisable(true);
            btExcluir.setDisable(true);
        });
        tabItens1.setOnMouseClicked(event -> {
            ItensCondicional itensCondicional = tabItens1.getSelectionModel().getSelectedItem();
            if (itensCondicional != null) {
                btRemoverItem.setDisable(false);
            }
        });
        telaPrincipal.setDisable(false);

        telaItem_ACTION();
        actionsTelaCondicional();
    }

    private void actionsTelaCondicional() {
        cbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Cliente cliente = new Cliente();
            try {
                cliente.setKey(newValue);
                cliente = clienteDAO.buscar(cliente);
                if (cliente != null)
                    this.condicional.setCliente(cliente);
            } catch (Exception e) {
                cbCliente.getEditor().setText("");
            } finally {
                cliente = this.condicional.getCliente();
                if (cliente == null) {
                    cbCliente.getSelectionModel().clearSelection();
                } else {
                    cbCliente.getSelectionModel().select((cliente.getKey()));
                }
                telaCondicional_UPDATE_valorDisponivel();
            }
        });

        cbAtendente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbAtendente.getSelectionModel().getSelectedItem().equals(oldValue)) {
                try {
                    Atendente atendente = new Atendente();
                    atendente.setKey(newValue);
                    atendente = atendenteDAO.buscar(atendente);
                    if (atendente != null)
                        this.condicional.setAtendente(atendente);
                } catch (Exception e) {
                    cbAtendente.getEditor().setText("");
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

    private void telaCondicional_UPDATE_valorDisponivel() {
        if (this.condicional.getCliente() == null) {
            lbCLienteValorMaxDisponivel.setText("R$   -");
        } else {
            double limiteCliente = this.condicional.getCliente().getLimiteDouble();
            double valorDessaCondicional = this.condicional.getValorDouble();
            double valorDebitosAtivos = condicionalDAO.getSomaValorTotalPorCliente(this.condicional.getCliente());
            double valorDessaCondi_no_DB = 0.0;
            System.out.println("\n\nLimite desse Cliente: \t\t" + Util.toStringDinheiro(limiteCliente));
            System.out.println("Valor dos debitos ativos: \t" + Util.toStringDinheiro(valorDebitosAtivos));
            System.out.println("Valor dessa Condi.: \t\t" + Util.toStringDinheiro(valorDessaCondicional));

            valorDisponivel = limiteCliente - valorDebitosAtivos;
            if (this.condicional.getId() != 0) {
                valorDessaCondi_no_DB = condicionalDAO.buscar(condicional).getValorDouble();
                System.out.println("Valor disponivel old: \t\t" + Util.toStringDinheiro(valorDisponivel));
                valorDisponivel += valorDessaCondi_no_DB;
            }
            valorDisponivel -= valorDessaCondicional;
            System.out.println("Valor disponivel new: \t\t" + Util.toStringDinheiro(valorDisponivel));
            lbCLienteValorMaxDisponivel.setText(Util.toStringDinheiro(valorDisponivel));

            if (valorDisponivel <= 0.0)
                lbCLienteValorMaxDisponivel.setStyle("-fx-text-fill: red");
            else
                lbCLienteValorMaxDisponivel.setStyle("-fx-text-fill: black");
        }
    }

    @FXML
    void acaoClickedBtCancel(ActionEvent event) {
        carregarTabViewCondicional();
        telaCondicional_TRANSITION();
        btAlterar.setDisable(true);
        btExcluir.setDisable(true);
        this.condicional = new Condicional();
    }

    @FXML
    void acaoClickedBtOK(ActionEvent event) {
        if (isValid()) {
            try {
                connection.setAutoCommit(false);
                condicionalDAO.setConnection(connection);
                itensCondicionalDAO.setConnection(connection);
                roupaDAO.setConnection(connection);

                // Novo registro
                if (condicional.getId() == 0) {
                    condicionalDAO.inserir(condicional);
                    this.condicional.setId(condicionalDAO.getLastId());
                } else { // Alteração de registro
                    condicionalDAO.alterar(condicional);
                    for (ItensCondicional item : itensCondicionalDAO.listarPorCondicional(condicional)) {
                        Roupa roupa = item.getRoupa();
                        int qtdOld = roupa.getQtd_em_condicional();
                        int qtdNew = qtdOld - item.getQtd();
                        roupa.setQtd_em_condicional(qtdNew);
                        roupaDAO.alterar(roupa);
                        item.setCondicional(this.condicional);
                        itensCondicionalDAO.remover(item);
                    }
                }
                // atualizando o condicional dos itens
                for (int i = 0; i < this.condicional.getItensCondicional().size(); i++) {
                    this.condicional.getItensCondicional().get(i).setCondicional(this.condicional);
                }
                // inserindo itens
                for (ItensCondicional item : condicional.getItensCondicional()) {
                    roupaDAO.alterar(item.getRoupa());
                    itensCondicionalDAO.inserir(item);
                }
                connection.commit();
                TrayNotification tray = new TrayNotification();
                tray.setRectangleFill(Paint.valueOf("#8846a2"));
                tray.setTitle("Salvo com Sucesso!");
                tray.setMessage("A condicional Nº " + this.condicional.getId() + " foi salva!");
                tray.setAnimationType(AnimationType.POPUP);
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.seconds(10));
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    TrayNotification tray = new TrayNotification("ERRO", "Houve um problema ao Salvar!",
                            NotificationType.ERROR);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(10));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            } finally {
                acaoClickedBtCancel(event);
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isValid() {
        if (condicional.getCliente() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cliente não selecionado");
            alert.setContentText("Selecione um cliente para continuar");
            alert.showAndWait();
            cbCliente.getEditor().setStyle("-fx-border-color: red");
            cbCliente.requestFocus();
            cbCliente.show();
            return false;
        } else {
            cbCliente.getEditor().setStyle("-fx-border-color: none");
        }
        if (condicional.getAtendente() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Atendente não selecionado");
            alert.setContentText("Selecione um atendente para continuar");
            alert.showAndWait();
            cbAtendente.getEditor().setStyle("-fx-border-color: red");
            cbAtendente.requestFocus();
            cbAtendente.show();
            return false;
        } else {
            cbAtendente.getEditor().setStyle("-fx-border-color: none");
        }
        if (condicional.getItensCondicional().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum item adicionado");
            alert.setContentText("Adicione pelo menos um item para continuar");
            alert.showAndWait();
            return false;
        } else {
            this.condicional.updateItens(tabItens1.getItems());
            if (valorDisponivel < 0) {
                TrayNotification tray = new TrayNotification("ERRO", "O limete foi excedido!",
                        NotificationType.ERROR);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(10));
                return false;
            }
        }
        return true;
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
        cbRoupa.getEditor().setText("");
    }

    @FXML
    void acaoClickedItemBtOK(ActionEvent event) {
        boolean jaExiste = false, update = false;
        int index = -1;
        for (ItensCondicional item : obsListaItensDialog) {
            index++;
            if (item.getRoupa().getId() == this.itensCondicional.getRoupa().getId()) {
                jaExiste = true;
                break;
            }
        }
        if (!jaExiste) {
            obsListaItensDialog.add(this.itensCondicional);
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
                ItensCondicional item = obsListaItensDialog.get(index);
                item.setQtd(item.getQtd() + this.itensCondicional.getQtd());
                item.setValorUni(this.itensCondicional.getValorUniDouble());
                obsListaItensDialog.set(index, item);
                update = true;
            }
        }

        if (update) {
            this.condicional.updateItens(obsListaItensDialog);
            lbNItens.setText(condicional.getQtd() + "");
            lbTotal.setText(condicional.getValor());
            carregarTabViewItensDialog(condicional.getItensCondicional());
            updateListaRoupas(itensCondicional);
            limpaTelaItens();
            cbRoupa.requestFocus();
            telaCondicional_UPDATE_valorDisponivel();
        }
    }

    private void updateListaRoupas(ItensCondicional item) {
        int key = item.getRoupa().getId();
        Roupa roupa;
        if (mapRoupas != null && !mapRoupas.isEmpty() && mapRoupas.containsKey(key)) {
            roupa = mapRoupas.get(key);
            roupa.setQtd_em_condicional(roupa.getQtd_em_condicional() + item.getQtd());
            if (roupa.estoqueDisponinel() > 0) {
                mapRoupas.put(key, roupa);
            } else {
                mapRoupas.remove(key);
                mapRoupasLixo.put(key, roupa);
            }
        } else if (mapRoupasLixo.containsKey(key)) {
            roupa = mapRoupasLixo.get(key);
            mapRoupasLixo.remove(key);
            roupa.setQtd_em_condicional(roupa.getQtd_em_condicional() - item.getQtd());
            mapRoupas.put(key, roupa);
        } else {
            roupa = roupaDAO.buscar(item.getRoupa());
            roupa.setQtd_em_condicional(roupa.getQtd_em_condicional() + item.getQtd());
            mapRoupas.put(roupa.getId(), roupa);
        }
    }

    @FXML
    void actionAddItem(MouseEvent event) {
        limpaTelaItens();
        telaItem_ComboBoxRoupa();
        showTelaAddItemTrasition();
    }

    @FXML
    void actionNova(MouseEvent event) {
        telaCondicional_SHOW(null);
    }

    @FXML
    void actionAlterar(MouseEvent event) {
        this.condicional = tabCondicional.getSelectionModel().getSelectedItem();
        if (this.condicional != null) {
            telaCondicional_SHOW(this.condicional);
        }
    }

    @FXML
    void actionExcluir(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText("Você está perto de excluir uma Condicional!");
        alert.setContentText("Tem certeza que deseja excluir?");
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) telaPrincipal.getScene().getWindow();
        alert.initOwner(stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                connection.setAutoCommit(false);
                condicionalDAO.setConnection(connection);
                itensCondicionalDAO.setConnection(connection);
                roupaDAO.setConnection(connection);

                for (ItensCondicional item : itensCondicionalDAO.listarPorCondicional(condicional)) {
                    Roupa roupa = item.getRoupa();
                    int qtdOld = roupa.getQtd_em_condicional();
                    int qtdNew = qtdOld - item.getQtd();

                    roupa.setQtd_em_condicional(qtdNew);

                    roupaDAO.alterar(roupa);

                    itensCondicionalDAO.remover(item);
                }
                condicionalDAO.remover(condicional);
                connection.commit();
                TrayNotification tray = new TrayNotification("Olá", "A condicional foi excluida!",
                        NotificationType.NOTICE);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(10));
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    TrayNotification tray = new TrayNotification("ERRO", "Houve um problema ao excluir!",
                            NotificationType.ERROR);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(10));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            } finally {
                carregarTabViewCondicional();
                tabCondicional.requestFocus();
                btAlterar.setDisable(true);
                btExcluir.setDisable(true);
            }
        } else {
            TrayNotification tray = new TrayNotification("Olá", "Estou vendo que você desistiu de excluir!",
                    NotificationType.NOTICE);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(10));
        }
    }

    @FXML
    void actionPesquisar(MouseEvent event) {
        carregarTabViewCondicional();
        tfPesquisa.requestFocus();
        btAlterar.setDisable(true);
        btExcluir.setDisable(true);
    }

    @FXML
    void actionRemoverItem(MouseEvent event) {
        ItensCondicional item = tabItens1.getSelectionModel().getSelectedItem();
        obsListaItensDialog.remove(item);
        condicional.updateItens(obsListaItensDialog);
        item.setQtd(item.getQtd() * -1);
        updateListaRoupas(item);
        telaCondicional_UPDATE(this.condicional);
        telaCondicional_UPDATE_valorDisponivel();
    }

    // INICIO - TELA CONDICIONAL
    private void telaCondicional_SHOW(Condicional condicional) {
        carregaComboBoxCliente();
        carregaComboBoxAtendente();
        telaItem_ComboBoxRoupa();
        telaCondicional_START(condicional);
        telaCondicional_TRANSITION();
    }

    private void telaCondicional_TRANSITION() {
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

    private void telaCondicional_START(Condicional condicional) {
        btRemoverItem.setDisable(true);
        mapRoupasLixo.clear();
        iTitulo.requestFocus();
        if (condicional != null) {
            iTitulo.setText("Alterar Condicional - ID: " + condicional.getId());
            telaCondicional_UPDATE(this.condicional);
            System.out.println(cbCliente.getOpacity());
            cbCliente.setDisable(true);
            cbCliente.setVisible(false);
            lbNomeCliente.setVisible(true);
            lbNomeCliente.setText(condicional.getCliente().getKey());
        } else {
            iTitulo.setText("Inserir Condicional");
            telaCondicional_UPDATE(new Condicional());
            cbCliente.setDisable(false);
            cbCliente.setVisible(true);
            lbNomeCliente.setVisible(false);
        }
        telaCondicional_UPDATE_valorDisponivel();
    }

    private void telaCondicional_UPDATE(Condicional condicional) {
        String key;
        this.condicional = condicional;
        lbData.setText(condicional.getDataString());
        lbNItens.setText(condicional.getQtd() + "");
        lbTotal.setText(condicional.getValor());

        if (condicional.getCliente() == null) {
            cbCliente.getSelectionModel().clearSelection();
        } else {
            key = condicional.getCliente().getKey();
            cbCliente.selectionModelProperty().get().select(key);
        }
        if (condicional.getAtendente() == null) {
            cbAtendente.getSelectionModel().clearSelection();
        } else {
            key = condicional.getAtendente().getKey();
            cbAtendente.selectionModelProperty().get().select(key);
        }

        carregarTabViewItensDialog(this.condicional.getItensCondicional());
        if (tabItens1.getItems().size() == 0) {
            btRemoverItem.setDisable(true);
            iTitulo.requestFocus();
        }
    }

    // INICIO - TELA ITEM

    private void telaItem_ComboBoxRoupa() {
        List<String> roupaList = new ArrayList<>();
        if (mapRoupas == null) {
            mapRoupas = new TreeMap<Integer, Roupa>();
            roupaDAO.listar().forEach(roupa -> {
                if (roupa.estoqueDisponinel() > 0) {
                    mapRoupas.put(roupa.getId(), roupa);
                }
            });
        }
        mapRoupas.forEach((key, value) -> roupaList.add(value.getKey()));
        if (!mapRoupas.isEmpty()) {
            cbRoupa.setItems(FXCollections.observableArrayList(roupaList));
            new UtilAutoCompleteComboBox<String>(cbRoupa);
        }
    }

    private void telaItem_ACTION() {
        cbRoupa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbRoupa.getSelectionModel().getSelectedItem().equals(oldValue)) {
                boolean OK = false;
                Roupa roupa = new Roupa();
                try {
                    roupa.setKey(newValue);
                    if (mapRoupas.containsKey(roupa.getId())) {
                        roupa = mapRoupas.get(roupa.getId());
                        this.itensCondicional.setRoupa(roupa);
                        int max = roupa.estoqueDisponinel();
                        spinnerQtd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max, 1));
                        lbMaxDisponivel.setText(String.valueOf(max));
                        tfItemValorUni.setText(String.valueOf(roupa.getValorDouble()));
                        lbItemTotal.setText(Util.toStringDinheiro(roupa.getValorDouble() * spinnerQtd.getValue()));
                        OK = true;
                    }
                } catch (Exception e) {
                    cbRoupa.getEditor().setText("");
                    cbRoupa.getSelectionModel().clearSelection();
                } finally {
                    if (OK) {
                        cbRoupa.getSelectionModel().select(roupa.getKey());
                        btOkAddItem.setDisable(false);
                        spinnerQtd.setDisable(false);
                        tfItemValorUni.setDisable(false);
                    } else {
                        cbRoupa.getSelectionModel().clearSelection();
                        btOkAddItem.setDisable(true);
                        spinnerQtd.setDisable(true);
                        tfItemValorUni.setDisable(true);
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
    // OUTROS COMPLEMENTARES

    private void stylesDasTabelas() {
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

    private void carregarTabViewItens(Condicional condicional) {
        obsListaItens = FXCollections.observableArrayList(condicional.getItensCondicional());
        tabItens.setItems(obsListaItens);
    }

    private void carregarTabViewItensDialog(List<ItensCondicional> itensDeCondicional) {
        obsListaItensDialog = FXCollections.observableArrayList(itensDeCondicional);
        tabItens1.setItems(obsListaItensDialog);
    }

    private void carregarTabViewCondicional() {
        obsListaCondicional = FXCollections.observableArrayList(
                condicionalDAO.listarPorNomeCliente(tfPesquisa.getText()));
        tabCondicional.setItems(obsListaCondicional);
        tabItens.getItems().clear();
    }

    private void showTelaAddItemTrasition() {
        transitionTelaAddItem = new TranslateTransition(Duration.seconds(0.3), telaAddItem);
        double x = -(telaPrincipal.getWidth() / 2) - (telaAddItem.getWidth() / 2) - 17;
        double y = -(telaPrincipal.getHeight() / 2) - (telaAddItem.getHeight()) + 70;
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

    private void carregaComboBoxAtendente() {
        listaAtendente = atendenteDAO.listar();
        if (listaAtendente.size() > 0) {
            List<String> atendenteList = new ArrayList<>();
            listaAtendente.forEach(atendente -> atendenteList.add(atendente.getKey()));
            cbAtendente.setItems(FXCollections.observableArrayList(atendenteList));
            // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
            new UtilAutoCompleteComboBox<String>(cbAtendente);
        }

    }

    private void carregaComboBoxCliente() {
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