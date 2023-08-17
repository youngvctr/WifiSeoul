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

@WebServlet(name = "Detail", value = "/detail")
public class Detail extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException {
        LoadDB loadDB = new LoadDB();
        int id = Integer.parseInt(request.getParameter("id"));
        GetData[] detail = loadDB.detail(id);
        request.setAttribute("detail", detail);
        RequestDispatcher rd = request.getRequestDispatcher("detail.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            process(request, response);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}