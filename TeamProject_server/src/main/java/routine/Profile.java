package routine;

public class Profile {
    private String username;
    private boolean gender;
    private int age;
    private float height;
    private float weight;

    public Profile(String username, boolean gender, int age, float height, float weight) {
        this.username = username;
        this.gender   = gender;
        this.age      = age;
        this.height   = height;
        this.weight   = weight;
    }

    public String getUsername()  { return username; }
    public boolean isMale()      { return gender; }
    public String getGenderStr() { return gender ? "남자" : "여자"; }
    public int getAge()          { return age; }
    public float getHeight()     { return height; }
    public float getWeight()     { return weight; }
}
