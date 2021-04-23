package ru.netology.db;

import lombok.SneakyThrows;

import java.sql.*;

public final class DbUtils {
    private DbUtils() {

    }

    @SneakyThrows
    public static void clearTables() {
        try (Connection connection = createConnection()) {
            try (Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                statement.executeUpdate("DELETE FROM order_entity");
                statement.executeUpdate("DELETE FROM credit_request_entity");
                statement.executeUpdate("DELETE FROM payment_entity");
            }
        }
    }

    @SneakyThrows
    public static boolean checkPayment() {
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
    }

    @SneakyThrows
    public static boolean checkCredit() {
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
    }

    @SneakyThrows
    private static Connection createConnection() {
        final String datasource = System.getProperty("app.datasource");
        final String dbAddress = System.getProperty("app.db_address");
        final String dbName = System.getProperty("app.db_name");
        final String dbUser = System.getProperty("app.db_user");
        final String dbPass = System.getProperty("app.db_pass");

        checkDriver(datasource);

        final String url = "jdbc:" + datasource + "://" + dbAddress + "/" + dbName + "?autoReconnect=true&useSSL=false";

        return DriverManager.getConnection(url, dbUser, dbPass);
    }

    @SneakyThrows
    private static void checkDriver(String datasource) {
        if ("mysql".equals(datasource)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } else if ("postgres".equals(datasource)) {
            Class.forName("org.postgresql.Driver");
        } else {
            throw new IllegalArgumentException("Unsupported datasource");
        }
    }
}
