package librarysystem;


import librarysystem.Transaction;
import librarysystem.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Queue {
    
    private int id;
    private int readerId;
    private String bookId;
    
    public Queue[] loadQueue (String fileName) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        File file = new File (fileName);
        Queue queue;
        String line;
        Queue[] queues;
        Transaction transaction = new Transaction();
        
        queues = new Queue [transaction.countLines(file)];
                 
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bf = new BufferedReader(isr);
  
            line = bf.readLine();
            int lines = 0;
            
            while(true) {
                line = bf.readLine();
                
                if (line == null) {                   
                    
                    String[] data = line.split(",");
                    queue = new Queue();
                    queue.setId(Integer.parseInt(data [0]));
                    queue.setReaderId(Integer.parseInt(data[1]));
                    queue.setBookId(data[2]);
                    
                    queues [lines] = queue;
                    lines++;
                }
                
                bf.close();
            }
               
        
        }
            
    public void list (Queue[] q) {
        System.out.println("Listing the waiting queue");
        
        if (q.length == 0) {
            System.out.println("Empty Queue");
            return;
        }
        
        for (Queue queue : q) {
            System.out.println("ID...... : " + queue.getId());
            System.out.println("Reader...... : " + queue.getReaderId());
            System.out.println("Book...... : " + queue.getBookId());
            System.err.println("-------------------");
        }
       
    }
    
    public void saveQueues (String filename, Queue queue) throws IOException {
        
            File file = new File (filename);
            FileWriter fw = new FileWriter (file, true);
            id = queue.getId();
            readerId = queue.getReaderId();
            bookId = queue.getBookId();
            fw.write("\n" + id + " ; " + readerId + " : " + bookId);
            fw.close();

    }
    
    public int[] checkIfThereIsReadersWaiting (Queue[] queues, String bookId) {
        int[] ans;
        int queueSize = 0;
        
        for (Queue queue : queues) {
            if (queue.getBookId() == bookId) {
                queueSize++;
            }
        }
        
        ans = new int[queueSize];
        int i =0;
        
        for (Queue queue : queues) {
            if (queue.getBookId() == bookId) {
                ans[i++] = queue.getReaderId();
            }
        }
        
        return ans;
    }
    
    public void removeFromQueue (String filename, Queue[] queues, String bookId, int readerId) throws IOException {
       readerId = 0;
        
            File file = new File (filename);
            FileWriter fw = new FileWriter(file);
            fw.write("id; readerId; bookId");
            
            for (int i = 0; i < queues.length; i++) {
                System.out.println("readerId " + queues[i].getReaderId());
                System.out.println("id " + readerId);
                
                if (queues[i].getReaderId() != readerId) {
                    id = queues[i].getId();
                    readerId = queues[i].getReaderId();
                    bookId = queues[i].getBookId();
                    fw.write("\n " + id + "; " + readerId + "; " + bookId);
                }
            }
            
            fw.close();
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
}
