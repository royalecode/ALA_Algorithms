package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingLabyrinthSolver implements LabyrinthSolver {

    private List<Integer> configuracio;
    private List<Integer> Xmillor;
    private Cell[][] laberint;
    private Coordenada posicioActual, ORIGEN;
    private int Vmillor;

    public BacktrackingLabyrinthSolver() {
        configuracio = new ArrayList<Integer>();
        this.Vmillor = -1;
    }

    @Override
    public List<Direction> solve(Cell[][] laberint, LabyrinthRenderer labyrinthRenderer) {
        this.laberint = laberint;
        calcularOrigenAndDesti();
        laberintV1(this.configuracio,0);
        labyrinthRenderer.render(laberint, translateConfiguration(this.Xmillor));
        return translateConfiguration(this.Xmillor);
    }

    private Coordenada calcularPosicio(List<Integer> x, int k) {
        Coordenada posicio = new Coordenada(ORIGEN.getX(), ORIGEN.getY());
        for (int i = 0; i < k; i++) {
            if (1 == x.get(i)) posicio.setY(posicio.getY() - 1);
            if (2 == x.get(i)) posicio.setX(posicio.getX() + 1);
            if (3 == x.get(i)) posicio.setY(posicio.getY() + 1);
            if (4 == x.get(i)) posicio.setX(posicio.getX() - 1);
        }
        return posicio;
    }

    private void calcularOrigenAndDesti() {
        for (int i = 0; i < this.laberint.length; i++) {
            for (int j = 0; j < this.laberint[i].length; j++) {
                if (this.laberint[i][j].name().equals("START")) {
                    ORIGEN = new Coordenada(i, j);
                }
            }
        }
    }

    public boolean solucio(List<Integer> x, int k) {
        boolean solution = false;
        this.posicioActual = calcularPosicio(x, k);
        if (posicioActual.getX() < 0 || posicioActual.getY() < 0 || posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length) return false;
        if (this.laberint[posicioActual.getY()][posicioActual.getX()].name().equals("EXIT")) solution = true;
        return solution;
    }

    private boolean bona(List<Integer> x, int k) {
        this.posicioActual = calcularPosicio(x, k);
        Coordenada posicioAnterior = new Coordenada(ORIGEN.getX(), ORIGEN.getY());

        if (posicioActual.getX() < 0 || posicioActual.getY() < 0 || posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length) return false;

        if (this.laberint[posicioActual.getY()][posicioActual.getX()].name().equals("WALL")) {
            return false;
        }

        for (int i = 0; i < (k); i++) {
            if (posicioAnterior.getX() == posicioActual.getX() && posicioAnterior.getY() == posicioActual.getY()) {
                return false;
            }

            if (1 == x.get(i)) posicioAnterior.setY(posicioAnterior.getY() - 1);
            if (2 == x.get(i)) posicioAnterior.setX(posicioAnterior.getX() + 1);
            if (3 == x.get(i)) posicioAnterior.setY(posicioAnterior.getY() + 1);
            if (4 == x.get(i)) posicioAnterior.setX(posicioAnterior.getX() - 1);
        }
        return true;
    }

    public void tractarSolucio (List<Integer> x, int k) {
        if (this.Vmillor > k || this.Vmillor == -1) {
            this.Vmillor = k;
            this.Xmillor = new ArrayList<Integer>(x);
        }
    }

    public void laberintV1 (List<Integer> x, int k) {
        if (k >= x.size()) x.add(k, 0);
        else x.set(k, 0);
        while (x.get(k) < 4) {
            x.set(k, x.get(k) + 1);

            if (solucio(x, k)) {
                if (bona(x,k)) tractarSolucio(x,k);
            } else {
                if (bona(x,k)) laberintV1(x, k+1);
            }
        }
    }

    public List<Direction> translateConfiguration(List<Integer> x) {
        List<Direction> configuracio = new ArrayList<Direction>();
        for (int i = 0; i < this.Vmillor; i++) {
            if (1 == x.get(i)) configuracio.add(Direction.UP);
            if (2 == x.get(i)) configuracio.add(Direction.RIGHT);
            if (3 == x.get(i)) configuracio.add(Direction.DOWN);
            if (4 == x.get(i)) configuracio.add(Direction.LEFT);
        }
        return configuracio;
    }
}
