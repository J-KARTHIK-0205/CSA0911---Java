class Calculator {
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}

class ScientificCalculator extends Calculator {
    @Override
    public double multiply(double a, double b) {
        // A more complex calculation: raise a to the power of b's magnitude, scaled
        return Math.pow(a, 2) * b / (a == 0 ? 1 : a);
    }
}

public class Q44_MethodOverridingCalculator {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        ScientificCalculator sciCalc = new ScientificCalculator();

        System.out.println("Calculator multiply: " + calc.multiply(3, 4));
        System.out.println("ScientificCalculator multiply: " + sciCalc.multiply(3, 4));
        System.out.println("Add: " + calc.add(5, 2));
        System.out.println("Subtract: " + calc.subtract(5, 2));
        System.out.println("Divide: " + calc.divide(10, 2));
    }
}
