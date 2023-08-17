package servlet.mission;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Logs", value = "/logData")
public class Logs extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException{
        String id = request.getParameter("delete");
        try {
            LoadDB log = new LoadDB();
            log.delete(Integer.parseInt(id));
        } catch (NumberFormatException e) {

        }
        LoadDB loadDB = new LoadDB();
        GetData[] logData = loadDB.loadDB();
        request.setAttribute("logData", logData);
        RequestDispatcher rd = request.getRequestDispatcher("log-data.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            process(request, response);
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
}