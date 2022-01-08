package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.List;

public class BacktrackingLabyrinthSolverSimple extends BacktrackingLabyrinthSolver implements LabyrinthSolver {

    public BacktrackingLabyrinthSolverSimple () {
        super();
    }

    @Override
    public List<Direction> solve(Cell[][] laberint, LabyrinthRenderer labyrinthRenderer) {
        super.laberint = laberint;
        calcularOrigenAndDesti();
        laberintV1(this.configuracio,0);
        labyrinthRenderer.render(laberint, translateConfiguration(this.Xmillor));
        return translateConfiguration(this.Xmillor);
    }
}
