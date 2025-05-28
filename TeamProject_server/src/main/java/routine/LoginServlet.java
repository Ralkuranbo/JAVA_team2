package routine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserStore.authenticate(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user", user);

            // 로그인 성공 후 {username}.csv 프로필 복원
            String dataDir = getServletContext().getRealPath("/data");
            File csv = new File(dataDir, username + ".csv");
            if (csv.exists()) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8))) {
                    String[] parts = reader.readLine().split(",");
                    boolean gender = parts[0].equals("M");
                    int age        = Integer.parseInt(parts[1]);
                    float height   = Float.parseFloat(parts[2]);
                    float weight   = Float.parseFloat(parts[3]);
                    Profile p = new Profile(username, gender, age, height, weight);
                    session.setAttribute("profile", p);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/menu.jsp");
        } else {
            req.setAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
