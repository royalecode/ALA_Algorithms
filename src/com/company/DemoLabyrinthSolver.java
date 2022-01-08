package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class DemoLabyrinthSolver implements LabyrinthSolver {

    private List configuracio, Xmillor;
    private Cell[][] laberint;
    private Coordenada posicioActual, ORIGEN, DESTI;
    private int Vmillor;

    public DemoLabyrinthSolver() {
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

    private Coordenada calcularPosicio(List x, int k) {
        Coordenada posicio = new Coordenada(ORIGEN.getX(), ORIGEN.getY());
        for (int i = 0; i < x.size(); i++) {
            if (1 == (int) x.get(i)) posicio.setY(posicio.getY() - 1);
            if (2 == (int) x.get(i)) posicio.setX(posicio.getX() + 1);
            if (3 == (int) x.get(i)) posicio.setY(posicio.getY() + 1);
            if (4 == (int) x.get(i)) posicio.setX(posicio.getX() - 1);
        }
        return posicio;
    }

    private void calcularOrigenAndDesti() {
        for (int i = 0; i < this.laberint.length; i++) {
            for (int j = 0; j < this.laberint[i].length; j++) {
                if (this.laberint[i][j].name().equals("START")) {
                    ORIGEN = new Coordenada(i, j);
                } else if (this.laberint[i][j].name().equals("EXIT")) {
                    DESTI = new Coordenada(i, j);
                }
            }
        }
    }

    public boolean solucio(List x, int k) {
        boolean solution = false;
        this.posicioActual = calcularPosicio(x, k);
        //return posicioActual.getX() == DESTI.getX() && posicioActual.getY() == DESTI.getY();
        if (posicioActual.getX() < 0 || posicioActual.getY() < 0 || posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length) return false;
        if (this.laberint[posicioActual.getY()][posicioActual.getX()].name().equals("EXIT")) solution = true;
        return solution;
    }

    private boolean bona(List x, int k) {
        this.posicioActual = calcularPosicio(x, k);
        Coordenada posicioAnterior = new Coordenada(ORIGEN.getX(), ORIGEN.getY());

        if (posicioActual.getX() < 0 || posicioActual.getY() < 0 || posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length) return false;

        if (this.laberint[posicioActual.getY()][posicioActual.getX()].name().equals("WALL")) {
            System.out.println("es paret");
            return false;
        }

        for (int i = 0; i < (k); i++) {
            if (posicioAnterior.getX() == posicioActual.getX() && posicioAnterior.getY() == posicioActual.getY()) {
                return false;
            }

            if (1 == (int) x.get(i)) posicioAnterior.setY(posicioAnterior.getY() - 1);
            if (2 == (int) x.get(i)) posicioAnterior.setX(posicioAnterior.getX() + 1);
            if (3 == (int) x.get(i)) posicioAnterior.setY(posicioAnterior.getY() + 1);
            if (4 == (int) x.get(i)) posicioAnterior.setX(posicioAnterior.getX() - 1);
        }
        return true;
    }

    public void tractarSolucio (List x, int k) {
        if (this.Vmillor > k || this.Vmillor == -1) {
            this.Vmillor = k;
            this.Xmillor = new ArrayList<Integer>(x);
            //this.Xmillor = x;
        }
    }

    public void laberintV1 (List x, int k) {
        x.add(k, 0);
        while ((int) x.get(k) < 4) {
            x.set(k,(int) x.get(k) + 1);

            if (solucio(x, k)) {
                if (bona(x,k)) tractarSolucio(x,k);
            } else {
                if (bona(x,k)) laberintV1(x, k+1);
            }
        }
    }

    public List translateConfiguration(List x) {
        List configuracio = new ArrayList<Direction>();
        for (int i = 0; i < x.size(); i++) {
            if (1 == (int) x.get(i)) configuracio.add(Direction.UP);
            if (2 == (int) x.get(i)) configuracio.add(Direction.RIGHT);
            if (3 == (int) x.get(i)) configuracio.add(Direction.DOWN);
            if (4 == (int) x.get(i)) configuracio.add(Direction.LEFT);
        }
        return configuracio;
    }
}
