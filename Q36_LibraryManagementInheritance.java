class Item {
    protected String title;
    protected String author;
    protected boolean borrowed;

    public Item(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }

    public void borrowItem() {
        borrowed = true;
        System.out.println("\"" + title + "\" has been borrowed.");
    }

    public void returnItem() {
        borrowed = false;
        System.out.println("\"" + title + "\" has been returned.");
    }
}

class Book extends Item {
    private int pages;

    public Book(String title, String author, int pages) {
        super(title, author);
        this.pages = pages;
    }

    public void showDetails() {
        System.out.println("Book: " + title + " by " + author + " (" + pages + " pages)");
    }
}

class DVD extends Item {
    private int durationMinutes;

    public DVD(String title, String author, int durationMinutes) {
        super(title, author);
        this.durationMinutes = durationMinutes;
    }

    public void showDetails() {
        System.out.println("DVD: " + title + " by " + author + " (" + durationMinutes + " minutes)");
    }
}

public class Q36_LibraryManagementInheritance {
    public static void main(String[] args) {
        Book book = new Book("Effective Java", "Joshua Bloch", 412);
        DVD dvd = new DVD("The Matrix", "Wachowski Sisters", 136);

        book.showDetails();
        book.borrowItem();
        book.returnItem();

        dvd.showDetails();
        dvd.borrowItem();
        dvd.returnItem();
    }
}
