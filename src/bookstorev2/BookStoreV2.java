/**
 *                Revision History (newest first)
 *************************************************************
 * 8/12/2021  Rewritten to fix the data file formats to avoid the
 *            Scanner bug.
 * 8/11/2021  Rewritten for a better version of Book  AApplin.
 * 8/30/2019  implemented first version - Anne Applin
 */
package bookstorev2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is being written specifically to show several things <br>
 * <ol>
 * <li>How to check for required command line arguments</li>
 * <li>How to catch exceptions</li>
 * <li>How to test each problem we are trying to prevent</li>
 * <li>How to set up run properties in NetBeans</li>
 * <li>How to use HTML in Java documentation</li>
 * </ol>
 *
 * @author aapplin
 */
public class BookStoreV2 {

    // properties of this class (everything in Java is a class!)
    private ArrayList<Book> inventory = new ArrayList<>();

    /**
     * Purpose: open, read and close the data file. Loads the data into an array
     * of book objects.
     *
     * @param fileName the name of the file to be read in.
     */
    public void readInventory(String fileName) {
        try { // try to connect to a physical file
            Scanner inFile = new Scanner(new FileReader(fileName));
            if (!inFile.hasNext()) {
                System.err.println("file " + fileName + " is empty.");
                System.exit(2);
            }
            while (inFile.hasNext()) {// as long as there is data in the file.
                String isbn = inFile.next(); // read the ISBN.
                // read title with embedded spaces
                String title = inFile.nextLine();
                // read author with embedded spaces
                String author = inFile.nextLine();
                int pages = inFile.nextInt();
                double price = inFile.nextDouble();
                int numberOnShelf = inFile.nextInt();

                if (isbn.length() == 13 && numberOnShelf >= 0) {
                    inventory.add(new Book(isbn, title,
                            author, pages, price,
                            numberOnShelf));

                }
            }
            System.out.println(inventory.size() + " books loaded.");
            inFile.close();
            Collections.sort(inventory);
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            System.exit(3);
        } catch (InputMismatchException ex) {
            System.err.println("tried to read the wrong data type.");
            System.exit(4);
        }
    }

    /**
     * Binary search is used on sorted data only! It effectively cuts the search
     * area in half at each iteration. The number of comparisons for a search is
     * (log2 n) which is very fast. The search will only take 13 iterations for
     * a list of size 10,000.
     *
     * @param keyToFind the String we are looking for
     * @return the index where the item was found or the place where it would be
     * if it were in the list.
     */
    public int binarySearch(String keyToFind) {
        int low = 0; // starting index
        int high = inventory.size() - 1; // ending indes
        int mid = 0; // index of the middle element
        int iterations = 0; // counter for the number of searches
        boolean found = false; // we haven't started looking yet
        while (low <= high & !found) {
            mid = low + (high - low) / 2;
            if (inventory.get(mid).getIsbn().compareTo(keyToFind) == 0) {
                found = true;
            } else if (inventory.get(mid).getIsbn().compareTo(keyToFind) > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
            iterations++;
        }
        System.out.println("number of iterations: " + iterations);
        if (!found) {
            mid = -mid - 1; // where it would be if it was here
        }
        return mid;
    }

    /**
     * This method reads a shipment file one book at a time. If the book is
     * already in the inventory then we update the number of copies in stock. If
     * it is a new book we add it to the next location in the array and
     * increment the count of books that we carry.
     *
     * @param fileName the name of the file that has the new books in it.
     */
    public void updateInventory(String fileName) {
        try { // try to connect to a physical file
            Scanner inFile = new Scanner(new FileReader(fileName));
            if (!inFile.hasNext()) {
                System.err.println("file " + fileName + " is empty.");
                System.exit(5);
            }
            int newBooks = 0;
            int booksUpdated = 0;
            while (inFile.hasNext()) {// as long as there is data in the file.
                String isbn = inFile.next(); // read the ISBN.
                String title = inFile.nextLine();
                String author = inFile.nextLine();
                int pages = inFile.nextInt();
                double price = inFile.nextDouble();
                int numberInShipment = inFile.nextInt();
                if (isbn.length() == 13 && numberInShipment >= 0) {
                    int index = binarySearch(isbn);
                    if (index > -1) {
                        inventory.get(index).addToNumberOnShelf(numberInShipment);
                        booksUpdated++;
                    } else {
                        inventory.add(new Book(isbn, title,
                                author, pages, price,
                                numberInShipment));
                        newBooks++;
                    }
                }
            }
            System.out.println(booksUpdated + " books updated");
            System.out.println(newBooks + " books added.");
            inFile.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            System.exit(6);
        } catch (InputMismatchException ex) {
            System.err.println("tried to read the wrong data type.");
            System.exit(7);
        }
    }

    /**
     * writeInventory writes out the books in the inventory to a file in the
     * correct format to be read in.
     *
     * @param fileName The name of the file to be written to.
     */
    public void writeInventory(String fileName) {
        try {
            PrintStream out = new PrintStream(new File(fileName));
            for (int i = 0; i < inventory.size(); i++) {
                out.println(inventory.get(i)); // magically calls Book's toString()
            }
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Directory seems to be write protected.");
            System.exit(8);
        }
    }

    /**
     * The run() method is where all the action is. This one should tell the
     * whole story of what the program is doing.
     *
     * @param filenames the 3 required filenames
     */
    public void run(String[] filenames) {
        readInventory(filenames[0]);
        if (inventory.size() > 0) {
            System.out.println("Number of books in inventory: "
                    + inventory.size());
            updateInventory(filenames[1]);
            System.out.println("Number of books in inventory: "
                    + inventory.size());
            writeInventory(filenames[2]);
        }
    }

    /**
     * main method. There can only be ONE main method that runs at any given
     * time. That is why the main method is static.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: BookStoreV1 input update output");
            System.exit(1);
        }
        // declare an object of this class
        BookStoreV2 driver = new BookStoreV2();
        driver.run(args);
    }

}
