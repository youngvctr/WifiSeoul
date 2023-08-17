package servlet.mission;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Update", value = "/update")
public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoadDB loadDB = new LoadDB();
        int updated = 0;
        try {
            updated = loadDB.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String message = String.format("%,d", updated) + "개의 Wifi 정보를 정상적으로 저장하였습니다. ";
            request.setAttribute("loadWifi", message);
            RequestDispatcher rd = request.getRequestDispatcher("load-wifi.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
