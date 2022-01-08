package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private int labyrinthoOption;
    private int wordsOption;
    private Scanner sc;

    public Menu () {
        sc = new Scanner(System.in);
    }

    public void start() {
        menuForLabyrinth();
        menuForWords();
    }

    private void menuForLabyrinth() {
        boolean flag = false;
        do {
            System.out.println("Esculliu l'estratègia per resoldre el Laberint");
            System.out.println("\t1. Backtracking Normal");
            System.out.println("\t2. Backtracking amb Marcatge");
            System.out.println("\t3. Branch and Bound\n");

            System.out.print("Introduiu l'opció (número): ");
            try {
                labyrinthoOption = sc.nextInt();
                if (labyrinthoOption < 1 || labyrinthoOption > 3) System.out.println("Aquesta opció no existeix.\n");
                else flag = true;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("La opció ha de ser un número.\n");
            }

        } while(!flag);
        System.out.println("Bona elecció!\n");
    }

    private void menuForWords() {
        boolean flag = false;
        do {
            System.out.println("Esculliu l'estratègia per resoldre la sopa de lletres");
            System.out.println("\t1. Greedy");
            System.out.println("\t2. Branch and Bound\n");

            System.out.print("Introduiu l'opció (número): ");
            try {
                wordsOption = sc.nextInt();
                if (wordsOption < 1 || wordsOption > 2) System.out.println("Aquesta opció no existeix.\n");
                else flag = true;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("La opció ha de ser un número.\n");
            }

        } while(!flag);
        System.out.println("Bona elecció!\n");
    }

    public int getLabyrinthoOption() {
        return labyrinthoOption;
    }

    public int getWordsOption() {
        return wordsOption;
    }
}
