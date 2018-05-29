package com.att.biq.puzzle.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.att.biq.puzzle.server.Piece;

/**
 * 
 * @author Guy Bitan
 *
 */
public class FileManager extends ErrorsManager
{
	private final int SIDES = 5;
	private final String NUM_OF_ELEMENTS_KEY = "NumElements=";
	private final String SEPARATOR = " ";
	private final String COMMENTLINE = "#";
	private File inputFilePath;
	private int numElements = 0;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();

	public FileManager(String inputFilePath)
	{
		this.inputFilePath = new File(inputFilePath);
	}

	public FileManager()
	{
	}

	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}

	/**
	 * get pieces from file input
	 * 
	 * @return
	 * @throws IOException
	 */
	public void setPiecesFromFile() throws IOException
	{
		if (inputFilePath.exists())
		{
			try (FileInputStream fis = new FileInputStream(inputFilePath))
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null)
				{
					if (!sCurrentLine.startsWith(COMMENTLINE) && isNumElementsValid(sCurrentLine))
					{
						if (!sCurrentLine.replaceAll(" ", "").startsWith(NUM_OF_ELEMENTS_KEY))
						{
							int[] piece = convertLineToArr(sCurrentLine);

							if (piece != null)
							{
								addPiece(piece);
							}
						}
					}
				}
			}
			if (!isIdsAndSizeAreValids())
			{
				pieces = null;
			}
		}
		else
		{
			addError(ERROR_MISSING_IN_FILE + inputFilePath.getAbsolutePath());
		}
	}

	/**
	 * Check if the NumElements variable is a valid format
	 * 
	 * @param sCurrentLine
	 * @return
	 * @throws IOException
	 */
	private boolean isNumElementsValid(String sCurrentLine) throws IOException
	{
		if (numElements == 0)
		{
			if (sCurrentLine.replaceAll("\\s", "").startsWith(NUM_OF_ELEMENTS_KEY))
			{
				String[] numElementsArr = sCurrentLine.split("=");
				String value = numElementsArr[1].trim();

				if (value.isEmpty())
				{
					return false;
				}
				else
				{
					try
					{
						numElements = Integer.parseInt(value);
						return true;
					}
					catch (Exception e)
					{
						addError(e.getMessage());
						return false;
					}
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}

	/**
	 * Convert line string to integer array
	 * 
	 * @param sCurrentLine
	 * @return
	 */
	private int[] convertLineToArr(String sCurrentLine)
	{
		int[] goodPiece = new int[SIDES];

		if (sCurrentLine.startsWith(NUM_OF_ELEMENTS_KEY))
		{
			return null;
		}
		else
		{
			String[] lineArr = sCurrentLine.split(SEPARATOR);
			if (lineArr.length == 5)
			{
				int i = 0;
				for (String str : lineArr)
				{
					try
					{
						goodPiece[i++] = Integer.parseInt(str);
					}
					catch (NumberFormatException nfe)
					{
						addError(ERROR_BAD_INPUT_FORMAT + sCurrentLine);
						return null;
					}
				}
			}
			else
			{
				addError(ERROR_WRONG_ELEMENTS_FORMAT + sCurrentLine);
				return null;
			}
		}
		return goodPiece;
	}

	/**
	 * Add piece to ArrayList
	 * 
	 * @param pieceArr
	 */
	private void addPiece(int[] pieceArr)
	{
		int id = pieceArr[0];
		int[] newPieceArr = new int[4];
		newPieceArr[0] = pieceArr[1];
		newPieceArr[1] = pieceArr[2];
		newPieceArr[2] = pieceArr[3];
		newPieceArr[3] = pieceArr[4];
		pieces.add(new Piece(id, newPieceArr));
	}

	/**
	 * check if id's & size are valid
	 * 
	 * @return
	 */
	private boolean isIdsAndSizeAreValids()
	{
		String missingElements = "", wrongElements = "";
		boolean status = true;

		if (pieces.size() == numElements)
		{
			int[] arr = new int[numElements];
			for (int i = 0; i < numElements; i++)
			{
				int id = pieces.get(i).getId();

				if (id > numElements)
				{
					wrongElements += pieces.get(i).getId() + ",";
				}
				else
				{
					arr[id - 1] = 1;
				}
			}
			for (int i = 0; i < numElements; i++)
			{
				if (arr[i] != 1)
				{
					missingElements += i + 1 + ",";
				}
			}
		}
		else
		{
			addError(ERROR_NUM_ELEMENTS_NOT_EQUAL_TO_ACTUAL_PIECES + numElements + " and actual is:" + pieces.size());
			status = false;
		}
		if (!missingElements.isEmpty())
		{
			status = false;
			missingElements = missingElements.substring(0, missingElements.lastIndexOf(","));
			addError(ERROR_MISSING_ELEMENTS + missingElements);
		}
		if (!wrongElements.isEmpty())
		{
			status = false;
			wrongElements = wrongElements.substring(0, wrongElements.lastIndexOf(","));
			addError(ERROR_WRONG_ELEMENT_IDS + wrongElements);
		}
		return status;
	}
}
