/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 *
 * @author Habib
 */
public class View_Contact extends JFrame implements Serializable {

    JPanel haut, gauche, droit, droit_centre, droit1, droit2, droitcheck;
    JLabel gstcon, cont_gauche, firstname, lastname, city, phone_title, addcongrp;
    JTextField fstnametxt, lstnametxt, citytxt;
    JTable Phone_num;

    View_Contact(Contact c) {
        setTitle("Projet NFA035");
        setSize(600, 500);
        
        //panel en haut
        haut = new JPanel();
        haut.setPreferredSize(new Dimension(600, 40));
        gstcon = new JLabel("Gestion des Contacts");
        gstcon.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        gstcon.setForeground(Color.blue);
        haut.add(gstcon);

        //panel a gauche avec label new contact
        gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 40));
        gauche.setPreferredSize(new Dimension(150, 450));
        cont_gauche = new JLabel("View Contact");
        cont_gauche.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        cont_gauche.setForeground(Color.red);
        gauche.add(cont_gauche);

        //panel a droit contient les entrées,tableu et les checkbox grp 
        droit = new JPanel();
        droit.setLayout(new BoxLayout(droit, BoxLayout.Y_AXIS));
        droit.setPreferredSize(new Dimension(350, 450));
        //panel qui contient les entrées d'un contact et un label du tableau
        droit1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        droit1.setPreferredSize(new Dimension(350, 110));
        firstname = new JLabel("First name ");
        lastname = new JLabel("Last name ");
        city = new JLabel("City");
        fstnametxt = new JTextField(25);
        fstnametxt.setText(c.getFname());
        lstnametxt = new JTextField(25);
        lstnametxt.setText(c.getLname());
        citytxt = new JTextField(29);
        citytxt.setText(c.getCity());
        fstnametxt.setEditable(false);
        lstnametxt.setEditable(false);
        citytxt.setEditable(false);
        droit1.add(firstname);
        droit1.add(fstnametxt);
        droit1.add(lastname);
        droit1.add(lstnametxt);
        droit1.add(city);
        droit1.add(citytxt);

        //panel qui contient tableau de numero et checkbox des grps
        droit2 = new JPanel();
        droit2.setLayout(new BoxLayout(droit2, BoxLayout.Y_AXIS));
        droit2.setPreferredSize(new Dimension(335, 300));
        phone_title = new JLabel("Phone numbers");
        phone_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        String column[] = {"Region Code", "Phone number"};
        Phone_num = new JTable();
        DefaultTableModel defTableModel = new DefaultTableModel(column, 8) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        Phone_num.setModel(defTableModel);
        Phone_num.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(Phone_num);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //inserer les numero de telephone du contact
        String[] s = c.getTelList();

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < Phone_num.getColumnCount(); j++) {
                if (j == 0) {
                    Phone_num.setValueAt(s[i].substring(0, 2), i, j);
                } else if (j == 1) {
                    Phone_num.setValueAt(s[i].substring(2), i, j);
                }
            }
        }
        addcongrp = new JLabel("Contact Groups");
        addcongrp.setAlignmentX(Component.CENTER_ALIGNMENT);

        droit2.add(phone_title);
        droit2.add(sp);
        droit2.add(addcongrp);

        droitcheck = new JPanel();
        droitcheck.setLayout(new BoxLayout(droitcheck, BoxLayout.Y_AXIS));
        //imprimer les grp du contact avec les checkbox
        ArrayList<Group> ListGrp = c.getGrpList();
        for (int i = 0; i < ListGrp.size(); i++) {
            String g = ListGrp.get(i).getGrpname();
            JCheckBox check = new JCheckBox(g);
            check.setSelected(true);
            //check.setEnabled(false);
            check.setAlignmentX(Component.CENTER_ALIGNMENT);
            droitcheck.add(check);
        }
        
        droit.add(droit1);
        droit.add(droit2);
        droit.add(droitcheck);

        //panel centre background blue
        droit_centre = new JPanel();
        droit_centre.setBackground(Color.cyan);
        droit_centre.setPreferredSize(new Dimension(100, 450));

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(haut, BorderLayout.NORTH);
        cp.add(droit, BorderLayout.EAST);
        cp.add(droit_centre, BorderLayout.CENTER);
        cp.add(gauche, BorderLayout.WEST);
        setVisible(true);
    }
}
