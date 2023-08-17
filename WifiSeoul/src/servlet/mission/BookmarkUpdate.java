package servlet.mission;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "BookmarkUpdate", value = "/bookmark-update")
public class BookmarkUpdate extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException {
        String bookmark_name = request.getParameter("bookmark_name");
        int bookmark_id = Integer.parseInt(request.getParameter("bookmark_id"));

        if(bookmark_name.equals("북마크 그룹 이름 선택")){
            exceptionCall(response);
            return;
        }

        try {
            LoadDB loadDB = new LoadDB();
            int result = loadDB.putBookmark(bookmark_id, bookmark_name);

            if (result > 0) {
                GetBookmark[] bookmark = loadDB.getBookmarks();
                request.setAttribute("bookmark", bookmark);

                RequestDispatcher rd = request.getRequestDispatcher("bookmark-list.jsp");
                rd.forward(request, response);

                PrintWriter out = response.getWriter();
                out.printf("북마크 수정완료.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            process(request, response);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    private void exceptionCall(HttpServletResponse response) throws IOException {
        String msg = "북마크를 선택해주세요.";
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<script>alert('" + msg + "');history.go(-1);</script>");
        writer.flush();
        writer.close();
    }
}
