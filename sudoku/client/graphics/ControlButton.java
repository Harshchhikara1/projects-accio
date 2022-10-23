package net.davekirkwood.sudoku.client.graphics;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import com.google.gwt.user.client.ui.TextBox;

public class ControlButton extends SButton {

	private String colour;
	private String textColour;
	private String highlightColour;
	
   private Rectangle backgroundRectangle;
   private Rectangle highlightRectangle;
	
   private DrawingArea canvas;
   
   private boolean highlighted = false;
   
   private String text;
   private int fontSize;
   private boolean corner;
   
   private Text textbox;
   
	/**
	 * Constructor for buttons that don't highlight.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public ControlButton(DrawingArea canvas, int x, int y, int width, int height, String colour, String textColour, String text, int fontSize, boolean corner) {
		super(x, y, width, height);
		initialise(canvas, colour, textColour, null, text, fontSize, corner);
	}
	
	/**
	 * Constructor for buttons that are highlighted when clicked.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param colour
	 * @param textColour
	 * @param highlightColour
	 */
	public ControlButton(DrawingArea canvas, int x, int y, int width, int height, String colour, String textColour, String highlightColour, String text, int fontSize, boolean corner) {
		super(x, y, width, height);
		initialise(canvas, colour, textColour, highlightColour, text, fontSize, corner);
	}
	
	private void initialise(DrawingArea canvas, String colour, String textColour, String highlightColour, String text, int fontSize, boolean corner) {
		
		this.canvas = canvas;
		this.colour = colour;
		this.textColour = textColour;
		this.text = text;
		this.fontSize = fontSize;
		this.corner = corner;
		
		backgroundRectangle = new Rectangle(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4);
		backgroundRectangle.setFillColor(colour);
		backgroundRectangle.setStrokeColor(colour);

		highlightRectangle = new Rectangle(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4);
		highlightRectangle.setFillColor(highlightColour);
		highlightRectangle.setStrokeColor(highlightColour);

	}
	
	public void initBackground() {
		canvas.add(backgroundRectangle);
		
		canvas.add(highlightRectangle);
		highlightRectangle.setVisible(false);
		
		textbox = new Text((int)(getX() + (getWidth() / 2)), (int)(getY() + (getHeight() / 2)), text);
		textbox.setFontSize(fontSize);
		textbox.setFillColor(textColour);
		textbox.setFontFamily("Impact, Arial, Helvetica, sans-serif");
		
		int textWidth = textbox.getTextWidth();
		textbox.setX(textbox.getX() - (textWidth / 2));
		textbox.setY(textbox.getY() + (fontSize / 2));
		
		if(corner) {
			textbox.setX(textbox.getX() + (getWidth() / 4));
			textbox.setY(textbox.getY() - (getHeight() / 4));
		}
		canvas.add(textbox);
	}
	
   public void setHighlighted(boolean highlighted) {
      boolean changed = this.highlighted != highlighted;
      if(changed) {
	      this.highlighted = highlighted;
	      highlightRectangle.setVisible(highlighted);
      }
   }
   
   public boolean isHighlighted() {
		return highlighted;
	}
   
   public void setVisible(boolean visible) {
   	super.setVisible(visible);
   	backgroundRectangle.setVisible(visible);
   	highlightRectangle.setVisible(visible && highlighted);
   	textbox.setVisible(visible);
   }
   
}
