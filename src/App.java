import java.sql.Connection;

import jeff.model.dao.CondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Condicional;
import jeff.util.SQLs;

public class App {
    public static void main(String[] args) {
        System.console();
        Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
        Connection connection = database.conectar();
        Condicional condicional = new Condicional();
        SQLs.start();
        condicional.setID(1);
        CondicionalDAO condicionalDAO = new CondicionalDAO();
        condicionalDAO.setConnection(connection);
        // condicionalDAO.inserir(c);
        System.out.println(condicionalDAO.buscar(condicional));
    }
}