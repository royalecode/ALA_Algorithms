package com.company;

import edu.salleurl.arcade.words.controller.WordsRenderer;
import edu.salleurl.arcade.words.model.WordsSolver;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BacktrackingWordsSolver implements WordsSolver {

    private static final int DIAGONAL = 1;
    private static final int VERTICAL = 2;
    private static final int HORITZONTAL = 3;

    private int LONGITUD_PARAULA;
    private String paraula;
    private int DIRECCION;
    private ArrayList<Coordenada> configuracio;
    private ArrayList<ArrayList<Coordenada>> Xmillor;
    private char[][] sopa;
    private int Vsolucions;
    private final boolean isVisualizing;
    private final boolean isAnalysing;

    private WordsRenderer wordsRenderer;

    public BacktrackingWordsSolver() {
        this.Vsolucions = 0;
        configuracio = new ArrayList<>();
        this.Xmillor = new ArrayList<>();
        this.isVisualizing = false;
        this.isAnalysing = false;
    }

    public BacktrackingWordsSolver(boolean isVisualizing, boolean isAnalysing) {
        this.Vsolucions = 0;
        configuracio = new ArrayList<>();
        this.isVisualizing = isVisualizing;
        this.isAnalysing = isAnalysing;
    }

    @Override
    public int[] solve(char[][] sopa, String s, WordsRenderer wordsRenderer) {
        this.sopa = sopa;
        this.LONGITUD_PARAULA = s.length();
        this.paraula = s;
        this.wordsRenderer = wordsRenderer;

        Instant start = Instant.now();
        wordsV1(this.configuracio, 0);
        Instant end = Instant.now();

        int ms = (int) Duration.between(start, end).toMillis();
        if (isAnalysing) AnalysisPersitance.getInstance().fillRecord(AnalysisPersitance.WORDS_BACK, ms);
        System.out.println("Temps de Durada: " + ms + " milisegons");
        for (ArrayList<Coordenada> coordenadas : this.Xmillor) {
            System.out.println(coordenadas);
        }
        wordsRenderer.render(this.sopa, s, translateConfiguration(this.Xmillor.get(0), LONGITUD_PARAULA));
        return translateConfiguration(this.Xmillor.get(0), LONGITUD_PARAULA);
    }

    public boolean solucio(ArrayList<Coordenada> x, int k) {
        if (k < this.LONGITUD_PARAULA - 1) return false;

        for (int i = 0; i < k; i++) {
            if (this.sopa[x.get(i).getX()][x.get(i).getY()]
                    != this.paraula.charAt(i)) return false;
        }

        for (int i = 1; i < k; i++) {
            if (!checkDirection(x, i)) return false;
        }
        return true;
    }

    public boolean bona(ArrayList<Coordenada> x, int k) {
        if (this.sopa[x.get(k).getX()][x.get(k).getY()]
                != this.paraula.charAt(k)) return false;

        if (k == 1) {
            if ((x.get(k).getX() - x.get(0).getX()) == 1 &&
                    (x.get(k).getY() - x.get(0).getY()) == 1) this.DIRECCION = DIAGONAL;
            else if ((x.get(k).getX() - x.get(0).getX()) == 1 &&
                    x.get(k).getY() == x.get(0).getY()) this.DIRECCION = VERTICAL;
            else if ((x.get(k).getY() - x.get(0).getY()) == 1 &&
                    x.get(k).getX() == x.get(0).getX()) this.DIRECCION = HORITZONTAL;
            else return false;
        } else if (k > 1) {
            return (checkDirection(x, k));
        }

        return true;
    }

    public void tractarSolucio(ArrayList<Coordenada> x) {
        //System.out.println("soluciooon");
        //System.out.println(x);
        //System.out.println(this.DIRECCION);
        this.Xmillor.add(Vsolucions, new ArrayList<>(x));
        this.Vsolucions++;
    }

    public void wordsV1(ArrayList<Coordenada> x, int k) {
        int numOpcions, fila = 0, columna = 0;
        if (k >= x.size()) x.add(k, new Coordenada(-1, -1));
        else x.set(k, new Coordenada(-1, -1));

        numOpcions = k;
        while (numOpcions < (this.sopa.length * this.sopa[0].length)) {
            x.set(k, new Coordenada(fila, columna));

            if (solucio(x, k)) {
                if (bona(x, k)) {
                    visualize(x, sopa, this.paraula, k, 200);
                    tractarSolucio(x);
                }
            } else {
                if (bona(x, k)) {
                    visualize(x, sopa, this.paraula, k, 200);
                    wordsV1(x, k + 1);
                }
            }
            columna++;
            if (columna == this.sopa[0].length) {
               fila++;
               columna = 0;
            }
            numOpcions++;
        }
    }

    public boolean checkDirection(ArrayList<Coordenada> x, int i) {
        switch (this.DIRECCION) {
            case DIAGONAL:
                if ((x.get(i).getX() - x.get(i - 1).getX()) != 1 ||
                        (x.get(i).getY() - x.get(i - 1).getY()) != 1) return false;
                break;
            case VERTICAL:
                if ((x.get(i).getX() - x.get(i - 1).getX()) != 1 ||
                        x.get(i).getY() != x.get(i - 1).getY()) return false;
                break;
            case HORITZONTAL:
                if ((x.get(i).getY() - x.get(i - 1).getY()) != 1 ||
                        x.get(i).getX() != x.get(i - 1).getX()) return false;
                break;
        }
        return true;
    }

    private void visualize(ArrayList<Coordenada> x, char[][] sopa, String s, int numLletres, int ms) {
        if (this.isVisualizing) {
            try {
                Thread.sleep(ms);
            } catch (Exception ignore) {            }
            wordsRenderer.render(sopa, s, translateConfiguration(x, numLletres));
        }
    }

    public int[] translateConfiguration(ArrayList<Coordenada> configuracio, int numLletres) {
        if (configuracio == null) return new int[0];
        int[] solution = new int[4];
        solution[0] = configuracio.get(0).getX();
        solution[1] = configuracio.get(0).getY();
        solution[2] = configuracio.get(numLletres - 1).getX();
        solution[3] = configuracio.get(numLletres - 1).getY();
//        System.out.println("SOLUTION: " + configuracio.toString());
//        System.out.println("SOLUTION: " + Arrays.toString(solution));
        return solution;
    }
}
