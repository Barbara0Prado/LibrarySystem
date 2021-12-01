package librarysystem;


import librarysystem.Transaction;
import librarysystem.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Loan {
    
    private int id;
    private int readerId;
    private String bookId;
    private boolean status; // true= Rented / false= Returned
    
    public Loan() {
        this.id = 0;
        this.readerId = 0;
        this.bookId = " ";
        
    }
    
    public int list (Loan[] loans) {
        
        int total = 0;
        System.out.println("Listing Rented Books");
        
        for (Loan loan : loans) {
            total++;
            
            System.out.println("ID...... :" +loan.getId());
            System.out.println("Reader...... :" +loan.getReaderId());
            System.out.println("Book...... :" +loan.getBookId());
            System.err.println("-------------------");
            
        }
        
        return total;
    }
    
    public Loan searchingForLoanedBook (Loan[] loans, String bookId) {
        Loan loan = new Loan();
        
        for (Loan r : loans) {
            
            if(r.isStatus() == true) {
                if (r.getBookId() == bookId) {
                    loan.setId(r.getId());
                    loan.setReaderId(r.getReaderId());
                    loan.setBookId(r.getBookId());
                }
            }
        }
        
        return loan;
        
    }
    
    public Loan [] loadLoans (String fileName) throws IOException {
        File file = new File(fileName);
        Loan loan;
        Loan [] loans;
        Transaction transaction = new Transaction();
        loans = new Loan[transaction.countLines(file)];
        
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bf = new BufferedReader(isr);
            
            String line;
            line = bf.readLine();
            int lines = 0;
            
            while (true) {
                line = bf.readLine();
                
                if (line == null)
                    break;
                
                String[] data = line.split(";");
                loan = new Loan();
                loan.setId(Integer.parseInt(data[0]));
                loan.setReaderId(Integer.parseInt(data[1]));
                loan.setBookId((data[2]));
                loan.setStatus(Boolean.parseBoolean(data[3]));
                loans [lines] = loan;
                lines++;
            }
            
            bf.close();
            
        
        return loans;
    }
    
    public Loan searchLoanByBookId (Loan[] loans, String searchId) {
        Loan loan = new Loan();
        
        for (Loan loaned : loans) {
            
            if(loan.isStatus() ==  true) { // Only rented books will be considered
                if (loan.getBookId() == searchId) { // On this one we use bookId to be able to check if the book is not available
                    id = loan.getId();
                    readerId = loan.getReaderId();
                    bookId = loan.getBookId();
                    status = loan.isStatus();
                    loan.setId(id);
                    loan.setReaderId(readerId);
                    loan.setBookId(bookId);
                    loan.setStatus(status);
                    break;
                   
                }
            }
        }
        
        return loan;
    }
    
    public Loan[] addRent (Loan[] loans, Loan loaned) {
        Loan[] loan = new Loan[loans.length + 1];
        
        for (int i = 0; i < loans.length; i++) {
            loan[i] = loans[i];
            System.out.println("Ee " +loan[i].getId());
        }
        
        loan[loans.length] = loaned;
        //Save in the file
        this.saveLoan("Rented.txt", loaned); //We'll have everything saved in the file
        return loan;
    }
    
    
    public void saveLoan (String fileName, Loan loan) {
        
        try {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file, true);
            id = loan.getId();
            readerId = loan.getReaderId();
            bookId = loan.getBookId();
            status = loan.isStatus();
            
            fw.write("\n" + id + ";" + readerId + ";" + bookId + ";" + status);
            fw.close();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
      }
    
    public void updateLoan(String fileName, Loan[] loans, Loan loaned) {
        
        try {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);
            fw.write("ID; ReaderID; BookId; Status");
            
            for (int i = 0; i < loans.length; i++) {
                id = loans[i].getId();
                readerId = loans[i].getReaderId();
                bookId = loans[i].getBookId();
                
                if (loans[i].getId() != loaned.getId()) {
                    status = loans[i].isStatus();
                    
                } else {
                    
                    status = false;
                }
                
                fw.write("\n" + id + ";" + readerId + ";" + bookId + ";" + status);
            }
            
            fw.close();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
    
    public void performLoan(Loan[] loans, Book[] books, Reader[] readers, Queue[] queues) throws IOException {
       int readerId;
       String bookId;
       
       Scanner sc = new Scanner(System.in);
        System.out.println("Type here the book ID");
        bookId = sc.toString();
        System.out.println("Type here the reader ID");
        readerId = sc.nextInt();
        
        //Check if the book is added on the system
        Book book = new Book();
        book = book.searchById(books, bookId);
        
        //Check if the reader is added on the system
        Reader reader = new Reader();
        reader = reader.searchById(readers, readerId);
        
        if (book.getId() == " ") {
            System.out.println("Book is not added on the system");
            return;
        }
        
        if (reader.getId() == 0) {
            System.out.println("Reader is not added on the system");
            return;
        }
        
        //Check if the book is available to rent
        Loan rentTest;
        rentTest = new Loan();
        rentTest = rentTest.searchLoanByBookId(loans, bookId);
        
        if (rentTest.getReaderId() != 0) {
            System.out.println("Book is not available at the moment");
            System.out.println("Would you like to join the queue? (Y/N)");
            
            //If the book is not available, read the queue
            //Add reader to the queue
            
            sc = new Scanner(System.in);
            String confirm;
            confirm = sc.nextLine();
            
            if (confirm.equals("Y")) {
                Queue q = new Queue();
                
                q.setId(queues.length + 1);
                q.setReaderId(readerId);
                q.setBookId(bookId);
                q.saveQueues ("queues.txt", q);
                
            }
            
        } else {
            
            //sc.close();
            System.out.println("Confirm book loan? (Y/N)");
            
            sc = new Scanner(System.in);
            
            String confirm;
            confirm = sc.nextLine();
            
            if (confirm.equals("Y")) {
                
                //Add the book to the rented list
                System.out.println("Loan confirmed");
                Loan loan = new Loan();
                loan.setId(loans.length + 1);
                loan.setReaderId(readerId);
                loan.setBookId(bookId);
                loan.setStatus(true);
                
                //addRentals
                saveLoan("loans.txt", loan); //Everything will be saved in the file
            }
            
            sc.close();
        }
    }
    
    public void returnBook (Loan[] loans, Book[] books, Reader[] readers, Queue[] queues) throws IOException {
        int readerId; 
        String bookId, option;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Type down the book ID");
        bookId = sc.next();
        
        //Check if the book is actually loaned
        Loan loan = new Loan();
        loan = searchingForLoanedBook(loans, bookId);
        
        if (loan.getBookId() == "0") {
            System.out.println("The book searched is not loaned");
            return; //Quit returnBook method
         
        }
        
        sc = new Scanner (System.in);
        
        System.out.println("Confirm book return? (Y/N)");
        sc = new Scanner(System.in);
        String confirm = null;
        
        if (confirm.equals("Y")) {
            
            //Puts status 0 for the rental
            updateLoan("rents.txt", loans, loan);
            System.out.println("Book returned successfully");
        }
        
        
        //Check if book is on the queue and make the alert
        
        Queue queue = new Queue();
        int[] available = queue.checkIfThereIsReadersWaiting(queues, bookId);
        
        if (available.length > 0) {
            System.out.println("There are " + available.length + " waiting on the queue");
            
            for ( int i = 0; i < available.length; i++) {
                Reader r = new Reader();
                r = r.searchById(readers, available[i]);
                System.out.println("The reader " + r.getName() + "is waiting on the queue");
            }
            
            //Take the book off the waiting queue if necessary
            queue.removeFromQueue ("queue.txt", queues, bookId, available[0]);
            
            //Proceed the book renting already having the bookId and readerId
            loan.performLoan(loans, books, readers, queues);
            loans = loan.loadLoans("loans.txt");
            
            //queues = queue.loadQueue ("queue.txt");
        }
           
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }   
       
}
