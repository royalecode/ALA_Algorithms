package com.company;

import edu.salleurl.arcade.Arcade;
import edu.salleurl.arcade.ArcadeBuilder;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.words.model.WordsSolver;

public class Main {

    private static final int LABYRINTH_COLUMNS = 84;
    private static final int LABYRINTH_ROWS = 84;
    private static final int WORDS_COLUMNS = 12;
    private static final int WORDS_ROWS = 12;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();

        ArcadeBuilder builder = new ArcadeBuilder().setSeed(42);

        builder.setWordsColumns(WORDS_COLUMNS)
                .setWordsRows(WORDS_ROWS)
                .setWordsSolver(getClassSolutionWords(menu.getWordsOption()));

        builder.setLabyrinthColumns(LABYRINTH_COLUMNS)
                .setLabyrinthRows(LABYRINTH_ROWS)
                .setLabyrinthSolver(getClassSolutionLabyrinth(menu.getLabyrinthoOption()));

        builder.build().run();

//        for (int i = 0; i < 10; i++) {
//            System.out.println("DIM: " + 10 * (i + 1) + "/" + 10 * (i + 1));
//            builder.setLabyrinthColumns(10 * (i + 1))
//                    .setLabyrinthRows(10 * (i + 1))
//                    .setLabyrinthSolver(getClassSolutionLabyrinth(menu.getLabyrinthoOption()));
//
//            builder.build().run();
//        }
    }

    private static LabyrinthSolver getClassSolutionLabyrinth(int option) {
        if (option == 1) return new BacktrackingLabyrinthSolver();
        if (option == 2) return new BacktrackingLabyrinthSolverImproved();
        if (option == 3) return new BranchAndBoundLabryinthSolver();
        return null;
    }

    private static WordsSolver getClassSolutionWords(int option) {
        if (option == 1) return new DemoWordsSolver();
        if (option == 2) return new DemoWordsSolver();
        return null;
    }
}
