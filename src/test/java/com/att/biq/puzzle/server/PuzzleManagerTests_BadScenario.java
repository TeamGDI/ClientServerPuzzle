package com.att.biq.puzzle.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.att.biq.puzzle.server.ValidatePuzzleSolution;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class PuzzleManagerTests_BadScenario {
	private String pathToFilesFolder = "./resources/PuzzleManagerTests_BadScenarioFiles/";
	private String piecesInputFile;
	private String testOutputFile;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "test1.in", "test1.testOut" }, { "test14.in", "test14.testOut" }, });
	}

	public PuzzleManagerTests_BadScenario(String piecesInputFile, String testOutputFile) {
		this.piecesInputFile = pathToFilesFolder + piecesInputFile;
		this.testOutputFile = pathToFilesFolder + testOutputFile;
	}

	@Test
	public void testBadFilesSolution() throws IOException {
		assertFalse(ValidatePuzzleSolution.validate(piecesInputFile, testOutputFile));
	}

}
