package jeff.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import jeff.model.dao.CondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Condicional;
import jeff.util.Util;

public class DashBoard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private DatePicker dpFinal, dpInicio;

    @FXML
    private PieChart graficoPizza;

    // DATABASE
    private final Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
    private final Connection connection = database.conectar();
    private final CondicionalDAO condicionalDAO = new CondicionalDAO();
    private ObservableList<PieChart.Data> dadosPieChart = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        condicionalDAO.setConnection(connection);
        List<Condicional> listaCondicional = condicionalDAO.listar();
        LocalDate inicio = listaCondicional.get(0).getData();
        LocalDate fim = listaCondicional.get(listaCondicional.size() - 1).getData();
        dpInicio.setValue(inicio);
        dpFinal.setValue(fim);
        graficoPizza.setData(dadosPieChart);
        graficoPizza.setLabelLineLength(15);
        graficoPizza.setLegendVisible(false);
        actionBuscar(null);
    }

    @FXML
    void actionBuscar(ActionEvent event) {
        dadosPieChart.clear();
        LocalDate inicio = dpInicio.getValue();
        LocalDate fim = dpFinal.getValue();
        ArrayList<ArrayList<String>> dados = condicionalDAO.listarTotalCondicionaisClientePorData(inicio, fim);
        if (!dados.isEmpty()) {
            for (ArrayList<String> dado : dados) {
                String legenda = dado.get(0) + " - " + dado.get(1) + "  "
                        + Util.toStringDinheiro(Double.parseDouble(dado.get(2)));
                double total = Double.parseDouble(dado.get(2));
                dadosPieChart.add(new PieChart.Data(legenda, total));
            }
        } else {
            dadosPieChart.add(new PieChart.Data("Nenhum dado encontrado", 0));
        }
    }
}
