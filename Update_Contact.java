/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Habib
 */
public class Update_Contact extends JFrame {

    JPanel haut, gauche, droit, droit_centre, droit1, droit2, droitcheck, droit3;
    JLabel gstcon, cont_gauche, firstname, lastname, city, phone_title, modcongrp;
    JButton save, cancel;
    JTextField fstnametxt, lstnametxt, citytxt;
    JTable Phone_num;
    static Contact ConUpdate;
    static ArrayList NewList;
    ArrayList<JCheckBox> checkGrp = new ArrayList<JCheckBox>();

    Update_Contact(Contact c, ArrayList a) {
        ConUpdate = c;
        NewList = a;
        setTitle("Projet NFA035");
        setSize(600, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel en haut
        haut = new JPanel();
        haut.setPreferredSize(new Dimension(600, 40));
        gstcon = new JLabel("Gestion des Contacts");
        gstcon.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        gstcon.setForeground(Color.blue);
        haut.add(gstcon);

        //panel a gauche avec label new contact
        gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 40));
        gauche.setPreferredSize(new Dimension(175, 450));
        cont_gauche = new JLabel("Update Contact");
        cont_gauche.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        cont_gauche.setForeground(Color.red);
        gauche.add(cont_gauche);

        //panel a droit contient les entrées,tableu et les boutons
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
        DefaultTableModel defTableModel = new DefaultTableModel(column, 8);
        Phone_num.setModel(defTableModel);
        Phone_num.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(Phone_num);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //remplir le tableau de numero du contact
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

        modcongrp = new JLabel("Modify contact Groups");
        modcongrp.setAlignmentX(Component.CENTER_ALIGNMENT);

        droit2.add(phone_title);
        droit2.add(sp);
        droit2.add(modcongrp);

        droitcheck = new JPanel();
        droitcheck.setLayout(new BoxLayout(droitcheck, BoxLayout.Y_AXIS));

