import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WorkoutJournal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "workout_log.csv";
    private static final List<WorkoutEntry> log = new ArrayList<>();
    private static final Map<String, List<String>> workoutMap = new HashMap<>() {{
        put("가슴", List.of("벤치프레스", "체스트플라이", "딥스"));
        put("등", List.of("랫풀다운", "바벨로우", "데드리프트"));
        put("하체", List.of("스쿼트", "레그프레스", "런지"));
        put("어깨", List.of("숄더프레스", "사이드레터럴레이즈", "프론트레이즈"));
    }};

    public static void main(String[] args) {
        loadLogFromFile();
        while (true) {
            System.out.println("\n1. 운동 기록  2. 통계 보기  3. 부족 부위 추천  4. 저장  5. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> recordWorkout();
                case 2 -> showStatistics();
                case 3 -> suggestWeakPart();
                case 4 -> saveLogToFile();
                case 5 -> {
                    saveLogToFile();
                    return;
                }
                default -> System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void recordWorkout() {
        System.out.print("운동 날짜 (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        String part = selectPart();
        String name = selectWorkout(part);

        System.out.print("무게 (kg): ");
        int weight = scanner.nextInt();
        System.out.print("횟수: ");
        int reps = scanner.nextInt();
        System.out.print("세트 수: ");
        int sets = scanner.nextInt();

        System.out.print("목표 무게 (kg): ");
        int goalWeight = scanner.nextInt();
        System.out.print("목표 횟수: ");
        int goalReps = scanner.nextInt();
        scanner.nextLine();

        GoalWorkoutEntry entry = new GoalWorkoutEntry(date, part, name, weight, reps, sets, goalWeight, goalReps);
        log.add(entry);

        System.out.println(entry.isGoalAchieved() ? "🎯 목표 달성!" : "❗ 목표 미달성!");
    }

    private static String selectPart() {
        System.out.println("운동 부위를 선택하세요:");
        List<String> parts = new ArrayList<>(workoutMap.keySet());
        for (int i = 0; i < parts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, parts.get(i));
        }
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        return parts.get(index);
    }

    private static String selectWorkout(String part) {
        List<String> workouts = workoutMap.get(part);
        System.out.println("운동 종목을 선택하세요:");
        for (int i = 0; i < workouts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, workouts.get(i));
        }
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        return workouts.get(index);
    }

    private static void showStatistics() {
        new Thread(() -> {
            Map<String, List<WorkoutEntry>> grouped = new HashMap<>();
            for (WorkoutEntry e : log) {
                grouped.computeIfAbsent(e.part, k -> new ArrayList<>()).add(e);
            }
            System.out.println("📊 통계");
            for (String part : grouped.keySet()) {
                List<WorkoutEntry> entries = grouped.get(part);
                double avgWeight = entries.stream().mapToInt(e -> e.weight).average().orElse(0);
                int totalWeight = entries.stream().mapToInt(WorkoutEntry::getTotalWeight).sum();
                System.out.printf("- %s: 평균 무게 %.1fkg, 총 중량 %dkg\n", part, avgWeight, totalWeight);
            }
        }).start();
    }

    private static void suggestWeakPart() {
        Map<String, Integer> totals = new HashMap<>();
        for (WorkoutEntry entry : log) {
            totals.put(entry.part, totals.getOrDefault(entry.part, 0) + entry.getTotalWeight());
        }
        Optional<Map.Entry<String, Integer>> weakest = totals.entrySet().stream()
                .min(Map.Entry.comparingByValue());
        if (weakest.isPresent()) {
            System.out.printf("📉 부족한 부위: %s (총 중량 %dkg)\n",
                    weakest.get().getKey(), weakest.get().getValue());
        } else {
            System.out.println("기록이 부족합니다.");
        }
    }

    private static void saveLogToFile() {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME), "UTF-8"))) {
            for (WorkoutEntry entry : log) {
                writer.printf("%s,%s,%s,%d,%d,%d\n",
                        entry.date, entry.part, entry.name, entry.weight, entry.reps, entry.sets);
            }
            System.out.println("✅ 저장 완료");
        } catch (IOException e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
    }

    private static void loadLogFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] t = line.split(",");
                LocalDate date = LocalDate.parse(t[0]);
                String part = t[1];
                String name = t[2];
                int weight = Integer.parseInt(t[3]);
                int reps = Integer.parseInt(t[4]);
                int sets = Integer.parseInt(t[5]);
                log.add(new WorkoutEntry(date, part, name, weight, reps, sets));
            }
        } catch (IOException e) {
            System.out.println("파일 불러오기 오류: " + e.getMessage());
        }
    }
}

class WorkoutEntry {
    LocalDate date;
    String part;
    String name;
    int weight;
    int reps;
    int sets;

    public WorkoutEntry(LocalDate date, String part, String name, int weight, int reps, int sets) {
        this.date = date;
        this.part = part;
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public int getTotalWeight() {
        return weight * reps * sets;
    }

    public String toString() {
        return String.format("[%s] %s - %s: %dkg x %d회 x %d세트", date, part, name, weight, reps, sets);
    }
}

class GoalWorkoutEntry extends WorkoutEntry {
    int goalWeight;
    int goalReps;

    public GoalWorkoutEntry(LocalDate date, String part, String name,
                            int weight, int reps, int sets,
                            int goalWeight, int goalReps) {
        super(date, part, name, weight, reps, sets);
        this.goalWeight = goalWeight;
        this.goalReps = goalReps;
    }

    public boolean isGoalAchieved() {
        return weight >= goalWeight && reps >= goalReps;
    }

    @Override
    public String toString() {
        return super.toString() + (isGoalAchieved() ? " ✅ 목표 달성!" : " ❌ 미달성");
    }
}
