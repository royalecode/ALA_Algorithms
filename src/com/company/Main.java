package com.company;

import edu.salleurl.arcade.Arcade;
import edu.salleurl.arcade.ArcadeBuilder;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import static edu.salleurl.arcade.labyrinth.model.enums.Direction.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hola");
        Arcade arcade = new ArcadeBuilder()
                .setLabyrinthColumns(24)
                .setLabyrinthRows(24)
                .setWordsColumns(12)
                .setWordsRows(12)
                // Opcional, per fixar un input en comptes d'obtenir-ne un d'aleatori
                .setSeed(42)
                // DemoLabyrinthSolver implementa LabyrinthSolver
                .setLabyrinthSolver(new DemoLabyrinthSolver())
                // DemoWordsSolver implementa WordsSolver
                .setWordsSolver(new DemoWordsSolver())
                .build();
        arcade.run();

    }
}
