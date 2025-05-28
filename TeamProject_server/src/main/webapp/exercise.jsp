<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>운동 정보 입력</title>
    <script>
        const exercises = {
            "가슴": ["벤치프레스", "딥스", "푸쉬업", "인클라인 벤치프레스", "플라이", "머신 체스트프레스"],
            "등": ["랫풀다운", "풀업", "바벨로우", "시티드로우", "데드리프트", "T바로우"],
            "하체": ["스쿼트", "레그프레스", "런지", "레그컬", "레그익스텐션", "카프레이즈"],
            "어깨": ["숄더프레스", "사이드레터럴", "프론트레이즈", "벤트오버레이즈", "페이스풀", "머신숄더프레스"],
            "팔": ["바벨컬", "덤벨컬", "푸쉬다운", "해머컬", "오버헤드익스텐션", "컨센트레이션컬"],
            "복근": ["크런치", "레그레이즈", "플랭크", "러시안트위스트", "싯업", "케이블크런치"]
        };

        function updateExerciseOptions() {
            const part = document.getElementById("bodyPart").value;
            const select = document.getElementById("exerciseName");
            select.innerHTML = "";
            exercises[part].forEach(name => {
                const opt = document.createElement("option");
                opt.value = name;
                opt.text = name;
                select.appendChild(opt);
            });
        }

        window.onload = function() {
            updateExerciseOptions();
        }
    </script>
</head>
<body>
<h2>운동 정보 입력</h2>
<form action="routine" method="post">
    <label>날짜: <input type="date" name="date" required></label><br/>
    <label>근육 부위: 
        <select id="bodyPart" name="category" onchange="updateExerciseOptions()">
            <option value="가슴">가슴</option>
            <option value="등">등</option>
            <option value="하체">하체</option>
            <option value="어깨">어깨</option>
            <option value="팔">팔</option>
            <option value="복근">복근</option>
        </select>
    </label><br/>
    <label>운동 이름: 
        <select id="exerciseName" name="exercise"></select>
    </label><br/>
    <label>무게(kg): <input type="number" name="weight" required></label><br/>
    <label>반복 수: <input type="number" name="reps" required></label><br/>
    <label>세트 수: <input type="number" name="sets" required></label><br/>
    <button type="submit" name="action" value="add">입력</button>
    <button type="submit" name="action" value="delete">삭제</button>
</form>
<br/>
<form action="menu.jsp">
    <button type="submit">메뉴로 돌아가기</button>
</form>
</body>
</html>
