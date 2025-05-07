import java.util.*;
import java.io.*;

public class WorkoutJournalApp {
    private static List<WorkoutEntry> entries = new ArrayList<>();
    private static final Map<String, List<Exercise>> exercisesByPart = new LinkedHashMap<>();

    static {
    	// 가슴 운동들
        ChestExercise benchpress = new ChestExercise("벤치 프레스", new String[]{"대흉근", "삼두근"});
        ChestExercise inclineBenchpress = new ChestExercise("인클라인 벤치프레스", new String[]{"대흉근 상부", "삼두근", "어깨 전면"});
        ChestExercise pushup = new ChestExercise("푸시업", new String[]{"대흉근", "삼두근", "어깨 전면"});
        ChestExercise dumbbellFly = new ChestExercise("덤벨 플라이", new String[]{"대흉근", "어깨 전면", "삼두근"});
        ChestExercise cableCrossover = new ChestExercise("케이블 크로스오버", new String[]{"대흉근 내측", "대흉근 외측"});

        // 어깨 운동들
        ShoulderExercise militaryPress = new ShoulderExercise("밀리터리 프레스", new String[]{"어깨 전체", "어깨 전면", "어깨 측면"});
        ShoulderExercise dumbbellShoulderPress = new ShoulderExercise("덤벨 숄더 프레스", new String[]{"어깨 전체", "어깨 전면", "어깨 측면"});
        ShoulderExercise sideLateralRaise = new ShoulderExercise("사이드 레터럴 레이즈", new String[]{"어깨 측면"});
        ShoulderExercise frontRaise = new ShoulderExercise("프론트 레이즈", new String[]{"어깨 전면"});
        ShoulderExercise reverseFly = new ShoulderExercise("리버스 플라이", new String[]{"어깨 후면", "상부 등"});

        // 등 운동들
        BackExercise pullup = new BackExercise("풀업", new String[]{"광배근", "상부 등", "중부 등"});
        BackExercise latPulldown = new BackExercise("랫 풀다운", new String[]{"광배근", "상부 등"});
        BackExercise barbellRow = new BackExercise("바벨 로우", new String[]{"중부 등", "상부 등", "하부 등"});
        BackExercise deadlift = new BackExercise("데드리프트", new String[]{"하체", "하부 등", "허리", "엉덩이"});
        BackExercise tBarRow = new BackExercise("티바로우", new String[]{"상부 등", "중부 등"});

        // 하체 운동들
        LegExercise squat = new LegExercise("스쿼트", new String[]{"대퇴사두근", "햄스트링", "엉덩이", "허리"});
        LegExercise lunge = new LegExercise("런지", new String[]{"대퇴사두근", "햄스트링", "엉덩이"});
        LegExercise legPress = new LegExercise("레그 프레스", new String[]{"대퇴사두근", "햄스트링", "엉덩이"});
        LegExercise hipThrust = new LegExercise("힙 쓰러스트", new String[]{"엉덩이", "대퇴사두근", "햄스트링"});
        LegExercise legCurl = new LegExercise("레그 컬", new String[]{"햄스트링"});

        // 팔 운동들
        ArmExercise barbellCurl = new ArmExercise("바벨 컬", new String[]{"이두근"});
        ArmExercise dumbbellCurl = new ArmExercise("덤벨 컬", new String[]{"이두근"});
        ArmExercise tricepsPushdown = new ArmExercise("트라이셉스 푸시다운", new String[]{"삼두근"});
        ArmExercise dips = new ArmExercise("딥스", new String[]{"삼두근", "가슴", "어깨"});
        ArmExercise hammerCurl = new ArmExercise("해머 컬", new String[]{"이두근", "팔꿈치", "전완근"});

        // 복근 운동들
        AbsExercise crunch = new AbsExercise("크런치", new String[]{"복직근"});
        AbsExercise legRaise = new AbsExercise("레그 레이즈", new String[]{"하복부", "엉덩이"});
        AbsExercise plank = new AbsExercise("플랭크", new String[]{"코어", "복근", "허리"});
        AbsExercise bicycleCrunch = new AbsExercise("바이시클 크런치", new String[]{"복직근", "복사근"});
        AbsExercise hipDips = new AbsExercise("힙 딥스", new String[]{"하복부", "엉덩이", "허리"});
        
        exercisesByPart.put("가슴", List.of(benchpress, inclineBenchpress, pushup, dumbbellFly, cableCrossover));
        exercisesByPart.put("어깨", List.of(militaryPress, dumbbellShoulderPress, sideLateralRaise, frontRaise, reverseFly));
        exercisesByPart.put("등", List.of(pullup, latPulldown, barbellRow, deadlift, tBarRow));
        exercisesByPart.put("하체", List.of(squat, lunge, legPress, hipThrust, legCurl));
        exercisesByPart.put("팔", List.of(barbellCurl, dumbbellCurl, tricepsPushdown, dips, hammerCurl));
        exercisesByPart.put("복근", List.of(crunch, legRaise, plank, bicycleCrunch, hipDips));
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
        System.out.print("목표 무게: ");
        int goalWeight = scanner.nextInt();
        System.out.print("목표 횟수: ");
        int goalReps = scanner.nextInt();

        GoalWorkoutEntry entry = new GoalWorkoutEntry(exercise, weight, reps, sets, goalWeight, goalReps);
        entries.add(entry);

        System.out.println("운동이 추가되었습니다. 목표 달성 여부: " + (entry.isGoalAchieved() ? "성공" : "실패"));
    }
    
    private static void userInfo(Scanner scanner) {
    	// 키, 성별, 나이 등 사용자 정보 입력받기 
    }

    private static void saveToFile(Scanner scanner) throws IOException {
        System.out.print("저장할 파일 이름을 입력하세요: ");
        String fileName = scanner.next();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
        for (WorkoutEntry entry : entries) {
            writer.write(entry instanceof GoalWorkoutEntry ? ((GoalWorkoutEntry) entry).toCSV() : entry.toCSV());
            writer.newLine();
        }
        writer.close();
        System.out.println("저장 완료!");
    }

    private static void loadFromFile(Scanner scanner) throws IOException {
        System.out.print("불러올 파일 이름을 입력하세요: ");
        String fileName = scanner.next();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        entries.clear();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length > 5) {
                entries.add(GoalWorkoutEntry.fromCSV(data));
            } else {
                entries.add(WorkoutEntry.fromCSV(data));
            }
        }
        reader.close();
        System.out.println("불러오기 완료!");
    }

    private static void showStatistics() {
        Map<String, Integer> partWeightSum = new HashMap<>();
        for (WorkoutEntry entry : entries) {
            String part = entry.getExercise().getClass().getSimpleName();
            partWeightSum.put(part, partWeightSum.getOrDefault(part, 0) + entry.getTotalWeight());
        }
        System.out.println("\n[운동 통계: 부위별 총 중량]");
        for (Map.Entry<String, Integer> entry : partWeightSum.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "kg");
        }
    }

    private static void recommendUndertrained() {
        Map<String, Integer> partCount = new HashMap<>();
        for (WorkoutEntry entry : entries) {
            String part = entry.getExercise().getClass().getSimpleName();
            partCount.put(part, partCount.getOrDefault(part, 0) + 1);
        }
        String leastTrained = partCount.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("정보 없음");

        System.out.println("가장 적게 훈련한 부위는: " + leastTrained);
    }
}
