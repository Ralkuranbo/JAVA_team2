public class ShoulderExercise extends Exercise {
    protected String[] target;

    public ShoulderExercise(String name, String[] target) {
        super(name);
        this.target = target;
    }

    public String[] getTarget() {
        return target;
    }
}
