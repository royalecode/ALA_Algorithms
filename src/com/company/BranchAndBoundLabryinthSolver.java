package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BranchAndBoundLabryinthSolver implements LabyrinthSolver {

    protected static final String EXIT = "EXIT";
    protected static final String START = "START";
    protected static final String WALL = "WALL";

    protected List<Integer> Xmillor;
    protected Cell[][] laberint;
    protected Coordenada ORIGEN, DESTI;
    protected int Vmillor;

    private LabyrinthRenderer labyrinthRenderer;

    private class Configuracio {
        public List<Integer> arr;
        public int k;
        public List<Coordenada> m;

        public Configuracio() {
            this.arr = new ArrayList<Integer>();
            this.k = 0;
            this.m = new ArrayList<Coordenada>();
        }

        public Configuracio(ArrayList<Integer> arr, int k, ArrayList<Coordenada> m) {
            this.arr = arr;
            this.k = k;
            this.m = m;
        }
    }

    public BranchAndBoundLabryinthSolver() {
        this.Vmillor = -1;
    }

    @Override
    public List<Direction> solve(Cell[][] cells, LabyrinthRenderer labyrinthRenderer) {
/*      this.laberint = laberint;
        this.labyrinthRenderer = labyrinthRenderer;
        calcularOrigenAndDesti();
        Instant start = Instant.now();
        laberintV1(this.configuracio, 0);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Temps de Durada: " + timeElapsed.toMillis() + " milisegons");
        labyrinthRenderer.render(this.laberint, translateConfiguration(this.Xmillor));
        return translateConfiguration(this.Xmillor); */
        return null;
    }

    private void laberintV1() {

    }

    private Configuracio configuracioArrel() {
        return new Configuracio();
    }

    private ArrayList<Configuracio> expandeix(Configuracio x) {
        ArrayList<Configuracio> fills = new ArrayList<>();
        Configuracio aux;
        int i, j;
        for (i = 0; i < 4; i++) {
            aux = new Configuracio(
                    new ArrayList<>(x.arr.subList(0, x.k)),
                    (x.k + 1),
                    new ArrayList<>(x.m.subList(0, x.k))
            );
            Coordenada coord = new Coordenada(aux.m.get(aux.k - 1).getX(), aux.m.get(aux.k - 1).getY());
            switch (i + 1) {
                case 1 -> coord.setY(coord.getY() - 1);
                case 2 -> coord.setX(coord.getX() + 1);
                case 3 -> coord.setY(coord.getY() + 1);
                case 4 -> coord.setX(coord.getX() - 1);
            }
            aux.arr.add(i + 1);
            aux.m.add(coord);
            fills.add(aux);
        }
        return fills;
    }

    private boolean solucio(Configuracio x) {
        Coordenada posicioActual = x.m.get(x.k);

        if (posicioActual.getX() < 0 ||
                posicioActual.getY() < 0 ||
                posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length)
            return false;

        return posicioActual.getX() == DESTI.getX() &&
                posicioActual.getY() == DESTI.getY();

    }

    private boolean bona(Configuracio x) {
        Coordenada posicioActual = x.m.get(x.k);

        if (posicioActual.getX() < 0 ||
                posicioActual.getY() < 0 ||
                posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length)
            return false;

        if (this.laberint[posicioActual.getY()][posicioActual.getX()].name().equals(WALL))
            return false;

        for (int i = 0; i < x.k - 1; i++)
            if (posicioActual.getX() == x.m.get(i).getX() && posicioActual.getY() == x.m.get(i).getY())
                return false;

        return true;
    }

    private int valor(Configuracio x) {
        return x.k;
    }

    private int valorParcial(Configuracio x) {
        return valor(x);
    }

    private double valorEstimat(Configuracio x) {
        Coordenada posicioActual = x.m.get(x.k);

        double distanciaOrigen = DESTI.calcularDistancia(ORIGEN);
        double distanciaActual = DESTI.calcularDistancia(posicioActual);
        return (distanciaOrigen - distanciaActual) *
                ((laberint.length * laberint[0].length) - x.k);
    }

    public void calcularOrigenAndDesti() {
        for (int i = 0; i < this.laberint.length; i++) {
            for (int j = 0; j < this.laberint[i].length; j++) {
                if (this.laberint[i][j].name().equals(START)) {
                    ORIGEN = new Coordenada(i, j);
                }
                if (this.laberint[i][j].name().equals(EXIT)) {
                    DESTI = new Coordenada(i, j);
                }
            }
        }
    }
}
