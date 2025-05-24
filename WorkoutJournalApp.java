import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class WorkoutJournalApp {
    private static List<WorkoutEntry> entries = new ArrayList<>();
    private static final Map<String, List<Exercise>> exercisesByPart = new LinkedHashMap<>();
    private static User user;
    private static LocalDate date;
    private static Map<String, Integer> targetFrequency = new HashMap<>();
    static final Set<String> ALLTARGETS = new HashSet<>(Arrays.asList(
    	    "대흉근 중부", "삼두근", "대흉근 상부", "어깨 전면", "대흉근 내측", "대흉근 외측", "대흉근 하부",
    	    "어깨 측면", "어깨 후면", "광배근", "상부 등", "중부 등", "하부 등", "둔근",
    	    "대퇴사두근", "햄스트링", "이두근", "전완근", "복직근", "하복부", "코어", "복사근", "복횡근"
    	));

    static {
    	// 가슴
        ChestExercise benchpress = new ChestExercise("벤치 프레스");
        benchpress.target = new String[]{"대흉근 중부", "대흉근 하부", "삼두근"};

        ChestExercise inclineBench = new ChestExercise("인클라인 벤치프레스");
        inclineBench.target = new String[]{"대흉근 상부", "삼두근", "어깨 전면"};

        ChestExercise pushup = new ChestExercise("푸시업");
        pushup.target = new String[]{"대흉근 중부", "대흉근 하부", "삼두근", "어깨 전면"};

        ChestExercise dumbbellFly = new ChestExercise("덤벨 플라이");
        dumbbellFly.target = new String[]{"대흉근 중부", "삼두근"};

        ChestExercise cableCrossover = new ChestExercise("케이블 크로스오버");
        cableCrossover.target = new String[]{"대흉근 내측", "대흉근 외측"};

        // 어깨
        ShoulderExercise militaryPress = new ShoulderExercise("밀리터리 프레스");
        militaryPress.target = new String[]{"어깨 전면", "어깨 측면", "어깨 후면"};

        ShoulderExercise dumbbellShoulderPress = new ShoulderExercise("덤벨 숄더 프레스");
        dumbbellShoulderPress.target = new String[]{"어깨 전면", "어깨 측면", "어깨 후면"};

        ShoulderExercise sideLateralRaise = new ShoulderExercise("사이드 레터럴 레이즈");
        sideLateralRaise.target = new String[]{"어깨 측면"};

        ShoulderExercise frontRaise = new ShoulderExercise("프론트 레이즈");
        frontRaise.target = new String[]{"어깨 전면"};

        ShoulderExercise reverseFly = new ShoulderExercise("리버스 플라이");
        reverseFly.target = new String[]{"어깨 후면"};

        // 등
        BackExercise pullup = new BackExercise("풀업");
        pullup.target = new String[]{"광배근", "상부 등"};

        BackExercise latPulldown = new BackExercise("랫 풀다운");
        latPulldown.target = new String[]{"광배근", "상부 등"};

        BackExercise barbellRow = new BackExercise("바벨 로우");
        barbellRow.target = new String[]{"중부 등", "하부 등"};

        BackExercise deadlift = new BackExercise("데드리프트");
        deadlift.target = new String[]{"하체", "하부 등", "둔근", "코어"};

        BackExercise tBarRow = new BackExercise("티바로우");
        tBarRow.target = new String[]{"중부 등", "상부 등"};

        // 하체
        LegExercise squat = new LegExercise("스쿼트");
        squat.target = new String[]{"대퇴사두근", "햄스트링", "둔근"};

        LegExercise lunge = new LegExercise("런지");
        lunge.target = new String[]{"대퇴사두근", "햄스트링", "둔근"};

        LegExercise legPress = new LegExercise("레그 프레스");
        legPress.target = new String[]{"대퇴사두근", "햄스트링"};

        LegExercise hipThrust = new LegExercise("힙 쓰러스트");
        hipThrust.target = new String[]{"둔근", "햄스트링"};

        LegExercise legCurl = new LegExercise("레그 컬");
        legCurl.target = new String[]{"햄스트링"};

        // 팔
        ArmExercise barbellCurl = new ArmExercise("바벨 컬");
        barbellCurl.target = new String[]{"이두근"};

        ArmExercise dumbbellCurl = new ArmExercise("덤벨 컬");
        dumbbellCurl.target = new String[]{"이두근"};

        ArmExercise pushdown = new ArmExercise("푸시다운");
        pushdown.target = new String[]{"삼두근"};

        ArmExercise dips = new ArmExercise("딥스");
        dips.target = new String[]{"삼두근", "대흉근 하부", "대흉근 중부"};

        ArmExercise hammerCurl = new ArmExercise("해머 컬");
        hammerCurl.target = new String[]{"이두근", "전완근"};

        // 복근
        AbsExercise crunch = new AbsExercise("크런치");
        crunch.target = new String[]{"복직근"};

        AbsExercise legRaise = new AbsExercise("레그 레이즈");
        legRaise.target = new String[]{"하복부"};

        AbsExercise plank = new AbsExercise("플랭크");
        plank.target = new String[]{"코어", "복직근", "복사근"};

        AbsExercise bicycle = new AbsExercise("바이시클 크런치");
        bicycle.target = new String[]{"복직근", "복사근"};

        AbsExercise hipDips = new AbsExercise("힙 딥스");
        hipDips.target = new String[]{"복직근", "복사근", "복횡근"};
        
        exercisesByPart.put("가슴", List.of(benchpress, inclineBench, pushup, dumbbellFly, cableCrossover));
        exercisesByPart.put("어깨", List.of(militaryPress, dumbbellShoulderPress, sideLateralRaise, frontRaise, reverseFly));
        exercisesByPart.put("등", List.of(pullup, latPulldown, barbellRow, deadlift, tBarRow));
        exercisesByPart.put("하체", List.of(squat, lunge, legPress, hipThrust, legCurl));
        exercisesByPart.put("팔", List.of(barbellCurl, dumbbellCurl, pushdown, dips, hammerCurl));
        exercisesByPart.put("복근", List.of(crunch, legRaise, plank, bicycle, hipDips));
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("[운동 일지 관리]");
            System.out.println("1. 운동 추가");
            System.out.println("2. 사용자 정보 입력");
            System.out.println("3. 파일 저장");
            System.out.println("4. 파일 불러오기");
            System.out.println("5. 통계");
            System.out.println("6. 부족 부위 추천");
            System.out.println("0. 종료");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addWorkout(scanner);
                case 2 -> userInfo(scanner);
                case 3 -> saveToFile(scanner);
                case 4 -> loadFromFile(scanner);
                case 5 -> showStatistics();
                case 6 -> recommendUndertrained();
                case 0 -> {
                    System.out.println("프로그램 종료");
                    return;
                }
            }
        }
    }

    private static void addWorkout(Scanner scanner) {
        System.out.println("운동 부위를 선택하세요:");
        int idx = 1;
        List<String> parts = new ArrayList<>(exercisesByPart.keySet());
        for (String part : parts) {
            System.out.println(idx++ + ") " + part);
        }
        int partChoice = scanner.nextInt();
        scanner.nextLine();

        String selectedPart = parts.get(partChoice - 1);
        List<Exercise> list = exercisesByPart.get(selectedPart);

        System.out.println("운동을 선택하세요:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ") " + list.get(i).getName());
        }
        int exChoice = scanner.nextInt();
        scanner.nextLine();

        Exercise exercise = list.get(exChoice - 1);

        System.out.print("무게(kg): ");
        int weight = scanner.nextInt();
        System.out.print("횟수: ");
        int reps = scanner.nextInt();
        System.out.print("세트 수: ");
        int sets = scanner.nextInt();
        /*
        System.out.print("목표 무게: ");
        int goalWeight = scanner.nextInt();
        System.out.print("목표 횟수: ");
        int goalReps = scanner.nextInt();
        */
        
        date=LocalDate.now();
        WorkoutEntry entry = new WorkoutEntry(exercise, weight, reps, sets, date);
        entries.add(entry);
        
        String[] targets = exercise.target;
        if (targets != null) {
            for (String t : targets) {
                targetFrequency.put(t, targetFrequency.getOrDefault(t, 0) + 1);
            }
        }

        //System.out.println("운동이 추가되었습니다. 목표 달성 여부: " + (entry.isGoalAchieved() ? "성공" : "실패"));
    }
    
    private static void userInfo(Scanner scanner) {
    	// 키, 성별, 나이 등 사용자 정보 입력받기 
    	// 몸무게, 키를 숫자가 아닌 다른 형태로 입력했을시 예외처리
    	int age;
    	float weight, height;
    	String gender;
    	boolean gend;
    	System.out.println("[사용자 정보 입력]");
    	System.out.println(" 사용자의 성별을 입력해주세요(M/F): ");
    	gender = scanner.next();
    	gender = gender.toUpperCase();
    	if(gender.equals("M") || gender.equals("MALE"))
    		gend=true;
    	else if(gender.equals("F") || gender.equals("FEMALE"))
    		gend=false;
    	else {
    		System.out.println("오류 : 잘못된 성별입니다.");
    		return;
    	}
    	System.out.println(" 사용자의 나이를 입력해주세요: ");
    	age=scanner.nextInt();
    	if(age <= 0) {
    		System.out.println("오류 : 잘못된 나이입니다.");
    		return;
    	}
    	System.out.println(" 사용자의 몸무게(kg)를 입력해주세요: ");
    	weight=scanner.nextFloat();
    	if(weight <= 0) {
    		System.out.println("오류 : 잘못된 몸무게입니다.");
    		return;
    	}
    	System.out.println(" 사용자의 키(cm)를 입력해주세요: ");
    	height=scanner.nextFloat();
    	if(height<= 0) {
    		System.out.println("오류 : 잘못된 키입니다.");
    		return;
    	}
    	user = new User(gend, age, height, weight);
    }

    private static void saveToFile(Scanner scanner) throws IOException {
        String fileName = "운동 일지";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
        writer.write(""+ user.getGen() + ","+ user.getAge() + "," + user.getHeight() +","+ user.getWeight());
        writer.newLine();
        for (WorkoutEntry entry : entries) {
            writer.write(entry instanceof WorkoutEntry ? ((WorkoutEntry) entry).toCSV() : entry.toCSV());
            writer.newLine();
        }
        writer.close();
        System.out.println("저장 완료!");
    }

    private static void loadFromFile(Scanner scanner) throws IOException {
        String fileName = "운동 일지";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        entries.clear();
        String line;
        String data[];
        line=reader.readLine();
        data=line.split(",");
        boolean gend=data[0].equals("남자")?true:false;
        int age = Integer.parseInt(data[1]);
        float height = Float.parseFloat(data[2]);
        float weight = Float.parseFloat(data[3]);
        user = new User(gend, age, height, weight);
        while ((line = reader.readLine()) != null) {
            data = line.split(",");
            if (data.length > 5) {
                entries.add(WorkoutEntry.fromCSV(data));
            } else {
                entries.add(WorkoutEntry.fromCSV(data));
            }
        }
        reader.close();
        System.out.println("불러오기 완료!");
    }

    private static void showStatistics() {
        if (user == null) {
            System.out.println("사용자 정보가 설정되지 않았습니다. 먼저 사용자 정보를 입력해주세요.");
            return;
        }

        // 사용자 기본 정보 출력
        System.out.println("\n[사용자 정보]");
        System.out.println("성별: " + user.getGen());
        System.out.println("나이: " + user.getAge());
        System.out.println("키: " + user.getHeight() + "cm");
        System.out.println("몸무게: " + user.getWeight() + "kg");
        System.out.println("BMI: " + user.getBMI());

        // 날짜별 운동 기록 정리
        Map<LocalDate, List<WorkoutEntry>> entriesByDate = new TreeMap<>(); // 날짜 순 정렬을 위해 TreeMap 사용
        for (WorkoutEntry entry : entries) {
            LocalDate date = entry.getDate();
            entriesByDate.putIfAbsent(date, new ArrayList<>());
            entriesByDate.get(date).add(entry);
        }

        System.out.println("\n[날짜별 운동 기록]");
        for (Map.Entry<LocalDate, List<WorkoutEntry>> dateEntry : entriesByDate.entrySet()) {
            System.out.println(dateEntry.getKey());
            for (WorkoutEntry entry : dateEntry.getValue()) {
                System.out.printf("- [%s] %dkg x %d회 x %d세트 (총 중량: %dkg)\n",
                    entry.getExercise().getName(),
                    entry.getWeight(),
                    entry.getReps(),
                    entry.getSets(),
                    entry.getTotalWeight()
                );
            }
        }
        System.out.println("");
    }

    private static void recommendUndertrained() {
    	int minCount = Integer.MAX_VALUE;
        for (String target : ALLTARGETS) {
            int count = targetFrequency.getOrDefault(target, 0);
            if (count < minCount) {
                minCount = count;
            }
        }

        // 최소 카운트를 가진 모든 부위를 모은다
        List<String> leastTrained = new ArrayList<>();
        for (String target : ALLTARGETS) {
            if (targetFrequency.getOrDefault(target, 0) == minCount) {
                leastTrained.add(target);
            }
        }

        // 결과 출력
        System.out.println("가장 적게 훈련한 부위:");
        for (String part : leastTrained) {
            System.out.println("- " + part + " (횟수: " + targetFrequency.getOrDefault(part, 0) + ")");
        }
    }
}