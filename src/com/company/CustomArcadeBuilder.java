package com.company;

import edu.salleurl.arcade.ArcadeBuilder;
import edu.salleurl.arcade.labyrinth.model.LabyrinthGenerator;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.words.model.WordsGenerator;
import edu.salleurl.arcade.words.model.WordsSolver;

import java.lang.reflect.Field;
import java.util.Random;

public class CustomArcadeBuilder extends ArcadeBuilder {

    public CustomArcade customBuild() {
        try {
            Random random;

            Field seedField = ArcadeBuilder.class.getDeclaredField("seed");
            Field seededField = ArcadeBuilder.class.getDeclaredField("seeded");
            Field hasLabyrinthSolverField = ArcadeBuilder.class.getDeclaredField("hasLabyrinthSolver");
            Field hasWordsSolverField = ArcadeBuilder.class.getDeclaredField("hasWordsSolver");
            Field labyrinthRowsField = ArcadeBuilder.class.getDeclaredField("labyrinthRows");
            Field labyrinthColumnsField = ArcadeBuilder.class.getDeclaredField("labyrinthColumns");
            Field wordsRowsField = ArcadeBuilder.class.getDeclaredField("wordsRows");
            Field wordsColumnsField = ArcadeBuilder.class.getDeclaredField("wordsColumns");
            Field labyrinthSolverField = ArcadeBuilder.class.getDeclaredField("labyrinthSolver");
            Field wordsSolverField = ArcadeBuilder.class.getDeclaredField("wordsSolver");

            seedField.setAccessible(true);
            seededField.setAccessible(true);
            hasLabyrinthSolverField.setAccessible(true);
            hasWordsSolverField.setAccessible(true);
            labyrinthRowsField.setAccessible(true);
            labyrinthColumnsField.setAccessible(true);
            wordsRowsField.setAccessible(true);
            wordsColumnsField.setAccessible(true);
            labyrinthSolverField.setAccessible(true);
            wordsSolverField.setAccessible(true);

            long seed = (long) seedField.get(this);
            boolean seeded = (boolean) seededField.get(this);
            boolean hasLabyrinthSolver = (boolean) hasLabyrinthSolverField.get(this);
            boolean hasWordsSolver = (boolean) hasWordsSolverField.get(this);
            int labyrinthRows = (int) labyrinthRowsField.get(this);
            int labyrinthColumns = (int) labyrinthColumnsField.get(this);
            int wordsRows = (int) wordsRowsField.get(this);
            int wordsColumns = (int) wordsColumnsField.get(this);
            LabyrinthSolver labyrinthSolver = (LabyrinthSolver) labyrinthSolverField.get(this);
            WordsSolver wordsSolver = (WordsSolver) wordsSolverField.get(this);

            random = new Random(seed);

            if (hasLabyrinthSolver && hasWordsSolver) {
                return new CustomArcade(LabyrinthGenerator.generate(labyrinthRows, labyrinthColumns, random), WordsGenerator.generate(wordsRows, wordsColumns, random), WordsGenerator.getLastNeedle(), labyrinthSolver, wordsSolver);
            } else {
                throw new IllegalStateException("Please make sure you set both a LabyrinthSolver and a WordsSolver. The project won't solve itself :)");
            }
        } catch (Exception ignore) {
            return null;
        }
    }

    public CustomArcadeBuilder setSeed(long seed, int foo) {
        Field f1 = null;
        try {
            f1 = ArcadeBuilder.class.getDeclaredField("seed");
            f1.setAccessible(true);
            f1.set(this, seed);
        } catch (Exception ignore) {
        }
        return this;
    }
}
