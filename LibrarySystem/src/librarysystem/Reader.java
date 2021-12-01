package librarysystem;


import librarysystem.Transaction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Reader {
    
    private int id;
    private String name;
    
    public Reader() {
        this.id = 0;
        this.name = " ";
    }
    
    public Reader[] loadReaders (String fileName) throws IOException {
        File file = new File(fileName);
        Reader reader = new Reader();
        Reader[] readers;
        
        Transaction transaction;
        transaction = new Transaction();
        readers = new Reader [transaction.countLines(file)];
        
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
                
                String[] data = line.split(" ; ");
                reader = new Reader();
                reader.setId(Integer.parseInt(data[0]));
                reader.setName(data[1]);
                readers[lines] = reader;
                lines++;
                
            }
            
            bf.close();
            
        
        return readers;
    }
     public int readersList (Reader[] readers) {
        
        int total = 0;
        System.out.println("Listing readers in the system");
        
        for (Reader reader : readers) {
            System.out.println("ID...... :" +reader.getId());
            System.out.println("Author's First Name...... :" +reader.name);
            System.out.println("-------------------");
            total++;
        }
        
        return total;
    }
     
     public Reader[] listInOrder(Reader[] readers) {
        Reader readerTemp;
        String a1, a2;
        
        for (int i = 0; i <readers.length-1-i; i++) {
            for (int j = 0; j < readers.length-1-j; j++) {
                a1 = readers[j].getName().trim();
                a2 = readers[j + 1].getName().trim();
                
                if (a1.charAt(0) > a2.charAt(0)) {
                    readerTemp = readers[j];
                    readers [j] = readers[j + 1];
                    readers [j + 1] = readerTemp;
                }
            }
        }
        
        return readers;
    }
     
     public void search (Reader[] readers, String name) {
        
        for (Reader reader : readers) {
            
           //Searching for readers accordingly to what it was typed on the system
            if (reader.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("ID...... :" +reader.getId());
                System.out.println("Title...... :" +reader.getName());
                System.out.println("-------------------");
            }
        }
    }
    
    public Reader searchById (Reader[] readers, int searchId) {
        Reader r = new Reader();
        
        for (Reader reader : readers) {
            
            if(reader.getId() == searchId) {
                id = reader.getId();
                name = reader.getName();
                
                System.out.println("ID...... :" +id);
                System.out.println("Name...... :" +name);
                System.err.println("-------------------");
                
                r.setId(id);
                r.setName(name);
            }
        }
        
        return r;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
