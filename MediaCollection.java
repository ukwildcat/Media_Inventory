/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2_1181;

/**
 *
 * @author staceylawson
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MediaCollection implements Serializable {

    private ArrayList<MediaItem> collection = new ArrayList<>();
 
    public boolean isLoanable(MediaItem theItem) {
        return (theItem.getLoanedTo() == null);
    }
    
    
    public void loanItem(MediaItem theItem, String loanedTo, Date loanedOn) 
        throws Exception {
        
        if (!isLoanable(theItem)) throw new Exception(theItem.getTitle() + 
                " is already on loan.");
        theItem.loan(loanedTo, loanedOn);
    }

    
    public void returnItem(MediaItem theItem) throws Exception {

        if (theItem.getLoanedTo() == null) {
            throw new Exception(theItem.getTitle() + " is not on loan");
        } else {
            theItem.returnItem();
        }
    }


    
    public void storeCollection() throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File("media.dat")));) {
            oos.writeObject(collection);
        } catch (Exception e) {
            throw new Exception("Unable to save the updates to the collection.");
        }
    }

    
    public void readCollection() throws Exception {

        File f = new File("media.dat");

        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(f));) {
                collection = (ArrayList<MediaItem>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Unable to load the collection.");
            }
        }
    }
    
    
    public int getSize() {
        return collection.size();
    }
}
