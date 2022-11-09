/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Habib
 */
public class MainPage extends JFrame {

    JPanel haut, gauche, droit;
    JLabel gstcon;
    JButton contacts, groups;

    MainPage() {
        setTitle("Projet NFA035");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel en haut
        haut = new JPanel();
        haut.setPreferredSize(new Dimension(50, 40));
        gstcon = new JLabel("Gestion des Contacts");
        gstcon.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        gstcon.setForeground(Color.blue);
        haut.add(gstcon);
        
        //panel a gauche avec deux bouttons
        gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 40));
        gauche.setPreferredSize(new Dimension(100, 450));
        contacts = new JButton("Contacts");
        groups = new JButton("Groups");
        contacts.addActionListener(new ButtonListener());
        groups.addActionListener(new ButtonListener());
        gauche.add(contacts);
        gauche.add(groups);
        
        //panel a droit avec un bacground blue
        droit = new JPanel();
        droit.setPreferredSize(new Dimension(380, 450));
        droit.setBackground(Color.cyan);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(haut, BorderLayout.NORTH);
        cp.add(droit, BorderLayout.EAST);
        cp.add(gauche, BorderLayout.WEST);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            Object ob = event.getSource();
            if (ob == contacts) {
                new Contacts_page();
            } else if (ob == groups) {
                new Groups_page();
            }
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}
