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
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Habib
 */
public class Groups_page extends JFrame {

    JPanel haut, gauche, droit, droit_centre, droit1, droit2, droit3;
    JLabel gstcon, grp_gauche, Listgrp;
    JButton AddGroup, UpdateGrp, DeleteGrp;
    JTable Grp_name, Grp_details;

    Groups_page() {
        setTitle("Projet NFA035");
        setSize(600, 500);

        //panel en haut
        haut = new JPanel();
        haut.setPreferredSize(new Dimension(50, 40));
        gstcon = new JLabel("Gestion des Contacts");
        gstcon.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        gstcon.setForeground(Color.blue);
        haut.add(gstcon);

        //panel a gauche avec le boutton pour aider des grps
        gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 40));
        gauche.setPreferredSize(new Dimension(150, 450));
        grp_gauche = new JLabel("Groups");
        grp_gauche.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        grp_gauche.setForeground(Color.red);
        AddGroup = new JButton("Add new Group");
        AddGroup.addActionListener(new ButtonListener());
        gauche.add(grp_gauche);
        gauche.add(AddGroup);

        //panel a droit 
        droit = new JPanel();
        droit.setLayout(new BoxLayout(droit, BoxLayout.Y_AXIS));
        droit.setPreferredSize(new Dimension(250, 450));

        //panel qui contient Label on the top tables
        droit1 = new JPanel();
        Listgrp = new JLabel("List of groups");
        droit1.add(Listgrp);

        //panel qui contient deux jtable 
        droit2 = new JPanel(new GridLayout(2, 1, 10, 25));
        String column[] = {"Group name", "Nb.of contacts"};
        Grp_name = new JTable();
        DefaultTableModel defTableModel = new DefaultTableModel(column, 8) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        Grp_name.setModel(defTableModel);
        Grp_name.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(Grp_name);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        String column1[] = {"Contact Name", "Contact City"};
        Grp_details = new JTable();
        DefaultTableModel defTableModel1 = new DefaultTableModel(column1, 8) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        Grp_details.setModel(defTableModel1);
        Grp_details.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp1 = new JScrollPane(Grp_details);
        sp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //importer tous les group d un file et les mettre dans le premier tableau
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
        for (int i = 0; i < Grp_name.getRowCount(); i++) {
            if (i < listgrp.size()) {
                for (int j = 0; j < Grp_name.getColumnCount(); j++) {
                    if (j == 0) {
                        Grp_name.setValueAt(listgrp.get(i).getGrpname(), i, j);
                    } else if (j == 1) {
                        Grp_name.setValueAt(listgrp.get(i).getConList().size(), i, j);
                    }
                }
            } else {
                break;
            }
        }
        //printing la deuxieme table quand un grp isselected du premier table 
        Grp_name.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                String s1 = (String) Grp_name.getValueAt(Grp_name.getSelectedRow(), Grp_name.getSelectedColumn());
                if (!(s1 == null)) {
                    if (!event.getValueIsAdjusting()) {
                        for (int i = 0; i < Grp_details.getRowCount(); i++) {
                            for (int j = 0; j < Grp_details.getColumnCount(); j++) {
                                Grp_details.setValueAt("", i, j);
                            }
                        }
                        int rowIndex = Grp_name.getSelectedRow();
                        ArrayList<Contact> listcon = listgrp.get(rowIndex).getConList();
                        for (int i = 0; i < Grp_details.getRowCount(); i++) {
                            if (i < listcon.size()) {

                                for (int j = 0; j < Grp_details.getColumnCount(); j++) {
                                    if (j == 0) {
                                        Grp_details.setValueAt(listcon.get(i).getFname() + " " + listcon.get(i).getLname(), i, j);
                                    } else if (j == 1) {
                                        Grp_details.setValueAt(listcon.get(i).getCity(), i, j);
                                    }
                                }
                            } else {
                                break;
                            }
                        }

                    }
                }
            }
        }
        );
        //ouvrir view contact de la selection de deuxieme jtable d'un contact
        Grp_details.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                String s1 = (String) Grp_details.getValueAt(Grp_details.getSelectedRow(), Grp_details.getSelectedColumn());
                boolean b = "".equals(s1) || s1 == null;
                if (!b) {
                    if (!event.getValueIsAdjusting()) {
                        int rowIndex = Grp_details.getSelectedRow();
                        Contact viewCon = listgrp.get(Grp_name.getSelectedRow()).getConList().get(rowIndex);
                        new View_Contact(viewCon);
                    }
                }
            }
        });

        droit2.add(sp);
        droit2.add(sp1);

        //panel qui contient les boutons de changement de group
        droit3 = new JPanel();
        UpdateGrp = new JButton("Update Group");
        DeleteGrp = new JButton("Delete");
        UpdateGrp.addActionListener(new ButtonListener());
        DeleteGrp.addActionListener(new ButtonListener());
        droit3.add(UpdateGrp);
        droit3.add(DeleteGrp);
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
            int indexGroup = Grp_name.getSelectedRow();
            int close;
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
            if (ob == AddGroup) {
                new New_Group();
            } else if (ob == UpdateGrp) {
                if (indexGroup != -1) {
                    new Update_Group(listgrp.get(indexGroup), listgrp);
                }
            } else if (ob == DeleteGrp) {
                if (indexGroup != -1) {
                    close = JOptionPane.showConfirmDialog(null, "Vous voulez supprimer ce groupe ?", "Confirm message", JOptionPane.YES_NO_OPTION);
                    if (close == JOptionPane.YES_OPTION) {
                        ArrayList<Contact> objectsListcon = null;
                        try {
                            FileInputStream fileStream = new FileInputStream("Contact.obj");
                            ObjectInputStream input = new ObjectInputStream(fileStream);
                            objectsListcon = new ArrayList<>();
                            boolean cont = true;
                            while (cont) {
                                Contact obj = (Contact) input.readObject();
                                if (obj != null) {
                                    objectsListcon.add(obj);
                                } else {
                                    cont = false;
                                }
                            }

                            input.close();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        TreeMap<String, Contact> tmap = new TreeMap<String, Contact>();
                        for (int i = 0; i < objectsListcon.size(); i++) {
                            tmap.put((objectsListcon.get(i).getTelList()[0]), objectsListcon.get(i));
                        }
                        Set<Map.Entry<String, Contact>> entries
                                = tmap.entrySet();

                        ArrayList Listconsort = new ArrayList(tmap.values());
                        Listconsort.sort(new FnCompare());
                        ArrayList<Contact> listconNew = new ArrayList<Contact>();
                        listconNew.addAll(Listconsort);
                        //Suprrimer tous grp qui était dans les contact
                        ArrayList<Contact> listconGRP = listgrp.get(indexGroup).getConList();
                        ArrayList<Contact> Newcon = new ArrayList<>();

                        for (int i = 0; i < listconNew.size(); i++) {
                            for (int j = 0; j < listconGRP.size(); j++) {
                                if (listconNew.get(i).getTelList()[0].equals(listconGRP.get(j).getTelList()[0])) {
                                    ArrayList<Group> oldlist = listconNew.get(i).getGrpList();
                                    for (int z = 0; z < oldlist.size(); z++) {
                                        if (oldlist.get(z).getGrpname().equals(listgrp.get(indexGroup).getGrpname())) {
                                            oldlist.remove(oldlist.get(z));
                                        }
                                    }
                                    listconNew.get(i).setGrpList(oldlist);
                                }
                            }
                        }
                        listgrp.remove(listgrp.get(indexGroup));
                        //  ((DefaultTableModel) Grp_name.getModel()).removeRow(Grp_name.getSelectedRow());
                        try {
                            ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("Contact.obj"));
                            for (int i = 0; i < listconNew.size(); i++) {
                                os2.writeObject(listconNew.get(i));
                                os2.flush();
                            }
                            os2.close();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        try {
                            ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("Group.obj"));
                            for (int i = 0; i < listgrp.size(); i++) {
                                os2.writeObject(listgrp.get(i));
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
}
