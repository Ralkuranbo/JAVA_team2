package routine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/recommend")
public class RecommendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // targetFrequency까지 session에 넣어두었다면 JSP에서 로직 실행
        req.getRequestDispatcher("/recommend.jsp").forward(req, resp);
    }
}
