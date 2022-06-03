package jeff.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jeff.model.domain.Roupa;
import jeff.util.Util;

public class CadastroRoupaDialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOK;

    @FXML
    private Label iTitulo;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfValor;
    @FXML
    private TextField tfQtd;

    private Stage stageDialog;
    private Roupa roupa;
    private boolean clicadoOK = false;

    public Stage getStageDialog() {
        return stageDialog;
    }

    public void setStageDialog(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

    public Roupa getRoupa() {
        return roupa;
    }

    public void setRoupa(Roupa roupa) {
        this.roupa = roupa;
        tfID.setText(String.valueOf(roupa.getId()));
        tfNome.setText(roupa.getNome());
        tfValor.setText(String.valueOf(roupa.getValorDouble()));

    }

    public boolean isClicadoOK() {
        return clicadoOK;
    }

    public void setClicadoOK(boolean clicadoOK) {
        this.clicadoOK = clicadoOK;
    }

    @FXML
    void initialize() {
    }

    @FXML
    public void acaoClickedButtonOK() {
        if (validation()) {
            roupa.setNome(tfNome.getText());
            roupa.setValor(Util.converterStringParaDouble(tfValor.getText()));
            roupa.setQtd(Integer.parseInt(tfQtd.getText()));

            clicadoOK = true;
            stageDialog.close();
        }
    }

    private boolean validation() {
        String msgErro = "";
        if (tfNome.getText() == null || tfNome.getText().isEmpty()
                || tfNome.getText().length() < 3)
            msgErro = "Nome inválido\n";
        if (tfID.getText() == null || tfID.getText().isEmpty() || tfID.getText().length() < 11)
            msgErro = msgErro.concat("CPF inválido\n");
        if (tfValor.getText() == null || tfValor.getText().isEmpty()
                || tfValor.getText().length() > 8)
            msgErro = msgErro.concat("Telefone inválido\n");

        if (!msgErro.isBlank()) {
            Alert alert = new Alert(AlertType.ERROR, msgErro);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    public void acaoClickedBottunCancel() {
        stageDialog.close();
    }

}
