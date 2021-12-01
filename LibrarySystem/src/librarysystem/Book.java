package librarysystem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;



public class Book {
    
    private String id;
    private String book_title;
    private String author_first_name;
    private String author_last_name;
    private String genre;

    public Book() {
        this.id = "0";
        this.book_title = " ";
        this.author_first_name = " ";
        this.author_last_name = " ";
        this.genre = " ";
    }
    
    public Book (String id, String title, String authorFirstName, String authorLastName, String genre) {
        this.id = id;
        this.book_title = title;
        this.author_first_name = authorFirstName;
        this.author_last_name = authorLastName;
        this.genre = genre;        
    }
    
    public Book[] loadBooks(String fileName) throws IOException {
        
        File file = new File (fileName);
        Book book;
        Book[] books;
        
        Transaction transaction = new Transaction();
        books = new Book[transaction.countLines(file)];
        

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UFT-8");
            BufferedReader bf = new BufferedReader(isr);
            
            String line;
            line = bf.readLine();
            int lines = 0;
            
            while (true) {
                line = bf.readLine();
                
                if (line == null)
                    break;
                
                String[] data = line.split(" ; ");
                book = new Book ();
                book.setId(data [0]);
                book.setTitle(data [1]);
                book.setAuthorFirstName(data [2]);
                book.setAuthorLastName(data [3]);
                books [lines] = book;
                lines ++;
            }
            
            bf.close();
            
        
        return books;
        
    }
    
    public int booksList (Book[] books) {
        
        int total = 0;
        System.out.println("Listing books in the system");
        
        for (Book book : books) {
            System.out.println("ID...... :" + book.getId());
            System.out.println("Title...... :" + book.getTitle());
            System.out.println("Author's First Name...... :" + book.author_first_name);
            System.out.println("Author's Last Name...... :" + book.author_last_name);
            System.out.println("-------------------");
            total++;
        }
        
        return total;
    }
    
    public Book[] listInOrder(Book[] books) {
        Book bookTemp;
        String a1, a2;
        
        for (int i = 0; i <books.length-1-i; i++) {
            for (int j = 0; j < books.length-1-j; j++) {
                a1 = books[j].getTitle().trim();
                a2 = books[j + 1].getTitle().trim();
                
                if (a1.charAt(0) > a2.charAt(0)) {
                    bookTemp = books[j];
                    books [j] = books[j + 1];
                    books [j + 1] = bookTemp;
                }
            }
        }
        
        return books;
    }
    
    
    public void listAuthorInOrder (Book[] books) {
        Book bookTemp;
        String a1, a2;
        
        for (int i =0; i < books.length; i++) {
            for (int j = 0; j < books.length -1-i; j++) {
                a1 = books[j].getAuthorLastName().trim();
                a2 = books[j + 1].getAuthorLastName().trim();
                
                if (a1.charAt(0) > a2.charAt(0)) {
                    bookTemp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = bookTemp;
                
                }
                 
            }
        }
        
        int t = this.booksList(books);
    }
    
    
    public void search (Book[] books, String name) {
        
        for (Book book : books) {
            
           //Searching for books accordingly to what it was typed on the system
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("ID...... :" + book.getId());
                System.out.println("Title...... :" + book.getTitle());
                System.out.println("Author's First Name...... :" + book.getAuthorFirstName());
                System.out.println("Author's Last Name...... :" + book.getAuthorLastName());
                System.out.println("-------------------");
            }
            
            //Searching for authors whose part of what was typed, ie user can type only the last name
            if (book.getAuthorFirstName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("ID...... :" + book.getId());
                System.out.println("Title...... :" + book.getTitle());
                System.out.println("Author's First Name...... :" + book.getAuthorFirstName());
                System.out.println("Author's Last Name...... :" + book.getAuthorLastName());
                System.out.println("-------------------");
            }
        }
    }
    
    public Book searchById (Book[] books, String searchId) {
        Book b = new Book();
        
        for (Book book : books) {
            
            if (book.getId() == searchId) {
                id = book.getId();
                book_title = book.getTitle();
                author_first_name = book.getAuthorFirstName();
                author_last_name = book.getAuthorLastName();
                
                System.out.println("ID...... :" +id);
                System.out.println("Title...... :" + book_title);
                System.out.println("Author's First Name...... :" + author_first_name);
                System.out.println("Author's Last Name...... :" + author_last_name);
                System.out.println("-------------------");
                
                b.setId(id);
                b.setAuthorFirstName(author_first_name);
                b.setAuthorLastName(author_last_name);
                b.setTitle(book_title);
                
                break;
            }
        }
        
        return b;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return book_title;
    }

    public void setTitle(String title) {
        this.book_title = title;
    }

    public String getAuthorFirstName() {
        return author_first_name;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.author_first_name = authorFirstName;
    }

    public String getAuthorLastName() {
        return author_last_name;
    }

    public void setAuthorLastName(String authorLastName) {
        this.author_last_name = authorLastName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
