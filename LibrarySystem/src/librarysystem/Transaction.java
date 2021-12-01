package librarysystem;

import librarysystem.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Transaction {
    
    Book book;
    

    public int countLines(File file) throws IOException {
        System.out.println("Counting the lines");
        int total=0;
        
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            
            String line;
            line = bf.readLine();
            int lines = 0;
            
            while (true) {
                line = bf.readLine();
                
                if (line == null)
                    break;
                lines++;
            }
            
            total = lines;
            bf.close();
            
        System.out.println("t"+total);
        return total;
    }
    
    public void searchBooks (Book[] books) {
        book = new Book();
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.println("Search by Author or Title");
        name = sc.nextLine();
        
        book.search(books, name);
        
        for (Book book : books) {
            
            //Searching by titles that was typed in the system
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("ID...... :" +book.getId());
                System.out.println("Title..... :" +book.getTitle());
                System.out.println("Author's First Name...... :" +book.getAuthorFirstName());
                System.out.println("Author's Last Name...... :" +book.getAuthorLastName());
                System.out.println("-------------------");
            }
            
            //Searching by authors who are part of what was typed, ie you can search by their last name
            if (book.getAuthorFirstName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("ID...... :" +book.getId());
                System.out.println("Title..... :" +book.getTitle());
                System.out.println("Author's First Name...... :" +book.getAuthorFirstName());
                System.out.println("Author's Last Name...... :" +book.getAuthorLastName());
                System.out.println("-------------------");
            }
        }
        
        
    }
}
