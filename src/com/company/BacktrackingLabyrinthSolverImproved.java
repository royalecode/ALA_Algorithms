package com.company;

import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingLabyrinthSolverImproved extends BacktrackingLabyrinthSolver {


    private LabyrinthMarcatge m;

    public BacktrackingLabyrinthSolverImproved() {
        super();
        m = new LabyrinthMarcatge();
        this.idAnalysis = AnalysisPersitance.LABYRINTH_BACK_IMPROVED;
    }

    public BacktrackingLabyrinthSolverImproved(boolean visualize) {
        super(visualize);
        m = new LabyrinthMarcatge();
        this.idAnalysis = AnalysisPersitance.LABYRINTH_BACK_IMPROVED;
    }

    public void marcar(List<Integer> x, int k) {
        if (k > 0) m.historial.add(k, new Coordenada(m.historial.get(k - 1).getX(), m.historial.get(k - 1).getY()));
        else m.historial.add(k, new Coordenada(ORIGEN.getX(), ORIGEN.getY()));

        if (1 == x.get(k)) m.historial.get(k).setY(m.historial.get(k).getY() - 1);
        if (2 == x.get(k)) m.historial.get(k).setX(m.historial.get(k).getX() + 1);
        if (3 == x.get(k)) m.historial.get(k).setY(m.historial.get(k).getY() + 1);
        if (4 == x.get(k)) m.historial.get(k).setX(m.historial.get(k).getX() - 1);
    }

    @Override
    public boolean solucio(List<Integer> x, int k) {
//        if (m.historial.get(k).getX() < 0 || m.historial.get(k).getY() < 0 || m.historial.get(k).getY() > laberint.length
//                || m.historial.get(k).getX() > laberint[0].length) return false;
        if (this.laberint[m.historial.get(k).getY()][m.historial.get(k).getX()].name().equals(EXIT)) return true;
        return false;
    }

    @Override
    public boolean bona(List<Integer> x, int k) {
//        if (m.historial.get(k).getX() < 0 || m.historial.get(k).getY() < 0 || m.historial.get(k).getY() > laberint.length
//                || m.historial.get(k).getX() > laberint[0].length) return false;
        if (this.laberint[m.historial.get(k).getY()][m.historial.get(k).getX()].name().equals(WALL)) {
            return false;
        }

        //comprova posicio anterior des de origen a actual
        for (int i = 0; i < (k - 1); i++) {
            if (m.historial.get(i).getX() == m.historial.get(k).getX()
                    && m.historial.get(i).getY() == m.historial.get(k).getY()) {
                return false;
            }
        }

        //comprova posicio anterior des de actual a origen
        /*for (int i = (k - 1); i > 0; i--) {
            if (m.historial.get(i).getX() == m.historial.get(k).getX()
                    && m.historial.get(i).getY() == m.historial.get(k).getY()) {
                return false;
            }
        }*/
        return true;
    }

    @Override
    public void tractarSolucio(List<Integer> x, int k) {
//        System.out.println("Vmillor: " + k);
//        System.out.println("Xmillor: " + x.size());
        this.Vmillor = k;

        this.Xmillor = new ArrayList<Integer>(x);
    }

    @Override
    public void laberintV1(List<Integer> x, int k) {
        if (k >= x.size()) x.add(k, 0);
        else x.set(k, 0);
        while (x.get(k) < 4) {
            x.set(k, x.get(k) + 1);
            marcar(x, k);

            if (solucio(x, k)) {
                if (bona(x, k)) {
                    if (isVisualizing) visualize(x, k, 10);
                    tractarSolucio(x, k);
                }
            } else {
                if (bona(x, k)) {
                    if (isVisualizing) visualize(x, k, 10);
                    if (Vmillor > k || Vmillor == -1) laberintV1(x, k + 1);
                }
            }
        }
    }

    @Override
    public List<Direction> translateConfiguration(List<Integer> x) {
        List<Direction> configuracio = new ArrayList<Direction>();
        for (int i = 0; i <= this.Vmillor; i++) {
            switch (x.get(i)) {
                case 1 -> configuracio.add(Direction.UP);
                case 2 -> configuracio.add(Direction.RIGHT);
                case 3 -> configuracio.add(Direction.DOWN);
                case 4 -> configuracio.add(Direction.LEFT);
            }
        }
        return configuracio;
    }

/*    public List<Direction> translateConfiguration(List<Integer> x, int k) {
        List<Direction> configuracio = new ArrayList<Direction>();
        for (int i = 0; i < k; i++) {
            switch (x.get(i)) {
                case 1 -> configuracio.add(Direction.UP);
                case 2 -> configuracio.add(Direction.RIGHT);
                case 3 -> configuracio.add(Direction.DOWN);
                case 4 -> configuracio.add(Direction.LEFT);
            }
        }
        return configuracio;
    }

    public List<Direction> translateConfiguration(List<Integer> x) {
        return translateConfiguration(x, this.Vmillor);
    }*/
}
