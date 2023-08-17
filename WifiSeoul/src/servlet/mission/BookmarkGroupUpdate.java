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

@WebServlet(name = "BookmarkGroupUpdate", value = "/bookmark-group-update")
public class BookmarkGroupUpdate extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String bookmark_name = request.getParameter("bookmark_name");
        int bookmark_id = Integer.parseInt(request.getParameter("bookmark_id"));
        int bookmark_order = Integer.parseInt(request.getParameter("bookmark_order"));

        if(bookmark_name.equals("")){
            exceptionCall(response);
            return;
        }

        try {
            LoadDB loadDB = new LoadDB();
            int result = loadDB.putBookmarkGroup(bookmark_id, bookmark_name, bookmark_order);

            if (result > 0) {
                GetBookmark[] bookmark = loadDB.getBookmarkGroupList();
                request.setAttribute("bookmark", bookmark);

                RequestDispatcher rd = request.getRequestDispatcher("bookmark-group.jsp");
                rd.forward(request, response);

                PrintWriter out = response.getWriter();
                out.printf("북마크 수정완료.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exceptionCall(HttpServletResponse response) throws IOException {
        String msg = "북마크 명을 바르게 입력해주세요.";
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<script>alert('" + msg + "');history.go(-1);</script>");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            process(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
