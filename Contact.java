/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.io.*;
import java.util.*;

/**
 *
 * @author Habib
 */
class Contact implements Serializable {

    private String Fname, Lname, City;
    private LinkedHashSet<String> TelList;
    private ArrayList<Group> ListGrp =new ArrayList<>();

    Contact(String Fn, String Ln, String cty, ArrayList n) {
        this.Fname = Fn.substring(0, 1).toUpperCase() + Fn.substring(1).toLowerCase();
        this.Lname = Ln.substring(0, 1).toUpperCase() + Ln.substring(1).toLowerCase();
        this.City = cty.substring(0, 1).toUpperCase() + cty.substring(1).toLowerCase();
        this.TelList = new LinkedHashSet<String>(n);
    }

    public String getFname() {
        return this.Fname;
    }

    public String getLname() {
        return this.Lname;
    }

    public String getCity() {
        return this.City;
    }

    public String[] getTelList() {
        String[] s = new String[1];
        s = TelList.toArray(s);
        return s;
    }

    public ArrayList<Group> getGrpList() {
        return this.ListGrp;
    }

    public void setFname(String Fn) {
        this.Fname = Fn.substring(0, 1).toUpperCase() + Fn.substring(1).toLowerCase();
    }

    public void setLname(String Ln) {
        this.Lname = Ln.substring(0, 1).toUpperCase() + Ln.substring(1).toLowerCase();
    }

    public void setCity(String cty) {
        this.City = cty.substring(0, 1).toUpperCase() + cty.substring(1).toLowerCase();
    }

    public void setTelList(ArrayList n) {
        TelList = new LinkedHashSet<String>(n);
    }

    public void setGrpList(ArrayList<Group> g) {
        this.ListGrp = g;
    }

    public String affichernum() {
        String[] s = getTelList();
        String res = "";
        for (int i = 0; i < s.length; i++) {
            res += s[i] + "\n";
        }
        return res;
    }

    public String toString() {
        //return "\nFirst name: " + getFname() + "\nLast name: " + getLname() + "\nCity: " + getCity() + "\n" + affichernum();
        return getFname() + " " + getLname() + " - " + getCity();
    }

}

class FnCompare implements Comparator<Contact> {

    @Override
    public int compare(Contact e1, Contact e2) {
        return e1.getFname().compareTo(e2.getFname());
    }
}

class LnCompare implements Comparator<Contact> {

    @Override
    public int compare(Contact e1, Contact e2) {
        return e1.getLname().compareTo(e2.getLname());
    }
}

class ctyCompare implements Comparator<Contact> {

    @Override
    public int compare(Contact e1, Contact e2) {
        return e1.getCity().compareTo(e2.getCity());
    }
}

