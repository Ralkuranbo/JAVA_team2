public class LegExercise extends Exercise {
    protected String[] target;

    public LegExercise(String name, String[] target) {
        super(name);
        this.target = target;
    }

    public String[] getTarget() {
        return target;
    }
}
