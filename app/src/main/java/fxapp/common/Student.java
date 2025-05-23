package fxapp.common;

public class Student implements Comparable<Student> {
    private int id;
    private String firstName;
    private String lastName;
    private double gpa;
    
    public Student(int id, String firstName, String lastName, double gpa) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    
    // Default compare by ID
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s %s, GPA: %.2f", id, firstName, lastName, gpa);
    }
}