        //remplir les checkbox et selectionnes les ancient group du contact
        ArrayList<Group> ListGrpCon = c.getGrpList();
        ArrayList<Group> objectsList = null;
        try {
            FileInputStream fileStream = new FileInputStream("Group.obj");
            ObjectInputStream input = new ObjectInputStream(fileStream);
            objectsList = new ArrayList<>();
            boolean cont = true;
            while (cont) {
                Group obj = (Group) input.readObject();
                if (obj != null) {
                    objectsList.add(obj);
                } else {
                    cont = false;
                }
            }
            input.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        TreeSet<Group> ListUniGrp = new TreeSet<Group>();
        for (int i = 0; i < objectsList.size(); i++) {
            ListUniGrp.add(objectsList.get(i));
        }
        ArrayList<Group> listgrp = new ArrayList<Group>();
        listgrp.addAll(ListUniGrp);
        for (int i = 0; i < listgrp.size(); i++) {
            String g = listgrp.get(i).getGrpname();
            JCheckBox check = new JCheckBox(g);
            check.setAlignmentX(Component.CENTER_ALIGNMENT);
            for (int j = 0; j < ListGrpCon.size(); j++) {
                if (listgrp.get(i).getGrpname().equals(ListGrpCon.get(j).getGrpname())) {
                    check.setSelected(true);
                }
            }
            this.checkGrp.add(check);
            droitcheck.add(check);
        }

        //panel qui contient les boutons de save et cancel
        droit3 = new JPanel();
        droit3.setPreferredSize(new Dimension(335, 40));
        save = new JButton("Save");
        cancel = new JButton("cancel");
        save.addActionListener(new ButtonListener());
        cancel.addActionListener(new ButtonListener());
        droit3.add(save);
        droit3.add(cancel);

        droit.add(droit1);
        droit.add(droit2);
        droit.add(droitcheck);
        droit.add(droit3);

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

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            Object ob = event.getSource();
            int close;
            if (ob == save) {
                String s1 = (String) Phone_num.getValueAt(0, 0);
                String s2 = (String) Phone_num.getValueAt(0, 1);
                boolean b = "".equals(s1) || "".equals(s2);
                if (fstnametxt.getText().isEmpty() || lstnametxt.getText().isEmpty() || citytxt.getText().isEmpty() || b) {
                    JOptionPane.showMessageDialog(null, "Un contact doit avoir un nom,un numéro et un numéro de téléphone", "Error message", JOptionPane.ERROR_MESSAGE);
                } else {
                    //algo pour saisir les numero de telephon du tableau
                    ArrayList<String> n = new ArrayList<>();
                    for (int i = 0; i < Phone_num.getRowCount(); i++) {
                        String s = "";
                        for (int j = 0; j < Phone_num.getColumnCount(); j++) {
                            String srow = (String) Phone_num.getValueAt(i, 0);
                            String srow1 = (String) Phone_num.getValueAt(i, 1);
                            boolean b1 = "".equals(srow) || "".equals(srow1);
                            boolean b2 = srow == null || srow1 == null;
                            if (!(b1 || b2)) {
                                if (j == 0) {
                                    s += (String) Phone_num.getValueAt(i, j);
                                } else if (j == 1) {
                                    s += (String) Phone_num.getValueAt(i, j);
                                }
                            } else {
                                break;
                            }
                        }
                        if (!"".equals(s)) {
                            n.add(s);
                        } else {
                            break;
                        }
                    }
                    NewList.remove(ConUpdate);
                    //Saisir les checkbox saisies
                    ArrayList<String> NomGrp = new ArrayList<String>();
                    for (int i = 0; i < checkGrp.size(); i++) {
                        if (checkGrp.get(i).isSelected()) {
                            NomGrp.add(checkGrp.get(i).getText());
                        }
                    }

                    ArrayList<Group> GroupListCon = new ArrayList<>();
                    ArrayList<Group> objectsList = null;
                    try {
                        FileInputStream fileStream = new FileInputStream("Group.obj");
                        ObjectInputStream input = new ObjectInputStream(fileStream);
                        objectsList = new ArrayList<>();
                        boolean cont = true;
                        while (cont) {
                            Group obj = (Group) input.readObject();
                            if (obj != null) {
                                objectsList.add(obj);
                            } else {
                                cont = false;
                            }
                        }
                        input.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    TreeSet<Group> ListUniGrp = new TreeSet<Group>();
                    for (int i = 0; i < objectsList.size(); i++) {
                        ListUniGrp.add(objectsList.get(i));
                    }
                    ArrayList<Group> listgrp = new ArrayList<Group>();
                    listgrp.addAll(ListUniGrp);

                    ArrayList<Group> ListGrpCon = ConUpdate.getGrpList();
                    for (int i = 0; i < listgrp.size(); i++) {
                        for (int j = 0; j < ListGrpCon.size(); j++) {
                            if (listgrp.get(i).getGrpname().equals(ListGrpCon.get(j).getGrpname())) {
                                ArrayList<Contact> oldlist = listgrp.get(i).getConList();
                                for (int z = 0; z < oldlist.size(); z++) {
                                    if (oldlist.get(z).getTelList()[0].equals(ConUpdate.getTelList()[0])) {
                                        oldlist.remove(oldlist.get(z));
                                    }
                                }
                                listgrp.get(i).setConList(oldlist);
                            }
                        }
                    }
                    if (!NomGrp.isEmpty()) {
                        for (int i = 0; i < listgrp.size(); i++) {
                            for (int j = 0; j < NomGrp.size(); j++) {
                                if (listgrp.get(i).getGrpname().equals(NomGrp.get(j))) {
                                    ArrayList<Contact> oldlist = listgrp.get(i).getConList();
                                    oldlist.add(ConUpdate);
                                    listgrp.get(i).setConList(oldlist);
                                    GroupListCon.add(listgrp.get(i));
                                }
                            }
                        }
                    }
                    ConUpdate.setGrpList(GroupListCon);
                    ConUpdate.setTelList(n);
                    ConUpdate.setFname(fstnametxt.getText());
                    ConUpdate.setLname(lstnametxt.getText());
                    ConUpdate.setCity(citytxt.getText());
                    try {
                        FileOutputStream file = new FileOutputStream("Group.obj");
                        ObjectOutputStream os2 = new ObjectOutputStream(file);
                        for (int i = 0; i < listgrp.size(); i++) {
                            os2.writeObject(listgrp.get(i));
                            os2.flush();
                        }

                        os2.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    NewList.add(ConUpdate);

                    try {
                        ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("Contact.obj"));
                        for (int i = 0; i < NewList.size(); i++) {
                            os2.writeObject(NewList.get(i));
                            os2.flush();
                        }

                        os2.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    setVisible(false);
                    dispose();
                }
            } else if (ob == cancel) {
                close = JOptionPane.showConfirmDialog(null, "Vous voulez quitter cette fenêtre?", "Confirm message", JOptionPane.YES_NO_OPTION);
                if (close == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();
                }

            }
        }
    }

}
