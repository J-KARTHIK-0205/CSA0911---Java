class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Student extends Person {
    private String major;
    private double gpa;

    public Student(String name, int age, String major, double gpa) {
        super(name, age);
        this.major = major;
        this.gpa = gpa;
    }

    public void manageCoursework() {
        System.out.println(name + " (Student, " + major + ") is managing coursework. GPA: " + gpa);
    }
}

class Professor extends Person {
    private String department;

    public Professor(String name, int age, String department) {
        super(name, age);
        this.department = department;
    }

    public void teachClass(String course) {
        System.out.println(name + " (Professor, " + department + ") is teaching " + course + ".");
    }
}

public class Q37_UniversityManagementInheritance {
    public static void main(String[] args) {
        Student student = new Student("Emily", 20, "Computer Science", 3.8);
        Professor professor = new Professor("Dr. Carter", 45, "Mathematics");

        student.manageCoursework();
        professor.teachClass("Linear Algebra");
    }
}
