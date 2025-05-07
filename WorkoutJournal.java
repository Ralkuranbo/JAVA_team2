// 전체 운동을 부모 클래스로 추상화
abstract class Exercise {
    protected String name;

    public Exercise(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// 부위별 운동 클래스
class ChestExercise extends Exercise {
	protected String[] target;
    public ChestExercise(String name, String[] target) {
        super(name);
        this.target=target;
    }
    
    public String[] getTarget() {
    	return target;
    }
}

class BackExercise extends Exercise {
	protected String[] target;
    public BackExercise(String name, String[] target) {
        super(name);
        this.target=target;
    }
    
    public String[] getTarget() {
    	return target;
    }
}

class LegExercise extends Exercise {
	protected String[] target;
    public LegExercise(String name, String[] target) {
        super(name);
        this.target=target;
    }
    
    public String[] getTarget() {
    	return target;
    }
}

class ShoulderExercise extends Exercise {
	protected String[] target;
    public ShoulderExercise(String name, String[] target) {
        super(name);
        this.target=target;
    }
    
    public String[] getTarget() {
    	return target;
    }
}

class ArmExercise extends Exercise {
	protected String[] target;
    public ArmExercise(String name, String[] target) {
        super(name);
        this.target=target;
    }
    
    public String[] getTarget() {
    	return target;
    }
}

class AbsExercise extends Exercise {
	protected String[] target;
    public AbsExercise(String name, String[] target) {
        super(name);
        this.target=target;
    }
    
    public String[] getTarget() {
    	return target;
    }
}

// WorkoutEntry 클래스에서 Exercise 사용
class WorkoutEntry {
    private Exercise exercise;
    private int weight;
    private int reps;
    private int sets;

    public WorkoutEntry(Exercise exercise, int weight, int reps, int sets) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getTotalWeight() {
        return weight * reps * sets;
    }

    public String toCSV() {
        return String.join(",",
                exercise.getClass().getSimpleName(),
                exercise.getName(),
                String.valueOf(weight),
                String.valueOf(reps),
                String.valueOf(sets)
        );
    }

    public static WorkoutEntry fromCSV(String[][] data) {
        Exercise exercise;
        switch (data[0]) {
            case "ChestExercise": 
                exercise = new ChestExercise(data[1], data[2]);
                break;
            case "BackExercise":
                exercise = new BackExercise(data[1], target);
                break;
            case "LegExercise":
                exercise = new LegExercise(data[1], target);
                break;
            case "ShoulderExercise":
                exercise = new ShoulderExercise(data[1], target);
                break;
            case "ArmExercise":
                exercise = new ArmExercise(data[1], target);
                break;
            case "AbsExercise":
                exercise = new AbsExercise(data[1], target);
                break;
            default:
                exercise = new ChestExercise(data[1], target); // fallback
        }
        return new WorkoutEntry(exercise,
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Integer.parseInt(data[4])
        );
    }
}

// GoalWorkoutEntry는 그대로 유지
class GoalWorkoutEntry extends WorkoutEntry {
    private int goalWeight;
    private int goalReps;

    public GoalWorkoutEntry(Exercise exercise, int weight, int reps, int sets, int goalWeight, int goalReps) {
        super(exercise, weight, reps, sets);
        this.goalWeight = goalWeight;
        this.goalReps = goalReps;
    }

    public boolean isGoalAchieved() {
        return getWeight() >= goalWeight && getReps() >= goalReps;
    }

    @Override
    public String toCSV() {
        return super.toCSV() + "," + goalWeight + "," + goalReps;
    }

    public static GoalWorkoutEntry fromCSV(String[] data, String[] target) {
        WorkoutEntry base = WorkoutEntry.fromCSV(data, target);
        return new GoalWorkoutEntry(
                base.getExercise(),
                base.getWeight(),
                base.getReps(),
                base.getSets(),
                Integer.parseInt(data[5]),
                Integer.parseInt(data[6])
        );
    }
}

// 메인 클래스와 UI 로직에서 Exercise 클래스 기반으로 선택하도록 수정 필요
// 예시: Map<String, List<Exercise>> 부위별 운동 맵 구성

// 기타 클래스 및 로직은 이전 코드 유지하되, Exercise 클래스와 상속 구조를 반영하여 업데이트

