class User{
	private int age;
	private float weight;
	private boolean gender;
	private float height;
	
	public User(int age, float weight, boolean gender, float height) {
		this.age=age;
		this.weight=weight;
		this.gender=gender;
		this.height=height;
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
	
	public boolean getGen() {
		return gender;
	}
}
