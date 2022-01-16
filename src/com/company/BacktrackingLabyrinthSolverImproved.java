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

    public BacktrackingLabyrinthSolverImproved(boolean visualize, boolean analyse) {
        super(visualize, analyse);
        m = new LabyrinthMarcatge();
        this.idAnalysis = AnalysisPersitance.LABYRINTH_BACK_IMPROVED;
    }

    public void marcar(List<Integer> x, int k) {
        if (k > 0) m.historial.add(k, new Coordenada(m.historial.get(k - 1).getX(), m.historial.get(k - 1).getY()));
        else m.historial.add(k, new Coordenada(ORIGEN.getX(), ORIGEN.getY()));

        switch (x.get(k)) {
            case 1 -> m.historial.get(k).setY(m.historial.get(k).getY() - 1);
            case 2 -> m.historial.get(k).setX(m.historial.get(k).getX() + 1);
            case 3 -> m.historial.get(k).setY(m.historial.get(k).getY() + 1);
            case 4 -> m.historial.get(k).setX(m.historial.get(k).getX() - 1);
        }
    }

    @Override
    public boolean solucio(List<Integer> x, int k) {
        return this.laberint[m.historial.get(k).getY()][m.historial.get(k).getX()].name().equals(EXIT);
    }

    @Override
    public boolean bona(List<Integer> x, int k) {
        if (this.laberint[m.historial.get(k).getY()][m.historial.get(k).getX()].name().equals(WALL)) {
            return false;
        }

        for (int i = 0; i < (k - 1); i++) {
            if (m.historial.get(i).getX() == m.historial.get(k).getX()
                    && m.historial.get(i).getY() == m.historial.get(k).getY()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void tractarSolucio(List<Integer> x, int k) {
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
}
