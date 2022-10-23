package net.davekirkwood.sudoku.client.history;

import net.davekirkwood.sudoku.client.graphics.Square;

public class HistoryObj {

	private Square square;
	private int value;
	private int[] candidates;
	private int[] corners;
	
	public HistoryObj(Square square) {
		this.square = square;
		this.value = square.getValue();
		this.candidates = square.getCandidates();
		this.corners = square.getCorners();
	}

	public Square getSquare() {
		return square;
	}

	public int getValue() {
		return value;
	}

	public int[] getCandidates() {
		return candidates;
	}

	public int[] getCorners() {
		return corners;
	}
	
	
}
