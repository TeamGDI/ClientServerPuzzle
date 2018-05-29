package com.att.biq.puzzle.server;


/**
 * 
 * @author Guy Bitan
 *
 */
public class Piece implements Comparable<Piece> {

	private int id;
	private int rotation=0;
	private Shape edges;

	public Piece(int id, int[] edges) {
		this.id = id;
		this.edges = new Shape(edges);
	}

	public Piece(int id, Shape edges, int rotation) {
		this.id = id;
		this.edges = edges;
		this.rotation = rotation;
	}

	public Shape getEdges() {
		return edges;
	}

	public Integer getId() { return id; }

	public int getRotation() {
		return rotation;
	}

	public int getLeft() { return edges.getLeft(); }

	public int getTop() { return edges.getTop(); }

	public int getRight() {
		return edges.getRight();
	}

	public int getBottom() { return edges.getBottom(); }


	@Override
	public int compareTo(Piece piece) {
		return getId().compareTo(piece.getId());
	}

	@Override
	public boolean equals(Object obj) {
		return id==((Piece)obj).id;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}

	public Piece[] rotations() {
		Piece[] pieces = new Piece[4];
		pieces[0] = this;
		pieces[1] = pieces[0].createRotatedClockWise();
		pieces[2] = pieces[1].createRotatedClockWise();
		pieces[3] = pieces[2].createRotatedClockWise();
		return pieces;
	}

	public Piece createRotatedClockWise() {
		return new Piece(this.id, this.edges.createRotatedClockWise(), rotation+1);
	}
}
