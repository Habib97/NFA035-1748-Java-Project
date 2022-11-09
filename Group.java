/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Habib
 */
public class Group implements Comparable<Group>, Serializable {

    private String GrpName, GrpDesc="";
    private ArrayList<Contact> ListCon = new ArrayList<>();

    Group(String n) {
       this.GrpName = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();
    }

    public String getGrpname() {
        return this.GrpName;
    }

    public String getGrpdesc() {
        return this.GrpDesc;
    }

    public ArrayList<Contact> getConList() {
        return this.ListCon;
    }

    public void setGrpname(String n) {
        this.GrpName = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();
    }

    public void setGrpDesc(String n) {
        this.GrpDesc = n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase();
    }

    public void setConList(ArrayList<Contact> c) {
        this.ListCon = c;
    }

    public String toString() {
        //return "\nFirst name: " + getFname() + "\nLast name: " + getLname() + "\nCity: " + getCity() + "\n" + affichernum();
        return getGrpname() + " : " + getGrpdesc();
    }

    public int compareTo(Group t) {
        return this.getGrpname().compareTo(t.getGrpname());
    }

}
