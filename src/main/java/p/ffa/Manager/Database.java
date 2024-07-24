package p.ffa.Manager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private File dataFolder;
    private Connection connection;

    public Database(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + new File(dataFolder, "database.db").getAbsolutePath());
            initializeTables();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("No se pudo cargar el controlador de SQLite.");
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    private void initializeTables() throws SQLException {
        String createSolicitudesTable = "CREATE TABLE IF NOT EXISTS solicitudes (" +
                "invitador TEXT NOT NULL, " +
                "invitado TEXT NOT NULL" +
                ");";

        String createParejasTable = "CREATE TABLE IF NOT EXISTS parejas (" +
                "jugador1_uuid TEXT NOT NULL, " +
                "jugador1_nombre TEXT NOT NULL, " +
                "jugador2_uuid TEXT NOT NULL, " +
                "jugador2_nombre TEXT NOT NULL" +
                ");";

        String createStatsTable = "CREATE TABLE IF NOT EXISTS stats (" +
                "uuid TEXT PRIMARY KEY, " +
                "kills INTEGER DEFAULT 0, " +
                "deaths INTEGER DEFAULT 0, " +
                "streak INTEGER DEFAULT 0" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createSolicitudesTable);
            statement.execute(createParejasTable);
            statement.execute(createStatsTable);
        }
    }
}
