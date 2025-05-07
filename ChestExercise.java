public class ChestExercise extends Exercise {
    protected String[] target;

    public ChestExercise(String name, String[] target) {
        super(name);
        this.target = target;
    }

    public String[] getTarget() {
        return target;
    }
}
