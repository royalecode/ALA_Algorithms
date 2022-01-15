package com.company;

import edu.salleurl.arcade.words.controller.WordsRenderer;
import edu.salleurl.arcade.words.model.WordsSolver;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class BacktrackingWordsSolver implements WordsSolver {

    private int LONGITUD_PARAULA;
    private String paraula;
    private int DIRECCION;
    private ArrayList<Coordenada> configuracio;
    private ArrayList<ArrayList<Coordenada>> Xmillor;
    private char[][] sopa;
    private int Vsolucions;
    private boolean isVisualizing;

    private WordsRenderer wordsRenderer;

    public BacktrackingWordsSolver() {
        this.Vsolucions = 0;
        configuracio = new ArrayList<>();
        this.Xmillor = new ArrayList<>();
    }

    public BacktrackingWordsSolver(boolean isVisualizing) {
        this.Vsolucions = 0;
        configuracio = new ArrayList<>();
        this.isVisualizing = isVisualizing;
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
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Temps de Durada: " + timeElapsed.toMillis() + " milisegons");
        for (ArrayList<Coordenada> coordenadas : this.Xmillor) {
            System.out.println(coordenadas);
        }
        wordsRenderer.render(this.sopa, s, translateConfiguration(this.Xmillor.get(0)));
        return translateConfiguration(this.Xmillor.get(0));
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
                    (x.get(k).getY() - x.get(0).getY()) == 1) this.DIRECCION = 1;
            else if ((x.get(k).getX() - x.get(0).getX()) == 1 &&
                    x.get(k).getY() == x.get(0).getY()) this.DIRECCION = 2;
            else if ((x.get(k).getY() - x.get(0).getY()) == 1 &&
                    x.get(k).getX() == x.get(0).getX()) this.DIRECCION = 3;
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
        int numOpcions = 0, fila = 0, columna = 0;
        if (k >= x.size()) x.add(k, new Coordenada(-1, -1));
        else x.set(k, new Coordenada(-1, -1));

        numOpcions = k;
        while (numOpcions < (this.sopa.length * this.sopa[0].length)) {
            x.set(k, new Coordenada(fila, columna));

            if (solucio(x, k)) {
                if (bona(x, k)) {
                    //if (isVisualizing) visualize(x, k, 10);
                    tractarSolucio(x);
                }
            } else {
                if (bona(x, k)) {
                    //if (isVisualizing) visualize(x, k, 10);
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
            case 1:
                if ((x.get(i).getX() - x.get(i - 1).getX()) != 1 ||
                        (x.get(i).getY() - x.get(i - 1).getY()) != 1) return false;
                break;
            case 2:
                if ((x.get(i).getX() - x.get(i - 1).getX()) != 1 ||
                        x.get(i).getY() != x.get(i - 1).getY()) return false;
                break;
            case 3:
                if ((x.get(i).getY() - x.get(i - 1).getY()) != 1 ||
                        x.get(i).getX() != x.get(i - 1).getX()) return false;
                break;
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
