package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingLabyrinthSolverImproved extends BacktrackingLabyrinthSolver implements LabyrinthSolver {

    private LabyrinthMarcatge m;

    public BacktrackingLabyrinthSolverImproved () {
        super();
        m = new LabyrinthMarcatge();
    }

    @Override
    public List<Direction> solve(Cell[][] laberint, LabyrinthRenderer labyrinthRenderer) {
        this.laberint = laberint;
        calcularOrigenAndDesti();
        laberintV1(this.configuracio,0);
        labyrinthRenderer.render(laberint, translateConfiguration(this.Xmillor));
        return translateConfiguration(this.Xmillor);
    }

    public void marcar (List<Integer> x, int k) {
        if (k>0) m.historial.add(k, new Coordenada(m.historial.get(k-1).getX() , m.historial.get(k-1).getY()));
        else m.historial.add(k, new Coordenada(ORIGEN.getX(), ORIGEN.getY()));

        if (1 == x.get(k)) m.historial.get(k).setY(m.historial.get(k).getY() - 1);
        if (2 == x.get(k)) m.historial.get(k).setX(m.historial.get(k).getX() + 1);
        if (3 == x.get(k)) m.historial.get(k).setY(m.historial.get(k).getY() + 1);
        if (4 == x.get(k)) m.historial.get(k).setX(m.historial.get(k).getX() - 1);
    }

    @Override
    public boolean solucio(List<Integer> x, int k) {
        if (m.historial.get(k).getX() < 0 || m.historial.get(k).getY() < 0 || m.historial.get(k).getY() > laberint.length
                || m.historial.get(k).getX() > laberint[0].length) return false;
        if (this.laberint[m.historial.get(k).getY()][m.historial.get(k).getX()].name().equals(EXIT)) return true;
        return false;
    }

    @Override
    public boolean bona(List<Integer> x, int k) {
        if (m.historial.get(k).getX() < 0 || m.historial.get(k).getY() < 0 || m.historial.get(k).getY() > laberint.length
                || m.historial.get(k).getX() > laberint[0].length) return false;

        if (this.laberint[m.historial.get(k).getY()][m.historial.get(k).getX()].name().equals(WALL)) {
            return false;
        }

        for (int i = 0; i < (k-1); i++) {
            if (m.historial.get(i).getX() == m.historial.get(k).getX()
                    && m.historial.get(i).getY() == m.historial.get(k).getY()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tractarSolucio (List<Integer> x, int k) {
        this.Vmillor = k;
        this.Xmillor = new ArrayList<Integer>(x);
    }

    @Override
    public void laberintV1 (List<Integer> x, int k) {
        if (k >= x.size()) x.add(k, 0);
        else x.set(k, 0);
        while (x.get(k) < 4) {
            x.set(k, x.get(k) + 1);
            marcar(x, k);

            if (solucio(x, k)) {
                if (bona(x,k)) tractarSolucio(x,k);
            } else {
                if (bona(x,k)) {
                    if (this.Vmillor > k || Vmillor == -1) laberintV1(x, k+1);
                }
            }
        }
    }

    @Override
    public List<Direction> translateConfiguration(List<Integer> x) {
        List<Direction> configuracio = new ArrayList<Direction>();
        for (int i = 0; i < this.Vmillor+1; i++) {
            if (1 == x.get(i)) configuracio.add(Direction.UP);
            if (2 == x.get(i)) configuracio.add(Direction.RIGHT);
            if (3 == x.get(i)) configuracio.add(Direction.DOWN);
            if (4 == x.get(i)) configuracio.add(Direction.LEFT);
        }
        return configuracio;
    }
}
