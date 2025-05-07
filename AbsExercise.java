public class AbsExercise extends Exercise {
    protected String[] target;

    public AbsExercise(String name, String[] target) {
        super(name);
        this.target = target;
    }

    public String[] getTarget() {
        return target;
    }
}
