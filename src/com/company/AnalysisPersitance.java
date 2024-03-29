package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AnalysisPersitance {

    public static final int LABYRINTH_BACK = 1;
    public static final int LABYRINTH_BACK_IMPROVED = 2;
    public static final int LABYRINTH_BRANCH = 3;
    public static final int WORDS_BACK = 4;
    public static final int WORDS_GREED = 5;

    private class Pair {
        public int dimension;
        public int ms;

        public Pair(int dimension) {
            this.dimension = dimension;
        }

        public void setMs(int ms) {
            this.ms = ms;
        }
    }

    private static AnalysisPersitance instance;

    private List<Pair> LabyrinthBacktrakingData;
    private List<Pair> LabyrinthBacktrakingImprovedData;
    private List<Pair> LabyrinthBranchAndBoundData;
    private List<Pair> WordsBacktrakingData;
    private List<Pair> WordsGreedyData;

    private AnalysisPersitance() {
        resetRecords();
    }

    public static AnalysisPersitance getInstance() {
        if (instance == null) instance = new AnalysisPersitance();
        return instance;
    }

    public void resetRecords(){
        LabyrinthBacktrakingData = new ArrayList<>();
        LabyrinthBacktrakingImprovedData = new ArrayList<>();
        LabyrinthBranchAndBoundData = new ArrayList<>();
        WordsBacktrakingData = new ArrayList<>();
        WordsGreedyData = new ArrayList<>();
    }

    public void createRecords(int dimension){
        LabyrinthBacktrakingData.add(new Pair(dimension));
        LabyrinthBacktrakingImprovedData.add(new Pair(dimension));
        LabyrinthBranchAndBoundData.add(new Pair(dimension));
        WordsBacktrakingData.add(new Pair(dimension));
        WordsGreedyData.add(new Pair(dimension));
    }

    public void createRecord(int dataType, int dimension) {
        switch (dataType) {
            case LABYRINTH_BACK -> LabyrinthBacktrakingData.add(new Pair(dimension));
            case LABYRINTH_BACK_IMPROVED -> LabyrinthBacktrakingImprovedData.add(new Pair(dimension));
            case LABYRINTH_BRANCH -> LabyrinthBranchAndBoundData.add(new Pair(dimension));
            case WORDS_BACK -> WordsBacktrakingData.add(new Pair(dimension));
            case WORDS_GREED -> WordsGreedyData.add(new Pair(dimension));
        }
    }

    public void fillRecord(int dataType, int ms) {
        switch (dataType) {
            case LABYRINTH_BACK ->
                    LabyrinthBacktrakingData.get(LabyrinthBacktrakingData.size() - 1).ms = ms;
            case LABYRINTH_BACK_IMPROVED ->
                    LabyrinthBacktrakingImprovedData.get(LabyrinthBacktrakingImprovedData.size() - 1).ms = ms;
            case LABYRINTH_BRANCH ->
                    LabyrinthBranchAndBoundData.get(LabyrinthBranchAndBoundData.size() - 1).ms = ms;
            case WORDS_BACK ->
                    WordsBacktrakingData.get(WordsBacktrakingData.size() - 1).ms = ms;
            case WORDS_GREED ->
                    WordsGreedyData.get(WordsGreedyData.size() - 1).ms = ms;
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(instance, AnalysisPersitance.class);
    }

    public void exportToFile(String filename){
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(filename);
            myWriter.write(this.toJson());
            myWriter.close();
        } catch (Exception e) {
            System.out.println(" -- ERROR: al guardar la persistencia!! --");
            e.printStackTrace();
        }
    }
}
