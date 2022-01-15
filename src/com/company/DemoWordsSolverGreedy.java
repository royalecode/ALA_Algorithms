package com.company;

import edu.salleurl.arcade.words.controller.WordsRenderer;
import edu.salleurl.arcade.words.model.WordsSolver;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class DemoWordsSolverGreedy implements WordsSolver {

    private int LONGITUD_PARAULA;
    private ArrayList<Coordenada> configuracio;

    @Override
    public int[] solve(char[][] sopa, String s, WordsRenderer wordsRenderer) {
        this.LONGITUD_PARAULA = s.length();
        Instant start = Instant.now();
        this.configuracio = wordsV1(sopa, s);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Temps de Durada: " + timeElapsed.toMillis() + " milisegons");
        System.out.println(this.configuracio);
        wordsRenderer.render(sopa, s, translateConfiguration(this.configuracio));
        return translateConfiguration(this.configuracio);
    }

    public ArrayList<Coordenada> wordsV1 (char[][] sopa, String paraula) {
        ArrayList<Coordenada> x = new ArrayList<>(LONGITUD_PARAULA);
        for (int i = 0; i < LONGITUD_PARAULA; i++) {
            x.add(i, new Coordenada(-1, -1));
        }
        int i = 0, j;
        boolean trobada = false;

        while (!trobada && i < sopa.length) {
            j = 0;
            while (!trobada && j < sopa[i].length) {
                System.out.println(sopa[i][j] + " lletra de la sopa");
                if (sopa[i][j] == paraula.charAt(0)) {
                    //System.out.println("hola");
                    System.out.println(i + ",,," + j);
                    x.set(0, new Coordenada(i, j));
                    trobada = comprovarParaula(sopa, paraula, i, j, x);
                }
                j++;
            }
            i++;
        }

        System.out.println(trobada);
        if (trobada) return x;
        return null;
    }

    private boolean comprovarParaula(char[][] sopa, String s, int i, int j, ArrayList<Coordenada> x) {
        if (comprovarHoritzontal(sopa, s, i, j, x)) return true;
        if (comprovarDiagonal(sopa, s, i, j, x)) return true;
        return comprovarVertical(sopa, s, i, j, x);
    }

    private boolean comprovarVertical(char[][] sopa, String s, int i, int j, ArrayList<Coordenada> x) {
        for (int k = 1; k < LONGITUD_PARAULA; k++) {
            i++;
            System.out.println(s.charAt(k) + " character comparing");
            if (i >= sopa[0].length || j >= sopa.length) return false;
            if (sopa[i][j] == s.charAt(k)) x.set(k, new Coordenada(i, j));
            else return false;
        }
        return true;
    }

    private boolean comprovarDiagonal(char[][] sopa, String s, int i, int j, ArrayList<Coordenada> x) {
        for (int k = 1; k < LONGITUD_PARAULA; k++) {
            i++;
            j++;
            System.out.println(s.charAt(k) + " character comparing");
            if (i >= sopa[0].length || j >= sopa.length) return false;
            if (sopa[i][j] == s.charAt(k)) x.set(k, new Coordenada(i, j));
            else return false;
        }
        return true;
    }

    private boolean comprovarHoritzontal(char[][] sopa, String s, int i, int j, ArrayList<Coordenada> x) {
        for (int k = 1; k < LONGITUD_PARAULA; k++) {
            j++;
            System.out.println(s.charAt(k) + " character comparing");
            if (i >= sopa[0].length || j >= sopa.length) return false;
            if (sopa[i][j] == s.charAt(k)) x.set(k, new Coordenada(i, j));
            else return false;
        }
        return true;
    }

    public int[] translateConfiguration (ArrayList<Coordenada> configuracio) {
        if (configuracio == null) return new int[0];
        int[] solution = new int[4];
        solution[0] = configuracio.get(0).getX();
        solution[1] = configuracio.get(0).getY();
        solution[2] = configuracio.get(LONGITUD_PARAULA -1).getX();
        solution[3] = configuracio.get(LONGITUD_PARAULA -1).getY();
        return solution;
    }
}
