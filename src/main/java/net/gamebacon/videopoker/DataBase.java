package net.gamebacon.videopoker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private Connection connection;

    public void connect(String host, String port, String dbName, String user, String password) {
        if (!isConnected())
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s" + "?useLegacyDatetimeCode=false&serverTimezone=Europe/Stockholm", host, port, dbName), user, password);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Connection unsuccessful");
            }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }
}
