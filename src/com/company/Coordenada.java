package com.company;

import java.util.List;

public class Coordenada {
    private int x;
    private int y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCoordinates (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordenada {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Coordenada calcularPosicio(List<Integer> x, int k) {
        Coordenada posicio = new Coordenada(this.x, this.y);
        for (int i = 0; i <= k; i++) {
            switch (x.get(i)) {
                case 1 -> posicio.y = posicio.y - 1;
                case 2 -> posicio.x = posicio.x + 1;
                case 3 -> posicio.y = posicio.y + 1;
                case 4 -> posicio.x = posicio.x - 1;
            }
        }
        return posicio;
    }

    public double calcularDistanciaEuclidea(Coordenada coord) {
        int distanciaX = Math.abs(this.x - coord.x);
        int distanciaY = Math.abs(this.y - coord.y);
        return Math.sqrt(
                (distanciaX * distanciaX) + (distanciaY * distanciaY)
        );
    }

    public double calcularDistanciaManhattan(Coordenada coord) {
        int distanciaX = Math.abs(this.x - coord.x);
        int distanciaY = Math.abs(this.y - coord.y);
        return distanciaX + distanciaY;
    }
}
