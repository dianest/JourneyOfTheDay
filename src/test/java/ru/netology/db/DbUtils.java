package ru.netology.db;

import java.sql.*;

public final class DbUtils {
    private DbUtils() {

    }

    public static void clearTables() {
        try {
            try (Connection connection = createConnection()) {
                try (Statement statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    statement.executeUpdate("DELETE FROM order_entity");
                    statement.executeUpdate("DELETE FROM credit_request_entity");
                    statement.executeUpdate("DELETE FROM payment_entity");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find the database driver " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database " + e.getMessage());
        }
    }

    public static boolean checkPayment() {
        try {
            try (Connection connection = createConnection()) {
                try (Statement statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    int paymentsCount = 0;
                    int creditsCount = 0;
                    int ordersCount = 0;

                    final ResultSet paymentsCountResult = statement.executeQuery(
                            "SELECT COUNT(*) FROM payment_entity;");
                    if (paymentsCountResult.first()) {
                        paymentsCount = paymentsCountResult.getInt(1);
                    }
                    final ResultSet creditsCountResult = statement.executeQuery(
                            "SELECT COUNT(*) FROM credit_request_entity;");
                    if (creditsCountResult.first()) {
                        creditsCount = creditsCountResult.getInt(1);
                    }
                    final ResultSet ordersCountResult = statement.executeQuery(
                            "SELECT COUNT(*) FROM order_entity;");
                    if (ordersCountResult.first()) {
                        ordersCount = ordersCountResult.getInt(1);
                    }
                    return paymentsCount == 1 && ordersCount == 1 && creditsCount == 0;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find the database driver " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database " + e.getMessage());
        }

        return false;
    }

    public static boolean checkCredit() {
        try {
            try (Connection connection = createConnection()) {
                try (Statement statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                    int paymentsCount = 0;
                    int creditsCount = 0;
                    int ordersCount = 0;

                    final ResultSet paymentsCountResult = statement.executeQuery(
                            "SELECT COUNT(*) FROM payment_entity;");
                    if (paymentsCountResult.first()) {
                        paymentsCount = paymentsCountResult.getInt(1);
                    }
                    final ResultSet creditsCountResult = statement.executeQuery(
                            "SELECT COUNT(*) FROM credit_request_entity;");
                    if (creditsCountResult.first()) {
                        creditsCount = creditsCountResult.getInt(1);
                    }
                    final ResultSet ordersCountResult = statement.executeQuery(
                            "SELECT COUNT(*) FROM order_entity;");
                    if (ordersCountResult.first()) {
                        ordersCount = ordersCountResult.getInt(1);
                    }
                    return paymentsCount == 0 && ordersCount == 1 && creditsCount == 1;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find the database driver " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database " + e.getMessage());
        }

        return false;
    }

    private static Connection createConnection() throws ClassNotFoundException, SQLException {
        if (System.getProperty("app.datasource").equals("mysql")) {
            final String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            final String url = "jdbc:mysql://localhost:3306/app?autoReconnect=true&useSSL=false";
            final String username = "app";
            String password = "pass";

            return DriverManager.getConnection(url, username, password);
        } else if (System.getProperty("app.datasource").equals("postgres")) {
            final String driverName = "org.postgresql.Driver";
            Class.forName(driverName);
            final String url = "jdbc:postgresql://localhost:5432/app?autoReconnect=true&useSSL=false";
            final String username = "app";
            String password = "pass";

            return DriverManager.getConnection(url, username, password);
        } else {
            throw new IllegalArgumentException("Unsupported datasource");
        }
    }
}
