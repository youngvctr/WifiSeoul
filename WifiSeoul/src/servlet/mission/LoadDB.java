package servlet.mission;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoadDB {
    protected int update() throws SQLException, IOException {
        DbUtil.initialize();
        String query = "REPLACE INTO new_wifi_information "
                + "(id, mgr_no, wrdofc, main_nm, address1, address2, instl_floor, instl_ty, "
                + "instl_mby, svc_se, cmcwr, cnstc_year, in_out, remars3, lng, lat, work_dttm) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int updated = 0;
        Connection conn = DbUtil.connect();
        conn.setAutoCommit(false);
        PreparedStatement statement = conn.prepareStatement(query);
        for (GetData data : JsonUtil.getAllWifiSpotsInSeoul()) {
            statement.setInt(1, data.getId());
            statement.setString(2, data.getMgr_no());
            statement.setString(3, data.getWrdofc());
            statement.setString(4, data.getMain_nm());
            statement.setString(5, data.getAddress1());
            statement.setString(6, data.getAddress2());
            statement.setString(7, data.getInstl_floor());
            statement.setString(8, data.getInstl_ty());
            statement.setString(9, data.getInstl_mby());
            statement.setString(10, data.getSvc_se());
            statement.setString(11, data.getCmcwr());
            statement.setString(12, data.getCnstc_year());
            statement.setString(13, data.getIn_out());
            statement.setString(14, data.getRemars3());
            statement.setDouble(15, data.getLat());
            statement.setDouble(16, data.getLng());
            statement.setString(17, data.getWork_dttm());
            statement.addBatch();
            updated++;
        }
        statement.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        statement.close();
        conn.close();
        return updated;
    }

    protected GetData[] search(double latitude, double longitude, int limit) throws NamingException {
        String selectQuery = "SELECT * FROM new_wifi_information;";
        String insertQuery = "INSERT INTO search_log (lat, lng, req_datetime) VALUES (?, ?, datetime('now', 'localtime'));";
        GetData[] wifiSpots = new GetData[limit];

        try {
            Connection conn = DbUtil.connect();
            PreparedStatement statement = conn.prepareStatement(selectQuery);
            ResultSet rs = statement.executeQuery();

            List<GetData> candidates = new ArrayList<>();
            while (rs.next()) {
                GetData getData = new GetData(
                        rs.getInt("id"),
                        rs.getString("mgr_no"),
                        rs.getString("wrdofc"),
                        rs.getString("main_nm"),
                        rs.getString("address1"),
                        rs.getString("address2"),
                        rs.getString("instl_floor"),
                        rs.getString("instl_ty"),
                        rs.getString("instl_mby"),
                        rs.getString("svc_se"),
                        rs.getString("cmcwr"),
                        rs.getString("cnstc_year"),
                        rs.getString("in_out"),
                        rs.getString("remars3"),
                        rs.getDouble("lat"),
                        rs.getDouble("lng"),
                        rs.getString("work_dttm"));

                double dist = getData.setDistance(latitude, longitude, rs.getDouble("lat"), rs.getDouble("lng"));
                getData.setDist(dist);
                candidates.add(getData);
            }
            candidates.sort((x, y) -> (int) (x.getDist() * 10000 - y.getDist() * 10000));

            for (int i = 0; i < limit; i++) {
                wifiSpots[i] = candidates.get(i);
            }

            statement = conn.prepareStatement(insertQuery);
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wifiSpots;
    }

    protected GetData[] loadDB() throws SQLException, IOException {
        String query = "SELECT * FROM search_log \n"
                + " ORDER BY id DESC \n"
                + "LIMIT 20;";
        Connection conn = DbUtil.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<GetData> tmp = new ArrayList<>();
        while (rs.next()) {
            tmp.add(new GetData(rs.getInt("id"),
                    rs.getDouble("lat"),
                    rs.getDouble("lng"),
                    rs.getString("req_datetime")));
        }
        GetData[] logs = new GetData[tmp.size()];
        for (int i = 0; i < logs.length; i++) {
            logs[i] = tmp.get(i);
        }
        statement.close();
        conn.close();
        return logs;
    }

    public GetBookmark[] getBookmarks() throws SQLException {
        String query = "SELECT * FROM bookmark \n"
                        + "INNER JOIN bookmark_group \n"
                        + "ON bookmark.bookmark_name = bookmark_group.bookmark_name \n"
                        + "ORDER BY bookmark_order ASC;"; //"INSERT INTO search_log (lat, lng, req_datetime) VALUES (?, ?, NOW());";
        Connection conn = DbUtil.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<GetBookmark> tmp = new ArrayList<>();
        while (rs.next()) {
            String updateDate = rs.getString("update_datetime");
            if (updateDate == null) updateDate = "";
            tmp.add(new GetBookmark(rs.getInt("id"),
                    rs.getString("bookmark_name"),
                    rs.getString("main_nm"),
                    rs.getInt("bookmark_order"),
                    rs.getString("register_datetime"),
                    updateDate));
        }

        GetBookmark[] bookmark = new GetBookmark[tmp.size()];
        for (int i = 0; i < bookmark.length; i++) {
            bookmark[i] = tmp.get(i);
        }
        statement.close();
        conn.close();
        return bookmark;
    }

    public List<String> getBookmarkGroup() throws SQLException {
        String query = "SELECT bookmark_name FROM bookmark_group ORDER BY bookmark_order ASC;";
        Connection conn = DbUtil.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<String> bookmarkGroupList = new ArrayList<>();
        while (rs.next()) {
            bookmarkGroupList.add(rs.getString("bookmark_name"));
        }
        statement.close();
        conn.close();
        return bookmarkGroupList;
    }

    public GetBookmark[] getBookmarkGroupList() throws SQLException {
        String query = "SELECT * FROM bookmark_group ORDER BY bookmark_order ASC;";
        Connection conn = DbUtil.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<GetBookmark> tmp = new ArrayList<>();
        while (rs.next()) {
            tmp.add(new GetBookmark(rs.getInt("id"), rs.getInt("bookmark_order"), rs.getString("bookmark_name")));
        }
        GetBookmark[] bookmark = new GetBookmark[tmp.size()];
        for (int i = 0; i < bookmark.length; i++) {
            bookmark[i] = tmp.get(i);
        }
        statement.close();
        conn.close();
        return bookmark;
    }

    public int postBookmark(String bookmark_name, String main_nm) throws SQLException {
        String selectQuery = "SELECT bookmark_name, bookmark_order FROM bookmark_group ORDER BY bookmark_order ASC;";
        Connection conn = DbUtil.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(selectQuery);
        List<GetBookmark> tmp = new ArrayList<>();
        while (rs.next()) {
            if (rs.getString("bookmark_name").equals(bookmark_name)) {
                tmp.add(new GetBookmark(rs.getString("bookmark_name"),
                        rs.getInt("bookmark_order")));
            }
        }

        String insertQuery = "INSERT INTO bookmark (bookmark_name, main_nm, bookmark_order, register_datetime) VALUES (?, ?, ?, datetime('now', 'localtime'));";
        PreparedStatement preparedStatement = null;
        preparedStatement = conn.prepareStatement(insertQuery);
        preparedStatement.setString(1, bookmark_name);
        preparedStatement.setString(2, main_nm);
        preparedStatement.setInt(3, tmp.get(0).bookmark_order);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        statement.close();
        conn.close();
        return 1;
    }

    public int postBookmarkGroup(String bookmark_name, int bookmark_order) throws SQLException {
        String selectQuery = "SELECT * FROM bookmark_group WHERE bookmark_name = ?;";
        Connection conn = DbUtil.connect();
        PreparedStatement preparedStatement = null;
        preparedStatement = conn.prepareStatement(selectQuery);
        preparedStatement.setString(1, bookmark_name);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            preparedStatement.close();
            conn.close();
            return -1;
        }

        String query = "INSERT INTO bookmark_group (bookmark_name, bookmark_order) VALUES (?, ?);";
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, bookmark_name);
        preparedStatement.setInt(2, bookmark_order);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
        return 1;
    }

    public int putBookmark(int bookmark_id, String bookmark_name) throws SQLException {
        String selectQuery = "SELECT bookmark_name, bookmark_order FROM bookmark_group WHERE bookmark_name = ?;";
        Connection conn = DbUtil.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
        preparedStatement.setString(1, bookmark_name);
        ResultSet rs = preparedStatement.executeQuery();
        List<GetBookmark> tmp = new ArrayList<>();
        while (rs.next()) {
            tmp.add(new GetBookmark(rs.getString("bookmark_name"), rs.getInt("bookmark_order")));
        }

        String query = "UPDATE bookmark SET bookmark_name = ?, bookmark_order = ?, update_datetime = ? WHERE id = ?;";
        long time = System.currentTimeMillis();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, bookmark_name);
        preparedStatement.setInt(2, tmp.get(0).bookmark_order);
        preparedStatement.setString(3, now);
        preparedStatement.setInt(4, bookmark_id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
        return 1;
    }

    public int putBookmarkGroup(int bookmark_id, String bookmark_name, int bookmark_order) throws SQLException {
        Connection conn = DbUtil.connect();
        String query = "UPDATE bookmark_group SET bookmark_name = ?, bookmark_order = ? WHERE id = ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, bookmark_name);
        preparedStatement.setInt(2, bookmark_order);
        preparedStatement.setInt(3, bookmark_id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
        return 1;
    }

    public void deleteBookmark(int id) throws SQLException {
        String query = "DELETE FROM bookmark WHERE id = ?;";
        Connection conn = DbUtil.connect();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        conn.close();
    }

    public void deleteBookmarkGroup(int id) throws SQLException {
        String selectQuery = "SELECT bookmark_name, bookmark_order FROM bookmark_group WHERE id = ?;";
        Connection conn = DbUtil.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        List<GetBookmark> tmp = new ArrayList<>();
        while (rs.next()) {
            tmp.add(new GetBookmark(rs.getString("bookmark_name"), rs.getInt("bookmark_order")));
        }

        String tempQuery = "DELETE FROM bookmark WHERE bookmark_name = ? AND bookmark_order = ?;";
        preparedStatement = conn.prepareStatement(tempQuery);
        preparedStatement.setString(1, tmp.get(0).bookmark_name);
        preparedStatement.setInt(2, tmp.get(0).bookmark_order);
        preparedStatement.executeUpdate();

        String deleteQuery = "DELETE FROM bookmark_group WHERE id = ?;";
        preparedStatement = conn.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
    }

    protected GetData[] detail(int id) throws SQLException {
        String query = "SELECT * FROM new_wifi_information where id = ?;";
        Connection conn = DbUtil.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        List<GetData> tmp = new ArrayList<>();
        if (rs.next()) {
            GetData getData = new GetData(
                    rs.getInt("id"),
                    rs.getString("mgr_no"),
                    rs.getString("wrdofc"),
                    rs.getString("main_nm"),
                    rs.getString("address1"),
                    rs.getString("address2"),
                    rs.getString("instl_floor"),
                    rs.getString("instl_ty"),
                    rs.getString("instl_mby"),
                    rs.getString("svc_se"),
                    rs.getString("cmcwr"),
                    rs.getString("cnstc_year"),
                    rs.getString("in_out"),
                    rs.getString("remars3"),
                    rs.getDouble("lat"),
                    rs.getDouble("lng"),
                    rs.getString("work_dttm"));
            tmp.add(getData);
        }
        GetData[] detail = new GetData[tmp.size()];
        for (int i = 0; i < detail.length; i++) {
            detail[i] = tmp.get(i);
        }
        preparedStatement.close();
        conn.close();
        return detail;
    }

    protected void delete(int id) throws SQLException {
        String query = "DELETE FROM search_log WHERE id = ?;";
        Connection conn = DbUtil.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
    }
}