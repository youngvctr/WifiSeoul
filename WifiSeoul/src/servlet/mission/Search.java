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

@WebServlet(name = "Search", value = "/search")
public class Search extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");

        try {
            if (lat.equals("") || lng.equals("")) {
                exceptionCall(response);
                return;
            }

            if (lat.contains("0.0") || lng.contains("0.0")) {
                exceptionCall(response);
                return;
            }

            if (!lat.matches("^[^0]\\d*\\.{1}\\d*[^0]$") || !lng.matches("^[^0]\\d*\\.{1}\\d*[^0]$")) {
                exceptionCall(response);
                return;
            }

            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lng);
            if (latitude < 33 || latitude > 43 || longitude < 124 || longitude > 132) {
                exceptionCall(response);
                return;
            }
        } catch (NumberFormatException | IllegalStateException e) {
            e.printStackTrace();
            exceptionCall(response);
        }

        LoadDB loadDB = new LoadDB();
        GetData[] getData = loadDB.search(Double.parseDouble(lat), Double.parseDouble(lng), 20);
        request.setAttribute("getData", getData);
        RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
        rd.forward(request, response);
    }

    public void exceptionCall(HttpServletResponse response) throws IOException {
        String msg = "앗! 먼저 위치를 검색해주세요.";
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
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
