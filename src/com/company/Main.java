package com.company;

import edu.salleurl.arcade.ArcadeBuilder;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.words.model.WordsSolver;

import java.util.Date;

public class Main {
    private static final int LABYRINTH_COLUMNS = 89;
    private static final int LABYRINTH_ROWS = 89;
    private static final int WORDS_COLUMNS = 40;
    private static final int WORDS_ROWS = 40;
    private static final int SEED = 42;

    private static final int ANALYSIS_ITERATIONS = 5;
    private static final int ANALYSIS_MAX_DIMENSION = 200;

    private static Menu menu;

    public static void main(String[] args) {
        menu = new Menu();
        menu.start();

        if (menu.isAnalyze()) {
            generateAnalysis(new CustomArcadeBuilder());
        } else {
            ArcadeBuilder builder = new ArcadeBuilder().setSeed(SEED);

            builder.setWordsColumns(WORDS_COLUMNS)
                    .setWordsRows(WORDS_ROWS)
                    .setWordsSolver(getClassSolutionWords(menu.getWordsOption()));

            builder.setLabyrinthColumns(LABYRINTH_COLUMNS)
                    .setLabyrinthRows(LABYRINTH_ROWS)
                    .setLabyrinthSolver(getClassSolutionLabyrinth(menu.getLabyrinthoOption()));

            builder.build().run();
        }

/*        builder.setLabyrinthColumns(LABYRINTH_COLUMNS)
                .setLabyrinthRows(LABYRINTH_ROWS)
                .setLabyrinthSolver(new BacktrackingLabyrinthSolver())
                .build().run();

        builder.setLabyrinthColumns(LABYRINTH_COLUMNS)
                .setLabyrinthRows(LABYRINTH_ROWS)
                .setLabyrinthSolver(new BacktrackingLabyrinthSolverImproved())
                .build().run();*/

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
        if (option == 1) return new BacktrackingLabyrinthSolver(menu.isVisualize(), menu.isAnalyze());
        if (option == 2) return new BacktrackingLabyrinthSolverImproved(menu.isVisualize(), menu.isAnalyze());
        if (option == 3) return new BranchAndBoundLabryinthSolver(menu.isVisualize(), menu.isAnalyze());
        return null;
    }

    private static WordsSolver getClassSolutionWords(int option) {
        if (option == 1) return new GreedyWordsSolver(menu.isVisualize(), menu.isAnalyze());
        if (option == 2) return new BacktrackingWordsSolver(menu.isVisualize(), menu.isAnalyze());
        return null;
    }

    private static void generateAnalysis(ArcadeBuilder builder) {
        WordsSolver wordsAlgorithm = getClassSolutionWords(menu.getWordsOption());
        LabyrinthSolver labyrinthoAlgorithm = getClassSolutionLabyrinth(menu.getLabyrinthoOption());

        int wordsAlgorithmId = 0;
        int labyrinthoAlgorithmId = 0;

        if (wordsAlgorithm instanceof GreedyWordsSolver) {
            wordsAlgorithmId = AnalysisPersitance.WORDS_GREED;
        } else if (wordsAlgorithm instanceof BacktrackingWordsSolver) {
            wordsAlgorithmId = AnalysisPersitance.WORDS_BACK;
        }

        if (labyrinthoAlgorithm instanceof BacktrackingLabyrinthSolverImproved) {
            labyrinthoAlgorithmId = AnalysisPersitance.LABYRINTH_BACK_IMPROVED;
        } else if (labyrinthoAlgorithm instanceof BacktrackingLabyrinthSolver) {
            labyrinthoAlgorithmId = AnalysisPersitance.LABYRINTH_BACK;
        } else if (labyrinthoAlgorithm instanceof BranchAndBoundLabryinthSolver) {
            labyrinthoAlgorithmId = AnalysisPersitance.LABYRINTH_BRANCH;
        }

        for (int j = 0; j < ANALYSIS_ITERATIONS; j++) {
            builder.setSeed(new Date().getTime());
            for (int i = 5; i < ANALYSIS_MAX_DIMENSION; i = i + 2) {
                wordsAlgorithm = getClassSolutionWords(menu.getWordsOption());
                labyrinthoAlgorithm = getClassSolutionLabyrinth(menu.getLabyrinthoOption());

                AnalysisPersitance.getInstance().createRecord(labyrinthoAlgorithmId, i);
                AnalysisPersitance.getInstance().createRecord(wordsAlgorithmId, i);
                System.out.println("___________________________________________");
                System.out.println("Dimension: " + i);

                builder.setWordsColumns(i)
                        .setWordsRows(i)
                        .setWordsSolver(wordsAlgorithm);

                builder.setLabyrinthColumns(i)
                        .setLabyrinthRows(i)
                        .setLabyrinthSolver(labyrinthoAlgorithm);

                if (builder instanceof CustomArcadeBuilder customArcadeBuilder) {
                    customArcadeBuilder.customBuild().run();
                } else {
                    builder.build().run();
                }
            }
            AnalysisPersitance.getInstance().exportToFile("data" + j + ".json");
        }
    }
}
