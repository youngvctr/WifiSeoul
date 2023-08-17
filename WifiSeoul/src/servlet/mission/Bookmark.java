package servlet.mission;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "Bookmark", value = "/bookmark")
public class Bookmark extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoadDB loadDB = new LoadDB();
            GetBookmark[] bookmark = loadDB.getBookmarks();
            request.setAttribute("bookmark", bookmark);
            RequestDispatcher rd = request.getRequestDispatcher("bookmark-list.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String bookmark_name = request.getParameter("bookmark_name");
            int bookmark_order = Integer.parseInt(request.getParameter("bookmark_order"));
            LoadDB loadDB = new LoadDB();

            int result = loadDB.postBookmarkGroup(bookmark_name, bookmark_order);
            if(result < 0) {
                response.setStatus(205);
                return;
            }

            if (result > 0) {
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write("북마크 추가에 성공했습니다.");
                writer.flush();
                writer.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
