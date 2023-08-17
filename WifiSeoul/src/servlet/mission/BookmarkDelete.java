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

@WebServlet(name = "BookmarkDelete", value = "/bookmark-delete")
public class BookmarkDelete extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException {
        int id = Integer.parseInt(request.getParameter("delete"));
        try {
            LoadDB loadDB = new LoadDB();
            loadDB.deleteBookmark(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        LoadDB loadDB = new LoadDB();
        GetBookmark[] bookmark = loadDB.getBookmarks();
        request.setAttribute("bookmark", bookmark);
        RequestDispatcher rd = request.getRequestDispatcher("bookmark-list.jsp");
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
