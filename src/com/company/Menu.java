package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private int labyrinthoOption;
    private int wordsOption;
    private final Scanner sc;
    private boolean visualize;
    private boolean analyze;

    public Menu() {
        sc = new Scanner(System.in);
    }

    public void start() {
        boolean flag = false;
        int option;
        do {
            System.out.println("Benvingut al Arcade! Escolleix el que vols fer:");
            System.out.println("\t1. Executar algorismes.");
            System.out.println("\t2. Visualitzar algorismes.");
            System.out.println("\t3. Executar analisi.\n");

            System.out.print("Introduiu l'opció (número): ");
            try {
                option = sc.nextInt();
                if (option < 1 || option > 3) System.out.println("Aquesta opció no existeix.\n");
                else {
                    flag = true;
                    menuOptions(option);
                }
                ;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("La opció ha de ser un número.\n");
            }
        } while (!flag);
    }

    private void menuOptions(int opcio) {
        switch (opcio) {
            case 2 -> visualize = true;
            case 3 -> analyze = true;
        }
        menuAlgorithms();
    }

    private void menuAlgorithms() {
        menuForLabyrinth();
        menuForWords();
    }

    private void menuForLabyrinth() {
        boolean flag = false;
        do {
            System.out.println("\nEsculliu l'estratègia per resoldre el Laberint");
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

        } while (!flag);
        System.out.println("Bona elecció!\n");
    }

    private void menuForWords() {
        boolean flag = false;
        do {
            System.out.println("Esculliu l'estratègia per resoldre la sopa de lletres");
            System.out.println("\t1. Greedy");
            System.out.println("\t2. Backtracking\n");

            System.out.print("Introduiu l'opció (número): ");
            try {
                wordsOption = sc.nextInt();
                if (wordsOption < 1 || wordsOption > 2) System.out.println("Aquesta opció no existeix.\n");
                else flag = true;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("La opció ha de ser un número.\n");
            }

        } while (!flag);
        System.out.println("Bona elecció!\n");
    }

    public int getLabyrinthoOption() {
        return labyrinthoOption;
    }

    public int getWordsOption() {
        return wordsOption;
    }

    public boolean isVisualize() {
        return visualize;
    }

    public boolean isAnalyze() {
        return analyze;
    }
}
