<%@ page contentType="text/html; charset=UTF-8" import="java.util.*, routine.WorkoutJournalApp, routine.Exercise" %>
<!DOCTYPE html>
<html>
<head><title>추천 운동</title></head>
<body>
  <h2>6. 부족 부위 추천</h2>
  <%
    // 세션에 "targetFrequency" 속성으로 Map<String,Long>이 담겨 있다고 가정
    Map<String,Long> freq = (Map<String,Long>) session.getAttribute("targetFrequency");
    if (freq == null || freq.isEmpty()) {
  %>
      <p>아직 운동 통계를 내기엔 데이터가 없습니다.</p>
  <%
    } else {
      long min = Collections.min(freq.values());
      for (String part : freq.keySet()) {
        if (freq.get(part) == min) {
  %>
          <h3><%= part %> (총중량: <%= freq.get(part) %>kg)</h3>
          <p>추천 운동: 
            <% 
              List<String> suggestions = new ArrayList<>();
              // WorkoutJournalApp.getExercisesByPart()에서 운동 목록을 조회
              for (List<Exercise> list : WorkoutJournalApp.getExercisesByPart().values()) {
                for (Exercise ex : list) {
                  String[] targets = ex.getTarget();
                  if (targets != null) {
                    for (String t : targets) {
                      if (t.equals(part) && !suggestions.contains(ex.getName())) {
                        suggestions.add(ex.getName());
                      }
                    }
                  }
                }
              }
              out.print(String.join(", ", suggestions));
            %>
          </p>
  <%    }
      }
    }
  %>
  <p><a href="menu.jsp">◀ 메뉴로 돌아가기</a></p>
</body>
</html>
