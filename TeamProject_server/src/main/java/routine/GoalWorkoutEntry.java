package routine; 

import java.time.LocalDate;

public class GoalWorkoutEntry extends WorkoutEntry {
    private int goalWeight;
    private int goalReps;

    public GoalWorkoutEntry(Exercise exercise, int weight, int reps, int sets, int goalWeight, int goalReps, LocalDate date) {
        super(exercise, weight, reps, sets, date);
        this.goalWeight = goalWeight;
        this.goalReps = goalReps;
    }

    public boolean isGoalAchieved() {
        return getWeight() >= goalWeight && getReps() >= goalReps;
    }

    @Override
    public String toCSV() {
        return super.getDate() + "," + super.toCSV() + "," + goalWeight + "," + goalReps;
    }

    public static GoalWorkoutEntry fromCSV(String[] data, String[] target) {
        WorkoutEntry base = WorkoutEntry.fromCSV(data);
        return new GoalWorkoutEntry(
                base.getExercise(),
                base.getWeight(),
                base.getReps(),
                base.getSets(),
                Integer.parseInt(data[5]),
                Integer.parseInt(data[6]),
                base.getDate()
        );
    }
}