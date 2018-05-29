package com.att.biq.puzzle.server;

import java.util.*;

public class PuzzleIndexer {

    private Map<Shape, Set<Piece>> pieces = new HashMap<>();

    public PuzzleIndexer(ArrayList<Piece> puzzlePiecesList,boolean isRotate) {
        for(Piece p : puzzlePiecesList){
            if (isRotate){
                add(p);
            }
            else{
               addNoRotation(p);
            }
        }
    }

    private void addNoRotation(Piece p) {
        Set<Piece> piecesSet = pieces.get(p.getEdges());
        if(piecesSet == null) {
            piecesSet = new HashSet<>();
            pieces.put(p.getEdges(), piecesSet);
        }
        piecesSet.add(p);
    }

    public Map<Shape, Set<Piece>> getPieces() {
        return pieces;
    }

    public void add(Piece piece) {
        for(Piece p : piece.rotations()) {
            Set<Piece> piecesSet = pieces.get(p.getEdges());
            if(piecesSet == null) {
                piecesSet = new HashSet<>();
                pieces.put(p.getEdges(), piecesSet);
            }
            piecesSet.add(p);
        }
    }

    //public Collection<Piece> getMatchingPieces (int reqLeft, int reqTop, int reqRight, int reqBottom, Set<Integer> usedPieceIds) {
    public Collection<Piece> getMatchingPieces (int reqLeft, int reqTop, int reqRight, int reqBottom,List<Integer> usedPieceIds) {
        return getMatchingPieces(new PieceRequirement(new int[]{reqLeft, reqTop, reqRight, reqBottom}), usedPieceIds);
    }

    //public Collection<Piece> getMatchingPieces (PieceRequirement req, Set<Integer> usedPieceIds) {
    public Collection<Piece> getMatchingPieces (PieceRequirement req, List<Integer> usedPieceIds) {
        Set<Piece> retval = new HashSet<>();
        for(Map.Entry<Shape, Set<Piece>> entry: pieces.entrySet()) {
            Shape puzzleShape = entry.getKey();
            if(req.match(puzzleShape)) {
                Set<Piece> identities = entry.getValue();
                for(Piece p : identities) {
                    if(!usedPieceIds.contains(p.getId())) {
                        retval.add(p);
                        break;
                    }
                }
            }
        }
        return retval;
    }
}
