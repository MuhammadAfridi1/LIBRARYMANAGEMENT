import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Catalog<LibraryItem> catalog = new Catalog<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Library Catalog Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Add DVD");
            System.out.println("3. Add Magazine");
            System.out.println("4. Remove Item");
            System.out.println("5. View Item");
            System.out.println("6. Display All Items");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String bookAuthor = scanner.nextLine();
                    System.out.print("Enter book item ID: ");
                    int bookItemID = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    Book newBook = new Book(bookTitle, bookAuthor, bookItemID);
                    catalog.addItem(newBook);
                    System.out.println("Book added.");
                    break;

                case 2:
                    System.out.print("Enter DVD title: ");
                    String dvdTitle = scanner.nextLine();
                    System.out.print("Enter DVD director: ");
                    String dvdDirector = scanner.nextLine();
                    System.out.print("Enter DVD item ID: ");
                    int dvdItemID = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    DVD newDVD = new DVD(dvdTitle, dvdDirector, dvdItemID);
                    catalog.addItem(newDVD);
                    System.out.println("DVD added.");
                    break;

                case 3:
                    System.out.print("Enter magazine title: ");
                    String magTitle = scanner.nextLine();
                    System.out.print("Enter magazine issue: ");
                    String magIssue = scanner.nextLine();
                    System.out.print("Enter magazine item ID: ");
                    int magItemID = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    Magazine newMagazine = new Magazine(magTitle, magIssue, magItemID);
                    catalog.addItem(newMagazine);
                    System.out.println("Magazine added.");
                    break;

                case 4:
                    System.out.print("Enter item ID to remove: ");
                    int removeID = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    catalog.removeItem(removeID);
                    break;

                case 5:
                    System.out.print("Enter item ID to view: ");
                    int viewID = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    LibraryItem item = catalog.getItem(viewID);
                    if (item != null) {
                        System.out.println(item);
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;

                case 6:
                    catalog.displayItems();
                    break;

                case 7:
                    System.out.println("Exiting.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
