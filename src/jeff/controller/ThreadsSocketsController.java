package jeff.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ThreadsSocketsController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label lbQtdClientesConectados;
    private final String host = "34.125.13.172";
    private final int port = 12345;

    @FXML
    void initialize() {
        new ClienteSocket().start();
    }

    private class ClienteSocket extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    Socket socket = new Socket(host, port);
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    var n = (Integer) in.readObject();
                    Platform.runLater(() -> lbQtdClientesConectados.setText(String.valueOf(n)));
                    Thread.sleep(10000);// Esperando 10 segundos
                    socket.close();
                }
            } catch (InterruptedException | ClassNotFoundException | IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}