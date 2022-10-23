package net.davekirkwood.sudoku.client.history;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;

public class History {

	public static List<HistoryRecord> undoHistory = new ArrayList<HistoryRecord>();
	public static List<HistoryRecord> redoHistory = new ArrayList<HistoryRecord>();
	
	public static void clearHistory() {
//		Window.alert("Clear history");
		undoHistory.clear();
		redoHistory.clear();
	}
	
	public static void clearRedo() {
		redoHistory.clear();
	}
	
	public static void addUndo(HistoryRecord undo) {
//		Window.alert("Add Undo");
		undoHistory.add(undo);
	}
	
	public static void addRedo(HistoryRecord undo) {
		redoHistory.add(undo);
	}
	
	public static HistoryRecord getUndo() {
//		Window.alert("Get Undo");
		if(undoHistory.size() > 0) {
			return undoHistory.remove(undoHistory.size()-1);
		}
		return null;
	}
	
	public static HistoryRecord getRedo() {
		if(redoHistory.size() > 0) {
			return redoHistory.remove(redoHistory.size()-1);
		}
		return null;
	}
}
