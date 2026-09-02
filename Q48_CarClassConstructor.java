public class Q48_CarClassConstructor {
    public static void main(String[] args) {
        Car car = new Car("Toyota", "Corolla", 2024);
        car.printDetails();
    }
}

class Car {
    private String make;
    private String model;
    private int year;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public void printDetails() {
        System.out.println("Make: " + make + ", Model: " + model + ", Year: " + year);
    }
}
