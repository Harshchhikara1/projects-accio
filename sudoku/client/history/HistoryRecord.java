package net.davekirkwood.sudoku.client.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecord {

	private List<HistoryObj> historyObjects = new ArrayList<HistoryObj>();

	public HistoryRecord(List<HistoryObj> historyObjects) {
		super();
		this.historyObjects = historyObjects;
	}

	public List<HistoryObj> getHistoryObjects() {
		return historyObjects;
	}
	
	
	
}
