package com.company;

import edu.salleurl.arcade.words.controller.WordsRenderer;
import edu.salleurl.arcade.words.model.WordsSolver;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class GreedyWordsSolver implements WordsSolver {
    private static final int HORITZONTAL = 1;
    private static final int VERTICAL = 2;
    private static final int DIAGONAL = 3;

    private int LONGITUD_PARAULA;
    private WordsRenderer wordsRenderer;
    private final boolean isVisualizing;
    private final boolean isAnalysing;

    public GreedyWordsSolver() {
        this.isVisualizing = false;
        this.isAnalysing = false;
    }

    public GreedyWordsSolver(boolean isVisualizing, boolean isAnalysing) {
        this.isVisualizing = isVisualizing;
        this.isAnalysing = false;
    }

    @Override
    public int[] solve(char[][] sopa, String s, WordsRenderer wordsRenderer) {
        this.LONGITUD_PARAULA = s.length();
        this.wordsRenderer = wordsRenderer;

        Instant start = Instant.now();
        ArrayList<Coordenada> configuracio = wordsV1(sopa, s);
        Instant end = Instant.now();

        int ms = (int) Duration.between(start, end).toMillis();
        if (isAnalysing) AnalysisPersitance.getInstance().fillRecord(AnalysisPersitance.WORDS_GREED, ms);
        System.out.println("Temps de Greedy: " + ms + " milisegons");

        wordsRenderer.render(sopa, s, translateConfiguration(configuracio, LONGITUD_PARAULA));
        return translateConfiguration(configuracio, LONGITUD_PARAULA);
    }

    public ArrayList<Coordenada> wordsV1(char[][] sopa, String paraula) {
        ArrayList<Coordenada> x = new ArrayList<>(LONGITUD_PARAULA);

        for (int i = 0; i < LONGITUD_PARAULA; i++) {
            x.add(i, new Coordenada(-1, -1));
        }

        int i = 0, j = 0;
        boolean trobada = false;

        while (!trobada && i < sopa.length) {
            j = 0;
            while (!trobada && j < sopa[i].length) {
//                System.out.println(sopa[i][j] + " lletra de la sopa");
                x.set(0, new Coordenada(i, j));
                visualize(x, sopa, paraula, 1, 200);
                if (sopa[i][j] == paraula.charAt(0)) {
                    // System.out.println("hola");
//                    System.out.println(i + ",,," + j);
                    trobada = comprovarParaula(sopa, paraula, i, j, x);
                }
                j++;
            }
            i++;
        }

//        System.out.println(trobada);
        return trobada ? x : null;
    }

    private boolean comprovarParaula(char[][] sopa, String s, int i, int j, ArrayList<Coordenada> x) {
        if (comprovarDireccio(VERTICAL, sopa, s, i, j, x)) return true;
        if (comprovarDireccio(HORITZONTAL, sopa, s, i, j, x)) return true;
        return comprovarDireccio(DIAGONAL, sopa, s, i, j, x);
    }

    private boolean comprovarDireccio(int direccio, char[][] sopa, String s, int i, int j, ArrayList<Coordenada> x) {
        for (int k = 1; k < LONGITUD_PARAULA; k++) {
            switch (direccio) {
                case VERTICAL -> i++;
                case HORITZONTAL -> j++;
                case DIAGONAL -> {
                    i++;
                    j++;
                }
            }
//            System.out.println(s.charAt(k) + " character comparing");
            if (i >= sopa[0].length || j >= sopa.length) return false;
            if (sopa[i][j] == s.charAt(k)) x.set(k, new Coordenada(i, j));
            else return false;
            visualize(x, sopa, s, k, 200);
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
