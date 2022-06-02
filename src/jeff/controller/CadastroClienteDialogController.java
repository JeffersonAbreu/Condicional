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
import jeff.model.domain.Cliente;

public class CadastroClienteDialogController {

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
    private TextField tfClienteCPF;

    @FXML
    private TextField tfClienteNome;

    @FXML
    private TextField tfClienteTelefone;

    private Stage stageDialog;
    private Cliente cliente;
    private boolean clicadoOK = false;

    public Stage getStageDialog() {
        return stageDialog;
    }

    public void setStageDialog(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        tfClienteNome.setText(cliente.getNome());
        tfClienteCPF.setText(cliente.getCpf());
        tfClienteTelefone.setText(cliente.getTelefone());
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
        cliente.setNome(tfClienteNome.getText());
        cliente.setCpf(tfClienteCPF.getText());
        cliente.setTelefone(tfClienteTelefone.getText());

        if (validation()) {
            clicadoOK = true;
            stageDialog.close();
        }
    }

    private boolean validation() {
        String msgErro = "";
        if (tfClienteNome.getText() == null || tfClienteNome.getText().isEmpty()
                || tfClienteNome.getText().length() < 3)
            msgErro = "Nome inválido\n";
        if (tfClienteCPF.getText() == null || tfClienteCPF.getText().isEmpty() || tfClienteCPF.getText().length() < 11)
            msgErro = msgErro.concat("CPF inválido\n");
        if (tfClienteTelefone.getText() == null || tfClienteTelefone.getText().isEmpty()
                || tfClienteTelefone.getText().length() > 8)
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
