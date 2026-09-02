class MenuItem {
    protected String name;
    protected double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Appetizer extends MenuItem {
    public Appetizer(String name, double price) {
        super(name, price);
    }

    public void order() {
        System.out.println("Ordered appetizer: " + name + " ($" + price + ")");
    }

    public void serve() {
        System.out.println("Serving appetizer: " + name);
    }
}

class Entree extends MenuItem {
    public Entree(String name, double price) {
        super(name, price);
    }

    public void order() {
        System.out.println("Ordered entree: " + name + " ($" + price + ")");
    }

    public void serve() {
        System.out.println("Serving entree: " + name);
    }
}

public class Q38_RestaurantManagementInheritance {
    public static void main(String[] args) {
        Appetizer appetizer = new Appetizer("Spring Rolls", 6.5);
        Entree entree = new Entree("Grilled Salmon", 18.0);

        appetizer.order();
        appetizer.serve();

        entree.order();
        entree.serve();
    }
}
