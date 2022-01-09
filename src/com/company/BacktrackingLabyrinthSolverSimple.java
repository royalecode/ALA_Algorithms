package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class BacktrackingLabyrinthSolverSimple extends BacktrackingLabyrinthSolver implements LabyrinthSolver {

    public BacktrackingLabyrinthSolverSimple () {
        super();
    }

    @Override
    public List<Direction> solve(Cell[][] laberint, LabyrinthRenderer labyrinthRenderer) {
        super.laberint = laberint;
        calcularOrigenAndDesti();
        Instant start = Instant.now();
        laberintV1(this.configuracio,0);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
        labyrinthRenderer.render(laberint, translateConfiguration(this.Xmillor));
        return translateConfiguration(this.Xmillor);
    }
}
