/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

/**
 *
 * @author Habib
 */
import java.util.*;
import java.io.*;
public class PhoneBook extends MyObservable {

    TreeMap<String, Contact> PhoneList=new TreeMap<>();

    public PhoneBook() {
         try {
            FileInputStream fileStream = new FileInputStream("Contact.obj");
            ObjectInputStream input = new ObjectInputStream(fileStream);
            boolean cont = true;
            while (cont) {
                Contact obj = (Contact) input.readObject();
                if (obj != null) {
                    PhoneList.put(obj.getTelList()[0],obj);
                } else {
                    cont = false;
                }
            }
            input.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void addContact(Contact c) {
        PhoneList.put(c.getTelList()[0], c);
        setChanged();
        notifyObservers();
        changed = false;
    }

    public void removeContact(Contact c) {
        PhoneList.remove(c.getTelList()[0]);
        setChanged();
        notifyObservers();
        changed = false;
    }
}
