package routine; 

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            // 로그인 안 된 상태면 다시 로그인 페이지로
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // ① 세션에서 username 꺼내기
        String username = (String) session.getAttribute("username");

        // ② 폼 파라미터 읽기
        String gender   = req.getParameter("gender");
        int age         = Integer.parseInt(req.getParameter("age"));
        float height    = Float.parseFloat(req.getParameter("height"));
        float weight    = Float.parseFloat(req.getParameter("weight"));
        boolean gend    = "M".equals(gender);

        // ③ User 객체 생성·저장
        User user = new User(gend, age, height, weight);
        session.setAttribute("user", user);

        // ④ CSV 저장 (webapp/data/{username}.csv)
        String dataDir = getServletContext().getRealPath("/data");
        File dir = new File(dataDir);
        if (!dir.exists()) dir.mkdirs();
        File csv = new File(dir, username + ".csv");

        try (BufferedWriter writer = new BufferedWriter(
                 new OutputStreamWriter(
                   new FileOutputStream(csv, false),
                   StandardCharsets.UTF_8))) {
            writer.write(user.getGen() + "," + user.getAge() + "," +
                         user.getHeight() + "," + user.getWeight());
            writer.newLine();
        }

        resp.sendRedirect(req.getContextPath() + "/menu.jsp");
    }
}
