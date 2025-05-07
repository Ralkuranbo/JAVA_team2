public class ArmExercise extends Exercise {
    protected String[] target;

    public ArmExercise(String name, String[] target) {
        super(name);
        this.target = target;
    }

    public String[] getTarget() {
        return target;
    }
}
