package com.att.biq.puzzle.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import com.att.biq.puzzle.server.AnalyzeInputs;
import com.att.biq.puzzle.server.Piece;
import org.junit.Test;

import com.att.biq.puzzle.client.FileManager;

public class AnalyzeinputTests {

	@Test
	public void ValidateSumOfEdgesGoodTest() {
		// Sum of edges for a single piece and assert is zero

		Piece pc1 =  new Piece(10,new int[]{0,0,0,0});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);

		analyze.validateEdgesSum();
		assertTrue(analyze.getErrorsList().isEmpty());
	}

	@Test
	public void ValidateSumOfEdgesBadTest() {
		// Sum of edges for single piece is not zero
		
		Piece pc1 = new Piece(11,new int[]{1,-1,1,1});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);
		analyze.validateEdgesSum();
		assertTrue(analyze.getErrorsList().contains("Can't solve puzzle: edges sum is not zero"));
	}

	@Test
	public void ValidatePieceFormatGoodTest() {
		// Format of edges for single piece is good: 0,0,1,0
		Piece pc1 =  new Piece(11,new int[]{0,0,1,0});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);

		analyze.validatePiecesFormat();
		assertFalse(analyze.getErrorsList().contains("Wrong elements format: 11"));

	}

	@Test
	public void ValidatePieceFormatBadTest() {
		// Format of edges for single piece is bad: 0,0,2,0

		Piece pc1 =  new Piece(13,new int[]{0,2,0,0});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);
		analyze.validatePiecesFormat();

		assertTrue(analyze.getErrorsList().contains("Wrong elements value on line: 13"));

	}

	@Test
	public void ValidateWrongNumberOfStraightEdgesBadTest() {
		// Less than minimum number of straight edges
		Piece pc1 =  new Piece(10,new int[]{0,1,1,0});
		Piece pc2 =  new Piece(11,new int[]{0,1,0,1});

		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		pcs.add(pc2);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);
		assertTrue(analyze.validateMinimumStraightEdges().isEmpty());

	}

	@Test
	public void ValidateNumberOfStraightEdgesGoodTest() {
		// input has minimum+ number of straight edges

		Piece pc1 =  new Piece(10,new int[]{0,0,0,0});
		Piece pc2 =  new Piece(11,new int[]{0,0,0,0});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		pcs.add(pc2);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);

		assertFalse(analyze.validateMinimumStraightEdges().isEmpty());

	}

	@Test
	public void ValidatePiecesCornersGoodTest() {
		// Input has minimum+ corners

		Piece pc1 =  new Piece(10,new int[]{0,0,0,0});
		Piece pc2 =  new Piece(11,new int[]{0,0,0,0});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		pcs.add(pc2);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);

		analyze.validateMinimumCorners();
		assertTrue(analyze.getErrorsList().isEmpty());

	}

	@Test
	public void ValidatePiecesCornersBadTest() {
		// Less than minimum corners
		Piece pc1 =  new Piece(10,new int[]{0,1,1,0});
		Piece pc2 =  new Piece(11,new int[]{0,-1,-1,0});
		ArrayList<Piece> pcs = new ArrayList<>();
		pcs.add(pc1);
		pcs.add(pc2);
		AnalyzeInputs analyze = new AnalyzeInputs(pcs);
		analyze.validateMinimumCorners();
		assertTrue(analyze.getErrorsList().contains("Can't solve puzzle: missing corner element"));
		
	}

	@Test
	public void GetPiecesFromFileAndAnalayzeBadFileTest1() throws IOException {
		String piecesFile = "./resources/analyzedInputTestsFiles/test1.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		AnalyzeInputs analyze = new AnalyzeInputs(fileManager.getPieces());
		analyze.analyzePicesList();
		assertTrue(analyze.getErrorsList().contains("Can't solve puzzle: wrong number of straight edges"));
		assertTrue(analyze.getErrorsList().contains("Can't solve puzzle: missing corner element"));
	}

	@Test
	public void GetPiecesFromFileAndAnalayzeGoodFileTest() throws IOException {
		String piecesFile = "./resources/analyzedInputTestsFiles/test2.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		AnalyzeInputs analyze = new AnalyzeInputs(fileManager.getPieces());
		analyze.analyzePicesList();
		assertTrue(analyze.getErrorsList().isEmpty());
	}
}
