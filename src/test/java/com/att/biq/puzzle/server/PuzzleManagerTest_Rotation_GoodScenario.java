package com.att.biq.puzzle.server;

import com.att.biq.puzzle.server.PuzzleManager;
import com.att.biq.puzzle.utility.ValidatePuzzleSolution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PuzzleManagerTest_Rotation_GoodScenario
{
	private String pathToFilesFolder = "./resources/PuzzleManagerTest_GoodScenarioFiles/";
	private String pathToSolutionFilesFolder = "./resources/PuzzleManagerTest_GoodScenarioFiles/";
	private String piecesInputFile;
	private String testOutputFile;

	@Parameterized.Parameters
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][]
		{
				{ "test1.in", "test1.rotationTestOut" },
				{ "test2.in", "test2.rotationTestOut" },
				{ "test9.in", "test9.rotationTestOut" },
				{ "test10.in", "test10.rotationTestOut" },
				{ "test11.in", "test11.rotationTestOut" },
				{ "test12.in", "test12.rotationTestOut" },
				{ "test13.in", "test13.rotationTestOut" },
				{ "test14.in", "test14.rotationTestOut" },
				{ "test15.in", "test15.rotationTestOut" },
				{ "test16.in", "test16.rotationTestOut" },
				{ "test17.in", "test17.rotationTestOut" }, });
	}

	public PuzzleManagerTest_Rotation_GoodScenario(String piecesInputFile, String testOutputFile)
	{
		this.piecesInputFile = pathToFilesFolder + piecesInputFile;
		this.testOutputFile = pathToSolutionFilesFolder + testOutputFile;
	}

	@Test
	public void testGoodFilesSolution() throws IOException
	{
		PuzzleManager puzzleManager = new PuzzleManager(piecesInputFile, testOutputFile,true,4);
		puzzleManager.solvePuzzle();
		assertTrue(ValidatePuzzleSolution.validate(piecesInputFile, testOutputFile));
	}
}
