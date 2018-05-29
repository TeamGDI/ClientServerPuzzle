package com.att.biq.puzzle.client;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * 
 * @author Guy Bitan
 *
 */
public abstract class ErrorsManager
{
	public final static String ERROR_MISSING_IN_FILE = "Input file doesn't exist in: ";
	public final static String ERROR_NUM_ELEMENTS_NOT_EQUAL_TO_ACTUAL_PIECES = "Number of elements is not equal to actual pieces NumElements=";
	public final static String ERROR_MISSING_ELEMENTS = "Missing puzzle element(s) with the following IDs: ";
	public final static String ERROR_WRONG_ELEMENT_IDS = "Wrong element IDs: ";
	public final static String ERROR_WRONG_ELEMENTS_FORMAT = "Wrong elements format: ";
	public final static String ERROR_NUM_ELEMENTS = "Number of good elements are invalid: ";
	public final static String ERROR_NUM_STRAIGHT_EDGES = "Can't solve puzzle: wrong number of straight edges";
	public final static String ERROR_MISSING_CORNER_ELEMENT = "Can't solve puzzle: missing corner element";
	public final static String ERROR_WRONG_ELEMENTS_VALUES = "Wrong elements value on line: ";
	public final static String ERROR_EDGES_SUM_NOT_ZERO = "Can't solve puzzle: edges sum is not zero";
	public final static String ERROR_BAD_INPUT_FORMAT = "Bad input file format in line: ";

	private ArrayList<String> errorsList = new ArrayList<String>();

	/**
	 * Add error to list
	 * 
	 * @param errorMsg
	 */
	public void addError(String errorMsg)
	{
		this.errorsList.add(errorMsg);
	}

	/**
	 * Clear all errors from list
	 */
	public void clear()
	{
		this.errorsList.clear();
	}

	/**
	 * Get all errors from list
	 * 
	 * @return
	 */
	public ArrayList<String> getAllErrors()
	{
		return this.errorsList;
	}

	/**
	 * Print errors to file
	 * 
	 * @param outPutFile
	 * @throws IOException
	 */
	public void printErrorsToFile(String outPutFile) throws IOException
	{

		File fout = new File(outPutFile);
		try (FileOutputStream fos = new FileOutputStream(fout);
			 final BufferedWriter bw= Files.newBufferedWriter(fout.toPath(),StandardCharsets.UTF_8))
		{
			for (String error : errorsList)
			{
				bw.write(error + "\n");
			}
		}
	}
}
