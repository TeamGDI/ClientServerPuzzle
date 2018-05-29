package com.att.biq.puzzle.server;


public class PieceRequirement extends Shape {

    public static final int JOKER = Integer.MIN_VALUE;

    public PieceRequirement(int[] edges) {
        super(edges);
    }

    public boolean match(Shape shape) {
        return edgeMatch(getLeft(), shape.getLeft()) &&
               edgeMatch(getTop(), shape.getTop()) &&
               edgeMatch(getRight(), shape.getRight()) &&
               edgeMatch(getBottom(), shape.getBottom());
    }

    private boolean edgeMatch(int reqEdge, int pieceEdge) {
        return reqEdge == JOKER || reqEdge == pieceEdge;
    }
}
