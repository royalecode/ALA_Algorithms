package com.company;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BacktrackingLabyrinthSolver implements LabyrinthSolver {

    protected static final String EXIT = "EXIT";
    protected static final String START = "START";
    protected static final String WALL = "WALL";

    protected int idAnalysis;

    protected List<Integer> configuracio;
    protected List<Integer> Xmillor;
    protected Cell[][] laberint;
    protected Coordenada posicioActual, ORIGEN;
    protected int Vmillor;
    protected boolean isVisualizing;

    private LabyrinthRenderer labyrinthRenderer;

    public BacktrackingLabyrinthSolver() {
        this(false);
    }

    public BacktrackingLabyrinthSolver(boolean isVisualizing) {
        idAnalysis = AnalysisPersitance.LABYRINTH_BACK;
        configuracio = new ArrayList<Integer>();
        this.Vmillor = -1;
        this.isVisualizing = isVisualizing;
    }

    @Override
    public List<Direction> solve(Cell[][] laberint, LabyrinthRenderer labyrinthRenderer) {
        this.laberint = laberint;
        this.labyrinthRenderer = labyrinthRenderer;
        calcularOrigenAndDesti();

        Instant start = Instant.now();
        laberintV1(this.configuracio, 0);
        Instant end = Instant.now();

        int ms = (int) Duration.between(start, end).toMillis();
        AnalysisPersitance.getInstance().fillRecord(idAnalysis, ms);
        System.out.println("Temps de Backtraking: " + ms + " milisegons");

//        labyrinthRenderer.render(this.laberint, translateConfiguration(this.Xmillor));
        return translateConfiguration(this.Xmillor);
    }

    public void calcularOrigenAndDesti() {
        for (int i = 0; i < this.laberint.length; i++) {
            for (int j = 0; j < this.laberint[i].length; j++) {
                if (this.laberint[i][j].name().equals(START)) {
                    ORIGEN = new Coordenada(i, j);
                }
            }
        }
    }

    public boolean solucio(List<Integer> x, int k) {
        posicioActual = ORIGEN.calcularPosicio(x, k);

        /*if (posicioActual.getX() < 0 ||
                posicioActual.getY() < 0 ||
                posicioActual.getY() > laberint.length ||
                posicioActual.getX() > laberint[0].length)
            return false;*/

        return this.laberint[posicioActual.getY()][posicioActual.getX()]
                .name()
                .equals(EXIT);
    }

    public boolean bona(List<Integer> x, int k) {
        this.posicioActual = ORIGEN.calcularPosicio(x, k);

//        if (posicioActual.getX() < 0 || posicioActual.getY() < 0 || posicioActual.getY() > laberint.length ||
//                posicioActual.getX() > laberint[0].length) return false;

        if (this.laberint[posicioActual.getY()][posicioActual.getX()].name().equals(WALL)) {
            return false;
        }

        //versio que comprova si has passat per la posició anterior des de l'origen a la posició actual
        Coordenada posicioAnterior = new Coordenada(ORIGEN.getX(), ORIGEN.getY());
        for (int i = 0; i < (k); i++) {
            if (posicioAnterior.getX() == posicioActual.getX() &&
                    posicioAnterior.getY() == posicioActual.getY())
                return false;

            switch (x.get(i)) {
                case 1 -> posicioAnterior.setY(posicioAnterior.getY() - 1);
                case 2 -> posicioAnterior.setX(posicioAnterior.getX() + 1);
                case 3 -> posicioAnterior.setY(posicioAnterior.getY() + 1);
                case 4 -> posicioAnterior.setX(posicioAnterior.getX() - 1);
            }
        }

        //versio que comprova si has passat per la posició anterior des de l'actual al origen
        /*Coordenada posicioAnterior = new Coordenada(posicioActual.getX(), posicioActual.getY());
        for (int i = k; i > 0; i--) {

            switch (x.get(i)) {
                case 1 -> posicioAnterior.setY(posicioAnterior.getY() + 1);
                case 2 -> posicioAnterior.setX(posicioAnterior.getX() - 1);
                case 3 -> posicioAnterior.setY(posicioAnterior.getY() - 1);
                case 4 -> posicioAnterior.setX(posicioAnterior.getX() + 1);
            }

            if (posicioAnterior.getX() == posicioActual.getX() &&
                    posicioAnterior.getY() == posicioActual.getY())
                return false;
        }*/
        return true;
    }

    public void tractarSolucio(List<Integer> x, int k) {
        //System.out.println("Vmillor: " + k);
        //System.out.println("Xmillor: " + x.size());

        if (this.Vmillor > k || this.Vmillor == -1) {
            this.Vmillor = k;
            this.Xmillor = new ArrayList<Integer>(x);
        }
    }

    public void laberintV1(List<Integer> x, int k) {
        if (k >= x.size()) x.add(k, 0);
        else x.set(k, 0);
        while (x.get(k) < 4) {
            x.set(k, x.get(k) + 1);

            if (solucio(x, k)) {
                if (bona(x, k)) {
                    if (isVisualizing) visualize(x, k, 10);
                    tractarSolucio(x, k);
                }
            } else {
                if (bona(x, k)) {
                    if (isVisualizing) visualize(x, k, 10);
                    laberintV1(x, k + 1);
                }
            }
        }
    }

    public void visualize(List<Integer> x, int k, int time) {
        labyrinthRenderer.render(this.laberint, translateConfiguration(x, k));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Direction> translateConfiguration(List<Integer> x, int k) {
        List<Direction> configuracio = new ArrayList<Direction>();
        for (int i = 0; i < this.Vmillor; i++) {
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
    }
}
