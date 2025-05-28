package routine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/import")
public class ImportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");
        String dataDir  = getServletContext().getRealPath("/data");
        File csv        = new File(dataDir, username + ".csv");

        if (!csv.exists()) {
            session.setAttribute("msg", "파일이 없습니다.");
            resp.sendRedirect(req.getContextPath() + "/menu.jsp");
            return;
        }

        List<WorkoutEntry> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                 new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8))) {
            // 첫 번째 줄(사용자 정보)은 스킵
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // WorkoutEntry.fromCSV 는 이제 String[] 하나만 받습니다
                WorkoutEntry e = WorkoutEntry.fromCSV(data);
                list.add(e);
            }
        }

        RoutineServlet.DB.put(username, list);
        session.setAttribute("entries", list);

        resp.sendRedirect(req.getContextPath() + "/menu.jsp");
    }
}
