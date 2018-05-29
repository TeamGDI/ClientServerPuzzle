package com.att.biq.puzzle.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.att.biq.puzzle.server.Piece;
import com.att.biq.puzzle.server.PuzzleSolver;
import com.att.biq.puzzle.server.Shape;
import com.att.biq.puzzle.client.FileManager;

public class ValidatePuzzleSolution {

    public static boolean validate(String input, String solutionFile) throws IOException {
        Piece[][] solutionToCheck = testPuzzleSolutions(input,solutionFile);
        PuzzleSolver puzzleSolver = new PuzzleSolver();
        if(solutionToCheck !=null && puzzleSolver.checkSolution(solutionToCheck)){
            return true;
        } else{
            return false;
        }
    }


    public static Piece[][] testPuzzleSolutions(String input, String solutionFile) throws IOException {
        Piece[][] solutionToCheck=null;
        ArrayList<Piece> pieces = getPieces(input);
        pieces = rotatePiecesDueTOSolutionFile(pieces,solutionFile);
        if (pieces!=null){
            solutionToCheck = getSolutionToCheck(pieces , solutionFile);
        }
        return solutionToCheck;
    }

    private static ArrayList<Piece> rotatePiecesDueTOSolutionFile(ArrayList<Piece> pieces, String solutionFile) {
        ArrayList<Piece> rotatePieces = new ArrayList<>();
        try {
            File file = new File(solutionFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line!=""){
                    String[] sol = line.split(" ");
                    for(int i=0;i<sol.length;i++) {
                        try {
                            int pId = Integer.parseInt(sol[i]);
                            int rotation = 0;
                            if (i + 1 < sol.length) {
                                if (sol[i + 1].contains("[")) {
                                    rotation = (Integer.parseInt(sol[i + 1].substring(1, sol[i + 1].length() - 1))) / 90;
                                    i = i + 1;
                                }
                            }
                            Shape shape = getPieceShape(pId, pieces);
                            if (rotation != 0) {
                                rotatePieces.add(new Piece(pId, new Shape(shape.getEdges(), rotation), rotation));
                            } else {
                                rotatePieces.add(new Piece(pId, shape, rotation));
                            }

                        } catch (Exception e){
                            return null;
                        }
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatePieces;
    }

    private static Shape getPieceShape(int pId, ArrayList<Piece> pieces) {
        for(Piece p : pieces) {
            if (p.getId() == pId) {
                return p.getEdges();
            }
        }
        return null;
    }


    private static ArrayList<Piece> getPieces(String input) throws IOException {
        FileManager fileManager = new FileManager(input);
        fileManager.setPiecesFromFile();
        return fileManager.getPieces();
    }


    private static Piece[][] getSolutionToCheck(ArrayList<Piece> pieces, String solutionFile) {
        ArrayList<ArrayList<Integer>> solToCheck= getSolutionFromFile(solutionFile);
        if(solToCheck==null){
            return null;
        }
        Piece[][] sol=null;
        if(solToCheck.isEmpty()){
            return sol;
        }
        sol = new Piece[solToCheck.size()][solToCheck.get(0).size()];
        for (int row=0;row<solToCheck.size();row++){
            for (int col=0;col<solToCheck.get(0).size();col++){
                for(Piece piece : pieces){
                    if (piece.getId()== solToCheck.get(row).get(col)){
                        sol[row][col] = piece;
                        break;
                    }
                }
            }
        }
        return sol;
    }

    private static ArrayList<ArrayList<Integer>> getSolutionFromFile(String solutionFile) {
        ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
        try {
            File file = new File(solutionFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line!=""){
                    ArrayList<Integer> tmp =  new ArrayList<>();
                    String[] piece_ids = line.split(" ");
                    for(int i=0;i<piece_ids.length;i++){
                        try{
                            int pId = Integer.parseInt(piece_ids[i]);
                            if (i+1<piece_ids.length) {
                                if (piece_ids[i+1].contains("[")) {
                                    i=i+1;
                                }
                            }
                            tmp.add(pId);
                        } catch (Exception e){
                            return null;
                        }
                    }
                    sol.add(tmp);
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sol;
    }




}
