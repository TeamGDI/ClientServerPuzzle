package com.att.biq.puzzle.server;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the puzzle class.
 *
 * Author: Doron Niv Date: 01/04/2018
 */
public class Puzzle{

    /**
	 * Class fields: puzzlePieces - List of puzzle pieces. solution - Two
	 * dimensional array that represent the puzzle solution.
	 */
    private PuzzleIndexer indexerPieces;
    private int numOfPieces;
    private boolean isRotate;
    private int numOfThreads;
    ArrayList<Integer> solutionPossibleRows;
	private Piece[][] solution;
	private boolean iSolved = false;
    private static AtomicBoolean solutionFound = new AtomicBoolean(false);
	/**
	 * This constructor initialize new Puzzle with a list of all his pieces.
	 */
    public Puzzle(PuzzleIndexer puzzleIndexer, int numOfPieces, boolean isRotate, int numOfThreads, ArrayList<Integer> solutionPossibleRows) {
        this.indexerPieces=puzzleIndexer;
        this.numOfPieces = numOfPieces;
        this.isRotate=isRotate;
        this.numOfThreads=numOfThreads;
        this.solutionPossibleRows = solutionPossibleRows;
    }

    public Piece[][] getSolution() {
        return solution;
    }

    public void solve(){
        if (numOfThreads <= 1) {
            solvePuzzleRegular();
        }
        else {
            solvePuzzleByThreads();
        }
    }


    private void solvePuzzleRegular(){
        PuzzleSolver puzzleSolver;
        solutionFound.getAndSet(false);
 	    for (Integer row : solutionPossibleRows) {
            int columns = numOfPieces/row;
            puzzleSolver = new PuzzleSolver(indexerPieces,row,columns);
            solution = puzzleSolver.solve(solutionFound);
            if(solution!=null){
                break;
            }
        }
    }


    public void solvePuzzleByThreads(){
        solutionFound.getAndSet(false);
        int index = 0;
        ArrayList<PuzzleSolver> pSolver = new ArrayList<>();
        if(solutionPossibleRows.size()<numOfThreads){
            numOfThreads=solutionPossibleRows.size();
        }
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        for(int row : solutionPossibleRows) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    int columns = numOfPieces / row;
                    PuzzleSolver puzzleSolver = new PuzzleSolver(indexerPieces, row, columns);
                    //System.out.println("Try to solve puzzle with " + row+ "x"+columns +  " board size ");
                    solution = puzzleSolver.solve(solutionFound);
                    pSolver.add(puzzleSolver);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()){

        }
        for(PuzzleSolver ps : pSolver){
            if(ps.isSolved()){
                index = pSolver.indexOf(ps);
                break;
            }
        }
        solution = pSolver.get(index).getSolution();
    }


    public String solution2String(){
        String sol="";
        if(solution!=null) {
            for (int i=0;i<solution.length;i++){
                for (int j=0;j<solution[0].length;j++){
                    if(solution[i][j].getRotation()==0) {
                        sol += solution[i][j] + " ";
                    } else {
                        sol += solution[i][j]+" [" +(solution[i][j].getRotation()*90)+"] ";
                    }
                }
                sol = sol.trim();
                sol+="\n";
                System.out.print("\n");
            }
        } else{
            sol = "Cannot solve puzzle: it seems that there is no proper solution\n";
            //System.out.println("Cannot solve puzzle: it seems that there is no proper solution");
        }
        return sol;
    }

	public synchronized void saveSolution2File(String outPutFile) throws IOException {
		File fout = new File(outPutFile);
		try (FileOutputStream fos = new FileOutputStream(fout);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
			bw.write(this.solution2String());
			bw.close();
		}
	}

}
