class Employee {
    protected String name;
    protected int id;
    protected double salary;

    public Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public void showDetails() {
        System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}

class Manager extends Employee {
    private int teamSize;

    public Manager(String name, int id, double salary, int teamSize) {
        super(name, id, salary);
        this.teamSize = teamSize;
    }

    public void manageTeam() {
        System.out.println(name + " is managing a team of " + teamSize + " people.");
    }
}

class SalesPerson extends Employee {
    private double salesTarget;

    public SalesPerson(String name, int id, double salary, double salesTarget) {
        super(name, id, salary);
        this.salesTarget = salesTarget;
    }

    public void makeSale(double amount) {
        System.out.println(name + " made a sale of $" + amount + " (target: $" + salesTarget + ")");
    }
}

public class Q33_EmployeeManagementInheritance {
    public static void main(String[] args) {
        Manager manager = new Manager("Alice", 101, 75000, 8);
        SalesPerson salesPerson = new SalesPerson("Bob", 102, 55000, 20000);

        manager.showDetails();
        manager.manageTeam();

        salesPerson.showDetails();
        salesPerson.makeSale(2500);
    }
}
