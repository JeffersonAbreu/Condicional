package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import jeff.model.dao.RoupaDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Roupa;
import jeff.util.UtilAutoCompleteComboBox;

public class DashBoard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private JFXComboBox<String> jfxComboBox;

    @FXML
    private JFXCheckBox checkBox;

    private RoupaDAO roupaDAO;
    List<Roupa> listaRoupa;
    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();

    @FXML
    void initialize() {
        roupaDAO = new RoupaDAO();
        roupaDAO.setConnection(connection);
        // Chamada dos métodos
        carregaUsuario();// Popula o JFXComboBox usuário com AutoComplete
    }

    // Método que popula o JFXComboBox usuário da tela de login e implementa o
    // AutoComplete
    private void carregaUsuario() {
        try {
            // Lista que receberá os dados do BD (Usuários)
            // Para alimentar o JFXComboBox usuário
            List<String> lista = new ArrayList<>();

            listaRoupa = roupaDAO.listar();

            if (listaRoupa.size() > 0) {// Caso puxar dados do BD
                // Popula o comboBox
                // Passa por todos os registros e adiciona na lista
                listaRoupa.forEach(roupa -> {
                    lista.add(roupa.getId() + " - " + roupa.getNome());
                });
                // Adiciona a lista ao JFXComboBox
                jfxComboBox.setItems(FXCollections.observableArrayList(lista));

                // Implementa o AutoComplete utilizando a classe utilAutoCompleteComboBox
                new UtilAutoCompleteComboBox<>(jfxComboBox);
            } else {// Caso não puxar dados do BD emite a msg de erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerta!");
                alert.setHeaderText("O servidor está offline");
                alert.setContentText("Tente novamente ou entre em contato com o suporte técnico");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();// Emite o alerta de erro servidor
                jfxComboBox.requestFocus();
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("ERR" + ex);
        }
    }

    @FXML
    void actionAllPesquisa(ActionEvent event) {
        if (checkBox.isSelected()) {
            UtilAutoCompleteComboBox.isAutoCompleteInit = true;
        } else {
            UtilAutoCompleteComboBox.isAutoCompleteInit = false;
        }
    }

    @FXML
    void actionGetSelected(MouseEvent event) {
        String t = jfxComboBox.getSelectionModel().getSelectedItem();
        System.out.println(t);
        System.out.println(t.substring(0, t.indexOf(" - ")));
        System.out.println(jfxComboBox.getSelectionModel().getSelectedIndex());
    }
}
