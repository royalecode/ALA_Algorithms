package com.company;

import edu.salleurl.arcade.Arcade;
import edu.salleurl.arcade.ArcadeBuilder;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.words.model.WordsSolver;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();

        Arcade arcade = new ArcadeBuilder()
                .setLabyrinthColumns(84)
                .setLabyrinthRows(84)
                .setWordsColumns(12)
                .setWordsRows(12)
                // Opcional, per fixar un input en comptes d'obtenir-ne un d'aleatori
                .setSeed(42)
                // DemoLabyrinthSolver implementa LabyrinthSolver
                .setLabyrinthSolver(getClassSolutionLabyrinth(menu.getLabyrinthoOption()))
                // DemoWordsSolver implementa WordsSolver
                .setWordsSolver(getClassSolutionWords(menu.getWordsOption()))
                .build();
        arcade.run();

    }

    private static LabyrinthSolver getClassSolutionLabyrinth(int option) {
        if (option == 1) return new BacktrackingLabyrinthSolver();
        if (option == 2) return new BacktrackingLabyrinthSolverImproved();
        //if (option == 3) return new BranchAndBoundLabryinthSolver();
        return null;
    }

    private static WordsSolver getClassSolutionWords(int option) {
        if (option == 1) return new DemoWordsSolver();
        if (option == 2) return new DemoWordsSolver();
        return null;
    }


}
