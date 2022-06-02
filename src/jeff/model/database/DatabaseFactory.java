package jeff.model.database;

public class DatabaseFactory {
    public static final String PostgreSQL = "postgresql", MySQL = "mysql", SQLite = "sqlite";

    public static Database getDatabase(String nome) {
        if (nome.equals("postgresql")) {
            return new DatabasePostgreSQL();
        } else if (nome.equals("mysql")) {
            return new DatabaseMySQL();
        } else if (nome.equals("sqlite")) {
            return new DatabaseSQLite();
        }
        return null;
    }
}
