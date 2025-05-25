<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
<h2>로그인</h2>
<form action="login" method="post">
    아이디: <input name="username"><br/>
    비밀번호: <input name="password" type="password"><br/>
    <button type="submit">로그인</button>
</form>
<a href="signup.jsp">회원가입</a>
</body>
</html>
