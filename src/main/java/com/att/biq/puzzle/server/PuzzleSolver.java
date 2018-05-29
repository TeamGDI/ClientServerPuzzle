package com.att.biq.puzzle.server;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.att.biq.puzzle.server.PieceRequirement.JOKER;

/**
 * This is the PuzzleSolver class. This class try to solve a given puzzle.
 *
 * Author: Doron Niv Date: 01/04/2018
 */
public class PuzzleSolver {

	/**
	 * Class fields: solution - Two dimensional array that represent the puzzle
	 * solution. fitPieces - List of the puzzle pieces that are already inside the
	 * solution array. isSolved - this field set to true in case there is a solution
	 * for the given puzzle.
	 */
	private int numOfRows;
	private int numOfColumns;
	private Piece[][] solution;
	private PuzzleIndexer puzzleIndexer;
	private List<Integer> usedIds = new ArrayList<>();
	private boolean isSolved = false;
	private int[] nextEmptyPlace = { 0, 0 };
	private int[] prevPiecePlace = new int[2];

	public PuzzleSolver() {
	}

	public PuzzleSolver(PuzzleIndexer puzzleIndexer, int numOfRows, int numOfColumns) {
		this.puzzleIndexer = puzzleIndexer;
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
		this.solution = new Piece[numOfRows][numOfColumns];
	}

	public boolean isSolved() {
		return isSolved;
	}

	public Piece[][] getSolution() {
		return solution;
	}

	/**
	 * This method is used to calculate the number of possible solutions rows
	 * numbers and call to to the findSolution recursive method with all
	 * possibilities.
	 *
	 */

	public Piece[][] solve(AtomicBoolean solutionFound) {
		findSolution(solutionFound);
		if (solutionFound.get()) {
			return solution;
		}
		solution = null;
		return solution;
	}

	/**
	 * This method is recursive method for finding puzzle solution.
	 *
	 */
	private void findSolution(AtomicBoolean solutionFound) {
		if (solutionFound.get()) { // if isSolve flag is true the puzzle is solve and no need to continue checking.
			return;
		}
		PieceRequirement reqPiece = getFitPiece(nextEmptyPlace[0], nextEmptyPlace[1]);
		Collection<Piece> fitPieces = puzzleIndexer.getMatchingPieces(reqPiece, usedIds);
		for (Piece p : fitPieces) {
			if (!solutionFound.get()) {
				solution[nextEmptyPlace[0]][nextEmptyPlace[1]] = p;
				usedIds.add(p.getId());
				if (usedIds.size() < numOfRows * numOfColumns) { // not all puzzle pieces are in use
					setNextEmptyPlace(); // the next empty place (row/column number) in the solution array.
					findSolution(solutionFound);
				}
			}
		}
		if (usedIds.size() == numOfRows * numOfColumns) {
			// All puzzle pieces are inside the solution array and fit each other.
			// In this case the puzzle is solved
			isSolved = true;
			solutionFound.getAndSet(true);
		} else {
			if (usedIds.size() > 0) {
				// remove the last piece from usedIds set collection.
				// usedIds.remove(solution[prevPiecePlace[0]][prevPiecePlace[1]].getId());
				usedIds.remove(usedIds.size() - 1);
				// remove the last piece from the puzzle solution.
				removeLastPieceFromPuzzle();

			}
		}
	}

	/**
	 * This method is recursive method for finding puzzle solution.
	 *
	 * @param row
	 *            The row number (solution[row][col]).
	 * @param col
	 *            The column number (solution[row][col]).
	 *
	 * @return true if piece fit to the current puzzle status else return false.
	 */
	private PieceRequirement getFitPiece(int row, int col) {
		int top;
		int left;
		int right;
		int bottom;

		if (row == 0) { // Top row in the solution array.
			top = 0; // in this case required piece top value must be zero.
		} else { // in this case required piece top value must the opposite from the bottom value
					// of the piece above.
			top = (solution[row - 1][col].getBottom()) * (-1); // multiply with -1 will give the opposite value
		}

		if (row == numOfRows - 1) { // Bottom row in the solution array.
			bottom = 0; // in this case required piece bottom value must be zero.
		} else { // in this case required piece bottom value is not important (JOKER)
			bottom = JOKER;
		}

		if (col == 0) { // Right (first) column in the solution array.
			left = 0; // in this case required piece right value must be zero.
		} else { // in this case required piece left value must the opposite from the left piece
					// right value.
			left = (solution[row][col - 1].getRight()) * (-1); // multiply with -1 will give the opposite value
		}

		if (col == numOfColumns - 1) { // Left (last) row in the solution array.
			right = 0; // in this case required piece left value must be zero (if not return false).
		} else { // in this case required piece right value is not important (JOKER)
			right = JOKER;
		}
		// create req
		PieceRequirement reqPiece = new PieceRequirement(new int[] { left, top, right, bottom });
		return reqPiece;
	}

	private void setNextEmptyPlace() {
		prevPiecePlace[0] = nextEmptyPlace[0];
		prevPiecePlace[1] = nextEmptyPlace[1];
		if (nextEmptyPlace[1] < solution[0].length - 1) {
			nextEmptyPlace[1]++;
		} else if (nextEmptyPlace[0] < solution.length - 1) {
			nextEmptyPlace[0]++;
			nextEmptyPlace[1] = 0;
		}
	}

	/**
	 * This method remove the last piece piece that set in the puzzle solution
	 * array.
	 */
	private void removeLastPieceFromPuzzle() {
		nextEmptyPlace[0] = prevPiecePlace[0];
		nextEmptyPlace[1] = prevPiecePlace[1];
		solution[prevPiecePlace[0]][prevPiecePlace[1]] = null;
		if (prevPiecePlace[1] > 0) {
			prevPiecePlace[1]--;
		} else if (prevPiecePlace[0] > 0) {
			prevPiecePlace[0]--;
			prevPiecePlace[1] = solution[0].length - 1;
		}
	}

	public boolean checkSolution(Piece[][] sol) {
		solution = sol;
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[0].length; j++) {
				PieceRequirement pieceRequirement = getFitPiece(i, j);
				if (!pieceRequirement.match(solution[i][j].getEdges())) {
					return false;
				}
			}
		}
		return true;
	}

	public void stop() {
		// TODO Auto-generated method stub
		Thread.currentThread().interrupt();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
