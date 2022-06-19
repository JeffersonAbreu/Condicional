package jeff.model.database;

public class DatabaseFactory {
    private static final String PostgreSQL = "postgresql", MySQL = "mysql", SQLite = "sqlite";
    public static final String SGBD = DatabaseFactory.PostgreSQL;
    private static Database getDatabase(String nome) {
        if (nome.equals("postgresql")) {
            return new DatabasePostgreSQL();
        } else if (nome.equals("mysql")) {
            return new DatabaseMySQL();
        } else if (nome.equals("sqlite")) {
            return new DatabaseSQLite();
        }
        return null;
    }

    public static Database getDatabase() {
        return DatabaseFactory.getDatabase(DatabaseFactory.SGBD);
    }
}
