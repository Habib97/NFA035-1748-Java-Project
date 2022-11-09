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

public abstract class MyObservable {

    boolean changed;
    List<MyObserver> observers;

    public MyObservable() {
        changed = false;
        observers = new ArrayList<MyObserver>();
    }

    public void setChanged() {
        changed = true;
    }

    public void addObserver(MyObserver ob) {
        observers.add(ob);
    }

    public void notifyObservers() {
        for (Iterator<MyObserver> it = observers.iterator(); it.hasNext();) {
            ((MyObserver) it.next()).update();
        }
    }

    public interface MyObserver {

        public abstract void update();

    }
}
