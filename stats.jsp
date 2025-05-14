<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, java.time.*, routine.WorkoutEntry" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>운동 통계</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        #radarChart {
            max-width: 400px;
            max-height: 400px;
        }
    </style>
</head>
<body>
<h2>최근 7일간 운동 통계 (근육 부위별 세트 수)</h2>
<canvas id="radarChart"></canvas>
<% 
    List<WorkoutEntry> entries = (List<WorkoutEntry>) session.getAttribute("entries");
    if (entries == null) entries = new ArrayList<>();

    LocalDate today = LocalDate.now();
    Map<String, Integer> categorySets = new LinkedHashMap<>();
    String[] categories = {"가슴", "등", "하체", "어깨", "팔", "복근"};
    for (String c : categories) categorySets.put(c, 0);

    List<WorkoutEntry> recentEntries = new ArrayList<>();
    for (WorkoutEntry e : entries) {
        if (!e.getDate().isBefore(today.minusDays(6))) {
            categorySets.put(e.getCategory(), categorySets.getOrDefault(e.getCategory(), 0) + e.getSets());
            recentEntries.add(e);
        }
    }
%>
<script>
    const data = {
        labels: ["가슴", "등", "하체", "어깨", "팔", "복근"],
        datasets: [{
            label: '세트 수',
            data: [
                <%= categorySets.get("가슴") %>,
                <%= categorySets.get("등") %>,
                <%= categorySets.get("하체") %>,
                <%= categorySets.get("어깨") %>,
                <%= categorySets.get("팔") %>,
                <%= categorySets.get("복근") %>
            ],
            fill: true,
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgb(54, 162, 235)',
            pointBackgroundColor: 'rgb(54, 162, 235)'
        }]
    };
    new Chart(document.getElementById('radarChart'), {
        type: 'radar',
        data: data,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                r: {
                    beginAtZero: true,
                    ticks: { stepSize: 1 }
                }
            },
            plugins: {
                legend: { position: 'top' }
            }
        }
    });
</script>

<h3>최근 7일 운동 기록</h3>
<table border="1">
    <tr><th>날짜</th><th>부위</th><th>운동</th><th>무게</th><th>반복</th><th>세트</th></tr>
    <% for (WorkoutEntry e : recentEntries) { %>
        <tr>
            <td><%= e.getDate() %></td>
            <td><%= e.getCategory() %></td>
            <td><%= e.getExercise() %></td>
            <td><%= e.getWeight() %>kg</td>
            <td><%= e.getReps() %></td>
            <td><%= e.getSets() %></td>
        </tr>
    <% } %>
</table>
<br/>
<form action="menu.jsp">
    <button type="submit">메뉴로 돌아가기</button>
</form>
</body>
</html>
