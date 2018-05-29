package com.att.biq.puzzle.server;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Test;

import com.att.biq.puzzle.client.ErrorsManager;
import com.att.biq.puzzle.client.FileManager;

public class FileManagerTests {

	@Test
	public void InputFileDoesNotExistTest() throws IOException {
		//file does not exist
		String piecesFile = "./resources/FileManagerTestsFiles/testKuku.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains("Input file doesn't exist"));
		assertTrue(fileManager.getAllErrors().size() == 1);

	}
	
	@Test
	public void NumberOfElementBadTest() throws IOException {
		//wrong Number Of Element
		String piecesFile = "./resources/FileManagerTestsFiles/test1.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains("Number of elements is not equal to actual pieces NumElements=10 and actual is:9"));
	}
	
	@Test
	public void MissingAndWrongElementsIdTest() throws IOException {
		//Missing elements ids
		String piecesFile = "./resources/FileManagerTestsFiles/test2.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains("Missing puzzle element(s) with the following IDs: 2,8"));
		assertTrue(fileManager.getAllErrors().toString().contains("Wrong element IDs: 10"));
	}
	
	@Test
	public void WrongElementsformatTest() throws IOException {
		//Missing edge, line like: 1 0 0 0 
		String piecesFile = "./resources/FileManagerTestsFiles/test3.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains(ErrorsManager.ERROR_WRONG_ELEMENTS_FORMAT));
	}
	
	@Test
	public void ExtraElementsInLineBadTest() throws IOException {
		//Extra digit in a line like: 1 0 0 0 -1 2
		String piecesFile = "./resources/FileManagerTestsFiles/test4.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains(ErrorsManager.ERROR_WRONG_ELEMENTS_FORMAT));
	}
	
	@Test
	public void ElementsEdgeAreNotNumbersBadTest() throws IOException {
		//Not a digit for edge in a line like: 1 E 0 0 -1 
		String piecesFile = "./resources/FileManagerTestsFiles/test5.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains("Bad input file format in line: 3"));
	}
	
	@Test
	public void ElementsIdIsNotNumbersBadTest() throws IOException {
		//Is is not a digit like: S 1 0 0 -1 
		String piecesFile = "./resources/FileManagerTestsFiles/test6.in";
		FileManager fileManager = new FileManager(piecesFile);
		fileManager.setPiecesFromFile();
		assertTrue(fileManager.getAllErrors().toString().contains("Bad input file format in line: S"));
	}
	
}
