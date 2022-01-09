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
        return startAlgorithm(laberint, labyrinthRenderer);
    }
}
