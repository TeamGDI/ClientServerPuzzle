package com.att.biq.puzzle.server;

import java.io.IOException;
import java.util.ArrayList;
import com.att.biq.puzzle.client.FileManager;

public class PuzzleManager {

	private String inputPiecesFile;
	private String outPutFile;
	private ArrayList<Piece> pieces;
	private boolean isRotate;
	private int numOfThreads = -1;
	
	public PuzzleManager(String inputPiecesFile, String outPutFile, boolean isRotate, int numOfThreads) {
		this.inputPiecesFile = inputPiecesFile;
		this.outPutFile = outPutFile;
		this.isRotate = isRotate;
		this.numOfThreads = numOfThreads;
		pieces = new ArrayList<>();
	}

	public void solvePuzzle() throws IOException {
		FileManager fileManager = new FileManager(inputPiecesFile);
		fileManager.setPiecesFromFile();

		AnalyzeInputs analyzeInputs = new AnalyzeInputs(fileManager.getPieces());
		analyzeInputs.analyzePicesList();

		if (analyzeInputs.getErrorsList().isEmpty()) {
			pieces = fileManager.getPieces();

			PuzzleIndexer puzzleIndexer = new PuzzleIndexer(pieces, isRotate);

			Puzzle puzzle = new Puzzle(puzzleIndexer, pieces.size(), isRotate, numOfThreads,
					analyzeInputs.getSolutionPossibleRows());
			puzzle.solve();
			puzzle.saveSolution2File(outPutFile);
		} else {
			for (int i = 0; i < analyzeInputs.getErrorsList().size(); i++) {
				fileManager.addError(analyzeInputs.getErrorsList().get(i));
			}
			fileManager.printErrorsToFile(outPutFile);
		}

	}

}
