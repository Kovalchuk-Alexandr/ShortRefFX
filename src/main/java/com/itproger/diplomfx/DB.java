package com.itproger.diplomfx;
import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "diplomfx_db";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        // создание строки подключения
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;

        Class.forName("com.mysql.cj.jdbc.Driver");

        // установка соединения
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASSWORD);
        return dbConn;
    }

    // Проверка подключения
    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public void addRef(String shortRef, String fullRef) {
        String sql = "INSERT INTO `reference` (`shortref`, `fullref`) VALUES (?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, shortRef);
            prSt.setString(2, fullRef);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Проверяем, есть ли такая запись
    public boolean isExistRef(String shortRef) {
        String sql = "SELECT `id` FROM `reference` WHERE `shortref` = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, shortRef);

            ResultSet res = prSt.executeQuery();
            return res.next();  // Находим первую попавшуюся запись, если есть
            // возвращаем thrue
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //return false;
    }
    public ResultSet getRef() {
        String sql = "SELECT `shortref`,`fullref` FROM `reference` ORDER BY `id` ASC";
        Statement statement = null;
        try {
            statement = getDbConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
