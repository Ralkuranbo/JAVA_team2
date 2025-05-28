package routine;

public class User {
    private int age;
    private float weight;
    private boolean gender;
    private float height;
    private String username;
    private String password;

    public User(boolean gender, int age, float height, float weight) {
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.height = height;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBMI() {
        double bmi = 1.3 * (weight / Math.pow(height / 100.0, 2.5));
        return Math.round(bmi * 10) / 10.0;
    }

    public int getAge() {
        return age;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public String getGen() {
        return gender ? "남자" : "여자";
    }
}