package routine;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean success = UserStore.register(username, password);
        if (success) {
            resp.getWriter().write("<script>alert('회원가입 성공');location.href='login.jsp';</script>");
        } else {
            resp.getWriter().write("<script>alert('이미 존재하는 사용자');location.href='signup.jsp';</script>");
        }
    }
}
