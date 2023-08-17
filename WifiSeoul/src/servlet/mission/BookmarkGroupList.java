package servlet.mission;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BookmarkGroupList", value = "/bookmark-group-list")
public class BookmarkGroupList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoadDB loadDB = new LoadDB();
            GetBookmark[] bookmark = loadDB.getBookmarkGroupList();
            request.setAttribute("bookmark", bookmark);
            RequestDispatcher rd = request.getRequestDispatcher("bookmark-group.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

