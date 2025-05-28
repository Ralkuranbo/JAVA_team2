package routine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/export")
public class ExportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 세션에서 사용자 정보와 운동 기록 꺼내기
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        User u = (User) session.getAttribute("user");
        List<WorkoutEntry> entries = RoutineServlet.DB
            .getOrDefault((String) session.getAttribute("username"), List.of());

        // 파일명: {username}.csv
        String username = u.getUsername();
        resp.setContentType("text/csv; charset=UTF-8");
        resp.setHeader("Content-Disposition",
                       "attachment; filename=\"" + username + ".csv\"");

        try (PrintWriter out = new PrintWriter(
                 new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8))) {
            // 1행: 성별,나이,키,몸무게
            out.println(u.getGen() + "," + u.getAge() + "," 
                        + u.getHeight() + "," + u.getWeight());

            // 2행부터: 운동 기록 CSV
            for (WorkoutEntry e : entries) {
                out.println(e.toCSV());
            }
        }
    }
}
