package net.davekirkwood.sudoku.client.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import net.davekirkwood.sudoku.client.history.HistoryObj;

public class Square extends SButton {
	
	public static final int FONT_SIZE_NORMAL = 58;
	public static final int FONT_SIZE_PENCIL = 15;
	
	private static final String COLOUR_PENCIL = "#555555";
	private static final String COLOUR_LOCKED = "#000000";
	private static final String COLOUR_NORMAL = "#7DA9FF";
	
//   private Image image;
//   private Rectangle cell;
   private int value = 0;
   private boolean locked = false;
   private boolean conflicted = false;
   private List<Integer> candidates = new ArrayList<Integer>();
   private List<Integer> corners = new ArrayList<Integer>();
   
   private boolean highlighted = false;
   private int x;
   private int y;
   boolean changed;
   private int xCoord;
   private int yCoord;
   
   private DrawingArea canvas;
   
   private Rectangle highlightRectangle;
   private Rectangle conflictRectangle;
   
   private List<Text> textObjects = new ArrayList<Text>();
   
   public Square(DrawingArea canvas, int x, int y, int xCoord, int yCoord, int width, int height) {
   	super(xCoord, yCoord, width, height);

      this.x = x;
      this.y = y;
      this.canvas = canvas;
      this.xCoord = xCoord;
      this.yCoord = yCoord;
      
   }
   
	public void initBackground() {
      highlightRectangle = new Rectangle(xCoord + 2, yCoord + 2, getWidth() - 4, getHeight() - 4);
      highlightRectangle.setFillColor("#9Dc9FF");
      highlightRectangle.setStrokeColor("#9Dc9FF");
		canvas.add(highlightRectangle);
      highlightRectangle.setVisible(false);
      
      conflictRectangle = new Rectangle(xCoord + 2, yCoord + 2, getWidth() - 4, getHeight() - 4);
      conflictRectangle.setFillColor("#FF5555");
      conflictRectangle.setStrokeColor("#FF5555");
		canvas.add(conflictRectangle);
		conflictRectangle.setVisible(false);
      
      
	}
	
   public void setValue(int value) {
      changed = this.value != value;
      this.value = value;
      draw();
   }
   
   public void clear() {
   	this.value = 0;
   	this.candidates.clear();
   	this.corners.clear();
   	this.setHighlighted(false);
   	draw();
   }
   
   public void toggleCandidate(Integer candidate) {
   	if(candidate == 0) {
   		candidates.clear();
   	} else {
	   	if(candidates.contains(candidate)) {
	   		candidates.remove(candidate);
	   	} else {
	   		candidates.add(candidate);
	   		Collections.sort(candidates);
	   	}
   	}
   	draw();
   }
   
   public void toggleCorner(Integer corner) {
   	if(corner == 0) {
   		corners.clear();
   	} else {
	   	if(corners.contains(corner)) {
	   		corners.remove(corner);
	   	} else {
	   		corners.add(corner);
	   		Collections.sort(corners);
	   	}
   	}
   	draw();
   }
   
   public int getValue() {
      return value;
   }
   
   public int[] getCandidates() {
   	int[] c = new int[candidates.size()];
   	for(int i=0; i<candidates.size(); i++) {
   		c[i] = candidates.get(i);
   	}
   	return c;
   }

   public int[] getCorners() {
   	int[] c = new int[corners.size()];
   	for(int i=0; i<corners.size(); i++) {
   		c[i] = corners.get(i);
   	}
   	return c;
   }

   public void applyHistory(HistoryObj history) {
   	this.value = history.getValue();
   	this.candidates.clear();
   	for(int i=0; i<history.getCandidates().length; i++) {
   		candidates.add(history.getCandidates()[i]);
   	}
   	this.corners.clear();
   	for(int i=0; i<history.getCorners().length; i++) {
   		corners.add(history.getCorners()[i]);
   	}
   	draw();
   }
   
   public void setLocked(boolean locked) {
      changed = this.locked != locked;
      this.locked = locked;
      draw();
   }
  
   public boolean isLocked() {
      return locked;
   }
   
   public void setConflicted(boolean conflicted) {
      changed = this.conflicted != conflicted;
      if(changed) {
         this.conflicted = conflicted;
	      conflictRectangle.setVisible(conflicted);
      }
   }
   
   public boolean isConflicted() {
      return conflicted;
   }
   
   public void setHighlighted(boolean highlighted) {
      changed = this.highlighted != highlighted;
      if(changed) {
	      this.highlighted = highlighted;
	      highlightRectangle.setVisible(highlighted);
      }
   }
   
   public boolean isHighlighted() {
		return highlighted;
	}
   
   private void draw() {
   	for(Text text : textObjects) {
   		canvas.remove(text);
   	}
   	textObjects.clear();
   	if(value != 0) {
   		Text textbox = new Text((int)(getX() + (getWidth() / 2)), (int)(getY() + (getHeight() / 2)), String.valueOf(value));
   		textbox.setFontSize(FONT_SIZE_NORMAL);
   		textbox.setFillColor(locked ? COLOUR_LOCKED : COLOUR_NORMAL);
   		textbox.setFontFamily(" Arial, Helvetica, sans-serif");
   		int textWidth = textbox.getTextWidth();
   		textbox.setX(textbox.getX() - (textWidth / 2));
   		textbox.setY(textbox.getY() + (FONT_SIZE_NORMAL / 2) - 2);
   		textObjects.add(textbox);
   	} else {
   		String candidateStr = "";
   		for(int candidate : candidates) {
   			candidateStr += candidate;
   		}
   		if(candidateStr.length() > 0) {
      		Text textbox = new Text((int)(getX() + (getWidth() / 2)), (int)(getY() + (getHeight() / 2)), String.valueOf(candidateStr));
      		textbox.setFontSize(FONT_SIZE_PENCIL);
      		textbox.setFillColor(COLOUR_PENCIL);
      		textbox.setStrokeColor(COLOUR_PENCIL);
      		textbox.setFontFamily("Arial, Helvetica, sans-serif");
      		int textWidth = textbox.getTextWidth();
      		textbox.setX(textbox.getX() - (textWidth / 2));
      		textbox.setY(textbox.getY() + (FONT_SIZE_PENCIL / 2));
      		textObjects.add(textbox);
   		}
   		
   		String cornerStr = "";
   		for(int corner : corners) {
   			cornerStr += corner;
   		}
   		if(cornerStr.length() > 0) {
      		Text textbox = new Text((int)(getX() + (getWidth() / 2)), (int)(getY() + (getHeight() / 2)), String.valueOf(cornerStr));
      		textbox.setFontSize(FONT_SIZE_PENCIL);
      		textbox.setFillColor(COLOUR_PENCIL);
      		textbox.setStrokeColor(COLOUR_PENCIL);
      		textbox.setFontFamily("Arial, Helvetica, sans-serif");
      		int textWidth = textbox.getTextWidth();
      		textbox.setX(textbox.getX() + (getWidth() / 2) - 4);
   			textbox.setX(textbox.getX() - textWidth);
   			textbox.setY(textbox.getY() - (getHeight() / 4));
//      		textbox.setY(textbox.getY() + (FONT_SIZE_PENCIL / 2));
      		textObjects.add(textbox);
   		}
   		
   	}
   	
   	for(Text textObject : textObjects) {
   		canvas.add(textObject);
   	}
   }
   
//   public void click() {
////   	if(!isLocked()) {
////	   	if(isHighlighted()) {
////	   		setHighlighted(false);
////	   	} else {
//	   		setHighlighted(true);
////	   	}
////   	}
//   }
   
}