package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BranchAndBoundLabryinthSolver implements LabyrinthSolver {

    protected static final String EXIT = "EXIT";
    protected static final String START = "START";
    protected static final String WALL = "WALL";

    protected Cell[][] laberint;
    protected Coordenada ORIGEN, DESTI;

    private LabyrinthRenderer labyrinthRenderer;
    protected boolean isVisualizing;

    public BranchAndBoundLabryinthSolver(){
        this.isVisualizing = false;
    }

    public BranchAndBoundLabryinthSolver(boolean isVisualizing){
        this.isVisualizing = isVisualizing;
    }

    private static class Configuracio {
        public List<Integer> arr;
        public int k;
        public List<Coordenada> m;
        public double valorEstimat;

        public Configuracio(ArrayList<Integer> arr, int k, ArrayList<Coordenada> m) {
            this.arr = arr;
            this.k = k;
            this.m = m;
            this.valorEstimat = Double.MAX_VALUE;
        }

        @Override
        public String toString() {
            return  "\n\tConfiguracio{" +
                    "\n\t\tk=" + k +
                    ",\n\t\t valorEstimat=" + valorEstimat +
                    "\n\t}";
        }
    }

    @Override
    public List<Direction> solve(Cell[][] laberint, LabyrinthRenderer labyrinthRenderer) {
        this.laberint = laberint;
        this.labyrinthRenderer = labyrinthRenderer;
        calcularOrigenAndDesti();

        Instant start = Instant.now();
        Configuracio resultat = laberintV1();
        Instant end = Instant.now();

        int ms = (int) Duration.between(start, end).toMillis();
        AnalysisPersitance.getInstance().fillRecord(AnalysisPersitance.WORDS_GREED, ms);
        System.out.println("Temps de Durada: " + ms + " milisegons");

        labyrinthRenderer.render(this.laberint, translateConfiguration(resultat));
        return translateConfiguration(resultat);
    }

    public Configuracio laberintV1() {
        int Vmillor = Integer.MAX_VALUE;
        Configuracio Xmillor = null;
        Configuracio x;
        ArrayList<Configuracio> fills;

        PriorityQueue<Configuracio> nodesVius = new PriorityQueue<>(
                (c1, c2) -> (int) Math.round(c2.valorEstimat - c1.valorEstimat)
        );

        nodesVius.add(configuracioArrel());

//        PrintWriter writer = null;
//        try {
//            writer = new PrintWriter("nodes.txt", "UTF-8");
//        } catch (Exception ignored) {}

        while (!nodesVius.isEmpty()) {
            x = nodesVius.poll();

//            writer.println("------------------------------------------");
//            writer.println(nodesVius);
//            writer.println("POLL: " + x.toString());

            fills = expandeix(x);
            for (Configuracio fill : fills) {
                if (solucio(fill)) {
                    if (bona(fill)) {
                        if (isVisualizing) visualize(fill, 10);
                        if (valor(fill) < Vmillor) {
                            Vmillor = valor(fill);
                            Xmillor = fill;
                        }
                    }
                } else {
                    if (bona(fill)) {
                        if (isVisualizing) visualize(fill, 10);
                        if (valorParcial(fill) < Vmillor) {
                            fill.valorEstimat = valorEstimat(fill);
                            nodesVius.add(fill);
                        }
                    }
                }
            }
        }
//        writer.close();
        return Xmillor;
    }


    private Configuracio configuracioArrel() {
        ArrayList<Coordenada> m = new ArrayList<>();
        m.add(ORIGEN);
        return new Configuracio(
                new ArrayList<Integer>(),
                0,
                m
        );
    }

    private ArrayList<Configuracio> expandeix(Configuracio x) {
        ArrayList<Configuracio> fills = new ArrayList<>();
        Configuracio aux;
        for (int i = 0; i < 4; i++) {
            aux = new Configuracio(
                    new ArrayList<>(x.arr.subList(0, x.k)),
                    (x.k + 1),
                    new ArrayList<>(x.m.subList(0, x.k + 1))
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

        /* Heuristica amb distancia Euclidea */
//        double distanciaOrigen = DESTI.calcularDistanciaEuclidea(ORIGEN);
//        double distanciaActual = DESTI.calcularDistanciaEuclidea(posicioActual);

        /* Heuristica amb distancia Manhattan */
        double distanciaOrigen = DESTI.calcularDistanciaManhattan(ORIGEN);
        double distanciaActual = DESTI.calcularDistanciaManhattan(posicioActual);

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

    public List<Direction> translateConfiguration(Configuracio x) {
        List<Direction> configuracio = new ArrayList<Direction>();
        for (int direccio : x.arr) {
            switch (direccio) {
                case 1 -> configuracio.add(Direction.UP);
                case 2 -> configuracio.add(Direction.RIGHT);
                case 3 -> configuracio.add(Direction.DOWN);
                case 4 -> configuracio.add(Direction.LEFT);
            }
        }
        return configuracio;
    }

    public void visualize(Configuracio x, int time) {
        labyrinthRenderer.render(this.laberint, translateConfiguration(x));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
