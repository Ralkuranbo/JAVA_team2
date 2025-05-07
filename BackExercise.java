public class BackExercise extends Exercise {
    protected String[] target;

    public BackExercise(String name, String[] target) {
        super(name);
        this.target = target;
    }

    public String[] getTarget() {
        return target;
    }
}
