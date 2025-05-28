package routine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.nio.charset.StandardCharsets;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // 폼 파라미터
        boolean gender = "M".equals(req.getParameter("gender"));
        int age        = Integer.parseInt(req.getParameter("age"));
        float height   = Float.parseFloat(req.getParameter("height"));
        float weight   = Float.parseFloat(req.getParameter("weight"));

        // 세션에도 저장
        Profile p = new Profile(username, gender, age, height, weight);
        session.setAttribute("profile", p);

        // {username}.csv 에 저장
        String dataDir = getServletContext().getRealPath("/data");
        File dir = new File(dataDir);
        if (!dir.exists()) dir.mkdirs();
        File csv = new File(dir, username + ".csv");

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(csv), StandardCharsets.UTF_8))) {
            // gender,age,height,weight 순으로 기록
            writer.write((gender ? "M":"F") + "," +
                         age + "," +
                         height + "," +
                         weight);
        }

        resp.sendRedirect(req.getContextPath() + "/menu.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 수정 모드: 기존 파일이 있으면 불러와서 세션에 올리고 JSP에 전달
        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");
        if (username != null) {
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
        }
        req.getRequestDispatcher("/userInfo.jsp").forward(req, resp);
    }
}
