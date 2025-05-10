import java.time.LocalDate;
class WorkoutEntry {
    private Exercise exercise;
    private int weight;
    private int reps;
    private int sets;
    private LocalDate date;

    public WorkoutEntry(Exercise exercise, int weight, int reps, int sets, LocalDate date) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
        this.date=date;
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
    
    public LocalDate getDate() {
    	return date;
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

    public static WorkoutEntry fromCSV(String[] data) {
        Exercise exercise;
        LocalDate date = LocalDate.parse(data[0]); // 날짜 파싱
        switch (data[1]) {
            case "ChestExercise": 
                exercise = new ChestExercise(data[2]);
                break;
            case "BackExercise":
                exercise = new BackExercise(data[2]);
                break;
            case "LegExercise":
                exercise = new LegExercise(data[2]);
                break;
            case "ShoulderExercise":
                exercise = new ShoulderExercise(data[2]);
                break;
            case "ArmExercise":
                exercise = new ArmExercise(data[2]);
                break;
            case "AbsExercise":
                exercise = new AbsExercise(data[2]);
                break;
            default:
                exercise = new ChestExercise(data[2]); // fallback
        }

        return new WorkoutEntry(
                exercise,
                Integer.parseInt(data[3]), // weight
                Integer.parseInt(data[4]), // reps
                Integer.parseInt(data[5]), // sets
                date
        );
    }
}
