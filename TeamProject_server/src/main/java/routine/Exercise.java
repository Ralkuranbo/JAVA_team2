package routine; 

public abstract class Exercise {
    protected String name;
    protected String[] target;

    public Exercise(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}