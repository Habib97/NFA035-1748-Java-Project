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

/**
 *
 * @author Habib
 */
public class Contacts_page extends JFrame implements Serializable {

    JPanel haut, gauche, droit, droit_centre, droit1, droit2, droit3;
    JLabel gstcon, cont_gauche, SearchCon;
    JButton SortFname, SortLname, SortCity, AddContact,
            ViewContact, UpdateContact, DeleteContact;
    JTextField SearchBar;
    JTextArea ContactList;
    JList<Contact> list;
    DefaultListModel model;
    static int sortorder = 1;

    Contacts_page() {
        setTitle("Projet NFA035");
        setSize(600, 500);

        //panel en haut
        haut = new JPanel();
        haut.setPreferredSize(new Dimension(50, 40));
        gstcon = new JLabel("Gestion des Contacts");
        gstcon.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        gstcon.setForeground(Color.blue);
        haut.add(gstcon);

        //panel a gauche avec deux bouttons
        gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 40));
        gauche.setPreferredSize(new Dimension(150, 450));
        cont_gauche = new JLabel("Contacts");
        cont_gauche.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        cont_gauche.setForeground(Color.red);
        SortFname = new JButton("Sort by First name");
        SortLname = new JButton("Sort by Last name");
        SortCity = new JButton("Sort by City");
        AddContact = new JButton("Add new Contact");
        SortFname.addActionListener(new ButtonListener());
        SortLname.addActionListener(new ButtonListener());
        SortCity.addActionListener(new ButtonListener());
        AddContact.addActionListener(new ButtonListener());
        gauche.add(cont_gauche);
        gauche.add(SortFname);
        gauche.add(SortLname);
        gauche.add(SortCity);
        gauche.add(AddContact);

        //panel a droit avec un search,textarea et les bouttons
        droit = new JPanel();
        droit.setLayout(new BoxLayout(droit, BoxLayout.Y_AXIS));
        droit.setPreferredSize(new Dimension(350, 450));

        //panel qui contient search
        droit1 = new JPanel();
        SearchCon = new JLabel("Search");
        SearchBar = new JTextField(25);
        // SearchBar.addTextListener(new FilterSearch(""));
        droit1.add(SearchCon);
        droit1.add(SearchBar);

        //panel qui contient textarea
        droit2 = new JPanel();
        model = new DefaultListModel();
        list = new JList<Contact>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane pane = new JScrollPane(list);
        pane.setPreferredSize(new Dimension(220, 320));
        droit2.add(pane);
        //mettre par defaut les contact en ordre du firstname
        TreeMap<String, Contact> tmap = new TreeMap<String, Contact>();
        try {
            FileInputStream fileStream = new FileInputStream("Contact.obj");
            ObjectInputStream input = new ObjectInputStream(fileStream);
            boolean cont = true;
            while (cont) {
                Contact obj = (Contact) input.readObject();
                if (obj != null) {
                    tmap.put((obj.getTelList()[0]), obj);
                } else {
                    cont = false;
                }
            }
            input.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        ArrayList listNum;
        listNum = new ArrayList(tmap.values());
        listNum.sort(new FnCompare());

        model.clear();
        for (int i = 0; i < listNum.size(); i++) {
            model.addElement(listNum.get(i));
        }
        //panel qui contient les boutons de changement de contact
        droit3 = new JPanel();
        ViewContact = new JButton("View");
        UpdateContact = new JButton("Update");
        DeleteContact = new JButton("Delete");
        ViewContact.addActionListener(new mButtonListener());
        UpdateContact.addActionListener(new mButtonListener());
        DeleteContact.addActionListener(new mButtonListener());
        droit3.add(ViewContact);
        droit3.add(UpdateContact);
        droit3.add(DeleteContact);
        droit.add(droit1);
        droit.add(droit2);
        droit.add(droit3);

        //panel centre background blue
        droit_centre = new JPanel();
        droit_centre.setBackground(Color.cyan);

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
            TreeMap<String, Contact> tmap = new TreeMap<String, Contact>();
            try {
                FileInputStream fileStream = new FileInputStream("Contact.obj");
                ObjectInputStream input = new ObjectInputStream(fileStream);
                boolean cont = true;
                while (cont) {
                    Contact obj = (Contact) input.readObject();
                    if (obj != null) {
                        tmap.put((obj.getTelList()[0]), obj);
                    } else {
                        cont = false;
                    }
                }
                input.close();
            } catch (Exception e) {
                e.getStackTrace();
            }

            if (ob == SortFname) {
                sortorder = 1;
                ArrayList listNum;
                listNum = new ArrayList(tmap.values());
                listNum.sort(new FnCompare());

                model.clear();
                for (int i = 0; i < listNum.size(); i++) {
                    model.addElement(listNum.get(i));
                }

            } else if (ob == SortLname) {
                sortorder = 2;
                ArrayList listNum;
                listNum = new ArrayList(tmap.values());
                listNum.sort(new LnCompare());

                model.clear();
                for (int i = 0; i < listNum.size(); i++) {
                    model.addElement(listNum.get(i));
                }

            } else if (ob == SortCity) {
                sortorder = 3;
                ArrayList listNum;
                listNum = new ArrayList(tmap.values());
                listNum.sort(new ctyCompare());

                model.clear();
                for (int i = 0; i < listNum.size(); i++) {
                    model.addElement(listNum.get(i));
                }
            } else if (ob == AddContact) {
                new New_Contact();
            }
        }
    }

    private class mButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            Object ob = event.getSource();
            int indexContact = list.getSelectedIndex();
            TreeMap<String, Contact> tmap = new TreeMap<String, Contact>();
            try {
                FileInputStream fileStream = new FileInputStream("Contact.obj");
                ObjectInputStream input = new ObjectInputStream(fileStream);
                boolean cont = true;
                while (cont) {
                    Contact obj = (Contact) input.readObject();
                    if (obj != null) {
                        tmap.put((obj.getTelList()[0]), obj);
                    } else {
                        cont = false;
                    }
                }
                input.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
            ArrayList listNum;
            listNum = new ArrayList(tmap.values());
            switch (sortorder) {
                case 1:
                    listNum.sort(new FnCompare());
                    break;
                case 2:
                    listNum.sort(new LnCompare());
                    break;
                case 3:
                    listNum.sort(new ctyCompare());
                    break;
            }

            if (ob == ViewContact) {
                if (indexContact != -1) {
                    Contact c = (Contact) listNum.get(indexContact);
                    new View_Contact(c);
                }
            } else if (ob == UpdateContact) {
                if (indexContact != -1) {
                    Contact c = (Contact) listNum.get(indexContact);
                    new Update_Contact(c, listNum);
                }
            } else if (ob == DeleteContact) {
                if (indexContact != -1) {
                    model.remove(indexContact);

                    ArrayList<Group> objectsList = new ArrayList<>();
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

                    TreeSet<Group> ListGrp = new TreeSet<Group>();
                    for (int i = 0; i < objectsList.size(); i++) {
                        ListGrp.add(objectsList.get(i));
                    }
                    ArrayList<Group> listgrp = new ArrayList<Group>();
                    listgrp.addAll(ListGrp);

                    Contact conDelete = (Contact) listNum.get(indexContact);
                    ArrayList<Group> grptodel = conDelete.getGrpList();
                    for (int i = 0; i < listgrp.size(); i++) {
                        for (int j = 0; j < grptodel.size(); j++) {
                            if (listgrp.get(i).getGrpname().equals(grptodel.get(j).getGrpname())) {
                                ArrayList<Contact> oldlist = listgrp.get(i).getConList();
                                for (int z = 0; z < oldlist.size(); z++) {
                                    if (oldlist.get(z).getTelList()[0].equals(conDelete.getTelList()[0])) {
                                        oldlist.remove(oldlist.get(z));
                                    }
                                }
                                listgrp.get(i).setConList(oldlist);
                            }
                        }
                    }

                    try {

                        FileOutputStream file = new FileOutputStream("Group.obj");
                        ObjectOutputStream output = new ObjectOutputStream(file);
                        for (int i = 0; i < listgrp.size(); i++) {
                            output.writeObject(listgrp.get(i));
                            output.flush();
                        }
                        output.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    listNum.remove(indexContact);
                    try {
                        ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("Contact.obj"));
                        for (int i = 0; i < listNum.size(); i++) {
                            os2.writeObject(listNum.get(i));
                            os2.flush();
                        }
                        os2.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        }
    }

}
