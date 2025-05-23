package fxapp.common;

import java.util.Comparator;

public class StudentComparators {
    public static final Comparator<Student> BY_ID = Comparator.comparingInt(Student::getId);
    public static final Comparator<Student> BY_FIRST_NAME = Comparator.comparing(Student::getFirstName);
    public static final Comparator<Student> BY_LAST_NAME = Comparator.comparing(Student::getLastName);
    public static final Comparator<Student> BY_GPA = Comparator.comparingDouble(Student::getGpa);
}
