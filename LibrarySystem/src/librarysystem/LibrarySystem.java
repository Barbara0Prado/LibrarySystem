
package librarysystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import librarysystem.Book;
import librarysystem.Reader;
import librarysystem.Queue;
import librarysystem.Loan;
import librarysystem.Transaction;
import java.util.Scanner;

public class LibrarySystem {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        List <List<String>> loadBooks = new ArrayList<>(); 
        BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/MOCK_DATA.csv"));
        String readLine;
                   
           while ((readLine = bufferedReader.readLine()) != null) {
                String[] values = readLine.split(",");
                loadBooks.add(Arrays.asList(values));
                   
                   }
     
                   for (List <String> book : loadBooks) {
                       for (String b : book) {
                           System.out.println("b");
                          // System.out.println(new File("."). getAbsolutePath());
                       }
                   }
       
        Book[] books;
        Book book = new Book();
        books = book.loadBooks("readers.txt;");
        
        Reader[] readers;
        Reader reader = new Reader();
        readers = reader.loadReaders("readers.txt;");
        
        Loan [] loans;
        Loan loan = new Loan();
        loans = loan.loadLoans("loans.txt;");
        
        Queue[] queues;
        Queue queue = new Queue();
        queues = queue.loadQueue("queue.txt;");
        
        Transaction transaction;
        transaction = new Transaction();
        
        Scanner sc;
        sc = new Scanner(System.in);
        
        String option = " ";
        
        for (;;) {
            System.out.println("Choose one option ");
            System.out.println("1 - List books ");
            System.out.println("2 - List books arranged in alphabetical order ");
            System.out.println("3 - List books arranged in author's name order ");
            System.out.println("4 - Search by name ");
            
            System.out.println("5 - List readers ");
            System.out.println("6 - List readers arranged in alphabetical order ");
            System.out.println("7 - Perform rent ");
            System.out.println("8 - Return book ");
            
            System.out.println("a - List rents ");
            System.out.println("b - List waiting list ");
            
            System.out.println("0 - Exit ");
            option = sc.nextLine();
            
            switch(option) {
                case "1":
                    book.booksList(books);
                    break;
                case "2":
                    book.listInOrder(books);
                    break;
                case "3":
                    book.listAuthorInOrder(books);
                    break;
                case "4":
                    transaction.searchBooks(books);
                    break;
                case "5":
                    reader.readersList(readers);
                    break;
                case "6":
                    reader.listInOrder(readers);
                    break;
                case "7":
                    loan.performLoan(loans, books, readers, queues);
                    loans = loan.loadLoans("loan.txt");
                    queues = queue.loadQueue("queue.txt");
                    break;
                    
                case "a":
                    loan.list(loans);
                    break;
                case "b":
                    queue.list(queues);
                case "8":
                    loan.returnBook(loans, books, readers, queues);
                    loans = loan.loadLoans("loan.txt");
                    queues = queue.loadQueue("queue.txt");
                    break;
                case "0":
                    System.out.println("The End");
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Choose some other option");
                    
                    
            }
        }
        
    }
    
}
