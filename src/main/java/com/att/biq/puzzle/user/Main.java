package com.att.biq.puzzle.user;

import java.io.IOException;
import com.att.biq.puzzle.server.PuzzleManager;

/**
 * 
 * @author Guy Bitan
 *
 */
public class Main {
	public static String inputFile = null;
	public static String outputFile = null;
	public static boolean isRotate = false;
	public static int numOfThreads = 0;

    public static void main(String[] args) throws IOException {
        setArgs(args);
        if(validateArgs()) {
            PuzzleManager puzzleManager = new PuzzleManager(inputFile, outputFile, isRotate, numOfThreads);
            puzzleManager.solvePuzzle();
        }
    }

    public static void setArgs(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].toLowerCase().equals("-input")) {
                    inputFile = args[i + 1];
                } else if (args[i].toLowerCase().equals("-output")) {
                    outputFile = args[i + 1];
                } else if (args[i].toLowerCase().equals("-threads")) {
                    numOfThreads = Integer.parseInt(args[i + 1]);
                } else if (args[i].toLowerCase().equals("-rotate")) {
                    isRotate = true;
                }
            }
        } catch (Exception e){
            return;
        }
    }

    private static boolean validateArgs() {
        if (inputFile==null || inputFile.equals("") || outputFile==null || outputFile.equals("")){
            System.out.println("ERROR  -  Invalid parameters");
            System.out.println(" please execute the program again with the below parameters:");
            System.out.println(" -input  <path to input file>");
            System.out.println(" -output <path to input file>");
            System.out.println(" -threads <number>  (optional, the default option puzzle solution is without threads)");
            System.out.println(" -rotate   (optional, the default option puzzle solution is without rotation)");
            return false;
        }
        return true;
    }
}
