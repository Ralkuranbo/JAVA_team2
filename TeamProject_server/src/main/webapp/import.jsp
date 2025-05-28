<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>파일 불러오기</title></head>
<body>
  <h2>4. 파일 불러오기</h2>
  <form action="import" method="post" enctype="multipart/form-data">
    <input type="file" name="csvfile" accept=".csv" required>
    <button type="submit">업로드 & 불러오기</button>
  </form>
  <p><a href="menu.jsp">◀ 메뉴로 돌아가기</a></p>
</body>
</html>
