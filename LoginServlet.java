package routine;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
    	resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        
    	String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserStore.authenticate(username, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("menu.jsp");
        } else {
            resp.getWriter().write("<script>alert('로그인 실패');location.href='login.jsp';</script>");
        }
    }
}
