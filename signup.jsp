<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
<h2>회원가입</h2>
<form action="signup" method="post">
    아이디: <input name="username"><br/>
    비밀번호: <input name="password" type="password"><br/>
    <button type="submit">가입</button>
</form>
<a href="login.jsp">로그인 페이지로</a>
</body>
</html>
