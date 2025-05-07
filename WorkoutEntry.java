public class WorkoutEntry {
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

    public static WorkoutEntry fromCSV(String[] data, String[] target) {
        Exercise exercise;
        switch (data[0]) {
            case "ChestExercise":
                exercise = new ChestExercise(data[1], target);
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
                exercise = new ChestExercise(data[1], target);
        }
        return new WorkoutEntry(
            exercise,
            Integer.parseInt(data[2]),
            Integer.parseInt(data[3]),
            Integer.parseInt(data[4])
        );
    }
}
