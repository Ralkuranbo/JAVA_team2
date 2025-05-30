<%@ page contentType="text/html;charset=UTF-8" language="java" import="routine.User" %>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>운동 루틴 메뉴</title>
</head>
<body>

<h2><%= user.getUsername() %>님, 환영합니다!</h2>

<h3>운동 루틴 관리</h3>
<ol>
    <li><form action="exercise.jsp"><button type="submit">운동 정보 입력</button></form></li>
    <li><form action="stats.jsp"><button type="submit">통계 보기</button></form></li>
    <li><form action="recommend.jsp"><button type="submit">운동 추천</button></form></li>
    <li><form action="export"><button type="submit">파일 저장</button></form></li>
    <li><form action="import.jsp"><button type="submit">파일 불러오기</button></form></li>
    <li><form action="logout"><button type="submit">로그아웃</button></form></li>
</ol>

</body>
</html>