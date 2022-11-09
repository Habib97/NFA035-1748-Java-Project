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
public class Update_Group extends JFrame {

    JPanel haut, gauche, droit, droit_centre, droit1, droit2, droit3;
    JLabel gstcon, grp_gauche, grpname, grpdesc;
    JButton save, cancel;
    JTextField grpnametxt, grpdesctxt;
    JTable cont_num;
    static Group Updgrp;
    static ArrayList<Group> Newlistgrp;

    Update_Group(Group g, ArrayList a) {
        Updgrp = g;
        Newlistgrp = a;
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
        gauche.setPreferredSize(new Dimension(160, 450));
        grp_gauche = new JLabel("Update Group");
        grp_gauche.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        grp_gauche.setForeground(Color.red);
        gauche.add(grp_gauche);

        //panel a droit contient les entrées,tableu et les boutons
        droit = new JPanel();
        droit.setLayout(new BoxLayout(droit, BoxLayout.Y_AXIS));
        droit.setPreferredSize(new Dimension(350, 450));
        //panel qui contient les entrées d'un contact et un label du tableau
        droit1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        droit1.setPreferredSize(new Dimension(350, 110));
        grpname = new JLabel("Group name");
        grpdesc = new JLabel("Description ");
        grpnametxt = new JTextField(25);
        grpdesctxt = new JTextField(25);
        grpnametxt.setText(g.getGrpname());
        grpdesctxt.setText(g.getGrpdesc());
        droit1.add(grpname);
        droit1.add(grpnametxt);
        droit1.add(grpdesc);
        droit1.add(grpdesctxt);

        //panel qui contient tableau de numero et checkbox des grps
        droit2 = new JPanel();
        droit2.setLayout(new BoxLayout(droit2, BoxLayout.Y_AXIS));
        droit2.setPreferredSize(new Dimension(335, 380));
        String column[] = {"Contact name", "City", "Add to group"};
        cont_num = new JTable();
        DefaultTableModel defTableModel = new DefaultTableModel(column, 20) {
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 0:
                    case 1:
                        return false;
                    default:
                        return true;
                }
            }
        };

        cont_num = new JTable() {
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        cont_num.setModel(defTableModel);
        cont_num.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(cont_num);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        ArrayList<Contact> objectsList = null;
        try {
            FileInputStream fileStream = new FileInputStream("Contact.obj");
            ObjectInputStream input = new ObjectInputStream(fileStream);
            objectsList = new ArrayList<>();
            boolean cont = true;
            while (cont) {
                Contact obj = (Contact) input.readObject();
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
        TreeMap<String, Contact> tmap = new TreeMap<String, Contact>();
        for (int i = 0; i < objectsList.size(); i++) {
            tmap.put((objectsList.get(i).getTelList()[0]), objectsList.get(i));
        }
        Set<Map.Entry<String, Contact>> entries
                = tmap.entrySet();

        // printing all contact in jtable
        ArrayList Listconsort = new ArrayList(tmap.values());
        Listconsort.sort(new FnCompare());
        ArrayList<Contact> listconNew = new ArrayList<Contact>();
        listconNew.addAll(Listconsort);

        for (int i = 0; i < Listconsort.size(); i++) {
            for (int j = 0; j < cont_num.getColumnCount(); j++) {
                String s = Listconsort.get(i).toString();
                int z = s.indexOf('-');
                if (j == 0) {
                    cont_num.setValueAt(s.substring(0, z - 1), i, j);
                } else if (j == 1) {
                    cont_num.setValueAt(s.substring(z + 2), i, j);
                } else if (j == 2) {
                    cont_num.setValueAt(false, i, j);
                }
            }
        }
        //check true contact in group g
        for (int i = 0; i < listconNew.size(); i++) {
            for (int j = 0; j < g.getConList().size(); j++) {
                if (listconNew.get(i).getTelList()[0].equals(g.getConList().get(j).getTelList()[0])) {
                    cont_num.setValueAt(true, i, 2);
                }
            }
        }

        droit2.add(sp);

        //panel qui contient les boutons de save et cancel
        droit3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        droit3.setPreferredSize(new Dimension(335, 40));
        save = new JButton("Save Group");
        cancel = new JButton("cancel");
        save.addActionListener(new ButtonListener());
        cancel.addActionListener(new ButtonListener());
        droit3.add(save);
        droit3.add(cancel);

        droit.add(droit1);
        droit.add(droit2);
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
                if (grpnametxt.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Un groupe doit avoir un nom !!", "Error message", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!(grpdesctxt.getText().length() == 0)) {
                        Updgrp.setGrpDesc(grpdesctxt.getText());
                    }
                    Newlistgrp.remove(Updgrp);

                    ArrayList<Contact> objectsList = null;
                    try {
                        FileInputStream fileStream = new FileInputStream("Contact.obj");
                        ObjectInputStream input = new ObjectInputStream(fileStream);
                        objectsList = new ArrayList<>();
                        boolean cont = true;
                        while (cont) {
                            Contact obj = (Contact) input.readObject();
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
                    TreeMap<String, Contact> tmap = new TreeMap<String, Contact>();
                    for (int i = 0; i < objectsList.size(); i++) {
                        tmap.put((objectsList.get(i).getTelList()[0]), objectsList.get(i));
                    }
                    Set<Map.Entry<String, Contact>> entries
                            = tmap.entrySet();

                    ArrayList Listconsort = new ArrayList(tmap.values());
                    Listconsort.sort(new FnCompare());
                    ArrayList<Contact> listconNew = new ArrayList<Contact>();
                    listconNew.addAll(Listconsort);
                    //Suprrimer tous grp qui était dans les contact
                    ArrayList<Contact> listconGRP = Updgrp.getConList();
                    ArrayList<Contact> Newcon = new ArrayList<>();

                    for (int i = 0; i < listconNew.size(); i++) {
                        for (int j = 0; j < listconGRP.size(); j++) {
                            if (listconNew.get(i).getTelList()[0].equals(listconGRP.get(j).getTelList()[0])) {
                                ArrayList<Group> oldlist = listconNew.get(i).getGrpList();
                                for (int z = 0; z < oldlist.size(); z++) {
                                    if (oldlist.get(z).getGrpname().equals(Updgrp.getGrpname())) {
                                        oldlist.remove(oldlist.get(z));
                                    }
                                }
                                listconNew.get(i).setGrpList(oldlist);
                            }
                        }
                    }

                    //mettre le grp dans la liste des contact
                    for (int i = 0; i < Listconsort.size(); i++) {
                        Boolean isChecked = Boolean.valueOf(cont_num.getValueAt(i, 2).toString());
                        if (isChecked) {
                            ArrayList<Group> oldgrp = listconNew.get(i).getGrpList();
                            oldgrp.add(Updgrp);
                            listconNew.get(i).setGrpList(oldgrp);
                            Newcon.add(listconNew.get(i));
                        }
                    }
                    Updgrp.setConList(Newcon);
                    Newlistgrp.add(Updgrp);
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
                        for (int i = 0; i < Newlistgrp.size(); i++) {
                            os2.writeObject(Newlistgrp.get(i));
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
