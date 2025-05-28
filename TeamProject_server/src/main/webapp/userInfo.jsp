<%@ page contentType="text/html; charset=UTF-8" import="routine.Profile" %>
<!DOCTYPE html>
<html>
<head><title>사용자 정보</title></head>
<body>
  <h2>사용자 정보 입력 / 수정</h2>
  <%
    Profile p = (Profile) session.getAttribute("profile");
    String genderValue = p!=null ? (p.isMale()?"M":"F") : "";
    String ageValue    = p!=null ? String.valueOf(p.getAge()) : "";
    String heightValue = p!=null ? String.valueOf(p.getHeight()): "";
    String weightValue = p!=null ? String.valueOf(p.getWeight()): "";
  %>
  <form action="profile" method="post">
    <label>성별:
      <select name="gender" required>
        <option value="M" <%= "M".equals(genderValue)?"selected":"" %>>남자</option>
        <option value="F" <%= "F".equals(genderValue)?"selected":"" %>>여자</option>
      </select>
    </label><br/>

    <label>나이:
      <input type="number" name="age" min="1" required value="<%= ageValue %>"/>
    </label><br/>

    <label>키(cm):
      <input type="number" name="height" min="50" required value="<%= heightValue %>"/>
    </label><br/>

    <label>몸무게(kg):
      <input type="number" name="weight" min="20" required value="<%= weightValue %>"/>
    </label><br/>

    <button type="submit"><%= p!=null?"수정":"저장" %></button>
  </form>
  <p><a href="menu.jsp">◀ 메뉴로 돌아가기</a></p>
</body>
</html>
