//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import edu.salleurl.arcade.labyrinth.view.JLabyrinthPanel;
import edu.salleurl.arcade.words.view.JWordsPanel;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class CustomArcadeView extends JFrame {
    private static final String LABYRINTH = "Arcade - Labyrinth";
    private static final String WORDS = "Arcade - Words";
    private String card;
    private CardLayout cardLayout = new CardLayout();

    public CustomArcadeView(JLabyrinthPanel jLabyrinthPanel, JWordsPanel jWordsPanel) {
        this.setLayout(this.cardLayout);
        this.add(jLabyrinthPanel, "Arcade - Labyrinth");
        this.pack();
        this.add(jWordsPanel, "Arcade - Words");
        this.card = "Arcade - Labyrinth";
        this.setTitle("Arcade - Labyrinth");
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
    }

    public void cycle() {
        String var1 = this.card;
        byte var2 = -1;
        switch(var1.hashCode()) {
            case -1847735940:
                if (var1.equals("Arcade - Labyrinth")) {
                    var2 = 0;
                }
                break;
            case 1676346854:
                if (var1.equals("Arcade - Words")) {
                    var2 = 1;
                }
        }

        switch(var2) {
            case 0:
                this.cardLayout.show(this.getContentPane(), "Arcade - Words");
                this.card = "Arcade - Words";
                this.setTitle("Arcade - Words");
                break;
            case 1:
                this.cardLayout.show(this.getContentPane(), "Arcade - Labyrinth");
                this.card = "Arcade - Labyrinth";
                this.setTitle("Arcade - Labyrinth");
        }
        this.pack();
        this.setLocationRelativeTo((Component)null);
    }

    public void showLabyrinthResult(boolean ok) {
        if (ok) {
            JOptionPane.showMessageDialog(this, "Correct result for the Labyrinth game. Great work!", "Labyrinth OK", 1);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect result for the Labyrinth game. The program will continue, but you won't pass!", "Labyrinth Error", 0);
        }
    }

    public void showWordsResult(boolean ok) {
        if (ok) {
            JOptionPane.showMessageDialog(this, "Correct result for the Words game. Great work!", "Words OK", 1);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect result for the Words game. You won't pass!", "Words Error", 0);
        }
    }
}
