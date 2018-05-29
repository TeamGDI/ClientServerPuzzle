package com.att.biq.puzzle.server;


public class Shape {

    private int leftPosition = 0;

    private int edges[] = new int[4];

    public Shape(int[] edges) {
        this.edges = edges;
    }

    public Shape(int[] edges, int leftPosition) {
        this.edges = edges;
        this.leftPosition = leftPosition;
    }

    public int[] getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape other = (Shape) o;
        return getLeft() == other.getLeft() && getTop() == other.getTop() && getRight() == other.getRight() && getBottom() == other.getBottom();
    }

    @Override
    public int hashCode() {
        return getLeft() * 54 + getTop() * 21 + getRight() * 3 + getBottom();
    }

    public Shape createRotatedClockWise() {
        return new Shape(this.edges, (leftPosition + 1) % 4);
    }

    public int getLeft() {
        return edges[leftPosition];
    }

    public int getTop() {
        return edges[(leftPosition+1)%4]; // TODO: document the numbers
    }

    public int getRight() {
        return edges[(leftPosition+2)%4]; // TODO: document the numbers
    }

    public int getBottom() {
        return edges[(leftPosition+3)%4]; // TODO: document the numbers
    }
}
