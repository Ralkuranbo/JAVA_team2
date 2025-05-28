package routine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * 운동 추가/삭제를 처리하는 서블릿
 */
@WebServlet("/routine")
public class RoutineServlet extends HttpServlet {
    // 사용자별 메모리 DB (public으로 열어두면 JSP 등에서 바로 참조할 수 있습니다)
    public static final Map<String, List<WorkoutEntry>> DB = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        List<WorkoutEntry> entries = DB.computeIfAbsent(username, k -> new ArrayList<>());

        String action = req.getParameter("action");
        if ("add".equals(action)) {
            LocalDate date      = LocalDate.parse(req.getParameter("date"));
            String category     = req.getParameter("category");
            String exName       = req.getParameter("exercise");
            int weight          = Integer.parseInt(req.getParameter("weight"));
            int reps            = Integer.parseInt(req.getParameter("reps"));
            int sets            = Integer.parseInt(req.getParameter("sets"));

            // ChestExercise 인스턴스를 생성하고, 필요 시 추가 로직으로 group 정보 처리
            ChestExercise exercise = new ChestExercise(exName);
            // 만약 근육 그룹별로 target을 설정하고 싶다면 다음과 같이 설정하세요:
            // exercise.setTarget(new String[]{ category });

            WorkoutEntry e = new WorkoutEntry(
                exercise,
                weight, reps, sets, date
            );
            entries.add(e);

        } else if ("delete".equals(action)) {
            if (!entries.isEmpty()) {
                entries.remove(entries.size() - 1);
            }
        }

        session.setAttribute("entries", entries);
        resp.sendRedirect(req.getContextPath() + "/exercise.jsp");
    }
}
