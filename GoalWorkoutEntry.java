import java.time.LocalDate;
class GoalWorkoutEntry extends WorkoutEntry {
    private int goalWeight;
    private int goalReps;
    private LocalDate date;

    public GoalWorkoutEntry(Exercise exercise, int weight, int reps, int sets, int goalWeight, int goalReps) {
        super(exercise, weight, reps, sets);
        this.goalWeight = goalWeight;
        this.goalReps = goalReps;
        this.date = LocalDate.now();
    }

    public boolean isGoalAchieved() {
        return getWeight() >= goalWeight && getReps() >= goalReps;
    }

    @Override
    public String toCSV() {
        return date + "," + super.toCSV() + "," + goalWeight + "," + goalReps;
    }

    public static GoalWorkoutEntry fromCSV(String[] data, String[] target) {
        WorkoutEntry base = WorkoutEntry.fromCSV(data);
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