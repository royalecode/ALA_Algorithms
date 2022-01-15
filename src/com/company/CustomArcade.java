//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthController;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.view.JLabyrinthPanel;
import edu.salleurl.arcade.words.controller.WordsController;
import edu.salleurl.arcade.words.model.WordsSolver;
import edu.salleurl.arcade.words.view.JWordsPanel;

public class CustomArcade {
    private Cell[][] labyrinth;
    private LabyrinthSolver labyrinthSolver;
    private LabyrinthController labyrinthController;
    private char[][] words;
    private String needle;
    private WordsSolver wordsSolver;
    private WordsController wordsController;
    private CustomArcadeView view;

    public CustomArcade(Cell[][] labyrinth, char[][] words, String needle, LabyrinthSolver labyrinthSolver, WordsSolver wordsSolver) {
        this.labyrinth = labyrinth;
        this.words = words;
        this.needle = needle;
        this.labyrinthSolver = labyrinthSolver;
        this.wordsSolver = wordsSolver;
        JLabyrinthPanel jLabyrinthPanel = new JLabyrinthPanel(labyrinth.length, labyrinth[0].length);
        JWordsPanel jWordsPanel = new JWordsPanel(words.length, words[0].length);
        this.view = new CustomArcadeView(jLabyrinthPanel, jWordsPanel);
        this.labyrinthController = new LabyrinthController(labyrinth, labyrinthSolver, jLabyrinthPanel);
        this.wordsController = new WordsController(words, needle, wordsSolver, jWordsPanel);
//        jLabyrinthPanel.renderBase(labyrinth);
//        jWordsPanel.renderBase(words, needle);
//        this.view.setVisible(true);
    }

    public void run() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var2) {
        }


        this.labyrinthController.run();
//        this.view.showLabyrinthResult(this.labyrinthController.run());
        this.view.cycle();
        this.wordsController.run();
//        this.view.showWordsResult(this.wordsController.run());
        this.view.dispose();
    }
}
