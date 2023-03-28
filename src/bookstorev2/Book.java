/*****************************************************************
 *                      Revision History
 *****************************************************************
 * today's date - implemented first version - your name
 ***************************************************************** 
 */

package bookstorev2;

/**
 * Description: A class to model a Book. Except for one property, Book objects
 * are immutable (they can't be changed) because we have not provided 
 * mutators (setters)
 * Depends on: nothing
 * Author: Your Name 
 * @author yourUsername
 */
public class Book implements Comparable{
    private String isbn;
    private String title;
    private String author;
    private int    pages;
    private double price;
    private int    numberOnShelf;
    

    /**
     * Parameterized Constructor for Book
     * @param isbn the International Standard Book Number.
     * @param title The title for this book
     * @param author the author of this book
     * @param pages the number of pages in this book
     * @param price the retail price of this book
     * @param numberOnShelf the number copies of this book in the inventory
     */
    public Book(String isbn, String title, String author, int pages, 
            double price, int numberOnShelf) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.numberOnShelf = numberOnShelf;
    }

    /**
     * A processing method that adds copies to the existing number of 
     * copies for this book.
     * @param n the number of copies that we are adding to the shelf.
     */
    public void addToNumberOnShelf(int n){
        if (n > 0)
            numberOnShelf += n;
    }

    /**
     * An Accessor for the property isbn.  
     * @return the isbn of this book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Accessor for the property title.
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor for the property author.
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Accessor for the property pages
     * @return the number of pages n the book
     */
    public int getPages() {
        return pages;
    }

    /**
     * Accessor for the property price.
     * @return the price of the book
     */
    public double getPrice() {
        return price;
    }

    /**
     * Accessor for the property numberOnShelf
     * @return the number of this book on hand
     */
    public int getNumberOnShelf() {
        return numberOnShelf;
    }
    
    @Override
    /**
     * The Java class Object has a toString() method so we override that one
     * in every class that we write so that we get the information out of the 
     * objects in a predetermined format.  This one outputs the Book data
     * in the same format that it is read in the inventory written out can be
     * read in the next day.
     * @return a string with the isbn and title on one line, the author on 
     * a second line, and the three numeric properties on a third line. 
     */
    public String toString(){        
        return String.format("%13s  %s\n%s\n%d  %.2f  %d",
                isbn, title, author, pages, price, numberOnShelf);        
    }
    /**
     * compareTo (abstract method of the Comparable Interface) is implemented to
     * impose a natural ordering on a group of objects. compareTo is used by the
     * Collections.sort() method to allow us to sort the competitors belonging
     * to some Java collection.
     *
     * @param that is the Book object we are comparing this one to
     * @return a negative integer, zero, or a positive integer if this object
     * comes before, is equal to, or comes after the specified object.
     */

    public int compareTo(Object that) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        // shouldn't be any null objects, but if there are 
        // put them at the end
        if (that == null) {
            return AFTER;
        }
        // this optimization is usually worthwhile, aand can always be
        // added - if the addresses are the same... they are equal
        if (this == that) {
            return EQUAL;
        }
        
        return this.isbn.compareTo(((Book)that).getIsbn());
    }
    /**
     * Unit Test for Book.  Constructs, prints, adds and prints
     * @param args
     */
    public static void main(String[] args){
        
        Book book = new Book("1234567890123", "A Bogus Book", "Dot Matrix",
                             324, 12.95, 5);
        System.out.println(book);
        book.addToNumberOnShelf(3);
        System.out.println(book);
    }
}
