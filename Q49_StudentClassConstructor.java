public class Q49_StudentClassConstructor {
    public static void main(String[] args) {
        int[] grades = {85, 92, 78, 90, 88};
        Student student = new Student("Sam", "S1001", grades);
        System.out.printf("Average grade: %.2f%n", student.calculateAverage());
        System.out.println("Letter grade: " + student.getLetterGrade());
    }
}

class Student {
    private String name;
    private String id;
    private int[] grades;

    public Student(String name, String id, int[] grades) {
        this.name = name;
        this.id = id;
        this.grades = grades;
    }

    public double calculateAverage() {
        int sum = 0;
        for (int g : grades) {
            sum += g;
        }
        return (double) sum / grades.length;
    }

    public String getLetterGrade() {
        double avg = calculateAverage();
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }
}
