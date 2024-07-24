package p.ffa.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsManager {

    private Database database;

    public StatsManager(Database database) {
        this.database = database;
    }

    public int getKills(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT kills FROM stats WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("kills");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDeaths(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT deaths FROM stats WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("deaths");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getStreak(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT streak FROM stats WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("streak");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void incrementKills(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO stats (uuid, kills) VALUES (?, 1) ON CONFLICT(uuid) DO UPDATE SET kills = kills + 1")) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        incrementStreak(uuid);
    }

    public void incrementDeaths(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO stats (uuid, deaths) VALUES (?, 1) ON CONFLICT(uuid) DO UPDATE SET deaths = deaths + 1")) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resetStreak(uuid);
    }

    public void incrementStreak(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO stats (uuid, streak) VALUES (?, 1) ON CONFLICT(uuid) DO UPDATE SET streak = streak + 1")) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetStreak(String uuid) {
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE stats SET streak = 0 WHERE uuid = ?")) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
