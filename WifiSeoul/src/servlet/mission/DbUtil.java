package servlet.mission;

import java.sql.*;

public class DbUtil {
    protected static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "C:\\db\\WifiSeoul\\seoul_public.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return conn;
    }

    protected static void initialize() throws SQLException {
        Connection conn = connect();
        conn.setAutoCommit(false);
        Statement statement = conn.createStatement();
        statement.executeUpdate(wifiDataQuery());
        statement.executeUpdate(logDataQuery());
        statement.executeUpdate(bookmarkDataQuery());
        statement.executeUpdate(bookmarkGroupDataQuery());
        conn.commit();
        conn.setAutoCommit(true);
        statement.close();
        conn.close();
    }

    private static String wifiDataQuery() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS new_wifi_information (\n");
        query.append("             id INTEGER PRIMARY KEY\n");
        query.append("           , mgr_no TEXT\n");
        query.append("           , wrdofc TEXT\n");
        query.append("           , main_nm TEXT\n");
        query.append("           , address1 TEXT\n");
        query.append("           , address2 TEXT\n");
        query.append("           , instl_floor TEXT\n");
        query.append("           , instl_ty TEXT\n");
        query.append("           , instl_mby TEXT\n");
        query.append("           , svc_se TEXT\n");
        query.append("           , cmcwr TEXT\n");
        query.append("           , cnstc_year TEXT\n");
        query.append("           , in_out TEXT\n");
        query.append("           , remars3 TEXT\n");
        query.append("           , lng REAL\n");
        query.append("           , lat REAL\n");
        query.append("           , work_dttm TEXT);");
        return query.toString();
    }

    private static String logDataQuery() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS search_log (\n");
        query.append("             id INTEGER PRIMARY KEY AUTOINCREMENT\n");
        query.append("           , lat REAL NOT NULL\n");
        query.append("           , lng REAL NOT NULL\n");
        query.append("           , req_datetime TEXT NOT NULL);");
        return query.toString();
    }

    private static String bookmarkDataQuery() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS bookmark (\n");
        query.append("             id INTEGER PRIMARY KEY AUTOINCREMENT\n");
        query.append("           , bookmark_name TEXT NOT NULL\n");
        query.append("           , bookmark_order INTEGER NOT NULL\n");
        query.append("           , main_nm TEXT NOT NULL\n");
        query.append("           , register_datetime TEXT NOT NULL\n");
        query.append("           , update_datetime TEXT);");
        return query.toString();
    }

    private static String bookmarkGroupDataQuery() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS bookmark_group (\n");
        query.append("             id INTEGER PRIMARY KEY AUTOINCREMENT\n");
        query.append("           , bookmark_name TEXT NOT NULL\n");
        query.append("           , bookmark_order INTEGER NOT NULL);");
        return query.toString();
    }
}