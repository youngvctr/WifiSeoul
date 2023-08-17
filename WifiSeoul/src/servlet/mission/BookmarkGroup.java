package servlet.mission;

import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "BookmarkGroup", value = "/bookmark-group")
public class BookmarkGroup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LoadDB loadDB = new LoadDB();
            List<String> bookmarkGroup = loadDB.getBookmarkGroup();
            Gson gson = new Gson();
            String json = gson.toJson(bookmarkGroup);
            response.getWriter().write(json);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("select");
            if(name.equals("북마크 그룹 이름 선택")){
                exceptionCall(response);
                return;
            }

            String mainNm = request.getParameter("main_nm");
            LoadDB loadDB = new LoadDB();
            int result = loadDB.postBookmark(name, mainNm);
            if (result > 0) {
                String msg = "북마크 저장에 성공했습니다.";
                response.setContentType("text/html; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write("<script>alert('" + msg + "');history.go(-1);</script>");
                writer.flush();
                writer.close();
            }
        } catch (SQLException  e) {
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
